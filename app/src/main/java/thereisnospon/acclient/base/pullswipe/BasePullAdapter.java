package thereisnospon.acclient.base.pullswipe;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.R;

/**
 * @author thereisnospon
 * 上拉加载下拉刷新View 的基础 Adapter。
 * 存储数据类型的结构为 List
 * 提供默认的 Footer
 * Created by yzr on 17/3/26.
 */

public abstract class BasePullAdapter<T,VH extends  RecyclerView.ViewHolder> extends AbsPullAdapter<T,VH> {

    List<T> datas;

    @LayoutRes
    private static final int DEFAULT_FOOTVIEW_ID= R.layout.lib_pullswipe_default_footview;

    public BasePullAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getNormalItemViewType(int position) {
        return 1;
    }

    @Override
    public int getNormalItemCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View view= inflateView(parent,DEFAULT_FOOTVIEW_ID);
        return new DefaultFootViewHoler(view);
    }

    public static class DefaultFootViewHoler extends RecyclerView.ViewHolder{

        public DefaultFootViewHoler(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void bindFootViewHolder(RecyclerView.ViewHolder holder) {

    }

    public T getItem(int position){
        return datas.get(position);
    }

    //更多数据，并且刷新ui
    @Override
    public void onMoreData(List<T> list) {
        datas.addAll(list);
        notifyDataSetChanged();
    }

    //刷新数据，并且刷新ui
    @Override
    public void onRefreshData(List<T> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }
}
