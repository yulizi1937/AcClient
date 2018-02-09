package thereisnospon.acclient.modules.submit.status;

import java.util.List;


import thereisnospon.acclient.base.pullswipe.BasePullAdapter;
import thereisnospon.acclient.base.pullswipe.BasePullFragment;
import thereisnospon.acclient.data.SubmmitStatus;

import thereisnospon.acclient.ui.adapter.SubmitStatusAdapter;


public class SubmitFragment extends BasePullFragment
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
    public BasePullAdapter createAdapter(List list) {
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
