package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\19 0019.
 */

public class CompanyDetailBean {
    /**
     * company : {"id":12,"name":"山西世恒铁路技术有限公司","province":"山西省","city":"太原市","county":"小店区","address":"技术路22号","longitude":112.557736,"latitude":37.783243,"starttime":"31200000","endtime":"64800000","scale":"1-10人","account":"15388888888","pass":"123456","inbox":"111","userid":null,"id1":null,"id2":null,"id3":null,"id4":null,"id5":null}
     * users : [{"userid":37,"name":"武帅龙","headimg":null}]
     */

    private CompanyBean company;
    private List<UsersBean> users;

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class CompanyBean {
        /**
         * id : 12
         * name : 山西世恒铁路技术有限公司
         * province : 山西省
         * city : 太原市
         * county : 小店区
         * address : 技术路22号
         * longitude : 112.557736
         * latitude : 37.783243
         * starttime : 31200000
         * endtime : 64800000
         * scale : 1-10人
         * account : 15388888888
         * pass : 123456
         * inbox : 111
         * userid : null
         * id1 : null
         * id2 : null
         * id3 : null
         * id4 : null
         * id5 : null
         */

        private int id;
        private String name;
        private String province;
        private String city;
        private String county;
        private String address;
        private double longitude;
        private double latitude;
        private String starttime;
        private String endtime;
        private String scale;
        private String account;
        private String pass;
        private String inbox;
        private Object userid;
        private Object id1;
        private Object id2;
        private Object id3;
        private Object id4;
        private Object id5;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
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

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getScale() {
            return scale;
        }

        public void setScale(String scale) {
            this.scale = scale;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getInbox() {
            return inbox;
        }

        public void setInbox(String inbox) {
            this.inbox = inbox;
        }

        public Object getUserid() {
            return userid;
        }

        public void setUserid(Object userid) {
            this.userid = userid;
        }

        public Object getId1() {
            return id1;
        }

        public void setId1(Object id1) {
            this.id1 = id1;
        }

        public Object getId2() {
            return id2;
        }

        public void setId2(Object id2) {
            this.id2 = id2;
        }

        public Object getId3() {
            return id3;
        }

        public void setId3(Object id3) {
            this.id3 = id3;
        }

        public Object getId4() {
            return id4;
        }

        public void setId4(Object id4) {
            this.id4 = id4;
        }

        public Object getId5() {
            return id5;
        }

        public void setId5(Object id5) {
            this.id5 = id5;
        }
    }

    public static class UsersBean {
        /**
         * userid : 37
         * name : 武帅龙
         * headimg : null
         */

        private int userid;
        private String name;
        private Object headimg;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getHeadimg() {
            return headimg;
        }

        public void setHeadimg(Object headimg) {
            this.headimg = headimg;
        }
    }


   /*
    *//**
     * id : 1
     * name : 山西赛文思达科技有限公司
     * province : 山西
     * city : 太原
     * county : 小店区
     * address : 山西太原小店区电子商务产业园
     * longitude : 112.557582
     * latitude : 37.783235
     * starttime : 32400
     * endtime : 64800
     * scale : 100
     * account : 18735195753
     * pass : 123456
     * inbox : abcdefg
     * id1 : null
     * id2 : null
     * id3 : null
     * id4 : null
     * id5 : null
     *//*

    private int id;
    private String name;
    private String province;
    private String city;
    private String county;
    private String address;
    private double longitude;
    private double latitude;
    private String starttime;
    private String endtime;
    private String scale;
    private String account;
    private String pass;
    private String inbox;
    private Object id1;
    private Object id2;
    private Object id3;
    private Object id4;
    private Object id5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getInbox() {
        return inbox;
    }

    public void setInbox(String inbox) {
        this.inbox = inbox;
    }

    public Object getId1() {
        return id1;
    }

    public void setId1(Object id1) {
        this.id1 = id1;
    }

    public Object getId2() {
        return id2;
    }

    public void setId2(Object id2) {
        this.id2 = id2;
    }

    public Object getId3() {
        return id3;
    }

    public void setId3(Object id3) {
        this.id3 = id3;
    }

    public Object getId4() {
        return id4;
    }

    public void setId4(Object id4) {
        this.id4 = id4;
    }

    public Object getId5() {
        return id5;
    }

    public void setId5(Object id5) {
        this.id5 = id5;
    }*/
}
