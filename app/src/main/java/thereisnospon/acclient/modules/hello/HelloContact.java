package thereisnospon.acclient.modules.hello;

import android.graphics.Bitmap;

/**
 * Created by yzr on 16/10/30.
 */

public interface HelloContact {


	interface View {
		void onLoginSuccess(String userName);

		void onLoginFailure(String error);

		void onRegisterSuccess(String userName);

		void onRegisterFailure(String error);

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
