package thereisnospon.acclient.modules.problem.list.search;

import java.util.List;

import thereisnospon.acclient.data.SearchProblem;

/**
 * @author thereisnospon
 * 搜索题目 mvp 契约类
 * Created by yzr on 16/6/10.
 */
public interface SearchProblemContact {


    interface View{
        void loadSccess(List<SearchProblem>list);
        void refreshSuccess(List<SearchProblem>list);
        void onFailure(String errMsg);
    }

    interface Presenter{
         void queryProblem(String query);
         void loadMoreQuery(String query);
    }

    interface Model{
        List<SearchProblem> queryProblem(String query);
        List<SearchProblem>loadMoreQuery(String query);
    }

}
