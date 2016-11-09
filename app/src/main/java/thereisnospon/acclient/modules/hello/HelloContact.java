package thereisnospon.acclient.modules.hello;

import android.graphics.Bitmap;

/**
 * Created by yzr on 16/10/30.
 */

interface HelloContact {


	interface View {

		void beforeLogin();

		void beforeRegister();

		void onLoginSuccess(String userName);

		void onLoginFailure(String error);

		void onRegisterSuccess(String userName);

		void onUserInputFailure(String error, @ErrorConstants.Value int errorType);

		void onCheckCode(Bitmap bitmap);

		void onCheckCodeErr(String error);
	}

	interface Presenter {
		void login(String name, String password);

		void register(String name, String email, String password, String chechPassword, String checkcode);

		void loadCheckCode();
	}

	interface Model {
		String login(String name, String password);

		String register(String name, String email, String password, String chechPassword, String checkcode);

		Bitmap checkCode();
	}

}
