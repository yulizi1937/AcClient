package thereisnospon.acclient.modules.discuss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

import thereisnospon.acclient.base.adapter.BaseSwipeAdapter;
import thereisnospon.acclient.base.fragment.NormalSwipeFragment;
import thereisnospon.acclient.data.DiscussItem;
import thereisnospon.acclient.ui.adapter.DiscussItemAdapter;

/**
 * Created by yzr on 16/9/9.
 */
public final class DiscussFragment extends NormalSwipeFragment<DiscussItem> implements DiscussContact.View{


	private DiscussContact.Presenter presenter;
	private String pid;

    public static DiscussFragment newInstance(){
        return new DiscussFragment();
    }

    public static DiscussFragment newInstance(@Nullable String problemid){
        DiscussFragment fragment=new DiscussFragment();
        fragment.pid=problemid;
        return fragment;
    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		pid = outState.getString("pid");
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState != null) {
			pid = savedInstanceState.getString("pid");
		}
	}

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter=new DiscussPresenter(this,pid);
        return normalView(inflater,container,savedInstanceState);
    }

    @Override
    public BaseSwipeAdapter<DiscussItem> createItemAdapter(List<DiscussItem> list) {
        return new DiscussItemAdapter(list);
    }

    @Override
    public void loadMore() {
        presenter.moreDiscuss();
    }

    @Override
    public void refresh() {
        presenter.refreshDiscuss();
    }

    @Override
    public void onRefreshDiscuss(List<DiscussItem> discussItems) {
        onRefreshData(discussItems);
    }

    @Override
    public void onMoreDiscuss(List<DiscussItem> discussItems) {
        onMoreData(discussItems);
    }

    @Override
    public void onFailure(String err) {
        Logger.d(err);
    }
}
