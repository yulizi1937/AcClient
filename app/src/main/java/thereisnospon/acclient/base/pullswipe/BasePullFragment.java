package thereisnospon.acclient.base.pullswipe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import thereisnospon.acclient.R;
import thereisnospon.acclient.base.fragment.BaseFragment;

/**
 * @author thereisnospon
 *  需要有上拉加载下拉刷新功能的 Fragment
 * Created by yzr on 17/3/26.
 */

public abstract class BasePullFragment extends BaseFragment implements PullSwipeView.PullSwipeListener{

    PullSwipeView pullSwipeView;
    private List list;
    private BasePullAdapter adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.lib_fragment_basic_swipepull;
    }

    //子类提供 Adapter
    public abstract BasePullAdapter createAdapter(List list);


    @Override
    public void initView(View view, Bundle savedInstanceState) {
        pullSwipeView=(PullSwipeView)view.findViewById(R.id.pullswipe);
        list=new ArrayList();
        adapter=createAdapter(list);
        pullSwipeView.setAnimationAdapter(new ScaleInAnimationAdapter(adapter));
        pullSwipeView.setPllSwipeListener(this);
        pullSwipeView.setLayoutManager(createLayoutManager());
        pullSwipeView.onRefresh();
    }

    @Override
    public void initData() {

    }



    //默认线性布局
    public RecyclerView.LayoutManager createLayoutManager(){
        return new LinearLayoutManager(getActivity());
    }

    public  void  onMoreData(List list){
        pullSwipeView.onMoreData(list);
    }

    public void onRefreshData(List list){
        pullSwipeView.onRefreshData(list);
    }


}
