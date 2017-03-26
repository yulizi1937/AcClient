package thereisnospon.acclient.event;

/**
 * Created by yzr on 16/6/5.
 */

/**
 * @author thereisnospon
 * 使用EventBus 用来通信的消息
 * @param <T>
 */
public class Event<T> {

    private int eventCode;
    private T data;


    public static final Object EMPTY=new Object();

    public Event(T data){
        this.data=data;
        this.eventCode=EventCode.EVENT_DEFAULT_CODE;
    }
    public Event(T data,int eventCode){
        this.data=data;
        this.eventCode=eventCode;
    }

    public T getData(){
        return  data;
    }
    public int getEventCode() {
        return eventCode;
    }

}
