package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：加班
 * Created by 关云秀 on 2017\9\7 0007.
 */

public class JiaBanDetailsBean {

    /**
     * userHead : {"userid":1,"name":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","department":"开发部"}
     * overtime : {"id":1,"userid":1,"starttime":"2017-08-01 18:00:00","endtime":"2017-08-01 20:00:00","usedtime":"2小时","isholiday":0,"way":"1.5倍","reason":"原因","auditid":"2,3,33fe8233e86344f7a19c78a6ab5d8a22","ft":0,"time":"2017-09-07 11:12:22"}
     * approved : [{"id":1,"oid":8,"categorys":3,"usernames":"小红","userheads":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","types":0,"times":"2017-09-07 11:12:22"}]
     */
    private ApprovedVoBean approvedVo;
    private UserHeadBean userHead;
    private OvertimeBean overtime;
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

    public OvertimeBean getOvertime() {
        return overtime;
    }

    public void setOvertime(OvertimeBean overtime) {
        this.overtime = overtime;
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

    public static class OvertimeBean {
        /**
         * id : 1
         * userid : 1
         * starttime : 2017-08-01 18:00:00
         * endtime : 2017-08-01 20:00:00
         * usedtime : 2小时
         * isholiday : 0
         * way : 1.5倍
         * reason : 原因
         * auditid : 2,3,33fe8233e86344f7a19c78a6ab5d8a22
         * ft : 0
         * time : 2017-09-07 11:12:22
         */

        private int id;
        private int userid;
        private String starttime;
        private String endtime;
        private String usedtime;
        private int isholiday;
        private String way;
        private String reason;
        private String auditid;
        private int ft;
        private String time;
        private String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

        public String getUsedtime() {
            return usedtime;
        }

        public void setUsedtime(String usedtime) {
            this.usedtime = usedtime;
        }

        public int getIsholiday() {
            return isholiday;
        }

        public void setIsholiday(int isholiday) {
            this.isholiday = isholiday;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


}
