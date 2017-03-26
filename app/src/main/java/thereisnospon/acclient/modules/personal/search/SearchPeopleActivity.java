package thereisnospon.acclient.modules.personal.search;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.base.activity.SearchBarActivity;

public final class SearchPeopleActivity extends SearchBarActivity {
	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		TextView textView = new TextView(this);
		textView.setText(getString(R.string.navi_search));
		contentLayout.addView(textView);
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
		params.gravity = Gravity.CENTER;
	}


	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		setupFragment(SearchPeopleFragment.newInstance(query));
		return false;
	}
}
