package thereisnospon.acclient.base.pullswipe;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;

/**
 * @author thereisnospon
 * 上拉加载下拉刷新View
 * 通过组合 SwipeRefreshLayout 与 RecycleView 实现
 * Created by yzr on 17/3/26.
 */

public class PullSwipeView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private AbstractSwipeAdapter mSwipeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private PullSwipeListener mListener;


    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE = 2;
    public static final int ACTION_IDLE = 0;
    private int mCurrentState = ACTION_IDLE;//RecyclerView.SCROLL_STATE_IDLE;
    private boolean isLoadMoreEnabled = true;
    private boolean isPullToRefreshEnabled = true;


    public PullSwipeView(@NonNull Context context) {
        this(context,null);
    }

    public PullSwipeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return  mSwipeRefreshLayout;
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public void setAdapter(AbstractSwipeAdapter adapter){
        mSwipeAdapter=adapter;
        mRecyclerView.setAdapter(mSwipeAdapter);
    }


    public void setAnimationAdapter (AnimationAdapter adapter){
        AbstractSwipeAdapter innerAdapter=(AbstractSwipeAdapter) adapter.getWrappedAdapter();
        mSwipeAdapter=innerAdapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        this.mLayoutManager = manager;
        mRecyclerView.setLayoutManager(manager);
        if(manager instanceof GridLayoutManager){
            GridLayoutManager gm=(GridLayoutManager)manager;
            gm.setSpanSizeLookup(new FooterSpanSizeLookup(mSwipeAdapter, mSwipeAdapter.getItemCount()));
        }
    }

    // 刷新，加载，和判断是否需要加载的 回调
    public interface  PullSwipeListener {
        public void  loadMore();
        public void  refresh();
        public boolean  hasMore();
    }

    public void setPllSwipeListener(PullSwipeListener listener){
        this.mListener=listener;
    }

    public void init(Context context, AttributeSet attrs){
        SwipeRefreshLayout swipeRefreshLayout=new SwipeRefreshLayout(context);
        RecyclerView recyclerView=new RecyclerView(context);
        initInner(swipeRefreshLayout,recyclerView);
    }

    //可以自己提供 RecycleView 和 SwipeRefreshLayout
    //使用前先把它们的 child, parent 清理
    public void init(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView){
        swipeRefreshLayout.removeAllViews();
        ViewGroup viewParent=(ViewGroup)recyclerView.getParent();
        if(viewParent!=null)
            viewParent.removeView(recyclerView);
        initInner(swipeRefreshLayout,recyclerView);
    }

    private void  initInner(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView){
        //view
        mSwipeRefreshLayout=refreshLayout;
        mRecyclerView=recyclerView;

        mSwipeRefreshLayout.addView(mRecyclerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mSwipeRefreshLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //SwipeRefresLayout 圆形进度条的颜色
        mSwipeRefreshLayout.setColorSchemeColors(
                Color.parseColor("#3F51B5"),
                Color.parseColor("#303F9F"),
                Color.parseColor("#FF4081"),
                Color.parseColor("#81C784"));
        // mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorGreen);

        mSwipeRefreshLayout.setOnRefreshListener(this);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //已经拉到了最底部并且还在上拉且有更多数据
                if (dy>0&&mCurrentState == ACTION_IDLE&&
                        isLoadMoreEnabled && checkIfNeedLoadMore()&&hasMore()) {
                    mCurrentState = ACTION_LOAD_MORE;
                    //通知 adapter 改变状态
                    mSwipeAdapter.onLoadMoreStateChange(true);
                    mSwipeRefreshLayout.setEnabled(false);
                    if(mListener!=null)
                        mListener.loadMore();
                }
            }
        });
    }

    //判断是否需要加载
    private boolean checkIfNeedLoadMore() {
        //找到当前可见的最后的 item 的位置
        int lastVisibleItemPosition = findLastVisibleItem(mLayoutManager);
        int totalCount = mLayoutManager.getItemCount();
        //如果滑到了最后的一个
        return totalCount - lastVisibleItemPosition < 2;
    }


    int findLastVisibleItem(RecyclerView.LayoutManager laoutManager){
        if(laoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager)laoutManager).findLastVisibleItemPosition();
        }else if(laoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager)laoutManager).findLastVisibleItemPosition();
        }else if(laoutManager instanceof StaggeredGridLayoutManager){
            int positions[]=new int[mLayoutManager.getChildCount()];
            positions=((StaggeredGridLayoutManager)laoutManager).
                    findFirstCompletelyVisibleItemPositions(positions);
            return positions[0];
        }
        throw new UnsupportedOperationException(
                "LayoutManager must in (" +
                        "LinearLayuotManager,GridLayoutManager," +
                        "StaggeredGridLayoutManager)");
    }


    private class FooterSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        private AbstractSwipeAdapter adapter;
        private int spanCount;

        public FooterSpanSizeLookup(AbstractSwipeAdapter adapter, int spanCount) {
            this.adapter = adapter;
            this.spanCount = spanCount;
        }
        @Override
        public int getSpanSize(int position) {
            if (adapter.isFooter(position)) {
                return spanCount;
            }
            return 1;
        }
    }


    public void addDecor(RecyclerView.ItemDecoration itemDecoration){
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }

    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public boolean hasMore(){
        return (mListener==null)?false:mListener.hasMore();
    }

    @Override
    final public void onRefresh() {
        if(mCurrentState==ACTION_IDLE){
            mCurrentState = ACTION_PULL_TO_REFRESH;
            enableLoadMore(true);
            if(mListener!=null)
                mListener.refresh();
        }
    }

    public void onRefreshCompleted() {
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case ACTION_LOAD_MORE:
                mSwipeAdapter.onLoadMoreStateChange(false);
                if (isPullToRefreshEnabled) {
                    mSwipeRefreshLayout.setEnabled(true);
                }
                break;
        }
        mCurrentState = ACTION_IDLE;
    }

    public void onMoreData(List data){
        mSwipeAdapter.onMoreData(data);
        onRefreshCompleted();
    }

    public void onRefreshData(List data){
        mSwipeAdapter.onRefreshData(data);
        onRefreshCompleted();
    }


}
