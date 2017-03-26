package thereisnospon.acclient.modules.personal.search;

import java.util.List;

import thereisnospon.acclient.base.mvp.MvpPullView;
import thereisnospon.acclient.data.SearchPeopleItem;

/**
 * @author thereisnospon
 * 搜索用户 mvp 契约类
 * Created by yzr on 16/6/16.
 */
public interface SearchUserContact {
    
    interface View {
        void refreshPeople(List<SearchPeopleItem>list);
        void loadMorePeople(List<SearchPeopleItem>list);
        void onFailure(String err);
    }

    interface Presenter{
        void searchPeople(String key);
        void loadMorePeople(String key);
    }
    interface Model{
        List<SearchPeopleItem>searchPeople(String key);
        List<SearchPeopleItem>loadMorePeople(String key);
    }
}
