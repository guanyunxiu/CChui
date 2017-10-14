package chui.swsd.com.cchui.net.util;


import java.util.List;

/**
 * 网络返回基类 支持泛型
 * Created by Tamic on 2016-06-06.
 */
public class BaseResponse<T> {

    private int code;
    private String msg;
    private String sessionId;
    private T user;
    private List<T> list;
    private String srvTime;
    private T obj;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isOk() {
        return code == 0;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public String getSrvTime() {
        return srvTime;
    }

    public void setSrvTime(String srvTime) {
        this.srvTime = srvTime;
    }


}
