package thereisnospon.acclient.modules.submit.status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import thereisnospon.acclient.base.adapter.BasePullAdapter;
import thereisnospon.acclient.base.fragment.NormalPullFragment;
import thereisnospon.acclient.base.pullswipe.BaseSwipeAdapter;
import thereisnospon.acclient.base.pullswipe.BasicSwpiePullFragment;
import thereisnospon.acclient.data.SubmmitStatus;

import thereisnospon.acclient.ui.adapter.SubmitStatusAdapter;


public class SubmitFragment extends BasicSwpiePullFragment
implements  SubmitStatusContact.View{

    SubmitStatusContact.Presenter presenter;

    SubmitQuery query;

    public static SubmitFragment newInstance(SubmitQuery query){
        SubmitFragment fragment=new SubmitFragment();
        fragment.query=query;
        return fragment;
    }

    @Override
    public void start() {
        super.start();
        presenter=new SubmitStatusPresenter(this);
    }


    @Override
    public void onMoreStatus(List<SubmmitStatus> statusList) {
            onMoreData(statusList);
    }

    @Override
    public void onRefreshStatus(List<SubmmitStatus> statusList) {
        onRefreshData(statusList);
    }

    @Override
    public void onFailure(String err) {

    }

    @Override
    public BaseSwipeAdapter createAdapter(List list) {
        return new SubmitStatusAdapter(list);
    }

    @Override
    public boolean hasMore() {
        return true;
    }

    @Override
    public void loadMore() {
        presenter.moreStatus(query);
    }

    @Override
    public void refresh() {
        presenter.loadStatus(query);
    }
}
