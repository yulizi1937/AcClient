package thereisnospon.acclient.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.adapter.NormalSwipeAdapter;
import thereisnospon.acclient.data.NoteItem;
import thereisnospon.acclient.databinding.ItemNoteBinding;


public final class NoteAdapter extends NormalSwipeAdapter<NoteItem> {


	private static final int ITEM_LAYOUT = R.layout.item_note;

	public NoteAdapter(List<NoteItem> list) {
		super(list);
	}

	@Override
	public RecyclerView.ViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
		Context cxt = parent.getContext();
		ItemNoteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(cxt), ITEM_LAYOUT, parent, false);
		return new NoteAdapter.VH(binding);
	}

	@Override
	public void bindNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		NoteItem noteItem = getItem(position);
		VH vh = (VH) viewHolder;
		vh.mBinding.noteTitle.setText(noteItem.getTitle());
		vh.mBinding.notePid.setText(noteItem.getPid());
		vh.mBinding.noteDate.setText(noteItem.getDate());
		vh.mBinding.executePendingBindings();
	}


	static final class VH extends RecyclerView.ViewHolder {
		private final ItemNoteBinding mBinding;

		private VH(ItemNoteBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
	}
}
