package chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details;

import chui.swsd.com.cchui.model.BaoXiaoDetailsBean;
import chui.swsd.com.cchui.model.ChuChaiDetailsBean;
import chui.swsd.com.cchui.model.GoOutDetailsBean;
import chui.swsd.com.cchui.model.JiaBanDetailsBean;
import chui.swsd.com.cchui.model.LeaveDetailsBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\7 0007.
 */

public interface SpDetailsContract {
    interface view{
        void onSuccess(LeaveDetailsBean leaveDetailsBean);//请假
        void onSuccess(ChuChaiDetailsBean chuChaiDetailsBean);//出差
        void onSuccess(GoOutDetailsBean goOutDetailsBean);//外出
        void onSuccess(JiaBanDetailsBean jiaBanDetailsBean);//加班
        void onSuccess(BaoXiaoDetailsBean baoXiaoDetailsBean);//报销
        void onSuccess();//审批成功
        void onFail();
    }
    interface presenter{
        void onSubmit(int flag,int id);
        void onSpSubmit(int flag,String uuid,int ft);
    }
}
