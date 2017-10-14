package chui.swsd.com.cchui.ui.mine.kaoqin;

import chui.swsd.com.cchui.model.KaoQinBean;
import chui.swsd.com.cchui.model.KaoQinListBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public interface MyKaoQinContract {
    interface view{
        void onSuccess(KaoQinListBean kaoQinBean);
        void onFail();
    }
    interface presenter{
        void onSubmit(String time);
    }
}
