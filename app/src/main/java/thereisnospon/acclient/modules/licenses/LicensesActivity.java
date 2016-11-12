package thereisnospon.acclient.modules.licenses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;


public final class LicensesActivity extends AppBarActivity {

	public static void showInstance(Activity cxt) {
		Intent intent = new Intent(cxt, LicensesActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
	}

	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		setupFragment(contentLayout.getId(), LicensesFragment.newInstance(getApplication()));
	}
}
