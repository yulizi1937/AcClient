package thereisnospon.acclient.base.pullswipe;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.R;

/**
 * Created by yzr on 17/3/26.
 */

public abstract class BaseSwipeAdapter<T,VH extends  RecyclerView.ViewHolder> extends AbstractSwipeAdapter<T,VH> {

    List<T> datas;

    @LayoutRes
    private static final int DEFAULT_FOOTVIEW_ID= R.layout.lib_pullswipe_default_footview;

    public BaseSwipeAdapter(List<T> datas) {
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

    @Override
    public void onMoreData(List<T> list) {
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onRefreshData(List<T> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }
}
