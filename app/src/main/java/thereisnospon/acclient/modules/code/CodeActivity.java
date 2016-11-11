package thereisnospon.acclient.modules.code;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;

public final class CodeActivity extends AppBarActivity {

    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        Intent intent=getIntent();
        String id=intent.getStringExtra(Arg.SHOWCODE_ID);
        String code=intent.getStringExtra(Arg.SHOWCODE_CODE);
        Fragment fragment;
        if(code!=null){
            fragment=CodeFragment.newCodeInstance(code);
        }else if(id !=null){
            fragment=CodeFragment.newInstance(id);
        }else{
            fragment=CodeFragment.newInstance("17562992");
        }
        setupFragment(contentLayout.getId(), fragment);
    }
}
