package thereisnospon.acclient.modules.personal.search;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.base.pullswipe.BasicSwpiePullFragment;
import thereisnospon.acclient.data.SearchPeopleItem;
import thereisnospon.acclient.ui.adapter.SearchPeopleAdapter;

/**
 * @author thereisnospon
 * 搜索用户 Fragment
 * Created by yzr on 16/6/16.
 */
public class SearchUserFragment extends BasicSwpiePullFragment
        implements  SearchUserContact.View{




    @Override
    public BaseSwipeAdapter createAdapter(List list) {
        return new SearchPeopleAdapter(list);
    }

    @Override
    public boolean hasMore() {
        return true;
    }



    private static final String TAG="SearchUserContact";
    private SearchUserContact.Presenter presenter;

    private String key;

    public static final String SAVED_KEY="saved_searchuse_key";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_KEY, key);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState == null) {
            return;
        }
        key = savedInstanceState.getString(SAVED_KEY);
    }

    public static SearchUserFragment newInstance(String key) {
        SearchUserFragment fragment = (SearchUserFragment) SearchUserFragment.instantiate(AppApplication.context, SearchUserFragment.class.getName());
        fragment.key = key;
        return fragment;
    }


    @Override
    public void refreshPeople(List<SearchPeopleItem> list) {
        onRefreshData(list);
    }

    @Override
    public void loadMorePeople(List<SearchPeopleItem> list) {
        onMoreData(list);
    }


    @Override
    public void start() {
        super.start();
        presenter=new SearchUserPresenter(this);
    }

    @Override
    public void onFailure(String err) {

    }

    @Override
    public void loadMore() {
        presenter.loadMorePeople(key);
    }

    @Override
    public void refresh() {
        presenter.searchPeople(key);
    }
}
