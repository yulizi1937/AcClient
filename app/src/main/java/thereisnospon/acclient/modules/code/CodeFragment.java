package thereisnospon.acclient.modules.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.FragmentShowcodeBinding;
import thereisnospon.acclient.modules.settings.Settings;
import thereisnospon.codeview.CodeViewTheme;

/**
 * Created by yzr on 16/8/2.
 */
public final class CodeFragment extends Fragment implements CodeContact.View {


	private CodeContact.Presenter presenter;


	private static final CodeViewTheme[] THEMES = new CodeViewTheme[] { CodeViewTheme.ANDROIDSTUDIO,
	                                                                   CodeViewTheme.ARDUINO_LIGHT,
	                                                                   CodeViewTheme.DEFAULT,
	                                                                   CodeViewTheme.GITHUB,
	                                                                   CodeViewTheme.MONOKAI_SUBLIME,
	                                                                   CodeViewTheme.OBSIDIAN,
	                                                                   CodeViewTheme.SOLARIZED_DARK,
	                                                                   CodeViewTheme.SOLARIZED_LIGHT, };

	private String id;
	private String code;
	private FragmentShowcodeBinding mBinding;

	public static CodeFragment newInstance(String id) {
		CodeFragment fragment = (CodeFragment) CodeFragment.instantiate(AppApplication.context, CodeFragment.class.getName() );
		fragment.id = id;
		return fragment;
	}

	public static CodeFragment newCodeInstance(String code) {
		CodeFragment fragment = (CodeFragment) CodeFragment.instantiate(AppApplication.context, CodeFragment.class.getName() );
		fragment.code = code;
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState == null) {
			return;
		}
		code = savedInstanceState.getString("code");
		id = savedInstanceState.getString("id");
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_showcode, container, false);
		initCodeView(mBinding.getRoot());
		presenter = new CodePresenter(this);
		showCode();
		return mBinding.getRoot();
	}

	void showCode() {
		if (code != null) {
			mBinding.codeView.showCode(code);
		} else if (id != null) {
			presenter.loadCode(id);
		}
	}

	void initCodeView(View view) {
		Settings settings = Settings.getInstance();
		int index = settings.getTheme();
		mBinding.codeView.setTheme(THEMES[index]);
		mBinding.codeView.fillColor();
		ViewGroup linearLayout = (ViewGroup) view.findViewById(R.id.code_back);
		linearLayout.setBackgroundColor(mBinding.codeView.getCodeBackgroundColor());
	}

	@Override
	public void onSuccess(String code) {
		mBinding.codeView.showCode(code);
		Logger.d(code);
	}

	@Override
	public void onFailure(String err) {
		Logger.d(err);
	}
}
