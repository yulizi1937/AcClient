package thereisnospon.acclient.modules.settings;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.MenuItem;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.modules.about.AboutActivity;
import thereisnospon.acclient.modules.licenses.LicensesActivity;

public  class SettingFragment extends PreferenceFragment
        implements
        Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private ProgressDialog mProgressDialog;

    private ListPreference theme;
    private ListPreference compiler;
    private SwitchPreference exitConfirm;
    //CheckBoxPreference skin;
    private Settings settings;



   // private OnThemeChangeListener themeChangeListener;


    public static SettingFragment newInstance() {
        return (SettingFragment) SettingFragment.instantiate(AppApplication.context, SettingFragment.class.getName());
    }

    private void showPb() {
        dismissPb();
        if (mProgressDialog == null) {
            Activity activity = getActivity();
            if (activity != null) {
                mProgressDialog = ProgressDialog.show(activity, getString(R.string.app_name), getString(R.string.loading));
            }
        }
    }

    private void dismissPb() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = null;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        dismissPb();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.settings);


        //themeChangeListener=(OnThemeChangeListener)getActivity();

        settings=Settings.getInstance();
        theme=(ListPreference)findPreference(Settings.THEME_PREF);


        String themeSummary=theme.getEntries()[settings.getTheme()].toString();
        theme.setSummary(themeSummary);
        theme.setOnPreferenceChangeListener(this);
        exitConfirm=(SwitchPreference)findPreference(Settings.EXIT_CONFIRM);
        exitConfirm.setOnPreferenceChangeListener(this);

        Preference about = findPreference(Settings.ABOUT_PREF);
        about.setOnPreferenceClickListener(this);

        Preference softwareLicenses = findPreference(Settings.SOFTWARE_LICENSES);
        softwareLicenses.setOnPreferenceClickListener(this);

       // skin=(CheckBoxPreference) findPreference(Settings.SKIN_PREF);
       // skin.setOnPreferenceChangeListener(this);



        compiler=(ListPreference)findPreference(Settings.COMPILER);
        String compilerSummary=compiler.getEntries()[settings.getCompiler()].toString();
        compiler.setSummary(compilerSummary);
        compiler.setOnPreferenceChangeListener(this);
    }



    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        listSummary(preference,newValue);
        if (preference == theme) {
            showPb();
            settings.theme = Integer.parseInt(newValue.toString());
            settings.putStringAsync(Settings.THEME_PREF, settings.theme + "", this);
        } else if (preference == exitConfirm) {
            showPb();
            settings.exitConfirm = Boolean.valueOf(newValue.toString());
            settings.putBooleanAsync(Settings.EXIT_CONFIRM, settings.exitConfirm, this);
        } else if (preference == compiler) {
            showPb();
            settings.compiler = Integer.parseInt(newValue.toString());
            settings.putStringAsync(Settings.COMPILER, settings.compiler + "", this);
        }
        return true;
    }

    private void listSummary(Preference preference, Object value){
        if(preference instanceof ListPreference){
            String str=getEntry((ListPreference)preference,value);
            preference.setSummary(str);
        }
    }

    private String getEntry(ListPreference preference, Object newValue){
        int index=preference.findIndexOfValue(newValue.toString());
        CharSequence rets[]=preference.getEntries();
        return index>=0?rets[index].toString():null;
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        Activity activity = getActivity();
        if (activity == null) {
            return false;
        }
        switch (preference.getKey()){
            case "pref_about":
                AboutActivity.showInstance(activity);
                break;
            case "pref_software_licenses":
                LicensesActivity.showInstance(activity);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onOptionsItemSelected(item)) {
                getActivity().onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}