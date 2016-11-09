package thereisnospon.acclient.modules.hello;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import java.util.regex.Pattern;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.R;
import thereisnospon.acclient.utils.StringCall;

/**
 * Created by yzr on 16/10/30.
 */

class HelloPresenter implements HelloContact.Presenter {


    private final HelloContact.Model model;
    private final HelloContact.View view;

    HelloPresenter(HelloContact.View view) {
        this.view = view;
        this.model=new HelloModel();
    }

    @Override
    public void login(final String name, final String message) {

        Observable.just(name)
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return model.login(name,message);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new StringCall() {
                    @Override
                    public void success(String nickName) {
                        view.onLoginSuccess(nickName);
                    }

                    @Override
                    public void failure(String msg) {
                        view.onLoginFailure(msg);
                    }
                });

    }

    @Override
    public void register(final String name, final String email, final String password, final String checkPassword, final String check) {
        if(!check(name,email,password,checkPassword)){
            return;
        }
        Observable.just(name)
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return model.register(name,email,password,checkPassword,check);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new StringCall() {
                    @Override
                    public void success(String nickName) {
                        view.onRegisterSuccess(nickName);
                    }

                    @Override
                    public void failure(String msg) {
                        view.onRegisterFailure("注册失败");
                    }
                });
    }


    private static final String CHECK_EMAIL_REGEX="^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
    private static final Pattern pattern;
    static {
        pattern=Pattern.compile(CHECK_EMAIL_REGEX);
    }


    private static boolean checkEmail(String email){
        return email!=null&&pattern.matcher(email).matches();
    }


    private boolean check(String name, String email, String password, String checkPassword){
        Context cxt = AppApplication.context;
        if(TextUtils.isEmpty(name)){
            view.onRegisterFailure(cxt.getString(R.string.hello_no_empty_username));
            return false;
        }

        if(!checkEmail(email)){
            view.onRegisterFailure(cxt.getString(R.string.hello_wrong_email));
            return false;
        }

        if(TextUtils.isEmpty(password)){
           view.onRegisterFailure(cxt.getString(R.string.hello_no_empty_password));
            return false;
        }

        if(!password.equals(checkPassword)){
            view.onRegisterFailure(cxt.getString(R.string.hello_password_not_equal));
            return false;
        }

        if(password.length()<6){
            view.onRegisterFailure(cxt.getString(R.string.hello_password_short));
            return false;
        }

        return true;
    }


    @Override
    public void loadCheckCode() {
        Observable.just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        return model.checkCode();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        if (bitmap != null) {
                            view.onCheckCode(bitmap);
                        } else {
                            view.onCheckCodeErr("null");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.onCheckCodeErr(throwable.getMessage());
                    }
                });
    }
}
