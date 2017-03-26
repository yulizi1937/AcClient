package thereisnospon.acclient.base.mvp;

import java.util.List;

/**
 * Created by yzr on 17/3/26.
 */

public interface MvpPullView<T> extends MvpView {
    public void  onMoreSuccess(List<T> list);
    public void  onRefreshSuccess(List<T> list);
}
