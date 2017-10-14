package chui.swsd.com.cchui.net;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\8 0008.
 */

public class UrlAddress {
    public static String URLAddress = "http://192.168.123.250:8888";
    public static String URL = URLAddress + "/Beat/";
    public static String REGISTER = "user/register.action";   //注册
    public static String LOGIN = "user/login.action"; //登录
    public static String FORGETPASS = "user/forgetpass.action"; //忘记密码
    public static String UPDATEPASSWD = "user/updatePasswd.action"; //修改密码
    public static String LOGOT = "user/logout.action"; //用户登出
    public static String SELDATA = "user/seldata.action"; //查看个人资料
    public static String UPDATEDATA = URL + "user/updatedata.action"; //修改个人资料
    public static String SELUSER = "department/seluser.action"; //获取部门列表(包含用户)
    public static String SIGNINSERT = "sign/insert.action"; //添加考勤记录
    public static String SIGNTODAY = "sign/today.action";//查看当天考勤
    public static String LEAVEINSERT = "leave/insert.action";//发布请假消息
    public static String EVECTIONINSERT = "evection/insert.action";//发布出差消息
    public static String GOOUTINSERT = "goout/insert.action";//发布外出消息
    public static String OVERTIMEINSERT = "overtime/insert.action";//发布加班消息
    public static String APPLAYINSERT = URL+"applay/insert.action";//发布报销
    public static String WORKSEL = "work/sel.action";//条件查询审批列表
    public static String MONTHSIGH = "sign/monthsign.action";//查询考勤
    public static String INSERTGROUPS = URL+"groups/insertgroups.action";//创建群组
    public static String SELMYGROUP = "groups/selmygroup.action";//获取群组列表
    public static String SELGROUPINFO = "groups/selgroupinfo.action";//获取群详细信息
    public static String DELETEGROUPS = "groups/delgroups.action";//删除群组
    public static String UPDATEGROUPS = URL+"groups/updategroups.action";//修改群组信息
    public static String ADDGROUPINFO = URL+"groups/addgroupinfo.action";//添加群成员
    public static String DELEGROUPINFO = URL+"groups/delgroupinfo.action";//删除群成员
    public static String ONELEAVE = "work/oneleave.action";//查看单个请假
    public static String ONEEVECTION = "work/oneevection.action";//查看单个出差
    public static String ONEGOOUT = "work/onegoout.action";//查看单个外出
    public static String ONEOVERTIME = "work/oneovertime.action";//查看单个加班
    public static String ONEAPPLAY = "work/oneapplay.action";//查看单个报销
    public static String DEPSEL = "department/sel.action";//获取部门列表
    public static String LEVELUPDATE = "leave/update.action";//审批请假条
    public static String EVECTIONUPDATE = "evection/update.action";//审批出差
    public static String GOOUTUPDATE = "goout/update.action";//审批外出
    public static String OVERTIMEUPDATE = "overtime/update.action";//审批加班
    public static String APPLAYUPDATE = "applay/update.action";//审批报销
    public static String REPORTINSERT = URL+"report/insert.action";//发布日报、周报、月报
    public static String REPORTSEL = "report/sel.action";//条件查询日报、周报、月报
    public static String REPORTSELONE = "report/selone.action";//查询单个日报、周报、月报
    public static String COMPANYINSERT = "company/insert.action";   //添加公司
    public static String DEPARTMENTINSERT = "department/insert.action";   //添加部门
    public static String COMPANYSEL = "company/sel.action";   //获取公司列表
    public static String COMPANYLOGIN = "company/login.action";   //管理员登录
    public static String COMPANYSELONE= "company/selone.action";   //查询单个公司详情
    public static String DEPARTMENTSEL= "department/sel.action";   //获取部门列表
    public static String COMPANYUPDATE= "company/update.action";   //修改公司信息
    public static String INFORMINSERT= "inform/insert.action";   //发布公告
    public static String INFORMSEL= "inform/sel.action";   //查看公告
    public static String INFORMSELONE= "inform/selone.action";   //查看单个公告
    public static String MAKEINSERT= "mark/insert.action";   //发布会议预约
    public static String MARKSEL= "mark/sel.action";   //查看会议预约列表
    public static String MARKSELONE= "mark/selone.action";   //查看单个会议预约
    public static String DEPUPDATE= "department/update.action";   //修改部门
    public static String FILEINSERT= URL+"file/insert.action";   //添加文件管理
    public static String FILESEL= "file/sel.action";   //条件获取文件管理
    public static String FILESELONE= "file/selone.action";   //查询单个文件管理
    public static String DEPSELBYNAME= "department/selbyname.action";   //获取某部门员工列表
    public static String FEEDBACKINSERT= "feedback/insert.action";   //意见反馈
    public static String COMUPDATEDATA= "user/company.action";   //修改或完善用户信息
    public static String updatesdk= URL+"updatesdk/update.action";   //版本更新
    public static String POWERTITLE= "power/title.action";   //查询源动力标题
    public static String POWERCON= "power/content.action";   //查询源动力内容
    public static String POWERGRADE= "power/grade.action";   //添加源动力分数
    public static String INTEGRAL= "user/integral.action";   //添加自己的源动力分数
    public static String SIGNTODAY2= URL+"sign/today.action";//查看当天考勤
}
