package thereisnospon.acclient.modules.discuss;

import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;

/**
 * Created by yzr on 16/9/9.
 */
public final class DiscussActivity extends AppBarActivity {
    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        String pid=getIntent().getStringExtra(Arg.PROBLEM_DISUCSS);
        if(pid==null){
            setupFragment(contentLayout.getId(), DiscussFragment.newInstance());
        }else{
            setupFragment(contentLayout.getId(), DiscussFragment.newInstance(pid));
        }
    }
}
