package chui.swsd.com.cchui.ui.apply.kaoqin;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\20 0020.
 */

import java.util.List;

import chui.swsd.com.cchui.model.KaoQinBean;

public  interface KaoQinContract {
    interface view {
        void onSuccess();

        void onSuccess(List<KaoQinBean> kaoQinBeanList);

        void onFail();
    }

    interface presenter {
        void onSubmit(String address, double longitude, double latitude);

        void onSelect();
    }
}
