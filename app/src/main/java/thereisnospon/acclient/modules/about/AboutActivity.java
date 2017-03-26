package thereisnospon.acclient.modules.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.webkit.WebView;
import android.widget.FrameLayout;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;

/**
 * @author thereisnospon
 * 关于界面
 */
public final class AboutActivity extends AppBarActivity {


	public static void showInstance(Activity cxt) {
		Intent intent = new Intent(cxt, AboutActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
	}

	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		WebView webView = new WebView(this);
		String about=getString(R.string.about_content);
		webView.loadDataWithBaseURL(null, about, "text/html", "utf-8", null);
		contentLayout.addView(webView);
	}

}
