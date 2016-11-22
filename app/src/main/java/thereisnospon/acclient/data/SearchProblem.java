package thereisnospon.acclient.data;

import android.util.Log;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yzr on 16/6/7.
 */
public class SearchProblem extends ProblemItem
{

    String author;
    String source;

    public SearchProblem(int id, String title, String author, String source, int accepted, int submission) {
       super(id,title,accepted,submission);
        this.author = author;
        this.source = source;
    }

    public static class Builder{

        public static List<SearchProblem> parse(String html){

            Document document= Jsoup.parse(html);

            Element tbody=document.getElementsByTag("TBODY").first();

            Elements trs=tbody.getElementsByTag("tr");
            List<SearchProblem>list=new ArrayList<>();
            for(int i=0;i<trs.size();i++){
                Element tr=trs.get(i);
                if(tr.children().first().hasClass("TABLE_TEXT")){
                    SearchProblem problem=get(tr);
                    if(problem!=null){
                        list.add(problem);
                    }
                }
            }
            return list;
        }


        public static final String IMG_REGX="src=\"images/([\\S\\s]+?.gif)\"";

        private static int getStatus(Element element){
            if(element==null||element.html()==null)
                return ProblemItem.UN_KNOW;
            String html=element.html();
            Pattern pattern=Pattern.compile(IMG_REGX);
            Matcher matcher=pattern.matcher(html);
            if(matcher.find()){
                String img=matcher.group(1);
                if(img.equals("ac"))
                    return ProblemItem.ACCEPTED;
                else return ProblemItem.UN_SOLVED;
            }
            return ProblemItem.UN_SUBMMIT;
        }


        private static SearchProblem getWithLogin(Element tr){
            Elements tds=tr.getElementsByTag("td");
            int status=getStatus(tds.get(0));
            int id=Integer.parseInt(tds.get(1).text());
            String title=tds.get(2).text();
            String author=tds.get(3).text();
            String source=tds.get(4).text();
            String str=tds.get(5).text();
            String nums[]=str.split("[\\(\\)/%]");

            if(nums.length!=4)
                return null;
            int accepted=Integer.parseInt(nums[1]);
            int submission=Integer.parseInt(nums[2]);
            SearchProblem problem=new SearchProblem(id,title,author,source,accepted,submission);
            problem.setStatus(status);

            return problem;
        }
        private static SearchProblem getWithout(Element tr){
            Elements tds=tr.getElementsByTag("td");
            int id=Integer.parseInt(tds.get(0).text());
            String title=tds.get(1).text();
            String author=tds.get(2).text();
            String source=tds.get(3).text();
            String str=tds.get(4).text();
            String nums[]=str.split("[\\(\\)/%]");
            if(nums.length!=4)
                return null;
            int accepted=Integer.parseInt(nums[1]);
            int submission=Integer.parseInt(nums[2]);
            SearchProblem problem=new SearchProblem(id,title,author,source,accepted,submission);
            problem.setStatus(ProblemItem.UN_KNOW);
            return problem;
        }
        private static SearchProblem get(Element tr){
            Elements tds=tr.getElementsByTag("td");
            if(tds.size()==5){

                return getWithout(tr);
            }else{

                return getWithLogin(tr);
            }
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
