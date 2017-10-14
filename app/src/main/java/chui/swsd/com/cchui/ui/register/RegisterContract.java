package chui.swsd.com.cchui.ui.register;

import java.util.List;

import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.model.DepartmentBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public interface RegisterContract {
    interface View{
      void onSuccess();
      void onSuccess(int id);
      void onFail();
      void onSuccess(List<CompanyBean> companyBeanList);
    }
    interface Presenter{
        void onSubmit(String name,String phone,String pass,int account,String companyname,String companyid,String inbox);
        void onTwoSubmit(String userid,String name,String province,String city,String county,String address,String longitude,String latitude,String starttime,String endtime,String scale,String account,String pass,String inbox);
        void onThreeSubmit(String cid, List<DepartmentBean> depList);
        void onSelectCom();
        void onUpdateData(String userid,int companyid,String companyname,String inbox);
    }
}
