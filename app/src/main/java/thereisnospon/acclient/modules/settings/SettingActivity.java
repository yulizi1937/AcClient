package thereisnospon.acclient.modules.settings;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;

public final class SettingActivity extends AppBarActivity
        implements SimpleSettingFragment.OnThemeChangeListener{


    private ImageView settingimage;
    private ViewGroup settingcontent;
    private SimpleSettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
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


    void getState(){
        addFragment();
    }

    void addFragment(){
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(settingFragment!=null){
            transaction.remove(settingFragment);
        }
        settingFragment=new SimpleSettingFragment();
        transaction.add(R.id.fragment_content,settingFragment);
        transaction.commit();
    }


    private Bitmap bitmap;


    //TODO
    //可能OOM
    void setDrawableCache(){
        settingcontent.setDrawingCacheEnabled(false);
        settingcontent.setDrawingCacheEnabled(true);
        bitmap=Bitmap.createBitmap(settingcontent.getDrawingCache());
        //saveBitmap(bitmap);
        settingimage.setImageBitmap(bitmap);
        settingimage.setAlpha(1f);
        settingimage.setVisibility(View.VISIBLE);
    }
    /*void saveBitmap(Bitmap bitmap){
        File file=getCacheDir();
        File x=new File(file,"233.png");
        try(FileOutputStream out=new FileOutputStream(x)){
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            Logger.d("save ok"+x.getAbsolutePath());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/



    void initTheme(){
        Settings settings = Settings.getInstance();
        if (settings.getBoolean(Settings.SKIN_PREF, settings.skinPref)) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppTheme);
            Logger.d("appTheme");
        }

    }

    void initView(){
        this.settingcontent = (ViewGroup) findViewById(R.id.setting_content);
        this.settingimage = (ImageView) findViewById(R.id.setting_image);

    }

    @Override
    public void onThemeChange() {
        changeTheme();
    }


}
