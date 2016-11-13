package thereisnospon.acclient.modules.settings;

import android.content.Context;
import android.content.SharedPreferences;

import thereisnospon.acclient.AppApplication;

/**
 * Created by yzr on 16/8/20.
 */
public final class Settings {


    private static Settings instance;

    private static final String SETTING_FILE="settings_pref";


    public static final String SKIN_PREF = "skins_pref";
    static final String ABOUT_PREF = "pref_about";
    static final String SOFTWARE_LICENSES = "pref_software_licenses";
    static final String THEME_PREF = "theme_pref";
    static final String EXIT_CONFIRM = "exitcon_pref";
    static final String COMPILER = "compiler_pref";


    public int getTheme(){
        String str=getString(THEME_PREF,theme+"");
        return Integer.parseInt(str);
    }

    public int getCompiler(){
        String str=getString(COMPILER,compiler+"");
        return Integer.parseInt(str);
    }

    int compiler=0;
    public int theme=0;
    boolean exitConfirm=true;
    public boolean skinPref=false;


    private final SharedPreferences mPreference;

    private Settings(Context context){

        mPreference=context.getSharedPreferences(SETTING_FILE,Context.MODE_PRIVATE);
    }

    public static Settings getInstance(){
        if(instance==null){
            instance=new Settings(AppApplication.context);
        }
        return instance;
    }


    public boolean ifExitConfirm(){
        return getBoolean(EXIT_CONFIRM,exitConfirm);
    }

    public boolean getBoolean(String key,boolean def){
        return mPreference.getBoolean(key,def);
    }


	void putBooleanAsync(String key, boolean value, final SharedPreferences.OnSharedPreferenceChangeListener l) {
		if (l != null) {
			SharedPreferences.OnSharedPreferenceChangeListener masterListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
				@Override
				public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
					l.onSharedPreferenceChanged(sharedPreferences, s);
					mPreference.unregisterOnSharedPreferenceChangeListener(this);
				}
			};
			mPreference.registerOnSharedPreferenceChangeListener(masterListener);
		}
		mPreference.edit()
		           .putBoolean(key, value)
		           .apply();
	}


    private String getString(String key, String def){
        return mPreference.getString(key,def);
    }

	void putStringAsync(String key, String value, final SharedPreferences.OnSharedPreferenceChangeListener l) {
		if (l != null) {
			SharedPreferences.OnSharedPreferenceChangeListener masterListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
				@Override
				public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
					l.onSharedPreferenceChanged(sharedPreferences, s);
					mPreference.unregisterOnSharedPreferenceChangeListener(this);
				}
			};
			mPreference.registerOnSharedPreferenceChangeListener(masterListener);
		}
		mPreference.edit()
		           .putString(key, value)
		           .apply();
	}
}
