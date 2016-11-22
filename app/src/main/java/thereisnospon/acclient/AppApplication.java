package thereisnospon.acclient;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by yzr on 16/6/5.
 */
public class AppApplication extends Application {
    public final static Gson gson = new Gson();
    public static Context  context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext(), "7ed01ab9f2", false);
    }

}
