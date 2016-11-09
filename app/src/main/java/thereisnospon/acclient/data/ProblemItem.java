package thereisnospon.acclient.data;

/**
 * Created by yzr on 16/6/10.
 */


public class ProblemItem {

    int id;
    String title;
    int accepted;
    int submmision;

    public ProblemItem(int id, String title, int accepted, int submmision) {
        this.id = id;
        this.title = title;
        this.accepted = accepted;
        this.submmision = submmision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getSubmmision() {
        return submmision;
    }

    public void setSubmmision(int submmision) {
        this.submmision = submmision;
    }

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static final int UN_KNOW=-1;
    public static final int ACCEPTED=5;
    public static final int UN_SOLVED=6;
    public static final int UN_SUBMMIT=0;



}
