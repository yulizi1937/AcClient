package thereisnospon.acclient.modules.user_detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import thereisnospon.acclient.R;
import thereisnospon.acclient.data.UserInfo;
import thereisnospon.acclient.databinding.FragmentUserDetailWrapperBinding;
import thereisnospon.acclient.databinding.FragmentUserDetailWrapperBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.submmit_status.SubmmitQuery;
import thereisnospon.acclient.modules.submmit_status.SubmmitStatusActivity;
import thereisnospon.acclient.ui.adapter.ProblemNodeAdapter;

/**
 * Created by yzr on 16/6/18.
 */
public final class UserDetailFragment extends Fragment implements UserDetailContact.View {
	private static final String TAG = "UserDetailFragment";
	private UserDetailContact.Presenter presenter;
	private thereisnospon.acclient.databinding.FragmentUserDetailWrapperBinding mBinding;


	private String id;

	public static UserDetailFragment newInstance(String id) {
		UserDetailFragment fragment = new UserDetailFragment();
		fragment.id = id;
		return fragment;
	}

	private void initView() {

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail_wrapper, container, false);
		presenter = new UserDetailPresenter(this);
		presenter.loadUser(id);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mBinding.fragmentUserDetail.userCardSubmission.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cl();
			}
		});
		mBinding.fragmentUserDetail.userCardAc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ac();
			}
		});
	}

	@Override
	public void onSuccess(UserInfo userInfo) {
		mBinding.fragmentUserDetail.userInfoNickname.setText(userInfo.getName());
		mBinding.fragmentUserDetail.userDetail.setText(userInfo.getDes());
		mBinding.fragmentUserDetail.userLocal.setText(userInfo.getSchool());
		mBinding.fragmentUserDetail.userRank.setText(userInfo.getRank() + "");
		mBinding.fragmentUserDetail.userSubmmision.setText(userInfo.getSubmission() + "");
		mBinding.fragmentUserDetail.userSubmmit.setText(userInfo.getSubmitted() + "");
		mBinding.fragmentUserDetail.userTime.setText(userInfo.getRegsiterDate() + "");
		mBinding.fragmentUserDetail.userSolved.setText(userInfo.getSolved() + "");
		mBinding.fragmentUserDetail.userAc.setText(userInfo.getAccepted() + "");
		ProblemNodeAdapter adapter = new ProblemNodeAdapter(id, userInfo.getAcceptedNodeList(), userInfo.getUnacceptedNodeList());
		mBinding.usernodeRecycle.setAdapter(adapter);
		mBinding.usernodeRecycle.setItemAnimator(new ScaleInAnimator());
		mBinding.usernodeRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	@Override
	public void onError(String err) {
		Log.d(TAG, "onError: " + err);
	}


	private void cl() {
		Intent intent = new Intent(getActivity(), SubmmitStatusActivity.class);
		intent.putExtra(Arg.SUBMMIT_QUERY_USER, id);
		intent.putExtra(Arg.SUBMMIT_QUERY_STATUS, SubmmitQuery.Status.ALL.getValue());
		startActivity(intent);
	}


	private void ac() {
		Intent intent = new Intent(getActivity(), SubmmitStatusActivity.class);
		intent.putExtra(Arg.SUBMMIT_QUERY_USER, id);
		intent.putExtra(Arg.SUBMMIT_QUERY_STATUS, SubmmitQuery.Status.AC.getValue());
		startActivity(intent);
	}
}
