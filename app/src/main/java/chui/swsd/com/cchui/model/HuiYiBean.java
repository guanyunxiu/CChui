package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\21 0021.
 */

public class HuiYiBean {

    /**
     * id : 11
     * title : 会议
     * starttime : 2017-09-20 11:00:00
     * time : 2017-09-21 09:54:11
     * usedtime : 30分钟
     * remark : null
     * user : [{"userid":1,"name":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png"},{"userid":2,"name":"小明","headimg":null}]
     */

    private int id;
    private String title;
    private String starttime;
    private String time;
    private String usedtime;
    private Object remark;
    private List<UserBean> user;

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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsedtime() {
        return usedtime;
    }

    public void setUsedtime(String usedtime) {
        this.usedtime = usedtime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * userid : 1
         * name : 小红
         * headimg : /Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png
         */

        private int userid;
        private String name;
        private String headimg;

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

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }
}
