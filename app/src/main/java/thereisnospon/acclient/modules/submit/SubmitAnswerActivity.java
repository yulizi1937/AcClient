package thereisnospon.acclient.modules.submit;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
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
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.event.Msg;
import thereisnospon.acclient.modules.code.CodeActivity;
import thereisnospon.acclient.modules.settings.Settings;
import thereisnospon.acclient.modules.submit.status.SubmitQuery;
import thereisnospon.acclient.modules.submit.status.SubmitStatusActivity;
import thereisnospon.acclient.utils.SpUtil;
import thereisnospon.acclient.utils.StringCall;

public final class SubmitAnswerActivity extends AppBarActivity implements View.OnClickListener {
	private String problemId = "1000";
	private BottomSheetBehavior behavior;
	private int compilerChoose = 0;
	private NavActivitySubmitAnswerBinding mBinding;
	private void submit() {
		Msg.t("submit");
		$submit();
	}

	private void review() {
		Msg.t("review");
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
		final String code = mBinding.submitCode.getText().toString();
		final String lan = compilerChoose + "";
		SubmitUtil.submmit(problemId, lan, code, new StringCall() {
			@Override
			public void success(String nickName) {
				submitSuccess(nickName);
			}

			@Override
			public void failure(String msg) {
				Msg.t("提交失败");
			}
		});
	}

	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_activity_submit_answer, contentLayout, false);
		contentLayout.addView(mBinding.getRoot());
		initView();
		resolveId();
	}

	private void resolveId() {
		Intent intent = getIntent();
		problemId = intent.getStringExtra(Arg.SBUMMIT_PROBLEM_ID);
		setTitle(problemId);
	}

	private void initView() {
		initBottomSheet();
		//submmitcode.setText(SubmmitUtil.CODE );
		initSpinner();
		setTitle(problemId);
		mBinding.setClickListener(this);
	}



	private void initBottomSheet() {
		behavior = BottomSheetBehavior.from(mBinding.bottomsheet);
		behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		mBinding.submitCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.submit_code:
				behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				break;
			case R.id.submmit_fab:
				if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
					behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				} else {
					behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
				}
				break;
			case R.id.submmit_review:
				behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				review();
				break;
			case R.id.submmit_submmit:
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
