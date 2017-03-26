package thereisnospon.acclient.modules.hello;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import net.steamcrafted.loadtoast.LoadToast;

import thereisnospon.acclient.R;

import static thereisnospon.acclient.modules.hello.HelloUtil.createLoadToast;

/**
 * Created by xzhao on 09.11.16.
 */
public abstract class BaseActivity extends AppCompatActivity implements HelloContact.View {
	@SuppressWarnings("WeakerAccess") protected LoadToast mLoadToast;
	@SuppressWarnings("WeakerAccess") protected HelloContact.Presenter presenter;

	private void createToast() {

		if (mLoadToast == null) {
			mLoadToast = createLoadToast(this);
		}
	}

	@Override
	public void beforeLogin() {
		createToast();
		mLoadToast.setText(getString(R.string.hello_btn_login));
		mLoadToast.show();
	}


	@Override
	public void beforeRegister() {
		createToast();
		mLoadToast.setText(getString(R.string.hello_btn_register));
		mLoadToast.show();
	}

	@Override
	public void onLoginSuccess(String userName) {
	}

	@Override
	public void onLoginFailure(String error) {
	}

	@Override
	public void onCheckCode(Bitmap bitmap) {

	}

	@Override
	public void onCheckCodeErr(String error) {

	}

	@Override
	public void onRegisterSuccess(String userName) {
	}

	@Override
	public void afterRegister() {

	}

	@Override
	public void afterLogin() {

	}

	@Override
	public @Nullable  ImageView getCheckCodeImageHolder() {
		return null;
	}
}
