package thereisnospon.acclient.modules.personal;

import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;

public final class UserDetailActivity extends AppBarActivity {
	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		setupFragment(contentLayout.getId(), UserDetailFragment.newInstance(getIntent().getStringExtra(Arg.LOAD_USER_DETAIL)));
	}
}
