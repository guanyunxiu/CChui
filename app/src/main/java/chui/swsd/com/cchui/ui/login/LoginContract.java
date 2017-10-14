package chui.swsd.com.cchui.ui.login;

import chui.swsd.com.cchui.model.UserBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class LoginContract {
    public interface view{
       void onSuccess(UserBean userBean);
        void onSuccess();
       void onFail();
   }
     public interface Presenter{
        void onLogin(String phone,String pass);
        void onManaLogin(String account,String pass);
    }
}
