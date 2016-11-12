package thereisnospon.acclient.modules.note;

import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;

public final class NoteActivity extends AppBarActivity {
    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        setupFragment(contentLayout.getId(), NoteFragment.newInstance());
    }
}
