package thereisnospon.acclient.base.fragment;

import android.util.Log;

import java.util.List;

import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.mvp.MvpPullView;
import thereisnospon.acclient.base.pullswipe.BasePullFragment;

/**
 * @author thereisnospon
 * mvp 模式下具有上拉加载下拉刷新公共功能的 Fragment
 * Created by yzr on 17/3/26.
 */

public abstract class BaseMvpPullFragment<T>  extends BasePullFragment implements MvpPullView<T> {

    MvpPullPresenter presenter;

    public abstract MvpPullPresenter createPresenter();

    @Override
    public void start() {
        presenter=createPresenter();
    }

    @Override
    public void loadMore() {
        presenter.requestMore();
    }

    @Override
    public void refresh() {
        presenter.requestRefresh();
    }

    @Override
    public boolean hasMore() {
        return true;
    }

    @Override
    public void onFailure(String msg) {
        Log.e("ERROR",msg);
    }

    @Override
    public void onMoreSuccess(List<T> list) {
        onMoreData(list);
    }

    @Override
    public void onRefreshSuccess(List<T> list) {
        onRefreshData(list);
    }


}
