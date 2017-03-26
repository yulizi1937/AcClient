package thereisnospon.acclient.modules.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.ActivityHelloSceneLoginBinding;
import thereisnospon.acclient.databinding.LoginActivityBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.problem.list.HdojActivity;
import thereisnospon.acclient.utils.SpUtil;
import thereisnospon.acclient.widget.TransitionListenerAdapter;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.os.Bundle.EMPTY;
import static thereisnospon.acclient.modules.login.ErrorConstants.NO_EMPTY_PASSWORD;
import static thereisnospon.acclient.modules.login.ErrorConstants.NO_EMPTY_USERNAME;



/**
 * @author thereisnospon,xzhao
 * 登陆 Activity
 */
public final class LoginActivity extends BaseLoginRegisterActivity {
	private static final int LAYOUT = R.layout.activity_hello;
	private static final int DURATION = 500;
	private Scene login;

	private String id;
	private String pass;
	private String nickname;
	private volatile boolean rememberPas = false;
	private volatile boolean isShowLoginUI = false;

	private LoginActivityBinding mBinding;
	private ActivityHelloSceneLoginBinding mLoginBinding;


	public static void showInstance(Activity cxt, boolean relogin) {
		Intent intent = new Intent(cxt, LoginActivity.class);
		intent.putExtra(Arg.RE_LOGIN, relogin);
		intent.setFlags(FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, EMPTY);
	}


