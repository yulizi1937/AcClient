package thereisnospon.acclient.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import thereisnospon.acclient.base.util.SavedField;

/*
 * @author  thereisnospon
 * Fragment 基类
 * Created by yzr on 17/3/26.
 */

public  abstract class BasicFragment  extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=createView(container,savedInstanceState);
        if(view==null){
             int layout=getLayoutRes();
             view=inflater.inflate(layout,container,false);
        }
        if(savedInstanceState!=null)
            onRestoreInstanceState(savedInstanceState);
        start();
        initView(view,savedInstanceState);
        initData();
        return view;
    }

    public View createView(ViewGroup container,Bundle savedInstanceState){
        return null;
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
