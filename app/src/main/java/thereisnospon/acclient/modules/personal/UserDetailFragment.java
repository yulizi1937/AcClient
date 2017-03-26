package thereisnospon.acclient.modules.personal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.base.fragment.BasicFragment;
import thereisnospon.acclient.data.UserInfo;



import thereisnospon.acclient.databinding.UserDetailBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.submit.status.SubmitQuery;
import thereisnospon.acclient.modules.submit.status.SubmitStatusActivity;
import thereisnospon.acclient.ui.adapter.ProblemNodeAdapter;

/**
 * @author thereisnospon
 * 用户详细信息 Fragment
 * Created by yzr on 16/6/18.
 */
public final class UserDetailFragment extends BasicFragment implements UserDetailContact.View {
	private static final String TAG = "UserDetailFragment";
	private UserDetailContact.Presenter presenter;


	private UserDetailBinding mBinding;
	private String id;

	public static UserDetailFragment newInstance(String id) {
		UserDetailFragment fragment = (UserDetailFragment) UserDetailFragment.instantiate(AppApplication.context, UserDetailFragment.class.getName());
		fragment.id = id;
		return fragment;
	}


	private static final String SAVE_ID="save_user_deatil_id";

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVE_ID, id);
	}


	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			id = savedInstanceState.getString(SAVE_ID);
		}
	}

	@Override
	public int getLayoutRes() {
		return 0;
	}

	@Override
	public View createView(ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(container.getContext()).
				inflate( R.layout.fragment_user_detail_wrapper, container, false);
		mBinding = DataBindingUtil.bind(view);
		return view;
	}

	@Override
	public void initView(View view, Bundle savedInstanceState) {
		presenter = new UserDetailPresenter(this);
		presenter.loadUser(id);
		mBinding.fragmentUserDetail.userCardSubmission.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goToUserSubmmitInfoActivity();
			}
		});
		mBinding.fragmentUserDetail.userCardAc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goToUserSummmitACActivity();
			}
		});
	}

	@Override
	public void initData() {

	}



	@Override
	public void onSuccess(UserInfo userInfo) {
		mBinding.fragmentUserDetail.userInfoNickname.setText(userInfo.getName());
		mBinding.fragmentUserDetail.userDetail.setText(userInfo.getDes());
		mBinding.fragmentUserDetail.userLocal.setText(userInfo.getSchool());
		mBinding.fragmentUserDetail.userRank.setText(String.valueOf(userInfo.getRank()));
		mBinding.fragmentUserDetail.userSubmmision.setText(String.valueOf(userInfo.getSubmission()));
		mBinding.fragmentUserDetail.userSubmmit.setText(String.valueOf(userInfo.getSubmitted()));
		mBinding.fragmentUserDetail.userTime.setText(String.valueOf(userInfo.getRegsiterDate()));
		mBinding.fragmentUserDetail.userSolved.setText(String.valueOf(userInfo.getSolved()));
		mBinding.fragmentUserDetail.userAc.setText(String.valueOf(userInfo.getAccepted()));
		ProblemNodeAdapter adapter = new ProblemNodeAdapter(id, userInfo.getAcceptedNodeList(), userInfo.getUnacceptedNodeList());
		mBinding.usernodeRecycle.setAdapter(adapter);
		mBinding.usernodeRecycle.setItemAnimator(new ScaleInAnimator());
		mBinding.usernodeRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	@Override
	public void onError(String err) {
		Log.d(TAG, "onError: " + err);
	}

	private void goToUserSubmmitInfoActivity() {
		Intent intent = new Intent(getActivity(), SubmitStatusActivity.class);
		intent.putExtra(Arg.SUBMMIT_QUERY_USER, id);
		intent.putExtra(Arg.SUBMMIT_QUERY_STATUS, SubmitQuery.Status.ALL.getValue());
		startActivity(intent);
	}


	private void goToUserSummmitACActivity() {
		Intent intent = new Intent(getActivity(), SubmitStatusActivity.class);
		intent.putExtra(Arg.SUBMMIT_QUERY_USER, id);
		intent.putExtra(Arg.SUBMMIT_QUERY_STATUS, SubmitQuery.Status.AC.getValue());
		startActivity(intent);
	}
}
