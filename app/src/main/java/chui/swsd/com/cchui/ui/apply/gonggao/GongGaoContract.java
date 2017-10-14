package chui.swsd.com.cchui.ui.apply.gonggao;

import java.util.List;

import chui.swsd.com.cchui.model.GongGaoBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\20 0020.
 */

public interface GongGaoContract {
    interface view{
        void onSuccess();
        void onFail();
        void onSuccess(List<GongGaoBean> gongGaoBeanList);
        void onSuccess(GongGaoBean gongGaoBean);
    }
    interface presenter{
        void onRelease(String title, String content, List<String> stringList);
        void onSelect(int page,int type);
        void onSelectSing(int id);
    }
}
