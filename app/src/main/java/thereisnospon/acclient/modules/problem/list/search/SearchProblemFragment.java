package thereisnospon.acclient.modules.problem.list.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


import thereisnospon.acclient.base.adapter.BasePullAdapter;
import thereisnospon.acclient.base.fragment.NormalPullFragment;
import thereisnospon.acclient.data.SearchProblem;
import thereisnospon.acclient.ui.adapter.SearchProblemAdapter;

/**
 * Created by yzr on 16/6/10.
 */
public class SearchProblemFragment extends NormalPullFragment<SearchProblem>
        implements SearchProblemContact.View{

    SearchProblemContact.Presenter presenter;

    private static String TAG="SSEARCH";

    String query;

    public static SearchProblemFragment newInstance(String query){
        SearchProblemFragment fragment=new SearchProblemFragment();
        fragment.query=query;
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=normalView(inflater,container,savedInstanceState);
        presenter=new SearchProblemPresenter(this);
        return view;
    }


    @Override
    public void loadSccess(List<SearchProblem> list) {
       notifyMoreData(list);
    }

    @Override
    public void refreshSuccess(List<SearchProblem> list) {
       notifyRefreshData(list);
    }

    @Override
    public void onFailure(String errMsg) {
        enableLoadMore(false);
    }

    @Override
    public BasePullAdapter<SearchProblem> createItemAdapter(List<SearchProblem> list) {
        return new SearchProblemAdapter(list);
    }

    @Override
    public void loadMore() {
        presenter.loadMoreQuery(query);
    }

    @Override
    public void refresh() {
        presenter.queryProblem(query);
    }
}
