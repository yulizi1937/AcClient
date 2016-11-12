package thereisnospon.acclient.modules.submit.status;

import java.util.List;

import thereisnospon.acclient.data.SubmmitStatus;

/**
 * Created by yzr on 16/6/18.
 */
public interface SubmitStatusContact {

    interface  View{
        void onMoreStatus(List<SubmmitStatus>statusList);
        void onRefreshStatus(List<SubmmitStatus>statusList);
        void onFailure(String err);
    }

    interface Presenter{
        void loadStatus(SubmitQuery query);
        void moreStatus(SubmitQuery query);
    }

    interface Model{
        List<SubmmitStatus>loadStatus(SubmitQuery query);
        List<SubmmitStatus>moreStatus(SubmitQuery query);
    }
}
