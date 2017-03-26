package thereisnospon.acclient.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.adapter.NormalPullAdapter;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.data.NoteItem;
import thereisnospon.acclient.databinding.ItemNoteBinding;


public final class NoteAdapter extends BaseSwipeAdapter<NoteItem,NoteAdapter.VH> {


	private static final int ITEM_LAYOUT = R.layout.item_note;

	public NoteAdapter(List<NoteItem> list) {
		super(list);
	}

	@Override
	public void bindNormalViewHolder(VH viewHolder, int position) {
		NoteItem noteItem = getItem(position);
		VH vh = (VH) viewHolder;
		vh.mBinding.noteTitle.setText(noteItem.getTitle());
		vh.mBinding.notePid.setText(noteItem.getPid());
		vh.mBinding.noteDate.setText(noteItem.getDate());
		vh.mBinding.executePendingBindings();
	}

	@Override
	public VH createNormalViewHolder(ViewGroup parent, int viewType) {
		Context cxt = parent.getContext();
		ItemNoteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(cxt), ITEM_LAYOUT, parent, false);
		return new VH(binding);
	}

	static final class VH extends RecyclerView.ViewHolder {
		private final ItemNoteBinding mBinding;

		private VH(ItemNoteBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
	}
}
