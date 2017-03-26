package thereisnospon.acclient.modules.submit.status;

/**
 * @author thereisnospon
 * 提交状态查询命令类
 * Created by yzr on 16/7/30.
 */
public class SubmitQuery {

    private String user;
    private Status status;

    public SubmitQuery(String user, Status status){
        this.user=user;
        this.status=status;
    }


    private String pid;

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return query();
    }

    public String query(){
        StringBuilder builder=new StringBuilder();
        if(user!=null)
            builder.append("&user="+user);
        if(status!=null)
            builder.append("&status="+status.value);
        if(pid!=null)
            builder.append("&pid="+pid);
        return builder.toString();
    }

    public enum Status{
        AC("5"),ALL("#status");
        private String value;
        public String getValue(){
            return value;
        }
        Status(String val){
            value=val;
        }
    }



}

