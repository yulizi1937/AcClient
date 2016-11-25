package thereisnospon.acclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.adapter.NormalSwipeAdapter;
import thereisnospon.acclient.data.DiscussItem;
import thereisnospon.acclient.databinding.ItemDiscussBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.discuss.DiscussHelper;
import thereisnospon.acclient.modules.code.CodeActivity;


public final class DiscussItemAdapter extends NormalSwipeAdapter<DiscussItem> {

	private static final int ITEM_LAYOUT = R.layout.item_discuss;

	public DiscussItemAdapter(List<DiscussItem> list) {
		super(list);
	}

	@Override
	public RecyclerView.ViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
		Context cxt = parent.getContext();
		ItemDiscussBinding binding = DataBindingUtil.inflate(LayoutInflater.from(cxt), ITEM_LAYOUT, parent, false);
		return new VH(binding);
	}

	@Override
	public void bindNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		final DiscussItem discussItem = getItem(position);
		final VH vh = (VH) viewHolder;
		vh.mBinding.discussPid.setText(discussItem.getPid());
		vh.mBinding.discussTitle.setText(discussItem.getTitle());
		vh.mBinding.disucssUsername.setText(discussItem.getUserName());
		vh.mBinding.discussDate.setText(discussItem.getDate());
		vh.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toDiscuss(vh.mBinding.discussDate.getContext(), discussItem.getDiscussUrl());
			}
		});
		vh.mBinding.executePendingBindings();
	}

	private void toDiscuss(final Context context, String url) {

		DiscussHelper.toDiscuss(url, new DiscussHelper.DiscussCall() {
			@Override
			public void onSuccess(String code) {
				Intent intent = new Intent(context, CodeActivity.class);
				intent.putExtra(Arg.SHOWCODE_CODE, code);
				context.startActivity(intent);
			}

			@Override
			public void onFailure(String err) {
				Toast.makeText(context, err, Toast.LENGTH_SHORT)
				     .show();
			}
		});
	}

	static final class VH extends RecyclerView.ViewHolder {
		private final ItemDiscussBinding mBinding;

		private VH(ItemDiscussBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
	}
}
