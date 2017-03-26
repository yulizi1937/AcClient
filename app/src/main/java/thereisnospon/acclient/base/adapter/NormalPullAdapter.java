package thereisnospon.acclient.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.R;

/**
 * Created by yzr on 16/8/20.
 *  实现了部分 下拉刷新，上拉加载 Adapter 的 功能。 如 ： Footer
 */
public abstract class NormalPullAdapter<T> extends BasePullAdapter<T> {


    List<T>mDataList;

    public NormalPullAdapter(List<T> list) {
        this.mDataList = list;
    }

    public static final int ITEM_TYPE=1;

    @Override
    public int getNormalItemViewType(int position) {
        return ITEM_TYPE;
    }

    public T getItem(int position){
        return mDataList.get(position);
    }

    @Override
    public int getNormalItemCount() {
        return mDataList==null?0:mDataList.size();
    }

    @Override
    public RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_footer,parent,false);
        return new FooterViewHolder(view);
    }

    @Override
    public void bindFooterViewHolder(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }
}
