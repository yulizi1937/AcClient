package thereisnospon.acclient.modules.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.base.adapter.BasePullAdapter;
import thereisnospon.acclient.base.fragment.BaseMvpSwipeFragment;
import thereisnospon.acclient.base.fragment.NormalPullFragment;
import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.data.NoteItem;
import thereisnospon.acclient.ui.adapter.NoteAdapter;

/**
 * @author thereisnospon
 * @// TODO: 17/3/26
 * Created by yzr on 16/9/9.
 */
public final class NoteFragment extends BaseMvpSwipeFragment<NoteItem> implements NoteContact.View {


    private NoteContact.Presenter presenter;

    public static NoteFragment newInstance() {
         return (NoteFragment) NoteFragment.instantiate(AppApplication.context, NoteFragment.class.getName());
    }

    @Override
    public MvpPullPresenter createPresenter() {
        presenter=new NotePresenter(this);
        return presenter;
    }

    @Override
    public BaseSwipeAdapter createAdapter(List list) {
        return new NoteAdapter(list);
    }
}
