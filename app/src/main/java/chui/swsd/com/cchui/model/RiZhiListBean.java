package chui.swsd.com.cchui.model;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\15 0015.
 */

public class RiZhiListBean {

    /**
     * userHead : {"userid":1,"name":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","department":"开发部"}
     * id : 2
     * accomplish : 已完成
     * category : 0
     * time : 1505446339910
     */

    private UserHeadBean userHead;
    private int id;
    private String accomplish;
    private int category;
    private String time;

    public UserHeadBean getUserHead() {
        return userHead;
    }

    public void setUserHead(UserHeadBean userHead) {
        this.userHead = userHead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccomplish() {
        return accomplish;
    }

    public void setAccomplish(String accomplish) {
        this.accomplish = accomplish;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class UserHeadBean {
        /**
         * userid : 1
         * name : 小红
         * headimg : /Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png
         * department : 开发部
         */

        private int userid;
        private String name;
        private String headimg;
        private String department;

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

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}
