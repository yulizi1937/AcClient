package thereisnospon.acclient.modules.discuss;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.FrameLayout;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;

/**
 * Created by yzr on 16/9/9.
 */
public final class DiscussActivity extends AppBarActivity {


    public static void showInstance(@NonNull Activity cxt, int id, @NonNull ActivityOptionsCompat options) {
        Intent intent = new Intent(cxt, DiscussActivity.class);
        intent.putExtra(Arg.PROBLEM_DISUCSS, String.valueOf(id));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityCompat.startActivity(cxt, intent, options.toBundle());
    }

    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        String pid=getIntent().getStringExtra(Arg.PROBLEM_DISUCSS);
        if(pid==null){
            setupFragment(contentLayout.getId(), DiscussFragment.newInstance());
        }else{
            setupFragment(contentLayout.getId(), DiscussFragment.newInstance(pid));
        }
        setActivityBackgroundColor(R.color.colorGrey);
    }
}
