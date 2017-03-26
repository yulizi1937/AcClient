package thereisnospon.acclient.modules.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.base.fragment.BasicFragment;
import thereisnospon.acclient.databinding.FragmentShowcodeBinding;
import thereisnospon.acclient.event.Event;
import thereisnospon.acclient.event.EventCode;
import thereisnospon.acclient.modules.settings.Settings;
import thereisnospon.codeview.CodeViewTheme;

/**
 * Created by yzr on 16/8/2.
 * @author thereisnospon
 * 显示代码块的 Fragment
 * 可以指定本地 代码内容 String 格式
 * 可以指定网站上 id 代表的 代码内容
 */
public final class CodeFragment extends BasicFragment implements CodeContact.View {


	private CodeContact.Presenter presenter;

	private String id;
	private String code;
	private FragmentShowcodeBinding mBinding;


	public static final String SAVED_ID="saved_id";
	public static final String SAVED_CODE="saved_code";


	//代码高亮主题
	private static final CodeViewTheme[] THEMES = new CodeViewTheme[] {
			CodeViewTheme.ANDROIDSTUDIO,
			CodeViewTheme.ARDUINO_LIGHT,
			CodeViewTheme.DEFAULT,
			CodeViewTheme.GITHUB,
			CodeViewTheme.MONOKAI_SUBLIME,
			CodeViewTheme.OBSIDIAN,
			CodeViewTheme.SOLARIZED_DARK,
			CodeViewTheme.SOLARIZED_LIGHT,
	};


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
		outState.putString(SAVED_ID,id);
		outState.putSerializable(SAVED_CODE,code);
	}


	@Override
	public void onRestoreInstanceState(Bundle inState) {
		super.onRestoreInstanceState(inState);
		code = inState.getString(SAVED_CODE);
		id = inState.getString(SAVED_ID);
	}


	@Override
	public View createView(ViewGroup container, Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
				R.layout.fragment_showcode, container, false);
		presenter = new CodePresenter(this);
		EventBus.getDefault().register(this);
		return mBinding.getRoot();
	}


	@Override
	public int getLayoutRes() {
		return 0;
	}

	@Override
	public void initView(View view, Bundle savedInstanceState) {
		initCodeView(mBinding.getRoot());
		showCode();
	}

	@Override
	public void initData() {

	}

	void showCode() {
		if (code != null) {
			mBinding.codeView.showCode(code);
		} else if (id != null) {
			presenter.loadCode(id);
		}
	}

	//初始化 CodeView ，初始化 代码高亮
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
		this.code=code;
		mBinding.codeView.showCode(code);
		Logger.d(code);
	}


	@Override
	public void onFailure(String err) {
		Logger.d(err);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	//与 CodeActivity 通信，发送代码数据，拷贝到剪贴板
	@Subscribe
	public void onEvent(Event event){
		if(event.getEventCode()== EventCode.CLIP_CODE_REQUEST){
			String data=code==null?"":code;
			EventBus.getDefault().post(new Event<String>(data,EventCode.CLIP_CODE_RESPONSE));
		}
	}
}
