package thereisnospon.acclient.modules.code;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.event.Event;
import thereisnospon.acclient.event.EventCode;

/**
 * @author thereisnospon
 * 显示 代码的 Activity
 */
public final class CodeActivity extends AppBarActivity {


    @Override
    public void beforeCreate() {
        super.beforeCreate();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    //收到来自 CodeFragment 的消息，将代码拷贝到剪贴板
    @Subscribe
    public void onEvent(Event event){
        if(event.getEventCode()==EventCode.CLIP_CODE_RESPONSE){
            String code=(String)event.getData();
            ClipData clipData=ClipData.newPlainText("code",code);
            ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(clipData);
            showShortSnackbar(R.string.info_copy_code, android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        Intent intent=getIntent();
        String id=intent.getStringExtra(Arg.SHOWCODE_ID);
        String code=intent.getStringExtra(Arg.SHOWCODE_CODE);
        Fragment fragment;
        if(code!=null){//指定代码内容显示
            fragment=CodeFragment.newCodeInstance(code);
        }else if(id !=null){//指定代码id，从网络加载显示
            fragment=CodeFragment.newInstance(id);
        }else{//默认加载的代码id....
            fragment=CodeFragment.newInstance("17562992");
        }
        setupFragment(contentLayout.getId(), fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.code_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_copy_code){
            EventBus.getDefault().post(new Event(Event.EMPTY, EventCode.CLIP_CODE_REQUEST));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
