package thereisnospon.acclient.modules.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.FrameLayout;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;

public final class UserDetailActivity extends AppBarActivity {


	public static void showInstance(@NonNull Activity cxt, @NonNull String id) {
		Intent intent = new Intent(cxt, UserDetailActivity.class);
		intent.putExtra(Arg.LOAD_USER_DETAIL, id);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
	}

	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		setupFragment(contentLayout.getId(), UserDetailFragment.newInstance(getIntent().getStringExtra(Arg.LOAD_USER_DETAIL)));
	}

	@Override
	protected
	@IdRes
	int getMenuId() {
		return R.id.menu_info;
	}
}
