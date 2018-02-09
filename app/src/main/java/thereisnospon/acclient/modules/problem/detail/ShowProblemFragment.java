package thereisnospon.acclient.modules.problem.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.api.HdojApi;
import thereisnospon.acclient.base.fragment.BaseFragment;
import thereisnospon.acclient.databinding.FragmentProblemDetailBinding;

/**
 * @author thereisnospon
 * i题目 Fragment,View
 * Created by yzr on 16/6/6.
 */
public final class ShowProblemFragment extends BaseFragment implements ShowProblemContact.View,
		SwipeRefreshLayout.OnRefreshListener {

	private static final @LayoutRes int LAYOUT = R.layout.fragment_problem_detail;
	private int id;
	private FragmentProblemDetailBinding mBinding;

	ShowProblemContact.Presenter presenter ;

	public static ShowProblemFragment newInstance(int id) {
		ShowProblemFragment fragment = (ShowProblemFragment) ShowProblemFragment.instantiate(AppApplication.context, ShowProblemFragment.class.getName());
		fragment.id = id;
		return fragment;
	}


	public ShowProblemFragment(){
		presenter = new ShowProblePresenter(this);
	}

	@Override
	public void onRefresh() {
		presenter.loadProblemDetail(id);
	}


	@Override
	public int getLayoutRes() {
		return 0;
	}

	@Override
	public View createView(ViewGroup container, Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), LAYOUT, container, false);
		return mBinding.getRoot();
	}

	@Override
	public void initView(View view, Bundle savedInstanceState) {

		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorGreen);
		mSwipeRefreshLayout.setOnRefreshListener(this);


		mSwipeRefreshLayout.setRefreshing(true);
		onRefresh();


		mBinding.webView.setWebViewClient(new WebViewClient() {
			private ProgressDialog mProgressDialog;


			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				return true;
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return true;
			}

			private void showPb() {
				dismissPb();
				if (mProgressDialog == null) {
					Activity activity = getActivity();

					if (activity != null) {
						mProgressDialog = ProgressDialog.show(activity, getString(R.string.app_name), getString(R.string.loading));
					}
				}
			}

			private void dismissPb() {
				if (mProgressDialog != null) {
					if (mProgressDialog.isShowing()) {
						mProgressDialog.dismiss();
					}
					mProgressDialog = null;
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						mSwipeRefreshLayout.setRefreshing(false);
					}
				}, 500);
			}
		});
	}

	@Override
	public void initData() {

	}

	private static final String SAVE_ID="save_probel_id";


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SAVE_ID, id);
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState == null) {
			return;
		}
		id = savedInstanceState.getInt(SAVE_ID);
	}


	SwipeRefreshLayout mSwipeRefreshLayout;


	@Override
	public void onSuccess(String html) {
		mBinding.webView.loadDataWithBaseURL(null, html, "text/html", HdojApi.HTML_CHAR_SET, null);
	}

	@Override
	public void onFailure(String msg) {
		mSwipeRefreshLayout.setRefreshing(false);
	}

}
