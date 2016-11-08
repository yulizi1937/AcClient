package thereisnospon.acclient.modules.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.ActivityHelloSceneIndexBinding;
import thereisnospon.acclient.databinding.ActivityHelloSceneLoginBinding;
import thereisnospon.acclient.databinding.LoginActivityBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.problem_list.HdojActivity;
import thereisnospon.acclient.utils.SpUtil;
import thereisnospon.acclient.widget.TransitiionListenerAdapter;

public final class LoginActivity extends AppCompatActivity implements LoginContact.View {
	private static final int LAYOUT = R.layout.activity_hello;
	private static final int DURATION = 500;
	private Scene login;
	private LoginContact.Presenter presenter;

	private String id;
	private String pass;
	private String nickname;
	private volatile boolean rememberPas = false;
	private volatile boolean isShowLoginUI = false;

	private LoginActivityBinding mBinding;
	private ActivityHelloSceneIndexBinding mIndexBinding;
	private ActivityHelloSceneLoginBinding mLoginBinding;


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("id", id);
		outState.putString("pass", pass);
		outState.putString("nickname", nickname);
		outState.putBoolean("rememberPas", rememberPas);
		outState.putBoolean("isShowLoginUI", isShowLoginUI);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		id = savedInstanceState.getString("id");
		pass = savedInstanceState.getString("pass");
		nickname = savedInstanceState.getString("nickname");
		rememberPas = savedInstanceState.getBoolean("rememberPas");
		isShowLoginUI = savedInstanceState.getBoolean("isShowLoginUI");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, LAYOUT);
		presenter = new LoginPresenter(this);
		initScene();
		initView();
		Intent intent = getIntent();
		if (intent.hasExtra(Arg.RE_LOGIN)) {
			reLogin();
		} else {
			tryLogin();
		}
	}

	private void initScene() {
		Scene.getSceneForLayout(mBinding.sceneRoot, R.layout.activity_hello_scene_index, this);
		mLoginBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hello_scene_login, mBinding.sceneRoot, false);
		login = new Scene(mBinding.sceneRoot, mLoginBinding.getRoot());
	}


	private void initView() {
		mLoginBinding.registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			}
		});
		mLoginBinding.loginRemember.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLoginBinding.loginRemember.setChecked(!mLoginBinding.loginRemember.isChecked());

				rememberPas = 	mLoginBinding.loginRemember.isChecked();
				if (rememberPas) {
					Snackbar.make(mBinding.sceneRoot, R.string.hello_remember_password, Snackbar.LENGTH_SHORT)
					        .setAction(R.string.lbl_cancel, new View.OnClickListener() {
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
				if (checkRemebered()) {
					presenter.login(id, pass);
				} else {
					shoLoginUI();
				}
			}
		}, 1000);
	}

	private void reLogin() {
		shoLoginUI();
	}


	private boolean checkRemebered() {
		SpUtil sp = SpUtil.getInstance();
		this.id = sp.getString(SpUtil.NAME);
		this.pass = sp.getString(SpUtil.PASS);
		this.nickname = sp.getString(SpUtil.NICKNAME);
		rememberPas = this.id != null && this.pass != null;
		return rememberPas;
	}


	private void shoLoginUI() {
		isShowLoginUI = true;
		TransitionSet transitionSet = new TransitionSet();
		transitionSet.addTransition(new AutoTransition());
		transitionSet.setDuration(DURATION);
		transitionSet.addListener(new TransitiionListenerAdapter() {
			@Override
			public void onTransitionEnd(Transition transition) {
				super.onTransitionEnd(transition);
				initView();
			}
		});
		TransitionManager.go(login, transitionSet);
	}


	@Override
	public void onLoginSuccess(String userName) {
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
		Intent intent = new Intent(this, HdojActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onLoginFailure(String error) {
		if (!isShowLoginUI) {
			shoLoginUI();
		} else {
			Snackbar.make(mBinding.sceneRoot, R.string.hello_login_unsuccessfully, Snackbar.LENGTH_SHORT)
			        .show();
		}
	}


	@Override
	public void onRegisterSuccess(String userName) {
	}

	@Override
	public void onRegisterFailure(String error) {
	}

	@Override
	public void onCheckCode(Bitmap bitmap) {

	}

	@Override
	public void onCheckCodeErr(String error) {

	}
}
