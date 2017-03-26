package thereisnospon.acclient.modules.problem.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.base.adapter.BasePullAdapter;
import thereisnospon.acclient.base.fragment.BaseMvpSwipeFragment;
import thereisnospon.acclient.base.fragment.BasePullFragment;
import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.data.HdojProblem;
import thereisnospon.acclient.ui.adapter.HdojProblemAdapter;

/**
 * @author thereisnospon
 * 首页题目 Fragment
 * Created by yzr on 16/6/5.
 */
public final class HdojProblemFragment extends BaseMvpSwipeFragment<HdojProblem> implements  HdojContact.View{
    private static final int INDEX=-1;
    private HdojContact.Presenter presenter;
    private int page=INDEX;

    private static final String SAVE_PAGE="SAVE_PAGE";

    public static  HdojProblemFragment newInstance(int page){
        HdojProblemFragment fragment= HdojProblemFragment.newInstance();
        fragment.page=page;
        return fragment;
    }

    public static  HdojProblemFragment newInstance(){
       return (HdojProblemFragment) HdojProblemFragment.instantiate(AppApplication.context, HdojProblemFragment.class.getName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_PAGE, page);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState == null){
            return;
        }
        page = savedInstanceState.getInt(SAVE_PAGE);
    }



    @Override
    public boolean hasMore() {
        return page==INDEX;
    }


    @Override
    public MvpPullPresenter createPresenter() {
        presenter=new HdojPresenterImpl(this,page);
        return presenter;
    }

    @Override
    public BaseSwipeAdapter createAdapter(List list) {
        return new HdojProblemAdapter(list);
    }
}