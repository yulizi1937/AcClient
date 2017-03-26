package thereisnospon.acclient.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yzr on 17/3/16.
 */

public abstract class BasicActivity  extends AppCompatActivity{

    @Override
   final protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeCreate();
        initData(savedInstanceState);
        initView(savedInstanceState);
        afterCreate();
    }

    public void beforeCreate(){

    }
    public abstract void initView(@Nullable Bundle savedInstanceState);
    public abstract void initData(@Nullable Bundle savedInstanceState);
    public void afterCreate(){

    }
}
