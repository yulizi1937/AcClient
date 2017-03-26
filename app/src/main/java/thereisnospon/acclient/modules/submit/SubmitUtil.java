package thereisnospon.acclient.modules.submit;

import java.io.IOException;

import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import thereisnospon.acclient.api.HdojApi;
import thereisnospon.acclient.utils.StringCall;
import thereisnospon.acclient.utils.net.HttpUtil;

/**
 * Created by yzr on 16/8/20.
 */
public class SubmitUtil {



    public static void submmit(
            final String problem, final String compiler,
            final String code, final StringCall call) {

        Observable.just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return getHtml(problem,compiler,code);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(call);

    }



    public static String getHtml(String problem,String language,String code){
        try{
            Response response=HttpUtil.getInstance()
                    .post(HdojApi.SUBMMIT)
                    .addParameter("check","0")
                    .addParameter("problemid",problem)
                    .addParameter("language",language)
                    .addParameter("usercode",code)
                    .execute();
            String html=new String(response.body().bytes(),"gb2312");
            return html;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }




}
