package thereisnospon.acclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.adapter.NormalPullAdapter;
import thereisnospon.acclient.data.SubmmitStatus;
import thereisnospon.acclient.databinding.ItemSubmmitStatusBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.code.CodeActivity;
import thereisnospon.acclient.utils.SpUtil;


public final class SubmitStatusAdapter extends NormalPullAdapter<SubmmitStatus> {


	private static final int ITEM_LAYOUT = R.layout.item_submmit_status;

	public SubmitStatusAdapter(List<SubmmitStatus> list) {
		super(list);
	}

	@Override
	public RecyclerView.ViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
		Context cxt = parent.getContext();
		ItemSubmmitStatusBinding binding = DataBindingUtil.inflate(LayoutInflater.from(cxt), ITEM_LAYOUT, parent, false);
		return new SubmitStatusAdapter.ItemViewHolder(binding);
	}

	@Override
	public void bindNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		final SubmmitStatus status = getItem(position);
		final ItemViewHolder vh = (ItemViewHolder) viewHolder;
		vh.mBinding.sbStatus.setText(status.getStatus());
		vh.mBinding.sbName.setText(status.getUserName());
		vh.mBinding.sbUsedt.setText(status.getUsedTime());
		vh.mBinding.sbMemory.setText(status.getUsedMemory());
		vh.mBinding.sbLength.setText(status.getCodeLen());
		vh.mBinding.sbLanguage.setText(status.getLanguage());
		vh.mBinding.sbDate.setText(status.getDate());
		vh.mBinding.sbPid.setText(status.getProblemId());
		vh.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toSubmmitCode(v, status.getSubmmitId(), status.getUserId());
			}
		});
		vh.mBinding.executePendingBindings();
	}

	private void toSubmmitCode(View view, String id, String userId) {

		SpUtil spUtil = SpUtil.getInstance();
		Context context = view.getContext();
		if (userId != null && userId.equals(spUtil.getString(SpUtil.NAME))) {
			Intent intent = new Intent(context, CodeActivity.class);
			intent.putExtra(Arg.SHOWCODE_ID, id);
			context.startActivity(intent);
		} else {
			Snackbar.make(view, "只能查看自己的代码", Snackbar.LENGTH_SHORT)
			        .show();
		}
	}


	static final class ItemViewHolder extends RecyclerView.ViewHolder {
		private final ItemSubmmitStatusBinding mBinding;

		private ItemViewHolder(ItemSubmmitStatusBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
	}

}
