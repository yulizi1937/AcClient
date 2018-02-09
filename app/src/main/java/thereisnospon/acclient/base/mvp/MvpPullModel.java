package thereisnospon.acclient.base.mvp;

import java.util.List;

/**
 * @author thereisnospon
 * mvp 模式具有刷新加载公共功能的 Model 接口
 * Created by yzr on 17/3/26.
 */

public interface MvpPullModel<T>  extends MvpModel {

    List<T> requestRefresh();
    List<T> requestMore();


}
