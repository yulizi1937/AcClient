package thereisnospon.acclient;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by yzr on 16/11/22.
 */

public class Msg {



    private static boolean DEBUG=true;


    public static void init(){
        Settings settings= Logger.init();
        settings.methodCount(2);
        settings.methodOffset(1);
    }


    public static void debug(boolean debug){
        DEBUG=debug;
    }


    private static String log(String line){
        return LOG_INFO+line;
    }

    public static final String LOG_INFO="---log info ----";
    public static final String VALUE_NULL=LOG_INFO+"the value is null !!\n";
    public static final String VALUE_TOSTR_NULL=LOG_INFO+"the value toString is null !!\n";
    public static final String COLLECTIONS_NULL=LOG_INFO+"the collections is null\n";
    public static final String ITERATIO_NULL=LOG_INFO+"the iterator is null\n";

    public static String line(Object object){
        if(object==null){
            return VALUE_NULL;
        }else{
            String str=object.toString();
            if(str==null){
                return VALUE_TOSTR_NULL;
            }else{
                return str+"\n";
            }
        }
    }

    private static String first(Object object){
        if(object==null)
            return line(object);
        StringBuilder sb=new StringBuilder();
        if(object instanceof  Object[]){
            for(Object obj:(Object[])object)
                sb.append(line(obj));
            return sb.toString();
        }else if(object instanceof int[]){
            return Arrays.toString((int[])object)+"\n";
        }else if(object instanceof long []){
            return Arrays.toString((long[])object)+"\n";
        }else if(object instanceof short[]){
            return Arrays.toString((short[])object)+"\n";
        }else if(object instanceof byte[]){
            return Arrays.toString((byte[])object)+"\n";
        }else if(object instanceof boolean[]){
            return Arrays.toString((boolean[])object)+"\n";
        }
        return line(object);
    }



    public static void dd(Object object,Object...objects){
        if(DEBUG){
            StringBuilder sb=new StringBuilder(first(object));
            for(Object obj:objects)
                sb.append(line(obj));
            Logger.d(sb.toString());
        }
    }



    public static void dd(Collection collections){
        if(DEBUG){
            StringBuilder sb=new StringBuilder();
            if(collections==null){
                Logger.d(COLLECTIONS_NULL);
                return;
            }
            Iterator it=collections.iterator();
            if(it==null){
                Logger.d(ITERATIO_NULL);
                return;
            }
            while (it.hasNext()){
                sb.append(line(it.next()));
            }
            Logger.d(sb.toString());
        }
    }


    public static void ee(Collection collections){
        if(DEBUG){
            StringBuilder sb=new StringBuilder();
            if(collections==null){
                Logger.e(COLLECTIONS_NULL);
                return;
            }
            Iterator it=collections.iterator();
            if(it==null){
                Logger.e(ITERATIO_NULL);
                return;
            }
            while (it.hasNext()){
                sb.append(line(it.next()));
            }
            Logger.e(sb.toString());
        }
    }



    public static void dd(String msg){
        if(DEBUG)
            Logger.d(msg);
    }

    public static void ee(String msg){
        if(DEBUG)
            Logger.e(msg);
    }

    public static void ee(Object object,Object...objects){
        if(DEBUG){
            StringBuilder sb=new StringBuilder(first(object));
            for(Object obj:objects)
                sb.append(line(obj));
            Logger.e(sb.toString());
        }
    }

    public static void d(String msg){
        if(DEBUG)
            Logger.d(msg);
    }

    public static void e(String msg){
        if(DEBUG)
            Logger.e(msg);
    }


    public static void t(Context context, String msg){
        if(DEBUG&&context!=null){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }
    }

    public static void s(View view, String msg){

    }


    public static void test(){

        dd(new int[]{1,2},1,2,4);
    }

}
