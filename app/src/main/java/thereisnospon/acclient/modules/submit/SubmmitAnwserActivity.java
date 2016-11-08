package thereisnospon.acclient.modules.submit;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.BaseActivity;
import thereisnospon.acclient.data.SubmmitStatus;
import thereisnospon.acclient.databinding.ActivitySubmmitAnwserBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.event.Msg;
import thereisnospon.acclient.modules.settings.Settings;
import thereisnospon.acclient.modules.show_code.CodeActivity;
import thereisnospon.acclient.modules.submmit_status.SubmmitQuery;
import thereisnospon.acclient.modules.submmit_status.SubmmitStatusActivity;
import thereisnospon.acclient.utils.SpUtil;
import thereisnospon.acclient.utils.StringCall;

public final class SubmmitAnwserActivity extends BaseActivity implements View.OnClickListener {
	private String problemId = "1000";
	private BottomSheetBehavior behavior;
	private int compilerChoose = 0;
	private ActivitySubmmitAnwserBinding mBinding;

	private void submmit() {
		Msg.t("submmit");
		$submmit();
	}

	private void review() {
		Msg.t("review");
		Intent intent = new Intent(this, CodeActivity.class);
		String code = mBinding.navActivitySubmmitAnwser.submmitCode.getText()
		                                                           .toString() + "";
		intent.putExtra(Arg.SHOWCODE_CODE, code);
		startActivity(intent);
	}

	private void submmitSuccess(String html) {
		List<SubmmitStatus> list = SubmmitStatus.Builder.parse(html);
		Toast.makeText(this, "submmit success" + list.size(), Toast.LENGTH_SHORT)
		     .show();
		Intent intent = new Intent(this, SubmmitStatusActivity.class);
		intent.putExtra(Arg.SUBMMIT_QUERY_USER,
		                SpUtil.getInstance()
		                      .getString(SpUtil.NAME));
		intent.putExtra(Arg.SUBMMIT_QUERY_STATUS, SubmmitQuery.Status.ALL.getValue());
		startActivity(intent);
	}

	private void $submmit() {
		final String code = mBinding.navActivitySubmmitAnwser.submmitCode.getText()
		                                                                 .toString();
		final String lan = compilerChoose + "";
		SubmmitUtil.submmit(problemId, lan, code, new StringCall() {
			@Override
			public void success(String nickName) {
				submmitSuccess(nickName);
			}

			@Override
			public void failure(String msg) {
				Msg.t("提交失败");
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_submmit_anwser);
		initView();
		resolveId();
	}

	private void resolveId() {
		Intent intent = getIntent();
		problemId = intent.getStringExtra(Arg.SBUMMIT_PROBLEM_ID);
		setTitle(problemId);
	}

	private void initView() {
		setSupportActionBar(mBinding.navActivitySubmmitAnwser.toolbar);
		initBottmsheet();
		//submmitcode.setText(SubmmitUtil.CODE );
		initSpinner();
		setTitle(problemId);
	}



	private void initBottmsheet() {
		behavior = BottomSheetBehavior.from(mBinding.navActivitySubmmitAnwser.bottomsheet);
		behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		mBinding.navActivitySubmmitAnwser.submmitCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				}
			}
		});
		mBinding.navActivitySubmmitAnwser.submmitFab.setOnClickListener(this);
		mBinding.navActivitySubmmitAnwser.submmitReview.setOnClickListener(this);
		mBinding.navActivitySubmmitAnwser.submmitSubmmit.setOnClickListener(this);
		mBinding.navActivitySubmmitAnwser.submmitCode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
				submmit();
				break;
		}
	}


	private void initSpinner() {

		int defualtComplier = Settings.getInstance()
		                              .getCompiler();

		String[] mItems = getResources().getStringArray(R.array.code_languages);

		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mBinding.navActivitySubmmitAnwser.spinner.setAdapter(adapter);
		mBinding.navActivitySubmmitAnwser.spinner.setSelection(defualtComplier);
		mBinding.navActivitySubmmitAnwser.spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_submmit_code:
				//Msg.t("menu");
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.submmit_menu, menu);
		return true;
	}

}
