package thereisnospon.acclient.modules.hello;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.RegisterActivityBinding;

import static thereisnospon.acclient.modules.hello.ErrorConstants.NO_EMPTY_PASSWORD;
import static thereisnospon.acclient.modules.hello.ErrorConstants.NO_EMPTY_USERNAME;
import static thereisnospon.acclient.modules.hello.ErrorConstants.PASSWORD_NOT_EQUAL;
import static thereisnospon.acclient.modules.hello.ErrorConstants.PASSWORD_SHORT;
import static thereisnospon.acclient.modules.hello.ErrorConstants.WRONG_EMAIL;

public final class RegisterActivity extends BaseActivity {

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
		HelloUtil.showDialogFragment(getSupportFragmentManager(), AfterRegisterCloseFragment.newInstance(userName), "close");
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
		Snackbar.make(mBinding.sceneRoot, error, Snackbar.LENGTH_LONG)
		        .show();
	}

	@Nullable
	@Override
	public ImageView getCheckCodeImageHolder() {
		return mBinding.checkCodeImg;
	}

	public static final class AfterRegisterCloseFragment extends AppCompatDialogFragment {
		private static final String EXTRAS_NAME = AfterRegisterCloseFragment.class.getName() + ".EXTRAS.name";

		static AfterRegisterCloseFragment newInstance(String username) {
			Bundle args = new Bundle(1);
			args.putString(EXTRAS_NAME, username);
			return (AfterRegisterCloseFragment) AfterRegisterCloseFragment.instantiate(AppApplication.context, AfterRegisterCloseFragment.class.getName(), args);
		}

		@NonNull
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.hello_btn_register)
			       .setCancelable(false)
			       .setMessage(getString(R.string.hello_register_successfully) + getArguments().getString(EXTRAS_NAME))
			       .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
					       ActivityCompat.finishAfterTransition(getActivity());
				       }
			       });
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}
}
