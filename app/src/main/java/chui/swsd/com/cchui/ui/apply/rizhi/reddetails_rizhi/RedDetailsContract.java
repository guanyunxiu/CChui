package chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi;

import chui.swsd.com.cchui.model.RzDetailsBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\16 0016.
 */

public interface RedDetailsContract {
    interface view{
        void onSuccess(RzDetailsBean rzDetailsBean);
        void onFail();
    }
    interface presenter{
        void onSubmit(int id);
    }
}
