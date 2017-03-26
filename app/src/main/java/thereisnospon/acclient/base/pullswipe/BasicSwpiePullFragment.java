package thereisnospon.acclient.base.pullswipe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.PUT;
import thereisnospon.acclient.R;
import thereisnospon.acclient.base.fragment.BasicFragment;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.base.pullswipe.PullSwipeView;

/**
 * Created by yzr on 17/3/26.
 */

public abstract class BasicSwpiePullFragment extends BasicFragment implements PullSwipeView.PullSwipeListener{

    PullSwipeView pullSwipeView;
    private List list;
    private BaseSwipeAdapter adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.lib_fragment_basic_swipepull;
    }




    @Override
    public void initView(View view, Bundle savedInstanceState) {
        pullSwipeView=(PullSwipeView)view.findViewById(R.id.pullswipe);
        list=new ArrayList();
        adapter=createAdapter(list);
        pullSwipeView.setAdapter(adapter);
        pullSwipeView.setPllSwipeListener(this);
        pullSwipeView.setLayoutManager(createLayoutManager());
        pullSwipeView.onRefresh();
    }

    @Override
    public void initData() {

    }

    public abstract BaseSwipeAdapter createAdapter(List list);

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
