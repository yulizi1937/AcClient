package thereisnospon.acclient.modules.rank;

import java.util.List;

import thereisnospon.acclient.base.mvp.MvpModel;
import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.mvp.MvpPullView;
import thereisnospon.acclient.base.mvp.MvpSwipeModel;
import thereisnospon.acclient.data.RankItem;

/**
 * @author thereisnospon
 * 用户排名 mvp 契约类
 * Created by yzr on 16/8/27.
 */
public interface RankContact {


    interface View extends MvpPullView<RankItem>{

    }

    interface Model extends MvpSwipeModel<RankItem> {

    }

    interface Presenter extends MvpPullPresenter<RankItem>{

    }
}
