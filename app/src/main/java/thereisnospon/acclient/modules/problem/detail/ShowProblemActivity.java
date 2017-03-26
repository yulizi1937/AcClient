package thereisnospon.acclient.modules.problem.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.discuss.DiscussActivity;
import thereisnospon.acclient.modules.submit.SubmitAnswerActivity;
import thereisnospon.acclient.utils.AcClientAnimationUtils;

public final class ShowProblemActivity extends AppBarActivity {

	private int id;
	private Button mDiscussBtn;
	private Button mAnswerBtn;

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
		mDiscussBtn = (Button) menu.findItem(R.id.problem_discuss)
		                           .getActionView();
		mDiscussBtn.setBackgroundResource(R.drawable.selector_item_background);
		mDiscussBtn.setText(R.string.discuss);
		mDiscussBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ActivityOptionsCompat options = AcClientAnimationUtils.createRevealOptions(mDiscussBtn, AcClientAnimationUtils.getViewCenter(mDiscussBtn), false);
				DiscussActivity.showInstance(ShowProblemActivity.this, id, options);
			}
		});


		mAnswerBtn = (Button) menu.findItem(R.id.problem_answer)
		                          .getActionView();
		mAnswerBtn.setBackgroundResource(R.drawable.selector_item_background);
		mAnswerBtn.setText(R.string.navi_answer);
		mAnswerBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ActivityOptionsCompat options = AcClientAnimationUtils.createRevealOptions(mAnswerBtn, AcClientAnimationUtils.getViewCenter(mAnswerBtn), false);
				SubmitAnswerActivity.showInstance(ShowProblemActivity.this, id, options);
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
}
