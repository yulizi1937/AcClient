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
import thereisnospon.acclient.base.fragment.NormalPullFragment;
import thereisnospon.acclient.data.NoteItem;
import thereisnospon.acclient.ui.adapter.NoteAdapter;

/**
 * Created by yzr on 16/9/9.
 */
public final class NoteFragment extends NormalPullFragment<NoteItem> implements NoteContact.View {


    private NoteContact.Presenter presenter;

    public static NoteFragment newInstance() {
         return (NoteFragment) NoteFragment.instantiate(AppApplication.context, NoteFragment.class.getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=normalView(inflater,container,savedInstanceState);
        presenter=new NotePresenter(this);
        return view;
    }

    @Override
    public BasePullAdapter<NoteItem> createItemAdapter(List<NoteItem> list) {
        return new NoteAdapter(list);
    }

    @Override
    public void loadMore() {
        presenter.moreNotes();
    }

    @Override
    public void refresh() {
        presenter.refreshNotes();
    }

    @Override
    public void onRefreshNotes(List<NoteItem> noteItemList) {
        notifyRefreshData(noteItemList);
    }

    @Override
    public void onMoreNotes(List<NoteItem> noteItems) {
        notifyMoreData(noteItems);
    }

    @Override
    public void onFailure(String msg) {
        Logger.d(msg);
    }
}
