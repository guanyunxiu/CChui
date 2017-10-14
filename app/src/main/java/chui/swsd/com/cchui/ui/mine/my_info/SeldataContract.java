package chui.swsd.com.cchui.ui.mine.my_info;

import java.io.File;

import chui.swsd.com.cchui.model.UserBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class SeldataContract {
    public interface view{
        void onSuccess(UserBean userBean);
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSelect(String phone);
        void onUpdate(String sex,String address,File photo);
    }
}
