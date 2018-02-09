package thereisnospon.acclient.utils.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import thereisnospon.acclient.AppApplication;
import thereisnospon.acclient.utils.net.cookie.CookiesManager;
import thereisnospon.acclient.utils.net.request.GetAuthRequest;
import thereisnospon.acclient.utils.net.request.GetRequest;
import thereisnospon.acclient.utils.net.request.PostRequest;

/**
 * Created by yzr on 16/6/5.
 */
public final class HttpUtil {

    public static final String TAG="HttpUtil";
    private OkHttpClient client;
    private static HttpUtil instance;


    private CookiesManager cookiesManager;

    private static class Holder{
        private static HttpUtil instance=new HttpUtil();
    }

    public CookiesManager getCookiesManager() {
        return cookiesManager;
    }

    private HttpUtil(){

        cookiesManager=new CookiesManager(AppApplication.context);
        client=new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        return null;
                    }
                })
                .cookieJar(cookiesManager).build();
    }

    public static HttpUtil getInstance(){
       return Holder.instance;
    }

    public  OkHttpClient.Builder newBuilder(){
        return client.newBuilder();
    }

    public GetAuthRequest authGet(String url){
        return new GetAuthRequest(url,client);
    }

    public GetRequest get(String url){
        return new GetRequest(url,client);
    }


    public PostRequest post(String url){
        //Log.d("NetActivityXXX","POST THREAD"+Thread.currentThread().getName());
        return new PostRequest(url,client);}
}