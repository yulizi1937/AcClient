package thereisnospon.acclient.base.pullswipe;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzr on 17/3/26.
 */

public class TestSwipeFragment extends BasicSwpiePullFragment {

    Handler handler=new Handler();

    private int cnt=0;


    @Override
    public BaseSwipeAdapter createAdapter(List list) {
        return new TestAdapter(list);
    }

    @Override
    public void loadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List <String>list=new ArrayList<String>();
                for(int i=0;i<20;i++)
                    list.add(""+(cnt++));
                onMoreData(list);
            }
        },1000);
    }

    @Override
    public void refresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cnt=0;
                List <String>list=new ArrayList<String>();
                for(int i=0;i<50;i++)
                    list.add(""+(cnt++));
                onRefreshData(list);
            }
        },1000);
    }

    @Override
    public boolean hasMore() {
        return true;
    }
}
