package thereisnospon.acclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import thereisnospon.acclient.AppApplication;

/**
 * Created by yzr on 16/8/21.
 */
public final class SpUtil {

    private static SpUtil instance;

    private static final String SP_NAME="sputil_file";

    private SharedPreferences sp;

    private SpUtil(Context context){
        sp=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }

    public static final String NAME="kname";
    public static final String PASS="kpass";
    public static final String NICKNAME="nickname";

    public String getString(String key){
        return sp.getString(key,null);
    }

    public void clear(){
        sp.edit().clear().commit();
    }



    public static final String FIRST_VISIT="firsitvisit";

    public  void putBoolean(String key,boolean value){
        sp.edit().putBoolean(key,value).commit();
    }

    public boolean getBoolean(String key){
        return sp.getBoolean(key,true);
    }


    public void putString(String key,String value){
        sp.edit().putString(key,value).commit();
    }

    public void putString(String key,String value,final SharedPreferences.OnSharedPreferenceChangeListener l){
        if (l != null) {
            SharedPreferences.OnSharedPreferenceChangeListener masterListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    l.onSharedPreferenceChanged(sharedPreferences, s);
                    sp.unregisterOnSharedPreferenceChangeListener(this);
                }
            };
            sp.registerOnSharedPreferenceChangeListener(masterListener);
        }
        sp.edit().putString(key,value).apply();
    }

    public static SpUtil getInstance(){

        if(instance==null){
            instance=new SpUtil(AppApplication.context);
        }
        return instance;
    }

}
