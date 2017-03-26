package thereisnospon.acclient.base.mvp;

import java.util.List;

/**
 * @author thereisnospon
 * mvp 模式 具有 刷新加载公共功能的 View 接口
 * Created by yzr on 17/3/26.
 */

public interface MvpPullView<T> extends MvpView {
    public void  onMoreSuccess(List<T> list);
    public void  onRefreshSuccess(List<T> list);
}
