package thereisnospon.acclient.modules.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.base.adapter.BasePullAdapter;
import thereisnospon.acclient.base.fragment.BaseMvpSwipeFragment;
import thereisnospon.acclient.base.fragment.NormalPullFragment;
import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.data.RankItem;
import thereisnospon.acclient.ui.adapter.RankItemAdapter;

/**
 * Created by yzr on 16/8/27.
 */
public class RankFragment extends BaseMvpSwipeFragment<RankItem> implements RankContact.View{


    RankContact.Presenter presenter;

    public static RankFragment newInstance() {
        return (RankFragment) RankFragment.instantiate(AppApplication.context, RankFragment.class.getName());
    }

    @Override
    public MvpPullPresenter createPresenter() {
        presenter=new RankPresenter(this);
        return presenter;
    }

    @Override
    public BaseSwipeAdapter createAdapter(List list) {
        return new RankItemAdapter(list);
    }
}
