package thereisnospon.acclient.modules.hello;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Pattern;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.utils.StringCall;

import static thereisnospon.acclient.modules.hello.ErrorConstants.NO_EMPTY_PASSWORD;
import static thereisnospon.acclient.modules.hello.ErrorConstants.NO_EMPTY_USERNAME;
import static thereisnospon.acclient.modules.hello.ErrorConstants.PASSWORD_NOT_EQUAL;
import static thereisnospon.acclient.modules.hello.ErrorConstants.PASSWORD_SHORT;
import static thereisnospon.acclient.modules.hello.ErrorConstants.REGISTER_UNSUCCESSFULLY;
import static thereisnospon.acclient.modules.hello.ErrorConstants.WRONG_EMAIL;

/**
 * Created by yzr on 16/10/30.
 */

final class HelloPresenter implements HelloContact.Presenter {


	private final HelloContact.Model model;
	private final HelloContact.View view;

	HelloPresenter(HelloContact.View view) {
		this.view = view;
		this.model = new HelloModel();
	}

	@Override
	public void login(final String name, final String password) {
		view.beforeLogin();
		if (!loginCheck(name, password)) {
			return;
		}

		Observable.just(name)
		          .observeOn(Schedulers.io())
		          .map(new Func1<String, String>() {
			          @Override
			          public String call(String s) {
				          return model.login(name, password);
			          }
		          })
		          .observeOn(AndroidSchedulers.mainThread())
		          .subscribe(new StringCall() {
			          @Override
			          public void success(String nickName) {
				          view.onLoginSuccess(nickName);
			          }

			          @Override
			          public void failure(String msg) {
				          view.onLoginFailure(msg);
			          }
		          });

	}

	@Override
	public void register(final String name, final String email, final String password, final String checkPassword, final String check) {
		view.beforeRegister();
		if (!regCheck(name, email, password, checkPassword)) {
			return;
		}
		Observable.just(name)
		          .observeOn(Schedulers.io())
		          .map(new Func1<String, String>() {
			          @Override
			          public String call(String s) {
				          return model.register(name, email, password, checkPassword, check);
			          }
		          })
		          .observeOn(AndroidSchedulers.mainThread())
		          .subscribe(new StringCall() {
			          @Override
			          public void success(String nickName) {
				          view.onRegisterSuccess(nickName);
			          }

			          @Override
			          public void failure(String msg) {
				          view.onUserInputFailure(AppApplication.context.getString(R.string.hello_register_unsuccessfully), REGISTER_UNSUCCESSFULLY);
			          }
		          });
	}


	private static final String CHECK_EMAIL_REGEX = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
	private static final Pattern pattern;


	static {
		pattern = Pattern.compile(CHECK_EMAIL_REGEX);
	}


	private static boolean checkEmail(String email) {
		return email != null && pattern.matcher(email)
		                               .matches();
	}


	private boolean regCheck(String name, String email, String password, String checkPassword) {
		Context cxt = AppApplication.context;
		if (TextUtils.isEmpty(name)) {
			view.onUserInputFailure(cxt.getString(R.string.hello_no_empty_username), NO_EMPTY_USERNAME);
			return false;
		}

		if (!checkEmail(email)) {
			view.onUserInputFailure(cxt.getString(R.string.hello_wrong_email), WRONG_EMAIL);
			return false;
		}

		if (TextUtils.isEmpty(password)) {
			view.onUserInputFailure(cxt.getString(R.string.hello_no_empty_password), NO_EMPTY_PASSWORD);
			return false;
		}

		if (!password.equals(checkPassword)) {
			view.onUserInputFailure(cxt.getString(R.string.hello_password_not_equal), PASSWORD_NOT_EQUAL);
			return false;
		}

		if (password.length() < 6) {
			view.onUserInputFailure(cxt.getString(R.string.hello_password_short), PASSWORD_SHORT);
			return false;
		}

		return true;
	}

	private boolean loginCheck(String name, String password) {
		Context cxt = AppApplication.context;
		if (TextUtils.isEmpty(name)) {
			view.onUserInputFailure(cxt.getString(R.string.hello_no_empty_username), NO_EMPTY_USERNAME);
			return false;
		}

		if (TextUtils.isEmpty(password)) {
			view.onUserInputFailure(cxt.getString(R.string.hello_no_empty_password), NO_EMPTY_PASSWORD);
			return false;
		}

		if (password.length() < 6) {
			view.onUserInputFailure(cxt.getString(R.string.hello_password_short), PASSWORD_SHORT);
			return false;
		}
		return true;
	}

	@Override
	public void loadCheckCode() {
		model.checkCode(AppApplication.context, view.getCheckCodeImageHolder());
	}
}