	private static final String SAVE_ID="login_saved_id";
	private static final String SAVE_PASS="login_saved_pass";
	private static final String SAVE_NICK="longin_saved_nick";
	private static final String SAVE_REMEMBER="login_saved_remember";
	private static final String SAVE_SHOWLOGIN="login_saved_showlogin";


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVE_ID, id);
		outState.putString(SAVE_PASS, pass);
		outState.putString(SAVE_NICK, nickname);
		outState.putBoolean(SAVE_REMEMBER, rememberPas);
		outState.putBoolean(SAVE_SHOWLOGIN, isShowLoginUI);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState == null) {
			return;
		}
		id = savedInstanceState.getString(SAVE_ID);
		pass = savedInstanceState.getString(SAVE_PASS);
		nickname = savedInstanceState.getString(SAVE_NICK);
		rememberPas = savedInstanceState.getBoolean(SAVE_PASS);
		isShowLoginUI = savedInstanceState.getBoolean(SAVE_SHOWLOGIN);
	}


	private void initScene() {
		Scene.getSceneForLayout(mBinding.sceneRoot, R.layout.activity_hello_scene_index, this);
		mLoginBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hello_scene_login, mBinding.sceneRoot, false);
		mLoginBinding.setDoneListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					mLoginBinding.loginButton.performClick();
					return true;
				}
				return false;
			}
		});
		login = new Scene(mBinding.sceneRoot, mLoginBinding.getRoot());
	}


	private void initUi() {
		mLoginBinding.registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RegisterActivity.showInstance(LoginActivity.this);
			}
		});
		mLoginBinding.loginRemember.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLoginBinding.loginRemember.setChecked(!mLoginBinding.loginRemember.isChecked());

				rememberPas = mLoginBinding.loginRemember.isChecked();
				if (rememberPas) {
					Snackbar.make(mBinding.sceneRoot, R.string.hello_remember_password, Snackbar.LENGTH_SHORT)
					        .setAction(R.string.cancel, new View.OnClickListener() {
						        @Override
						        public void onClick(View v) {
							        mLoginBinding.loginRemember.setChecked(false);
						        }
					        })
					        .show();
				}
			}
		});
		mLoginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.id = mLoginBinding.loginId.getText()
				                                             .toString();
				LoginActivity.this.pass = mLoginBinding.loginPass.getText()
				                                                 .toString();
				presenter.login(id, pass);
			}
		});
	}


	private void tryLogin() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//是否已经记住密码
				if (checkRemembered()) {
					presenter.login(id, pass);
				} else {
					//显示登陆的ui
					showLoginUI();
				}
			}
		}, 1000);
	}

	private void reLogin() {
		showLoginUI();
	}


	//查看是否已经记住密码
	private boolean checkRemembered() {
		SpUtil sp = SpUtil.getInstance();
		id = sp.getString(SpUtil.NAME);
		pass = sp.getString(SpUtil.PASS);
		nickname = sp.getString(SpUtil.NICKNAME);
		rememberPas = !TextUtils.isEmpty(id) && !TextUtils.isEmpty(pass) ;
		return rememberPas;
	}


	//显示登陆界面的Ui
	private void showLoginUI() {
		isShowLoginUI = true;
		TransitionSet transitionSet = new TransitionSet();
		transitionSet.addTransition(new AutoTransition());
		transitionSet.setDuration(DURATION);
		transitionSet.addListener(new TransitionListenerAdapter() {
			@Override
			public void onTransitionEnd(Transition transition) {
				super.onTransitionEnd(transition);
				initUi();
			}
		});
		TransitionManager.go(login, transitionSet);
	}


	@Override
	public void onLoginSuccess(String userName) {
		mLoadToast.success();
		SpUtil sp = SpUtil.getInstance();
		nickname = userName;
		if (rememberPas) {
			sp.putString(SpUtil.NAME, id);
			sp.putString(SpUtil.PASS, pass);
			sp.putString(SpUtil.NICKNAME, nickname);
		} else {
			sp.clear();
			sp.putString(SpUtil.NAME, id);
			sp.putString(SpUtil.NICKNAME, nickname);
		}
		HdojActivity.showInstance(this);
		ActivityCompat.finishAfterTransition(this);
	}

	@Override
	public void onLoginFailure(String error) {
		if (!isShowLoginUI) {
			showLoginUI();
			mLoadToast.error();
		} else {
			Snackbar.make(mBinding.sceneRoot, error, Snackbar.LENGTH_LONG)
			        .show();
			mLoadToast.setText(getApplicationContext().getString(R.string.hello_login_unsuccessfully));
			mLoadToast.error();
		}
	}


	@Override
	public void onUserInputFailure(String error, @ErrorConstants.Value int errorType) {
		switch (errorType) {
			case NO_EMPTY_USERNAME:
				mLoginBinding.loginIdContainer.setError(error);
				break;
			case NO_EMPTY_PASSWORD:
				mLoginBinding.loginPassContainer.setError(error);
				break;
			case ErrorConstants.PASSWORD_NOT_EQUAL:
			case ErrorConstants.PASSWORD_SHORT:
			case ErrorConstants.WRONG_EMAIL:
			case ErrorConstants.REGISTER_UNSUCCESSFULLY:
				break;
		}
		mLoadToast.setText(error);
		mLoadToast.error();
	}


	private void updateUIWhenLogin(boolean enabled) {
		mLoginBinding.loginId.setEnabled(enabled);
		mLoginBinding.loginIdContainer.setEnabled(enabled);
		mLoginBinding.loginPass.setEnabled(enabled);
		mLoginBinding.loginPassContainer.setEnabled(enabled);
		mLoginBinding.loginButton.setEnabled(enabled);
		mLoginBinding.registerButton.setEnabled(enabled);
		mLoginBinding.loginRemember.setEnabled(enabled);
	}

	@Override
	public void beforeLogin() {
		super.beforeLogin();
		updateUIWhenLogin(false);
	}


	@Override
	public void afterLogin() {
		super.afterLogin();
		updateUIWhenLogin(true);
	}


	@Override
	public void initView(@Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.setContentView(this, LAYOUT);
		presenter = new LoginRegisterPresenter(this);
		initScene();
		initUi();
		Intent intent = getIntent();

		if (intent.hasExtra(Arg.RE_LOGIN)) {
			reLogin();//重新登陆
		} else {
			tryLogin();//尝试登陆
		}
	}

	@Override
	public void initData(@Nullable Bundle savedInstanceState) {

	}
}
