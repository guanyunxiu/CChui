package chui.swsd.com.cchui.ui.apply.huiyi;

import java.util.List;

import chui.swsd.com.cchui.model.HuiYiBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\21 0021.
 */

public interface HuiYiListContract {
    interface view{
        void onSuccess(List<HuiYiBean> huiYiBeanList);
        void onSuccess(HuiYiBean huiYiBean);
        void onFail();
    }
    interface presenter{
        void onSelect(int page,int type);
        void onSelectSing(int id);
    }
}
