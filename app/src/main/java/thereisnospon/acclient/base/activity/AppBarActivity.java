package thereisnospon.acclient.base.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;


import thereisnospon.acclient.databinding.DrawerActivityLayoutBinding;

import thereisnospon.acclient.modules.discuss.DiscussActivity;
import thereisnospon.acclient.modules.login.LoginActivity;
import thereisnospon.acclient.modules.personal.UserDetailActivity;
import thereisnospon.acclient.modules.problem.list.HdojActivity;
import thereisnospon.acclient.modules.rank.RankActivity;
import thereisnospon.acclient.modules.settings.SettingActivity;
import thereisnospon.acclient.utils.AcClientActivityCompat;
import thereisnospon.acclient.utils.SpUtil;

/**
 * @author thereisnospon
 *  侧滑 Activity 基类
 *  Created by xzhao on 11.11.16.
 */
public abstract class AppBarActivity extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener{

	private static final @LayoutRes int LAYOUT = R.layout.activity_abstract_drawer;

	private String id;
	private String nickname;
	private DrawerActivityLayoutBinding mBinding;
	private boolean mPaused;


	//子类 Activity 设置自己的布局
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
	protected void onPause() {
		super.onPause();
		mPaused = true;
	}


	@Override
	protected void onResume() {
		super.onResume();
		mPaused = false;
	}


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
	final  public void initView(@Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.setContentView(this, LAYOUT);
		setupContent(mBinding.appbarContent);
		setupActionBar();
		mBinding.navigation.navView.setNavigationItemSelectedListener(this);
		setupDrawerHeader();
		setupContent(mBinding.appbarContent);
	}


	@Override
	final public void initData(@Nullable Bundle savedInstanceState) {
		SpUtil util = SpUtil.getInstance();
		this.id = util.getString(SpUtil.NAME);
		this.nickname = util.getString(SpUtil.PASS);
	}

	private void setupDrawerHeader(){

		View headerView = mBinding.navigation.navView.getHeaderView(0);
		TextView textView = (TextView) headerView.findViewById(R.id.drawer_header_name);
		textView.setText(SpUtil.getInstance().getString(SpUtil.NICKNAME));
		headerView.findViewById(R.id.btn_logout)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (mPaused) {
							return;
						}
						SpUtil.getInstance().putString(SpUtil.PASS, null, new SharedPreferences.OnSharedPreferenceChangeListener() {
							@Override
							public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
								AcClientActivityCompat.finishAffinityCompat(AppBarActivity.this, AppApplication.context.activityStack);
								LoginActivity.showInstance(AppBarActivity.this, true);
							}
						});
					}
				});
	}

	private void setupActionBar(){
		setSupportActionBar(mBinding.appbar.toolbar);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.appbar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		mBinding.drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}

	private void selectMenu(@IdRes int menuId) {
		mBinding.navigation.navView.getMenu()
		                           .findItem(menuId)
		                           .setChecked(true);
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_contest:
				selectMenu(R.id.menu_contest);
				DiscussActivity.showInstance(this);
				break;
			case R.id.menu_info:
				selectMenu(R.id.menu_info);
				UserDetailActivity.showInstance(this, this.id);
				break;
			case R.id.menu_problem:
				selectMenu(R.id.menu_problem);
				HdojActivity.showInstance(this);
				break;
			case R.id.menu_user:
				selectMenu(R.id.menu_user);
				RankActivity.showInstance(this);
				break;
			case R.id.menu_share:
				selectMenu(R.id.menu_share);
				share();
				break;
			case R.id.menu_setting:
				selectMenu(R.id.menu_setting);
				SettingActivity.showInstance(this);
				break;
		}
		mBinding.drawerLayout.closeDrawer(GravityCompat.START);
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

	protected void addViewToCoordinatorLayout(View addView) {
		mBinding.coordinatorLayout.addView(addView);
	}




	protected void showShortSnackbar(@StringRes int message, @StringRes int buttonLabel, @NonNull View.OnClickListener clickListener) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_SHORT)
				.setAction(buttonLabel, clickListener)
				.show();
	}

	protected void showShortSnackbar(@StringRes int message) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_SHORT)
				.show();
	}


	protected void showLongSnackbar(@StringRes int message, @StringRes int buttonLabel, @NonNull View.OnClickListener clickListener) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_LONG)
				.setAction(buttonLabel, clickListener)
				.show();
	}

	protected void showLongSnackbar(@StringRes int message) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_LONG)
				.show();
	}

	protected void showIndefiniteSnackbar(@StringRes int message, @StringRes int buttonLabel, @NonNull View.OnClickListener clickListener) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_INDEFINITE)
				.setAction(buttonLabel, clickListener)
				.show();
	}

	protected void showIndefiniteSnackbar(@StringRes int message) {
		Snackbar.make(mBinding.drawerLayout, message, Snackbar.LENGTH_LONG)
				.show();
	}

	protected void setActivityBackgroundColor(@ColorRes int colorRes) {
		mBinding.coordinatorLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), colorRes, getTheme()));
	}




}
