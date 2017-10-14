package chui.swsd.com.cchui.model;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\4 0004.
 */

public class KaoQinBean {

    /**
     * id : 1
     * time : 1504489074672
     * category : 0
     * userid : 1
     * address : 山西太原
     * longitude : 1.1
     * latitude : 1.1
     * state : 1
     */

    private int id;
    private double time;
    private int category;
    private int userid;
    private String address;
    private double longitude;
    private double latitude;
    private int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
