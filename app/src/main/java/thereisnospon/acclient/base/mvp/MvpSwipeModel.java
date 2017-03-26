package thereisnospon.acclient.base.mvp;

import java.util.List;

/**
 * Created by yzr on 17/3/26.
 */

public interface MvpSwipeModel<T>  extends MvpModel {

    List<T> requestRefresh();
    List<T> requestMore();


}
