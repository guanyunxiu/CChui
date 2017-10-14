package chui.swsd.com.cchui.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\24 0024.
 */

public class ShenPiBean extends MultiItemEntity {
    public static final int QJ = 0; //请假
    public static final int CC = 1; //出差
    public static final int QC = 2; //外出
    public static final int JB = 3; //加班
    public static final int BX = 4; //报销


    /**
     * userHead : {"userid":12,"name":"小费","headimg":null,"department":null}
     * ids : 16
     * category : 0
     * param1 : 事假
     * param2 : 2017年09月07日 17点
     * param3 : 2017年09月08日 17点
     * param4 : 0
     * param5 : null
     * time : 2017-09-07 17:06:45
     */

    private UserHeadBean userHead;
    private int ids;
    private int category;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private String time;
    private Approved approved;

    public Approved getApproved() {
        return approved;
    }

    public void setApproved(Approved approved) {
        this.approved = approved;
    }

    public UserHeadBean getUserHead() {
        return userHead;
    }

    public void setUserHead(UserHeadBean userHead) {
        this.userHead = userHead;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class UserHeadBean {
        /**
         * userid : 12
         * name : 小费
         * headimg : null
         * department : null
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
    public class Approved{
        private String usernames;
        private int types;

        public int getTypes() {
            return types;
        }

        public void setTypes(int types) {
            this.types = types;
        }

        public String getUsernames() {
            return usernames;
        }

        public void setUsernames(String usernames) {
            this.usernames = usernames;
        }
    }
}
