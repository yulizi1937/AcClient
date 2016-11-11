package thereisnospon.acclient.modules.problem.detail;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.FragmentProblemDetailBinding;

/**
 * Created by yzr on 16/6/6.
 */
public final class ShowProblemFragment extends Fragment implements ShowProblemContact.View {
	private static final @LayoutRes int LAYOUT = R.layout.fragment_problem_detail;
	private int id;
	private FragmentProblemDetailBinding mBinding;

	public static ShowProblemFragment newInstance(int id) {
		ShowProblemFragment fragment = (ShowProblemFragment) ShowProblemFragment.instantiate(AppApplication.context, ShowProblemFragment.class.getName());
		fragment.id = id;
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("id", id);
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState == null) {
			return;
		}
		id = savedInstanceState.getInt("id");
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(inflater, LAYOUT, container, false);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mBinding.webView.setWebViewClient(new WebViewClient() {
			private ProgressDialog mProgressDialog;

			private void showPb() {
				dismissPb();
				if (mProgressDialog == null) {
					mProgressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.loading));
				}
			}

			private void dismissPb() {
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				showPb();
			}


			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						dismissPb();
					}
				}, 500);
			}
		});
		ShowProblemContact.Presenter presenter = new ShowProblePresenter(this);
		presenter.loadProblemDetail(id);
	}

	@Override
	public void onSuccess(String html) {
		mBinding.webView.loadDataWithBaseURL(null, html, "text/html", "gb2312", null);
	}

	@Override
	public void onFailure(String msg) {
		Log.d("TTAG", msg);
	}

}
