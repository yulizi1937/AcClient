package thereisnospon.acclient.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thereisnospon.acclient.R;

/**
 * Created by yzr on 16/8/20.
 *  下拉刷新，上拉加载 的 RecycleView 需要的 Adapter
 *  使用  一种特殊的 Item 作为 FooterView ，用来提示上拉加载过程
 */
public abstract class BasePullAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FOOTER_TYPE=1000;

    private boolean isShowFooter;


    // 常规 Adapter 的 Item 相关方法。
    public abstract int getNormalItemViewType(int position);
    public abstract int getNormalItemCount();
    public abstract RecyclerView.ViewHolder createNormalViewHolder(ViewGroup parent,int viewType);
    public abstract void bindNormalViewHolder(RecyclerView.ViewHolder viewHolder,int position);

    // 用最后一个 View 来显示上拉加载提示。子类提供实现
    public abstract RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent);
    public abstract void bindFooterViewHolder(RecyclerView.ViewHolder holder);


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==FOOTER_TYPE){
            return createFooterViewHolder(parent);
        }else{
            return createNormalViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isShowFooter&&position==getItemCount()-1){
            bindFooterViewHolder(holder);
        }else {
            bindNormalViewHolder(holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowFooter&&position==getItemCount()-1){
            return FOOTER_TYPE;
        }else{
            return getNormalItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return getNormalItemCount()+(isShowFooter?1:0);
    }


    protected static class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public boolean isFooter(int position){
        return isShowFooter&&position==getItemCount()-1;
    }

    public void onLoadMoreStateChange(boolean isShown) {
        this.isShowFooter= isShown;
        if (isShown) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

}