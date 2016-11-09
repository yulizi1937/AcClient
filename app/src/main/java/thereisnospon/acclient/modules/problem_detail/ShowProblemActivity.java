package thereisnospon.acclient.modules.problem_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import thereisnospon.acclient.R;
import thereisnospon.acclient.base.activity.DrawerActivity;
import thereisnospon.acclient.event.Arg;
import thereisnospon.acclient.modules.discuss.DiscussActivity;

public final class ShowProblemActivity extends DrawerActivity {

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_problem);
        initDrawer();
        id=getIntent().getIntExtra(Arg.LOAD_PROBLEM_DETAIL,1000);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content,ShowProblemFragment.newInstance(id))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_problem_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.problem_discuss){
            Intent intent=new Intent(this, DiscussActivity.class);
            intent.putExtra(Arg.PROBLEM_DISUCSS,id+"");
            startActivity(intent);
            return true;
        }else return false;
    }
}
