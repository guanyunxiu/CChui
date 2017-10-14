package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\7 0007.
 */

public class BaoXiaoDetailsBean {

    /**
     * id : 20
     * userid : 1
     * auditid : 2,3,1374a18e2d92496290820bd83f70570e
     * ft : 0
     * time : 2017-09-07 14:55:42
     * userHead : {"userid":1,"name":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","department":"开发部"}
     * applayinfo : [{"money":1,"category":"采购经费","describes":"采购经费"}]
     * applayphoto : [{"path":"/beat/upload/1/applayphoto/8554dc3a-96e7-4f91-ad40-03bb1f6d197c.JPG"}]
     * approved : [{"id":20,"oid":20,"categorys":4,"usernames":"小红","userheads":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","types":0,"times":"2017-09-07 14:55:42"}]
     */

    private int id;
    private int userid;
    private String auditid;
    private int ft;
    private String time;
    private String number;
    private ApprovedVoBean approvedVo;
    private UserHeadBean userHead;
    private List<ApplayinfoBean> applayinfo;
    private List<PhotoBean> applayphoto;
    private List<ApprovedBean> approved;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ApprovedVoBean getApprovedVo() {
        return approvedVo;
    }

    public void setApprovedVo(ApprovedVoBean approvedVo) {
        this.approvedVo = approvedVo;
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

    public UserHeadBean getUserHead() {
        return userHead;
    }

    public void setUserHead(UserHeadBean userHead) {
        this.userHead = userHead;
    }

    public List<ApplayinfoBean> getApplayinfo() {
        return applayinfo;
    }

    public void setApplayinfo(List<ApplayinfoBean> applayinfo) {
        this.applayinfo = applayinfo;
    }

    public List<PhotoBean> getApplayphoto() {
        return applayphoto;
    }

    public void setApplayphoto(List<PhotoBean> applayphoto) {
        this.applayphoto = applayphoto;
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

    public static class ApplayinfoBean {
        /**
         * money : 1
         * category : 采购经费
         * describes : 采购经费
         */

        private int money;
        private String category;
        private String describes;

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }
    }


}
