package thereisnospon.acclient.modules.personal;

import thereisnospon.acclient.data.UserInfo;

/**
 * @author thereisnospon
 * 用户详细信息 mvp 契约类
 * Created by yzr on 16/6/18.
 */
public interface UserDetailContact  {
    interface View{
        void onSuccess(UserInfo userInfo);
        void onError(String err);
    }
    interface Presenter{
        void loadUser(String id);
    }
    interface Model{
        UserInfo loadUser(String id);
    }
}
