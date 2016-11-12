package thereisnospon.acclient;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.event.Msg;
import thereisnospon.acclient.modules.code.CodeActivity;
import thereisnospon.acclient.modules.hello.HelloUtil;
import thereisnospon.acclient.modules.hello.LoginActivity;
import thereisnospon.acclient.modules.personal.UserDetailActivity;
import thereisnospon.acclient.modules.personal.search.SearchPeopleActivity;
import thereisnospon.acclient.modules.problem.list.HdojActivity;
import thereisnospon.acclient.modules.rank.RankActivity;
import thereisnospon.acclient.modules.settings.SettingActivity;
import thereisnospon.acclient.modules.settings.Settings;
import thereisnospon.acclient.modules.submit.SubmitAnswerActivity;
import thereisnospon.acclient.modules.submit.status.SubmitStatusActivity;
import thereisnospon.acclient.utils.SpUtil;

public final class DebugActivity extends AppBarActivity implements ListView.OnItemClickListener{



    ListView listView;
    ArrayAdapter<String>adapter;
    String []debugs=new String[]{
            "Login","problem","search","submmit","Code","Setting"
            ,"submmit anwser","user","Rank","HELLO"
    };

    Handler handler;

    void mainMsg(final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Msg.t(msg);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class<?>cl=null;
        switch (position){
//
//                break;
            case 1:cl= HdojActivity.class;
                break;
            case 2:cl= SearchPeopleActivity.class;
                break;
            case 3:cl= SubmitStatusActivity.class;
                break;
            case 4:cl= CodeActivity.class;
                break;
            case 5:cl= SettingActivity.class;
                break;
            case 6:cl= SubmitAnswerActivity.class;
                break;
            case 7:cl= UserDetailActivity.class;
                Intent intent=new Intent(this,cl);
                intent.putExtra(Arg.LOAD_USER_DETAIL,"465101800");
                startActivity(intent);
                return ;
            case 8:cl= RankActivity.class;
                break;
            case 9:cl= LoginActivity.class;
                break;
            default:
                break;
        }
        Intent intent=new Intent(this,cl);
        startActivity(intent);
    }


    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        contentLayout.addView(getLayoutInflater().inflate(R.layout.nav_activity_debug, contentLayout, false));
        handler = new Handler();
        initList();
        checkLogin();
    }

    void goToLogin(){
        Msg.t("请先登陆");
//
//        startActivity(intent);
    }

    void checkLogin(){
        final String userName= SpUtil.getInstance().getString(SpUtil.NAME);
        String password= SpUtil.getInstance().getString(SpUtil.PASS);
        if(userName==null||password==null){
            goToLogin();
            return;
        }
        HelloUtil.login(userName, password, new HelloUtil.LoginCall() {
            @Override
            public void success(String nickName) {
                setTitle(nickName);
                Msg.t("验证成功"+userName);
            }

            @Override
            public void failure(String msg) {
                SpUtil.getInstance().clear();
                goToLogin();
            }
        });
    }

    void initList(){
        listView=(ListView)findViewById(R.id.debug_list);
        List<String> list=new ArrayList<>();
        for(String s:debugs)
            list.add(s);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    boolean firstOnBack=true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(Settings.getInstance().ifExitConfirm()&&firstOnBack){
                firstOnBack=false;
                Snackbar.make(listView,"再按一次返回键退出",Snackbar.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
