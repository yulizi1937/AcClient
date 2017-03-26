package thereisnospon.acclient.modules.discuss;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;
import thereisnospon.acclient.api.HdojApi;
import thereisnospon.acclient.data.DiscussItem;
import thereisnospon.acclient.utils.net.HttpUtil;
import thereisnospon.acclient.utils.net.request.IRequest;

/**
 * @author therisnospon
 * 讨论模块的 mvp 的 Model
 * Created by yzr on 16/9/9.
 */
public class DiscussModel implements DiscussContact.Model {


    private String pid;
    private int currentpage=1;

    public DiscussModel() {
        currentpage=1;
    }
    public DiscussModel(String pid) {
        this.pid = pid;
        currentpage=1;
    }

    @Override
    public List<DiscussItem> requestRefresh() {
        currentpage=1;
        List<DiscussItem>list=getList(getHtml(currentpage));
        if(list!=null&&list.size()>0){
            currentpage++;
        }
        return list;
    }

    @Override
    public List<DiscussItem> requestMore() {
        List<DiscussItem>list=getList(getHtml(currentpage+1));
        if(list!=null&&list.size()>0){
            currentpage++;
        }
        return list;
    }




    private List<DiscussItem>getList(String html){
       return html==null?null:DiscussItem.Builder.parse(html);
    }


    private String getHtml(int page){

        HttpUtil http=HttpUtil.getInstance();
        IRequest request;
        if(pid!=null){
            request=http.get(HdojApi.DISUCSS_PROBLEM).addParameter("problemid",pid);
        }else{
            request=http.get(HdojApi.DISUCSS);
        }
        request.addParameter("page",page+"");
        try{
            Response respons=request.execute();
            String html=new String(respons.body().bytes(), HdojApi.HTML_CHAR_SET);
            return html;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
