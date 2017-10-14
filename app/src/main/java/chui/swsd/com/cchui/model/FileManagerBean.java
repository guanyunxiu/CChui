package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\22 0022.
 */

public class FileManagerBean {

    /**
     * id : 4
     * title : 标题
     * time : 2017-09-22 10:15:44
     * user : [{"userid":2,"name":"小明","headimg":null},{"userid":3,"name":"小一","headimg":null}]
     */

    private int id;
    private String title;
    private String time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * userid : 2
         * name : 小明
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
}
