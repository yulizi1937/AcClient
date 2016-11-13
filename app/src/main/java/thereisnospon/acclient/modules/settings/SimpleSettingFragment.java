package thereisnospon.acclient.modules.settings;

/**
 * Created by yzr on 16/8/3.
 */

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.widget.Toast;

import thereisnospon.acclient.R;
import thereisnospon.acclient.modules.about.AboutActivity;
import thereisnospon.acclient.modules.licenses.LicensesActivity;

public  class SimpleSettingFragment extends PreferenceFragment
        implements
        Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener{

    ListPreference theme;
    ListPreference compiler;
    Preference about;
    SwitchPreference exitConfirm;
    //CheckBoxPreference skin;
    Settings settings;



   // private OnThemeChangeListener themeChangeListener;




    interface OnThemeChangeListener{
        void onThemeChange();
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

        about=findPreference(Settings.ABOUT_PREF);
        about.setOnPreferenceClickListener(this);

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
        if(preference==theme){
            settings.theme= Integer.parseInt(newValue.toString());
            settings.putString(Settings.THEME_PREF,settings.theme+"");
        }else if(preference==exitConfirm){
           settings.exitConfirm=Boolean.valueOf(newValue.toString());
           settings.putBoolean(Settings.EXIT_CONFIRM,settings.exitConfirm);
       }
        else if(preference==compiler){
           settings.compiler=Integer.parseInt(newValue.toString());
           settings.putString(Settings.COMPILER,settings.compiler+"");
       }
        return true;
    }

    public void listSummary(Preference preference,Object value){
        if(preference instanceof ListPreference){
            String str=getEntry((ListPreference)preference,value);
            preference.setSummary(str);
        }
    }

    String getEntry(ListPreference preference,Object newValue){
        int index=preference.findIndexOfValue(newValue.toString());
        CharSequence rets[]=preference.getEntries();
        return index>=0?rets[index].toString():null;
    }

    /**
     * 属性触摸时操作
     */
    @Override
    public boolean onPreferenceClick(Preference preference) {
         if(preference==about){
            Intent intent=new Intent(getActivity(), LicensesActivity.class);
            startActivity(intent);
        }
        return false;
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