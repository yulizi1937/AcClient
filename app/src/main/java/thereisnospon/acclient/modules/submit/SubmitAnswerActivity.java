package thereisnospon.acclient.modules.submit;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.data.SubmmitStatus;
import thereisnospon.acclient.databinding.NavActivitySubmitAnswerBinding;
import thereisnospon.acclient.databinding.NavActivitySubmitAnswerBottomSheetBinding;
import thereisnospon.acclient.databinding.NavActivitySubmitAnswerSubmitButtonBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.code.CodeActivity;
import thereisnospon.acclient.modules.settings.Settings;
import thereisnospon.acclient.modules.submit.status.SubmitQuery;
import thereisnospon.acclient.modules.submit.status.SubmitStatusActivity;
import thereisnospon.acclient.utils.SpUtil;
import thereisnospon.acclient.utils.StringCall;

public final class SubmitAnswerActivity extends AppBarActivity implements View.OnClickListener,
                                                                          NestedScrollView.OnScrollChangeListener,
                                                                          View.OnFocusChangeListener {

	public static void showInstance(@NonNull  Activity cxt, int id, @NonNull  ActivityOptionsCompat options) {
		Intent intent = new Intent(cxt, SubmitAnswerActivity.class);
		intent.putExtra(Arg.SBUMIT_PROBLEM_ID, String.valueOf(id));
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, options.toBundle());
	}


	private static final @LayoutRes int LAYOUT = R.layout.nav_activity_submit_answer;
	private static final @LayoutRes int LAYOUT_BOTTOM_SHEET = R.layout.nav_activity_submit_answer_bottom_sheet;
	private static final @LayoutRes int LAYOUT_SUBMIT_BUTTON = R.layout.nav_activity_submit_answer_submit_button;
	private final BottomSheetBehavior behavior = new BottomSheetBehavior();
	private final BottomSheetBehavior.BottomSheetCallback behaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
		@Override
		public void onStateChanged(@NonNull View bottomSheet, int newState) {
			switch (newState) {
				case BottomSheetBehavior.STATE_EXPANDED:
					mSubmitButtonBinding.submitFab.hide();
					break;
				case BottomSheetBehavior.STATE_COLLAPSED:
					mSubmitButtonBinding.submitFab.show();
					break;
			}
		}

		@Override
		public void onSlide(@NonNull View bottomSheet, float slideOffset) {

		}

	};
	private String problemId = "1000";
	private int compilerChoose = 0;
	private NavActivitySubmitAnswerBinding mBinding;
	private NavActivitySubmitAnswerBottomSheetBinding mBottomSheetBinding;
	private NavActivitySubmitAnswerSubmitButtonBinding mSubmitButtonBinding;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("problemId", problemId);
		outState.putInt("compilerChoose", compilerChoose);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState == null) {
			return;
		}
		problemId = savedInstanceState.getString("problemId");
		compilerChoose = savedInstanceState.getInt("compilerChoose");
	}

	private void submit() {

		$submit();
	}

	private void review() {

		Intent intent = new Intent(this, CodeActivity.class);
		String code = mBinding.submitCode.getText()
		                                 .toString() + "";
		intent.putExtra(Arg.SHOWCODE_CODE, code);
		startActivity(intent);
	}

	private void submitSuccess(String html) {
		List<SubmmitStatus> list = SubmmitStatus.Builder.parse(html);
		Toast.makeText(this, "submmit success" + list.size(), Toast.LENGTH_SHORT)
		     .show();
		Intent intent = new Intent(this, SubmitStatusActivity.class);
		intent.putExtra(Arg.SUBMMIT_QUERY_USER,
		                SpUtil.getInstance()
		                      .getString(SpUtil.NAME));
		intent.putExtra(Arg.SUBMMIT_QUERY_STATUS, SubmitQuery.Status.ALL.getValue());
		startActivity(intent);
	}

	private void $submit() {
		final String code = mBinding.submitCode.getText()
		                                       .toString();
		final String lan = compilerChoose + "";
		SubmitUtil.submmit(problemId, lan, code, new StringCall() {
			@Override
			public void success(String nickName) {
				submitSuccess(nickName);
			}

			@Override
			public void failure(String msg) {

			}
		});
	}

	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		mBinding = DataBindingUtil.inflate(getLayoutInflater(), LAYOUT, contentLayout, false);
		mBottomSheetBinding = DataBindingUtil.inflate(getLayoutInflater(), LAYOUT_BOTTOM_SHEET, contentLayout, false);
		mSubmitButtonBinding = DataBindingUtil.inflate(getLayoutInflater(), LAYOUT_SUBMIT_BUTTON, contentLayout, false);

		contentLayout.addView(mBinding.getRoot());
		addViewToCoordinatorLayout(mBottomSheetBinding.getRoot());
		addViewToCoordinatorLayout(mSubmitButtonBinding.getRoot());

		initView();
		resolveId();

		setActivityBackgroundColor(R.color.colorGrey);
	}

	private void resolveId() {
		Intent intent = getIntent();
		problemId = intent.getStringExtra(Arg.SBUMIT_PROBLEM_ID);
		setTitle(problemId);
	}

	private void initView() {
		initSpinner();
		setTitle(problemId);

		mBinding.setClickListener(this);
		mBottomSheetBinding.setClickListener(this);
		mSubmitButtonBinding.setClickListener(this);

		CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mBottomSheetBinding.getRoot()
		                                                                                            .getLayoutParams();
		params.setBehavior(behavior);
		behavior.setBottomSheetCallback(behaviorCallback);
		params = (CoordinatorLayout.LayoutParams) mSubmitButtonBinding.submitFab.getLayoutParams();
		params.gravity = GravityCompat.END | Gravity.BOTTOM ;

		mBinding.submitCode.setOnFocusChangeListener(this);
		//mBinding.scrollView.setOnScrollChangeListener(this);
	}

	@Override
	public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
		if (scrollY > oldScrollY) {
			if (mSubmitButtonBinding.submitFab.isShown()) {
				mSubmitButtonBinding.submitFab.hide();
			}
		} else {
			if (!mSubmitButtonBinding.submitFab.isShown()) {
				if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
					return;
				}
				mSubmitButtonBinding.submitFab.show();
			}
		}
	}


	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.submit_code:
				behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				break;
			case R.id.submit_fab:
				if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
					behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				} else {
					behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
				}
				break;
			case R.id.submit_review:
				behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				review();
				break;
			case R.id.do_submit:
				behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				submit();
				break;
		}
	}


	private void initSpinner() {
		int defualtComplier = Settings.getInstance()
		                              .getCompiler();

		String[] mItems = getResources().getStringArray(R.array.code_languages);

		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mBinding.spinner.setAdapter(adapter);
		mBinding.spinner.setSelection(defualtComplier);
		mBinding.spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				onComplierChange(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}


	private void onComplierChange(int position) {
		compilerChoose = position;
		String[] codeLanguages = getResources().getStringArray(R.array.code_languages);
		Toast.makeText(this, codeLanguages[position], Toast.LENGTH_SHORT)
		     .show();
	}



}
