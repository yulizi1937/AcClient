package thereisnospon.acclient.modules.problem.list;

import java.util.List;

import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.mvp.MvpPullView;
import thereisnospon.acclient.base.mvp.MvpPullModel;
import thereisnospon.acclient.data.HdojProblem;

/**
 * @author thereisnospon
 * 显示首页 mvp 契约类
 * Created by yzr on 16/6/5.
 */

public interface HdojContact {

    public interface View extends MvpPullView<HdojProblem>{

    }

    public interface Presenter extends MvpPullPresenter<HdojProblem>{
        public void  loadPage(int page);
    }

    public interface Model extends MvpPullModel<HdojProblem> {
        public List<HdojProblem>loadPage(int page);
    }
}
