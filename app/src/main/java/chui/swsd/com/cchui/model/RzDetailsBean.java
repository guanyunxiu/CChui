package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\16 0016.
 */

public class RzDetailsBean {

    /**
     * id : 1
     * userid : 1
     * time : 1505446237998
     * accomplish : 已完成
     * unfinished : 未完成
     * plan : 计划
     * coordinate : 需协调
     * remark : 备注
     * uuid : c741fc29ba71448dbcee34fbd9b1c07b
     * accepter : null
     * category : 0
     * userHead : {"userid":1,"name":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","department":"开发部"}
     * photos : [{"path":"/Beat/upload/1/report/3c8aaad2-e8b4-4452-8e9d-3df412ae63a3.JPG"}]
     * integralinfo : [{"detail":"+5","auditname":"小明","times":"123341325"}]
     */

    private int id;
    private int userid;
    private String time;
    private String accomplish;
    private String unfinished;
    private String plan;
    private String coordinate;
    private String remark;
    private String uuid;
    private Object accepter;
    private int category;
    private UserHeadBean userHead;
    private List<PhotoBean> photos;
    private List<IntegralinfoBean> integralinfo;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAccomplish() {
        return accomplish;
    }

    public void setAccomplish(String accomplish) {
        this.accomplish = accomplish;
    }

    public String getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(String unfinished) {
        this.unfinished = unfinished;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Object getAccepter() {
        return accepter;
    }

    public void setAccepter(Object accepter) {
        this.accepter = accepter;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public UserHeadBean getUserHead() {
        return userHead;
    }

    public void setUserHead(UserHeadBean userHead) {
        this.userHead = userHead;
    }

    public List<PhotoBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoBean> photos) {
        this.photos = photos;
    }

    public List<IntegralinfoBean> getIntegralinfo() {
        return integralinfo;
    }

    public void setIntegralinfo(List<IntegralinfoBean> integralinfo) {
        this.integralinfo = integralinfo;
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


    public static class IntegralinfoBean {
        /**
         * detail : +5
         * auditname : 小明
         * times : 123341325
         */

        private String detail;
        private String auditname;
        private String times;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getAuditname() {
            return auditname;
        }

        public void setAuditname(String auditname) {
            this.auditname = auditname;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}
