package thereisnospon.acclient.modules.settings;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;


public final class SettingActivity extends AppBarActivity {
    private ImageView settingimage;
    private ViewGroup settingcontent;
    private SettingFragment settingFragment;


    public static void showInstance(Activity cxt) {
    	Intent intent = new Intent(cxt, SettingActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
    }

    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        contentLayout.addView(getLayoutInflater().inflate(R.layout.nav_setting, contentLayout, false));
        initView();
        addFragment();
    }

    void changeTheme(){
        setDrawableCache();
        initTheme();
        getState();
    }

    private void getState(){
        addFragment();
    }

    private void addFragment(){
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(settingFragment!=null){
            transaction.remove(settingFragment);
        }
        settingFragment = SettingFragment.newInstance();
        transaction.add(R.id.fragment_content,settingFragment);
        transaction.commit();
    }

    //TODO 可能OOM
    private void setDrawableCache(){
        settingcontent.setDrawingCacheEnabled(false);
        settingcontent.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(settingcontent.getDrawingCache());
        //saveBitmap(bitmap);
        settingimage.setImageBitmap(bitmap);
        settingimage.setAlpha(1f);
        settingimage.setVisibility(View.VISIBLE);
    }



    private void initTheme(){
        Settings settings = Settings.getInstance();
        if (settings.getBoolean(Settings.SKIN_PREF, settings.skinPref)) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppTheme);
            Logger.d("appTheme");
        }

    }

    private void initView(){
        this.settingcontent = (ViewGroup) findViewById(R.id.setting_content);
        this.settingimage = (ImageView) findViewById(R.id.setting_image);
    }

	@Override
	protected
	@IdRes
	int getMenuId() {
		return R.id.menu_setting;
	}
}
