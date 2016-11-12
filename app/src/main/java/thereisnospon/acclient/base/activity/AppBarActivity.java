package thereisnospon.acclient.base.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.AppBarLayoutBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.about.AboutActivity;
import thereisnospon.acclient.modules.discuss.DiscussActivity;
import thereisnospon.acclient.modules.hello.LoginActivity;
import thereisnospon.acclient.modules.licenses.LicensesActivity;
import thereisnospon.acclient.modules.personal.UserDetailActivity;
import thereisnospon.acclient.modules.problem.list.HdojActivity;
import thereisnospon.acclient.modules.rank.RankActivity;
import thereisnospon.acclient.modules.settings.SettingActivity;
import thereisnospon.acclient.utils.SpUtil;

/**
 * Created by xzhao on 11.11.16.
 */
public abstract class AppBarActivity extends ThemeActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                                      SearchView.OnQueryTextListener {

	private static final @LayoutRes int LAYOUT = R.layout.activity_appbar;
	private String id;
	private String nickname;
	private AppBarLayoutBinding mBinding;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("id", id);
		outState.putString("nickname", nickname);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState == null) {
			return;
		}
		id = savedInstanceState.getString("id");
		nickname = savedInstanceState.getString("nickname");
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, LAYOUT);
		setupMain();
		setupContent(mBinding.appbarContent);
	}

	private void setupMain() {
		SpUtil util = SpUtil.getInstance();
		this.id = util.getString(SpUtil.NAME);
		this.nickname = util.getString(SpUtil.PASS);
		setSupportActionBar(mBinding.appbar.toolbar);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.appbar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		mBinding.drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
		mBinding.navigation.navView.setNavigationItemSelectedListener(this);

		View headerView = mBinding.navigation.navView.getHeaderView(0);
		TextView textView = (TextView) headerView.findViewById(R.id.drawer_header_name);
		textView.setText(util.getString(SpUtil.NICKNAME));
		headerView.findViewById(R.id.btn_logout)
		          .setOnClickListener(new View.OnClickListener() {
			          @Override
			          public void onClick(View view) {
				          ActivityCompat.finishAffinity(AppBarActivity.this);
				          LoginActivity.showInstance(AppBarActivity.this, true);
			          }
		          });
	}


	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		Intent intent = null;
		switch (id) {
			case R.id.menu_contest:
				intent = new Intent(this, DiscussActivity.class);
				break;
			case R.id.menu_info:
				intent = new Intent(this, UserDetailActivity.class);
				intent.putExtra(Arg.LOAD_USER_DETAIL, this.id);
				break;
			case R.id.menu_problem:
				intent = new Intent(this, HdojActivity.class);
				break;
			case R.id.menu_user:
				intent = new Intent(this, RankActivity.class);
				break;
			case R.id.menu_share:
				share();
				break;
			case R.id.menu_about:
				Intent intent1 = new Intent(this, AboutActivity.class);
				startActivity(intent1);
				break;
			case R.id.menu_setting:
				intent = new Intent(this, SettingActivity.class);
				break;
			case R.id.menu_software_licenses:
				LicensesActivity.showInstance(this);
				break;
            /*case R.id.menu_note:
                intent=new Intent(this, NoteActivity.class);
                break;*/
		}
		mBinding.drawerLayout.closeDrawer(GravityCompat.START);
		if (intent != null) {
			startActivity(intent);
		}
		return true;
	}

	private void share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_app_subject));
		intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text));
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(Intent.createChooser(intent, getString(R.string.share_app)));
	}

	protected abstract void setupContent(@NonNull FrameLayout contentLayout);

	protected final void setupFragment(@IdRes int container, @NonNull Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
		                           .replace(container, fragment)
		                           .commit();
	}


	protected final void setupFragment(@NonNull Fragment fragment) {
		setupFragment(R.id.appbar_content, fragment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (supportSearch()) {
			inflateSearchMenu(menu);
			initSearch(menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	private void initSearch(Menu menu) {
		final MenuItem searchItem = menu.findItem(R.id.ab_search);
		SearchView searchview = (SearchView) searchItem.getActionView();
		searchview.setOnQueryTextListener(this);
	}

	protected void addViewToCoordinatorLayout(View addView) {
		mBinding.coordinatorLayout.addView(addView);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	protected boolean supportSearch() {
		return false;
	}

	protected void inflateSearchMenu(@NonNull  Menu menu) {
		getMenuInflater().inflate(R.menu.toolbar_menu, menu);
	}
	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	protected void showShortSnackbar(@StringRes int message, @StringRes int buttonLabel, @NonNull View.OnClickListener clickListener) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_SHORT)
		        .setAction(buttonLabel, clickListener)
		        .show();
	}

	protected void showShortSnackbar(@StringRes int message ) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_SHORT)
		        .show();
	}


	protected void showLongSnackbar(@StringRes int message, @StringRes int buttonLabel, @NonNull View.OnClickListener clickListener) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_LONG)
		        .setAction(buttonLabel, clickListener)
		        .show();
	}

	protected void showLongSnackbar(@StringRes int message ) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_LONG)
		        .show();
	}


	protected void showIndefiniteSnackbar(@StringRes int message, @StringRes int buttonLabel, @NonNull View.OnClickListener clickListener) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_INDEFINITE)
		        .setAction(buttonLabel, clickListener)
		        .show();
	}

	protected void showIndefiniteSnackbar(@StringRes int message ) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_LONG)
		        .show();
	}
}
