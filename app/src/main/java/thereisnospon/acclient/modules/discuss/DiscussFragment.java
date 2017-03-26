package thereisnospon.acclient.modules.discuss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

import thereisnospon.acclient.base.adapter.BasePullAdapter;
import thereisnospon.acclient.base.fragment.BaseMvpSwipeFragment;
import thereisnospon.acclient.base.fragment.NormalPullFragment;
import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.data.DiscussItem;
import thereisnospon.acclient.ui.adapter.DiscussItemAdapter;

/**
 * Created by yzr on 16/9/9.
 */
public final class DiscussFragment extends BaseMvpSwipeFragment<DiscussItem> implements DiscussContact.View{

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

    @Override
    public MvpPullPresenter createPresenter() {
        presenter=new DiscussPresenter(this,pid);
        return presenter;
    }

    @Override
    public BaseSwipeAdapter createAdapter(List list) {
        return new DiscussItemAdapter(list);
    }
}
