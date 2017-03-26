package thereisnospon.acclient.modules.note;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import thereisnospon.acclient.api.HdojApi;
import thereisnospon.acclient.data.NoteItem;
import thereisnospon.acclient.utils.net.HttpUtil;
import thereisnospon.acclient.utils.net.request.IRequest;

/**
 * @author therisnospon
 * @// TODO: 17/3/26
 * Created by yzr on 16/9/9.
 */
public class NoteModel implements NoteContact.Model {


    private int currentPage=1;


    public NoteModel() {
        currentPage=1;
    }


    private List<NoteItem>getList(String html){
        return html==null?null:NoteItem.Builder.parse(html);
    }
    private String getHtml(int page){
        IRequest request= HttpUtil.getInstance()
                .get(HdojApi.NOTE)
                .addParameter("page",page+"");
        try{
            Response response=request.execute();
            String html=new String(response.body().bytes(),"gb2312");
            return html;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<NoteItem> requestRefresh() {
        currentPage=1;
        List<NoteItem>list=getList(getHtml(currentPage));
        if(list!=null&&list.size()>0){
            currentPage++;
        }
        return list;
    }

    @Override
    public List<NoteItem> requestMore() {
        List<NoteItem>list=getList(getHtml(currentPage+1));
        if(list!=null&&list.size()>0){
            currentPage++;
        }
        return list;
    }
}
