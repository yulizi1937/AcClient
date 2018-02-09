package thereisnospon.acclient.modules.note;

import java.util.List;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.base.fragment.BaseMvpPullFragment;

import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BasePullAdapter;
import thereisnospon.acclient.data.NoteItem;
import thereisnospon.acclient.ui.adapter.NoteAdapter;

/**
 * @author thereisnospon
 * @// TODO: 17/3/26
 * Created by yzr on 16/9/9.
 */
public final class NoteFragment extends BaseMvpPullFragment<NoteItem> implements NoteContact.View {


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
    public BasePullAdapter createAdapter(List list) {
        return new NoteAdapter(list);
    }
}
