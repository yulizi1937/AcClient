package thereisnospon.acclient.modules.rank;

import java.util.List;

import thereisnospon.acclient.AppApplication;

import thereisnospon.acclient.base.fragment.BaseMvpPullFragment;

import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.pullswipe.BasePullAdapter;
import thereisnospon.acclient.data.RankItem;
import thereisnospon.acclient.ui.adapter.RankItemAdapter;

/**
 * Created by yzr on 16/8/27.
 */
public class RankFragment extends BaseMvpPullFragment<RankItem> implements RankContact.View{


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
    public BasePullAdapter createAdapter(List list) {
        return new RankItemAdapter(list);
    }
}
