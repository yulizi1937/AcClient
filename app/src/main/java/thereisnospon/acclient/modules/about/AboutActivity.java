package thereisnospon.acclient.modules.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import thereisnospon.acclient.R;

public final class AboutActivity extends AppCompatActivity {


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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.loadDataWithBaseURL(null, ABOUT, "text/html", "utf-8", null);

	}
}
