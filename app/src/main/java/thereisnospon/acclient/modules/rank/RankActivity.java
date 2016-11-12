package thereisnospon.acclient.modules.rank;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.modules.personal.search.SearchPeopleFragment;

/**
 * Created by yzr on 16/8/27.
 */
public final class RankActivity extends AppBarActivity {
	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		setupFragment(contentLayout.getId(), RankFragment.newInstance());
	}

	@Override
	protected boolean supportSearch() {
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		Fragment fragment = SearchPeopleFragment.newInstance(query);
		setupFragment(fragment);
		return true;
	}
}
