package thereisnospon.acclient.base.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yzr on 16/8/20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void changeTheme(){
        /*Settings settings=Settings.getInstance();

            if(settings.getBoolean(Settings.SKIN_PREF,settings.skinPref)){
                setTheme(R.style.AppThemeNight);
                Logger.d("night");
            }else{
                setTheme(R.style.AppTheme);
                Logger.d("appTheme");
            }*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        changeTheme();
    }






}
