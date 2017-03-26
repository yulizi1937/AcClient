package thereisnospon.acclient.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yzr on 17/3/26.
 */

public  abstract class BasicFragment  extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout=getLayoutRes();
        View view=inflater.inflate(layout,container,false);
        if(savedInstanceState!=null)
            onRestoreInstanceState(savedInstanceState);
        start();
        initView(view,savedInstanceState);
        initData();
        return view;
    }

    @LayoutRes public abstract int getLayoutRes();
    public abstract void initView(View view,Bundle savedInstanceState);
    public abstract void initData();

    public void start(){

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle inState){

    }

}
