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
import thereisnospon.acclient.base.pullswipe.BasePullAdapter;
import thereisnospon.acclient.data.ProblemItem;
import thereisnospon.acclient.data.SearchProblem;
import thereisnospon.acclient.databinding.ItemListProblemBinding;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.problem.detail.ShowProblemActivity;
import thereisnospon.acclient.widget.Colors;


public final class SearchProblemAdapter extends BasePullAdapter<SearchProblem,SearchProblemAdapter.VH> {





	private static final int ITEM_LAYOUT = R.layout.item_list_problem;

	public SearchProblemAdapter(List<SearchProblem> list) {
		super(list);
	}

	@Override
	public VH createNormalViewHolder(ViewGroup parent, int viewType) {
		Context cxt = parent.getContext();
		ItemListProblemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(cxt), ITEM_LAYOUT, parent, false);
		return new SearchProblemAdapter.VH(binding);
	}

	private void goToProblemDetail(Context context, int id) {
		Intent intent = new Intent(context, ShowProblemActivity.class);
		intent.putExtra(Arg.LOAD_PROBLEM_DETAIL, id);
		context.startActivity(intent);
	}

	@Override
	public void bindNormalViewHolder(VH viewHolder, int position) {
		VH vh = (VH) viewHolder;
		final ProblemItem problem = getItem(position);
		vh.mBinding.problemTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToProblemDetail(v.getContext(), problem.getId());
			}
		});
		vh.mBinding.problemTitle.setText(problem.getTitle());
		vh.mBinding.problemId.setText(String.valueOf(problem.getId()));
		vh.mBinding.problemAc.setText(String.valueOf(problem.getAccepted()));
		vh.mBinding.problemSubmmit.setText(String.valueOf(problem.getSubmmision()));

		if(problem.getStatus()==ProblemItem.ACCEPTED){
			vh.mBinding.problemId.setText(String.valueOf(problem.getId())+"\t(AC)");
			vh.mBinding.problemId.setTextColor(Colors.GREEN);
		}else if(problem.getStatus()==ProblemItem.UN_SOLVED){
			vh.mBinding.problemId.setTextColor(Colors.YELLOW);
			vh.mBinding.problemId.setText(String.valueOf(problem.getId())+"\t(UAC)");
		}else {
			vh.mBinding.problemId.setTextColor(Colors.GRAY);
		}
		vh.mBinding.executePendingBindings();
	}

	static final class VH extends RecyclerView.ViewHolder {
		private final ItemListProblemBinding mBinding;

		private VH(ItemListProblemBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
	}
}
