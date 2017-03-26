package thereisnospon.acclient.base.activity;

import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * Created by yzr on 17/3/16.
 */

public class DemoActivity extends SearchBarActivity {

    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
