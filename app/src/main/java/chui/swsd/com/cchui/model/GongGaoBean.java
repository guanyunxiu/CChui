package chui.swsd.com.cchui.model;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\20 0020.
 */

public class GongGaoBean {

    /**
     * id : 3
     * title : 公告
     * content : 内容
     * time : 2017-09-18 18:18:37
     */

    private int id;
    private String title;
    private String content;
    private String accepterid;
    private String time;
    private int userid;
    private String username;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAccepterid() {
        return accepterid;
    }

    public void setAccepterid(String accepterid) {
        this.accepterid = accepterid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
