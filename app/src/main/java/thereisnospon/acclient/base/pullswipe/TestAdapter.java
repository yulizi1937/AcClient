package thereisnospon.acclient.base.pullswipe;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thereisnospon.acclient.R;

/**
 * Created by yzr on 17/3/26.
 */

public class TestAdapter extends BaseSwipeAdapter<String,TestAdapter.VH> {


    public TestAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public VH createNormalViewHolder(ViewGroup parent, int viewType) {
        View view=inflateView(parent, R.layout.item_string);
        return new VH(view);
    }

    @Override
    public void bindNormalViewHolder(VH viewHolder, int position) {
        viewHolder.textView.setText(position+"");
    }

    public static class VH extends RecyclerView.ViewHolder{

        TextView textView;
        public VH(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text);
        }
    }
}
