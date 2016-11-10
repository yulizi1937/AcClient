package thereisnospon.acclient.modules.hello;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.orhanobut.logger.Logger;

import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.RegisterActivityBinding;

import static thereisnospon.acclient.modules.hello.ErrorConstants.NO_EMPTY_PASSWORD;
import static thereisnospon.acclient.modules.hello.ErrorConstants.NO_EMPTY_USERNAME;
import static thereisnospon.acclient.modules.hello.ErrorConstants.PASSWORD_NOT_EQUAL;
import static thereisnospon.acclient.modules.hello.ErrorConstants.PASSWORD_SHORT;
import static thereisnospon.acclient.modules.hello.ErrorConstants.WRONG_EMAIL;

public final class RegisterActivity extends BasicActivity {

	private static final int LAYOUT = R.layout.activity_register;
	private RegisterActivityBinding mBinding;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
		mBinding.registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				register();
			}
		});
		mBinding.checkCodeImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.loadCheckCode();
			}
		});
		presenter = new HelloPresenter(this);
	}

	private void register() {

		String id = mBinding.registerId.getText()
		                               .toString();
		String email = mBinding.registerEmail.getText()
		                                     .toString();
		String pass = mBinding.registerPass1.getText()
		                                    .toString();
		String passch = mBinding.registerPass2.getText()
		                                      .toString();
		String check = null;
		if (mBinding.check.getVisibility() == View.VISIBLE) {
			check = mBinding.checkCodeText.getText()
			                              .toString();
		}
		presenter.register(id, email, pass, passch, check);
	}


	@Override
	public void onRegisterSuccess(String userName) {
		mLoadToast.success();
		Snackbar.make(mBinding.sceneRoot, getString(R.string.hello_register_successfully) + userName, Snackbar.LENGTH_SHORT)
		        .setAction(R.string.hello_return_to_login, new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
				        finish();
			        }
		        })
		        .show();
	}


	@Override
	public void onUserInputFailure(String error, @ErrorConstants.Value int errorType) {
		Logger.d(error);
		mBinding.check.setVisibility(View.VISIBLE);
		presenter.loadCheckCode();
		switch (errorType) {
			case NO_EMPTY_USERNAME:
				mBinding.registerIdContainer.setError(error);
				break;
			case WRONG_EMAIL:
				mBinding.registerEmailContainer.setError(error);
				break;
			case NO_EMPTY_PASSWORD:
				mBinding.registerPass1Container.setError(error);
				break;
			case PASSWORD_NOT_EQUAL:
				mBinding.registerPass2Container.setError(error);
				break;
			case PASSWORD_SHORT:
				mBinding.registerPass1Container.setError(error);
				break;
			case ErrorConstants.REGISTER_UNSUCCESSFULLY:
				Snackbar.make(mBinding.sceneRoot, error, Snackbar.LENGTH_LONG)
				        .show();
				break;
		}
		mLoadToast.setText(error);
		mLoadToast.error();
	}


	@Override
	public void onCheckCode(Bitmap bitmap) {
		mBinding.checkCodeImg.setImageBitmap(bitmap);
	}

	@Override
	public void onCheckCodeErr(String error) {
		mBinding.checkCodeImg.setImageBitmap(null);
	}
}
