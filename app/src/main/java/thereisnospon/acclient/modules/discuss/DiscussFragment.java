package thereisnospon.acclient.modules.discuss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thereisnospon.acclient.base.adapter.BasePullAdapter;
import thereisnospon.acclient.base.fragment.BaseMvpSwipeFragment;
import thereisnospon.acclient.base.fragment.NormalPullFragment;
import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.base.util.SavedField;
import thereisnospon.acclient.data.DiscussItem;
import thereisnospon.acclient.ui.adapter.DiscussItemAdapter;

/**
 * @author thereisnospon
 * 显示讨论区列表内容的 Fragment
 * Created by yzr on 16/9/9.
 */
public final class DiscussFragment extends BaseMvpSwipeFragment<DiscussItem> implements DiscussContact.View{


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
    public BaseSwipeAdapter createAdapter(List list) {
        return new DiscussItemAdapter(list);
    }

}
