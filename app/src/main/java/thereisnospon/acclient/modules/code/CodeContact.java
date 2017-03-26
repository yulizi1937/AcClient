package thereisnospon.acclient.modules.code;

import thereisnospon.acclient.base.mvp.MvpModel;
import thereisnospon.acclient.base.mvp.MvpPresenter;
import thereisnospon.acclient.base.mvp.MvpView;

/**
 * @author thereisnospon
 * 代码加载显示模块的 mvp 的契约类
 * Created by yzr on 16/8/2.
 */
public interface CodeContact {

    interface View extends MvpView{
        void onSuccess(String code);
    }

    interface Model extends MvpModel{
        String loadCode(String id);
    }

    interface Presenter extends MvpPresenter{
        void loadCode(String id);
    }
}
