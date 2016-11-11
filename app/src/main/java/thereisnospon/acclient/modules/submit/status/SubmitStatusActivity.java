package thereisnospon.acclient.modules.submit.status;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import thereisnospon.acclient.base.activity.AppBarActivity;
import thereisnospon.acclient.event.Arg;

public final class SubmitStatusActivity extends AppBarActivity {
    private SubmitQuery parseQuery(){
        Intent intent=getIntent();
        String user=intent.getStringExtra(Arg.SUBMMIT_QUERY_USER);
        String status=intent.getStringExtra(Arg.SUBMMIT_QUERY_STATUS);
        String pid=intent.getStringExtra(Arg.SUBMMIT_QUERY_PID);

        SubmitQuery submitQuery =new SubmitQuery(user, SubmitQuery.Status.ALL);
        if(status==null){
            submitQuery =new SubmitQuery(user, null);
        }else if(status.equals(SubmitQuery.Status.AC.getValue())){
            submitQuery =new SubmitQuery(user, SubmitQuery.Status.AC);
        }

        if(pid!=null){
            submitQuery.setPid(pid);
        }
        return submitQuery;
    }


    @Override
    protected void setupContent(@NonNull FrameLayout contentLayout) {
        SubmitQuery query=parseQuery();
        setupFragment(contentLayout.getId(), SubmitFragment.newInstance(query));
    }
}
