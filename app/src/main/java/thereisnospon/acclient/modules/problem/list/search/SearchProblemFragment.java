package thereisnospon.acclient.modules.problem.list.search;

import android.os.Bundle;

import java.util.List;



import thereisnospon.acclient.base.pullswipe.BasePullAdapter;
import thereisnospon.acclient.base.pullswipe.BasePullFragment;
import thereisnospon.acclient.data.SearchProblem;
import thereisnospon.acclient.ui.adapter.SearchProblemAdapter;

/**
 * @author threisnospon
 * 搜索题目 Fragment
 * Created by yzr on 16/6/10.
 */
public class SearchProblemFragment extends BasePullFragment
        implements SearchProblemContact.View{

    SearchProblemContact.Presenter presenter;

    private static String TAG="SSEARCH";

    String query;

    public static SearchProblemFragment newInstance(String query){
        SearchProblemFragment fragment=new SearchProblemFragment();
        fragment.query=query;
        return fragment;
    }

    public static final String SAVE_QUERY="save_query_";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_QUERY,query);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        query=inState.getString(SAVE_QUERY);
    }

    @Override
    public void start() {
        super.start();
        presenter=new SearchProblemPresenter(this);
    }

    @Override
    public void loadSccess(List<SearchProblem> list) {

        onMoreData(list);
    }

    @Override
    public void refreshSuccess(List<SearchProblem> list) {
       onRefreshData(list);
    }

    @Override
    public void onFailure(String errMsg) {

    }


    @Override
    public void loadMore() {
        presenter.loadMoreQuery(query);
    }

    @Override
    public void refresh() {
        presenter.queryProblem(query);
    }


    @Override
    public BasePullAdapter createAdapter(List list) {
        return new SearchProblemAdapter(list);
    }

    @Override
    public boolean hasMore() {
        return true;
    }
}
