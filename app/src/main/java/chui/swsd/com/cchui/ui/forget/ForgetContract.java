package chui.swsd.com.cchui.ui.forget;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public interface ForgetContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(String phone,String pass);
    }
}
