package thereisnospon.acclient.modules.code;

import java.io.IOException;

import okhttp3.Response;
import thereisnospon.acclient.api.HdojApi;
import thereisnospon.acclient.data.CodeBuilder;
import thereisnospon.acclient.utils.net.HttpUtil;
import thereisnospon.acclient.utils.net.request.IRequest;

/**
 * @author thereisnospon
 * 代码显示模块的 Model
 * Created by yzr on 16/8/2.
 */
public class CodeModel implements CodeContact.Model {


    @Override
    public String loadCode(String id) {
        String html=getHtml(id);
        String code= CodeBuilder.parse(html);
        return code;
    }


    private String getHtml(String id ){
        IRequest request= HttpUtil.getInstance()
                .get(HdojApi.VIEW_CODE+id);
        try{
            Response response=request.execute();
            return new String(response.body().bytes(), HdojApi.HTML_CHAR_SET);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }
}
