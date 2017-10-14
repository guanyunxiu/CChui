package chui.swsd.com.cchui.ui.mine.manager_info;

import java.util.List;

import chui.swsd.com.cchui.model.CompanyDetailBean;
import chui.swsd.com.cchui.model.DepartmentBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\19 0019.
 */

public interface ManagerComContract {
    interface view{
        void onSuccess(CompanyDetailBean companyDetailBean);
        void onSuccess(List<DepartmentBean> departmentBeanList);
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSelect(int cid);
        void onSelectDep(int cid);
        void onTwoSubmit(int userid,String name,String province,String city,String county,String address,String longitude,String latitude,String starttime,String endtime,String scale,String account,String pass,String inbox,List<String> listId);
        void onUpdateDep(int id,String name);
        void onAddDep(String departmentBean);
    }
}
