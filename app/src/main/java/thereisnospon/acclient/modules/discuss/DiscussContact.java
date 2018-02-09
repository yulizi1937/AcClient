package thereisnospon.acclient.modules.discuss;

import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.mvp.MvpPullView;
import thereisnospon.acclient.base.mvp.MvpPullModel;
import thereisnospon.acclient.data.DiscussItem;

/**
 * @author thereisnospon
 * 讨论模块 的 mvp 的契约类
 * Created by yzr on 16/9/9.
 */
public interface DiscussContact {

    interface View extends MvpPullView<DiscussItem>{
    }
    interface Presenter extends MvpPullPresenter<DiscussItem>{
    }
    interface Model extends MvpPullModel<DiscussItem> {

    }
}
