package thereisnospon.acclient.base.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import thereisnospon.acclient.R;

/**
 * Created by yzr on 17/3/16.
 * 有搜索框的 Activity 基类。
 */

public abstract class SearchBarActivity extends DrawerActivity implements  SearchView.OnQueryTextListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            inflateSearchMenu(menu);
            initSearch(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initSearch(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.ab_search);
        SearchView searchview = (SearchView) searchItem.getActionView();
        searchview.setOnQueryTextListener(this);
    }

    protected void inflateSearchMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
    }

}
