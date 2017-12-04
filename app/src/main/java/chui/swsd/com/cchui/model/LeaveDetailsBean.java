package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\7 0007.
 */

public class LeaveDetailsBean {

    /**
     * userHead : {"userid":1,"name":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","department":"开发部"}
     * leaves : {"id":1,"category":"事假","starttime":"2017-08-01","endtime":"2017-08-02","days":1,"reason":"有事","auditid":"null2,true,null,null,null,398d1c1f67bd49e3b473843bfae3a528,","ft":2,"userid":1,"time":"2017-08-31 17:49:14"}
     * approved : [{"id":1,"oid":2,"categorys":0,"usernames":"小红","userheads":"/Beat/upload/1/headimg/4e724c49-5931-4aa8-80ad-c39a5d99340f.jpg","types":2,"times":"2017-08-31 17:57:12"}]
     */
    private ApprovedVoBean approvedVo;
    private UserHeadBean userHead;
    private LeavesBean leaves;
    private List<ApprovedBean> approved;

    public ApprovedVoBean getApprovedVo() {
        return approvedVo;
    }

    public void setApprovedVo(ApprovedVoBean approvedVo) {
        this.approvedVo = approvedVo;
    }

    public UserHeadBean getUserHead() {
        return userHead;
    }

    public void setUserHead(UserHeadBean userHead) {
        this.userHead = userHead;
    }

    public LeavesBean getLeaves() {
        return leaves;
    }

    public void setLeaves(LeavesBean leaves) {
        this.leaves = leaves;
    }

    public List<ApprovedBean> getApproved() {
        return approved;
    }

    public void setApproved(List<ApprovedBean> approved) {
        this.approved = approved;
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

    public static class LeavesBean {
        /**
         * id : 1
         * category : 事假
         * starttime : 2017-08-01
         * endtime : 2017-08-02
         * days : 1
         * reason : 有事
         * auditid : null2,true,null,null,null,398d1c1f67bd49e3b473843bfae3a528,
         * ft : 2
         * userid : 1
         * time : 2017-08-31 17:49:14
         */

        private int id;
        private String category;
        private String starttime;
        private String endtime;
        private double days;
        private String reason;
        private String auditid;
        private int ft;
        private int userid;
        private String time;
        private String number;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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

        public double getDays() {
            return days;
        }

        public void setDays(double days) {
            this.days = days;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getAuditid() {
            return auditid;
        }

        public void setAuditid(String auditid) {
            this.auditid = auditid;
        }

        public int getFt() {
            return ft;
        }

        public void setFt(int ft) {
            this.ft = ft;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }


}
