package chui.swsd.com.cchui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public class KaoQinListBean {

    /**
     * id : 1
     * userid : 1
     * time : 2017-9
     * work : 0
     * workdate : [{"date":"2017年9月5日（周二）"},{"date":"2017年9月5日（周二）"},{"date":"2017年9月5日（周二）"},{"date":"2017年9月5日（周二）"}]
     * breaks : 0
     * breakdate : []
     * late : 1
     * latedate : []
     * early : 0
     * earlydate : []
     * lack : 0
     * lackdate : []
     * absenteeism : 2
     * absenteeismdate : [{"date":"0"}]
     */

    private int id;
    private int userid;
    private String time;
    private int work;
    private int breaks;
    private int late;
    private int early;
    private int lack;
    private int absenteeism;
    private List<WorkdateBean> workdate;
    private List<WorkdateBean> breakdate;
    private List<WorkdateBean> latedate;
    private List<WorkdateBean> earlydate;
    private List<WorkdateBean> lackdate;
    private List<WorkdateBean> absenteeismdate;

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

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public int getBreaks() {
        return breaks;
    }

    public void setBreaks(int breaks) {
        this.breaks = breaks;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    public int getEarly() {
        return early;
    }

    public void setEarly(int early) {
        this.early = early;
    }

    public int getLack() {
        return lack;
    }

    public void setLack(int lack) {
        this.lack = lack;
    }

    public int getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(int absenteeism) {
        this.absenteeism = absenteeism;
    }

    public List<WorkdateBean> getWorkdate() {
        return workdate;
    }

    public void setWorkdate(List<WorkdateBean> workdate) {
        this.workdate = workdate;
    }

    public List<WorkdateBean> getBreakdate() {
        return breakdate;
    }

    public void setBreakdate(List<WorkdateBean> breakdate) {
        this.breakdate = breakdate;
    }

    public List<WorkdateBean> getLatedate() {
        return latedate;
    }

    public void setLatedate(List<WorkdateBean> latedate) {
        this.latedate = latedate;
    }

    public List<WorkdateBean> getEarlydate() {
        return earlydate;
    }

    public void setEarlydate(List<WorkdateBean> earlydate) {
        this.earlydate = earlydate;
    }

    public List<WorkdateBean> getLackdate() {
        return lackdate;
    }

    public void setLackdate(List<WorkdateBean> lackdate) {
        this.lackdate = lackdate;
    }

    public List<WorkdateBean> getAbsenteeismdate() {
        return absenteeismdate;
    }

    public void setAbsenteeismdate(List<WorkdateBean> absenteeismdate) {
        this.absenteeismdate = absenteeismdate;
    }

    public static class WorkdateBean {
        /**
         * date : 2017年9月5日（周二）
         */

        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
    public static KaoQinListBean getKaoQinList(){
        List<WorkdateBean> list = new ArrayList<>();
        KaoQinListBean kaoQinBean = new KaoQinListBean();
        kaoQinBean.setWork(0);
        kaoQinBean.setWorkdate(list);
        kaoQinBean.setBreaks(0);
        kaoQinBean.setBreakdate(list);
        kaoQinBean.setLate(0);
        kaoQinBean.setLatedate(list);
        kaoQinBean.setEarly(0);
        kaoQinBean.setEarlydate(list);
        kaoQinBean.setLack(0);
        kaoQinBean.setLackdate(list);
        kaoQinBean.setAbsenteeism(0);
        kaoQinBean.setAbsenteeismdate(list);
        return kaoQinBean;
    }
}
