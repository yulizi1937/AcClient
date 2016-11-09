package thereisnospon.acclient.modules.hello;

import android.support.v7.app.AppCompatActivity;

import net.steamcrafted.loadtoast.LoadToast;

import thereisnospon.acclient.R;

import static thereisnospon.acclient.modules.hello.HelloUtil.createLoadToast;

/**
 * Created by xzhao on 09.11.16.
 */
public abstract class BasicActivity extends AppCompatActivity implements HelloContact.View {
	protected LoadToast mLoadToast;
	protected HelloContact.Presenter presenter;

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
}
