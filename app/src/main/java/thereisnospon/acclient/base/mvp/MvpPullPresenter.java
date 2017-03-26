package thereisnospon.acclient.base.mvp;

import java.util.List;

/**
 * Created by yzr on 17/3/26.
 */

public interface MvpPullPresenter<T> extends MvpPresenter {
    void requestRefresh();
    void requestMore();
}
