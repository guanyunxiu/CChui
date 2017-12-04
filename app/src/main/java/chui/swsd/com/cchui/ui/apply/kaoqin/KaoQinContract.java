package chui.swsd.com.cchui.ui.apply.kaoqin;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\20 0020.
 */

import java.util.List;

import chui.swsd.com.cchui.model.KaoQinBean;

public  interface KaoQinContract {
    interface view {
        void onSuccess(int code);

        void onSuccess(List<KaoQinBean> kaoQinBeanList);

        void onFail();
    }

    interface presenter {
        void onSubmit(String address, double longitude, double latitude,int outwork,int category);

        void onSelect();
    }
}
