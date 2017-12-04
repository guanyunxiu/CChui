package chui.swsd.com.cchui.net;

import android.content.Context;

import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 作者： 关云秀 on 2017/2/17.
 * 描述：
 */
public class FailMsg {
    public static final int ERR_OK = 0; //成功
    public static final int ERR_INVALIDATE = 1; //无效数据
    public static final int ERR_EXIST = 2; //(用户)已经存在
    public static final int ERR_USRNAME_OR_PWD_WRONG = 3; //用户名或密码错误
    public static final int ERR_NO_LOGIN = 4; //没有登录
    public static final int ERR_INVALID_SESSION = ERR_NO_LOGIN; //无效的session
    public static final int ERR_NOT_RIGHT = 5; //权限不足

    public static final int ERR_SMS_FAILED = 7; //sms验证码验证失败
    public static final int ERR_NO_PRE_CHECK_PHONE = 8;//错误的手机号
    public static final int ERR_SQL_FAILED = 9; //sql操作失败
    public static final int ERR_SIGN_OVERRUN = 10; //打卡次数已达上限
    public static final int ERR_NOT_EXIST = 11; //(用户)不存在
    public static final int ERR_NO_COMPARE = 12;//不匹配
    public static final int ERR_SAME_PASS=13;//旧密码和新密码相同
    public static final int ERR_RONGYUN_FAILED = 14; //融云的错误
    public static final int MANAGER_CODE = 17; //管理员账号或密码已存在
    public static final int INBOX_CODE = 18; //邀请码不正确
    public static final int ERR_KICK = 100; //账户重新登录把旧账户踢下去了
    public static final int ERR_INTERNAL = 1000; //内部错误（抛异常）
    public static boolean showMsg(Context context, int code) {
        switch (code){
            case ERR_INVALIDATE:
                CommonUtil.showToast(context, "无效数据");
                return false;
            case ERR_EXIST:
                CommonUtil.showToast(context,"(用户)已经存在");
                return false;
            case ERR_USRNAME_OR_PWD_WRONG:
                CommonUtil.showToast(context,"用户名或密码错误");
                return false;
            case ERR_NO_LOGIN:
                CommonUtil.showToast(context,"请重新登录");
              /*  Intent intent = new Intent(context,LoginActivity.class);
                context.startActivity(intent);*/
                return false;
            case ERR_NOT_RIGHT:
                CommonUtil.showToast(context,"权限不足");
                return false;
            case ERR_SMS_FAILED:
                CommonUtil.showToast(context,"sms验证码验证失败");
                return false;
            case ERR_NO_PRE_CHECK_PHONE:
                CommonUtil.showToast(context,"错误的手机号");
                return false;

            case ERR_SQL_FAILED:
                CommonUtil.showToast(context,"sql操作失败");
                return false;
            case ERR_SIGN_OVERRUN:
                CommonUtil.showToast(context,"打卡次数已达上限");
                return false;
            case ERR_NOT_EXIST:
                CommonUtil.showToast(context,"(用户)不存在");
                return false;
            case ERR_NO_COMPARE:
                CommonUtil.showToast(context,"输入的旧密码不正确");
                return false;
            case ERR_SAME_PASS:
                CommonUtil.showToast(context,"旧密码和新密码相同");
                return false;
            case ERR_RONGYUN_FAILED:
                CommonUtil.showToast(context,"融云错误");
                return false;
            case ERR_KICK:
                CommonUtil.showToast(context,"账户重新登录把旧账户踢下去了");
                return false;
            case ERR_INTERNAL:
                CommonUtil.showToast(context,"内部错误（抛异常）");
                return false;
            case MANAGER_CODE:
                CommonUtil.showToast(context,"管理员账号或密码已存在");
                return false;
            case INBOX_CODE:
                CommonUtil.showToast(context,"邀请码不正确");
                return false;
        }
        return true;
    }
}
