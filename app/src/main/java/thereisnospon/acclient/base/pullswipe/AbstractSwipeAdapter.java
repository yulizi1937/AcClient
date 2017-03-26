package thereisnospon.acclient.base.pullswipe;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author thereisnospon
 *  上拉加载下拉刷新View 的 Adapter
 *  用于作为提示上了加载的 Footer 作为最后一个 View 放在最后的位置
 * Created by yzr on 17/3/26.
 */

public abstract class AbstractSwipeAdapter<T,VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FOOTER_TYPE=1000;

    private boolean isShowFooter;

    //子类实现，代替原 getItemViewType,getItemCount
    public abstract int getNormalItemViewType(int position);
    public abstract int getNormalItemCount();

    //子类实现，创建 Footer （提示上拉加载View）
    public abstract RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent);
    public abstract void  bindFootViewHolder(RecyclerView.ViewHolder holder);

    //更多数据和数据更新
    public abstract void  onMoreData(List<T> list);
    public abstract void  onRefreshData(List<T> list);

    //代替原来的 onCreateViewHolder 和 bindViewHolder
    public abstract VH  createNormalViewHolder(ViewGroup parent, int viewType);
    public abstract void bindNormalViewHolder(VH viewHolder,int position);


    //根据 ViewType 判断是 普通的item 还是 Footer
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
            bindNormalViewHolder((VH)holder,position);
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



    private void bindFooterViewHolder(RecyclerView.ViewHolder holder){
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
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

    public<T extends View> T inflateView(ViewGroup parent, @LayoutRes int res){
        return(T) LayoutInflater.from(parent.getContext()).inflate(res,parent,false);
    }



}