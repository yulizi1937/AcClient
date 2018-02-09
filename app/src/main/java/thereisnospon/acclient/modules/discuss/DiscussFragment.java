package thereisnospon.acclient.modules.discuss;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import thereisnospon.acclient.base.fragment.BaseMvpPullFragment;

import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BasePullAdapter;
import thereisnospon.acclient.data.DiscussItem;
import thereisnospon.acclient.ui.adapter.DiscussItemAdapter;

/**
 * @author thereisnospon
 * 显示讨论区列表内容的 Fragment
 * Created by yzr on 16/9/9.
 */
public final class DiscussFragment extends BaseMvpPullFragment<DiscussItem> implements DiscussContact.View{


	private DiscussContact.Presenter presenter;
	private String pid;

    public static final String SAVED_ID="discuss_saved_id";

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
        outState.putCharSequence(SAVED_ID,pid);

	}


    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        pid = inState.getString(SAVED_ID);
    }



    @Override
    public MvpPullPresenter createPresenter() {
        presenter=new DiscussPresenter(this,pid);
        return presenter;
    }

    @Override
    public BasePullAdapter createAdapter(List list) {
        return new DiscussItemAdapter(list);
    }

}
