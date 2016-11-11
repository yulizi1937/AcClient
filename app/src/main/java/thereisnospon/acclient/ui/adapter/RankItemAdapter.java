package thereisnospon.acclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.adapter.NormalSwipeAdapter;
import thereisnospon.acclient.data.RankItem;
import thereisnospon.acclient.databinding.ItemSearchPeopleBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.personal.UserDetailActivity;


public final class RankItemAdapter extends NormalSwipeAdapter<RankItem> {

	private static final int ITEM_LAYOUT = R.layout.item_search_people;

	public RankItemAdapter(List<RankItem> list) {
		super(list);
	}

	@Override
	public RecyclerView.ViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
		Context cxt = parent.getContext();
		ItemSearchPeopleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(cxt), ITEM_LAYOUT, parent, false);
		return new RankItemAdapter.VH(binding);
	}

	@Override
	public void bindNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		VH vh = (VH) viewHolder;
		final RankItem item = getItem(position);
		vh.mBinding.searchPeopleNickname.setText(item.getName());
		vh.mBinding.searchPeopleAc.setText(String.valueOf(item.getSolved()));
		vh.mBinding.searchPeopleNickname.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToDetail(v.getContext(), item.getId());
			}
		});
		vh.mBinding.executePendingBindings();
	}


	private void goToDetail(Context context, String id) {
		Intent intent = new Intent(context, UserDetailActivity.class);
		intent.putExtra(Arg.LOAD_USER_DETAIL, id);
		context.startActivity(intent);
	}


	static final class VH extends RecyclerView.ViewHolder {
		private final ItemSearchPeopleBinding mBinding;

		private VH(ItemSearchPeopleBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
	}
}
