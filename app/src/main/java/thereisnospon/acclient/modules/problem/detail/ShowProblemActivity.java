package thereisnospon.acclient.modules.problem.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.discuss.DiscussActivity;
import thereisnospon.acclient.modules.submit.SubmitAnswerActivity;

public final class ShowProblemActivity extends AppBarActivity {

	private int id;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		id = outState.getInt("id");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState == null) {
			return;
		}
		id = savedInstanceState.getInt("id");
	}


	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		id = getIntent().getIntExtra(Arg.LOAD_PROBLEM_DETAIL, 1000);
		setupFragment(contentLayout.getId(), ShowProblemFragment.newInstance(id));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.problem_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.problem_discuss) {
			Intent intent = new Intent(this, DiscussActivity.class);
			intent.putExtra(Arg.PROBLEM_DISUCSS, id + "");
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, SubmitAnswerActivity.class);
			intent.putExtra(Arg.SBUMMIT_PROBLEM_ID, id + "");
			startActivity(intent);
		}
		return true;
	}
}
