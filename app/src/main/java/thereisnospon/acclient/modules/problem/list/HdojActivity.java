package thereisnospon.acclient.modules.problem.list;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.problem.detail.ShowProblemActivity;
import thereisnospon.acclient.modules.problem.list.search.SearchProblemFragment;
import thereisnospon.acclient.modules.settings.Settings;
import thereisnospon.acclient.utils.SpUtil;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.os.Bundle.EMPTY;

/**
 * Created by yzr on 16/6/5.
 */
public final class HdojActivity extends AppBarActivity {
	private static final String TAG = "HdojActivity";
	private boolean first = true;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("first", first);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState == null) {
			return;
		}
		first = savedInstanceState.getBoolean("first");
	}

	public static void showInstance(Activity cxt) {
		Intent intent = new Intent(cxt, HdojActivity.class);
		intent.setFlags(FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, EMPTY);
	}


	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		setupFragment(contentLayout.getId(), HdojProblemFragment.newInstance());

		showMessageInfo();
	}

	private void showMessageInfo(){

		if(SpUtil.getInstance().getBoolean(SpUtil.FIRST_VISIT)){

		Handler handler=new Handler(Looper.getMainLooper());
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				showMessageDialog();
			}
		},1000);

		SpUtil.getInstance().putBoolean(SpUtil.FIRST_VISIT,false);
		}
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		setupFragment(SearchProblemFragment.newInstance(query));
		Logger.d(query);
		return false;
	}


	@Override
	protected void inflateSearchMenu(@NonNull Menu menu) {
		getMenuInflater().inflate(R.menu.problem_list_menu, menu);
	}

	@Override
	protected boolean supportSearch() {
		return true;
	}


	private void showPageDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = LayoutInflater.from(this)
		                          .inflate(R.layout.alert_goto_page, null);
		final EditText editText = (EditText) view.findViewById(R.id.to_page);
		AlertDialog dialog = builder.setTitle(R.string.search_problem_by_page)
		                            .setView(view)
		                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			                            @Override
			                            public void onClick(DialogInterface dialog, int which) {
				                            goToPage(editText.getText()
				                                             .toString());
			                            }
		                            })
		                            .setNegativeButton(android.R.string.cancel, null)
		                            .setCancelable(true)
		                            .create();
		dialog.show();
	}




	private void showMessageDialog(){

		AlertDialog.Builder builder=new AlertDialog.Builder(this);

		builder.setTitle("温馨提醒")
				.setMessage("第一次使用oj的话，建议先看看11页的题目，难度会比较简单~")
				.setPositiveButton("前往", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						goToPage("11");
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

					}
				})
				.show();

	}


	private void goToPage(String str) {
		try {
			int page = Integer.parseInt(str);
			setupFragment(HdojProblemFragment.newInstance(page));
		} catch (NumberFormatException e) {
			showShortSnackbar(R.string.search_by_wrong_page);
		}
	}


	private void showIdDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = LayoutInflater.from(this)
		                          .inflate(R.layout.alert_goto_page, null);
		final EditText editText = (EditText) view.findViewById(R.id.to_page);
		AlertDialog dialog = builder.setTitle(R.string.search_by_problem_id)
		                            .setView(view)
		                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			                            @Override
			                            public void onClick(DialogInterface dialog, int which) {
				                            gotToId(editText.getText()
				                                            .toString());
			                            }
		                            })
		                            .setNegativeButton(android.R.string.cancel, null)
		                            .setCancelable(true)
		                            .create();
		dialog.show();
	}

	private void gotToId(String str) {
		try {
			int id = Integer.parseInt(str);
			Intent intent = new Intent(this, ShowProblemActivity.class);
			intent.putExtra(Arg.LOAD_PROBLEM_DETAIL, id);
			startActivity(intent);
		} catch (NumberFormatException e) {
			Toast.makeText(this, R.string.search_by_wrong_id, Toast.LENGTH_SHORT)
			     .show();
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_problem_go_page:
				showPageDialog();
				break;
			case R.id.menu_problem_go_id:
				showIdDialog();
				break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (first && Settings.getInstance()
			                     .ifExitConfirm()) {
				first = false;
				showShortSnackbar(R.string.search_pressback_return, android.R.string.ok, new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}
}
