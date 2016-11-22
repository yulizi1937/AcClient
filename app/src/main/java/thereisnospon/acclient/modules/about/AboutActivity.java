package thereisnospon.acclient.modules.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.webkit.WebView;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;



public final class AboutActivity extends AppBarActivity {


	private static final String ABOUT = "<!DOCTYPE html>\n" +
			"<html>\n" +
			"<head>\n" +
			"\t<title>关于</title>\n" +
			"</head>\n" +
			"<body>\n" +
			"\t<h1>关于</h1>\n" +
			"\t<pre>杭电oj客户端 AcClient 内部测试版。 不保证任何稳定性。。有</pre>\n" +
			"</body>\n" +
			"</html>";


	public static void showInstance(Activity cxt) {
		Intent intent = new Intent(cxt, AboutActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
	}

	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		WebView webView = new WebView(this);
		webView.loadDataWithBaseURL(null, ABOUT, "text/html", "utf-8", null);
		contentLayout.addView(webView);
	}



}
