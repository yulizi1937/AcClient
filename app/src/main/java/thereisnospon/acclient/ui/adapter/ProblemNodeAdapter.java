package thereisnospon.acclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.data.UserInfo;
import thereisnospon.acclient.databinding.ItemUserProNodeBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.submit.status.SubmitStatusActivity;

public final class ProblemNodeAdapter extends RecyclerView.Adapter<ProblemNodeAdapter.VH> {
	private static final int ITEM_LAYOUT = R.layout.item_user_pro_node;
	private final List<UserInfo.Node> nodes;
	private final String uid;

	public ProblemNodeAdapter(String userId, List<UserInfo.Node> solveds, List<UserInfo.Node> unsolved) {
		this.uid = userId;
		this.nodes = new ArrayList<>();
		for (UserInfo.Node n : solveds) {
			nodes.add(n);
		}
		for (UserInfo.Node n : unsolved) {
			nodes.add(n);
		}
	}

	@Override
	public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		Context cxt = parent.getContext();
		ItemUserProNodeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(cxt), ITEM_LAYOUT, parent, false);
		return new ProblemNodeAdapter.VH(binding);
	}


	@Override
	public void onBindViewHolder(VH holder, int position) {
		final UserInfo.Node node = nodes.get(position);
		holder.mBinding.userDetailNodeAc.setText(String.valueOf(node.getTag1()));
		holder.mBinding.userDetailNodeSubmmit.setText(String.valueOf(node.getTag2()));
		holder.mBinding.userDetailNodePid.setText(String.valueOf(node.getId()));
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toStatus(v.getContext(), String.valueOf(node.getId()));
			}
		});
		holder.mBinding.executePendingBindings();
	}

	private void toStatus(Context context, String pid) {
		Intent intent = new Intent(context, SubmitStatusActivity.class);
		intent.putExtra(Arg.SUBMMIT_QUERY_USER, uid);
		//intent.putExtra(Arg.SUBMMIT_QUERY_STATUS, SubmmitQuery.Status.ALL.getValue());
		intent.putExtra(Arg.SUBMMIT_QUERY_PID, pid);
		context.startActivity(intent);
	}


	@Override
	public int getItemCount() {
		return nodes != null ?
		       nodes.size() :
		       0;
	}

	static final class VH extends RecyclerView.ViewHolder {
		private final ItemUserProNodeBinding mBinding;

		private VH(ItemUserProNodeBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
	}
}
