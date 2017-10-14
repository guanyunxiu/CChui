package chui.swsd.com.cchui.model;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\11 0011.
 */

public class ApprovedBean  {
    /**
     * id : 1
     * oid : 2
     * categorys : 0
     * usernames : 小红
     * userheads : /Beat/upload/1/headimg/4e724c49-5931-4aa8-80ad-c39a5d99340f.jpg
     * types : 2
     * times : 2017-08-31 17:57:12
     */

    private int id;
    private String oid;
    private int categorys;
    private String usernames;
    private String userheads;
    private int types;
    private String times;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getCategorys() {
        return categorys;
    }

    public void setCategorys(int categorys) {
        this.categorys = categorys;
    }

    public String getUsernames() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames = usernames;
    }

    public String getUserheads() {
        return userheads;
    }

    public void setUserheads(String userheads) {
        this.userheads = userheads;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
