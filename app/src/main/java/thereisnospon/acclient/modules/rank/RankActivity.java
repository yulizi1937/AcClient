package thereisnospon.acclient.modules.rank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.base.activity.SearchBarActivity;
import thereisnospon.acclient.modules.personal.search.SearchPeopleFragment;

/**
 * Created by yzr on 16/8/27.
 */

public final class RankActivity extends SearchBarActivity {


	public static void showInstance(@NonNull Activity cxt) {
		Intent intent = new Intent(cxt, RankActivity.class);
		ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
	}


	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		setupFragment(contentLayout.getId(), RankFragment.newInstance());
	}


	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		Fragment fragment = SearchPeopleFragment.newInstance(query);
		setupFragment(fragment);
		return true;
	}

}
