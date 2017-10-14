package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：外出
 * Created by 关云秀 on 2017\9\7 0007.
 */

public class GoOutDetailsBean {

    /**
     * userHead : {"userid":1,"name":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","department":"开发部"}
     * goout : {"id":1,"userid":1,"starttime":"2017-08-01 09:00:00","endtime":"2017-08-01 10:00:00","usedtime":"1小时","searon":null,"auditid":"2,3,082afb7a0c6a4226ae9e38ba6cfaa46d","ft":0,"time":"2017-09-06 18:34:34"}
     * approved : [{"id":1,"oid":3,"categorys":2,"usernames":"小红","userheads":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","types":0,"times":"2017-09-06 18:34:34"}]
     */
    private ApprovedVoBean approvedVo;
    private UserHeadBean userHead;
    private GooutBean goout;
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

    public GooutBean getGoout() {
        return goout;
    }

    public void setGoout(GooutBean goout) {
        this.goout = goout;
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

    public static class GooutBean {
        /**
         * id : 1
         * userid : 1
         * starttime : 2017-08-01 09:00:00
         * endtime : 2017-08-01 10:00:00
         * usedtime : 1小时
         * searon : null
         * auditid : 2,3,082afb7a0c6a4226ae9e38ba6cfaa46d
         * ft : 0
         * time : 2017-09-06 18:34:34
         */

        private int id;
        private int userid;
        private String starttime;
        private String endtime;
        private String usedtime;
        private String searon;
        private String auditid;
        private int ft;
        private String time;
        private String number;

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

        public String getSearon() {
            return searon;
        }

        public void setSearon(String searon) {
            this.searon = searon;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
