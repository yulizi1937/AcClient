package thereisnospon.acclient.modules.note;

import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;


/**
 * @author thereisnospon
 * 笔记 Activity
 * @// TODO: 17/3/26
 */
public final class NoteActivity extends AppBarActivity {

    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        setupFragment(contentLayout.getId(), NoteFragment.newInstance());
    }
}
