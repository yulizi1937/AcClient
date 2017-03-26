package thereisnospon.acclient.base.mvp;

import java.util.List;

/**
 * @author thereisnospon
 * mvp 模式具有 刷新加载 公共功能的  Presenter 接口
 * Created by yzr on 17/3/26.
 */

public interface MvpPullPresenter<T> extends MvpPresenter {
    void requestRefresh();
    void requestMore();
}
