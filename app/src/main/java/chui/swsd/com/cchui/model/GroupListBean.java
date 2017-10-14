package chui.swsd.com.cchui.model;

import java.io.Serializable;
import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class GroupListBean {

    /**
     * id : 5
     * name : 都删点
     * headimg : /beat/upload/1/groupheadimg/958951c3-86c5-4574-aa91-86c337a7a2a3.JPG
     * remark : 交流讨
     * userid : 1
     * rongid : 9d11339a9db54a169e332af98c9e8ade
     * time : 2017-08-31 16:18:26
     */

    private int id;
    private String name;
    private String headimg;
    private String remark;
    private int userid;
    private String rongid;
    private String time;
    private int groupinfoSize;
    private List<UserSimple> userSimples;

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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getRongid() {
        return rongid;
    }

    public void setRongid(String rongid) {
        this.rongid = rongid;
    }

    public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public int getGroupinfoSize() {
        return groupinfoSize;
    }

    public void setGroupinfoSize(int groupinfoSize) {
        this.groupinfoSize = groupinfoSize;
    }

    public List<UserSimple> getUserSimples() {
        return userSimples;
    }

    public void setUserSimples(List<UserSimple> userSimples) {
        this.userSimples = userSimples;
    }

    public class UserSimple implements Serializable{
        private int userid;
        private String username;
        private String card;
        private String phone;
        private String userheadimg;
        private int groupinfoid;

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

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserheadimg() {
            return userheadimg;
        }

        public void setUserheadimg(String userheadimg) {
            this.userheadimg = userheadimg;
        }

        public int getGroupinfoid() {
            return groupinfoid;
        }

        public void setGroupinfoid(int groupinfoid) {
            this.groupinfoid = groupinfoid;
        }
    }
}
