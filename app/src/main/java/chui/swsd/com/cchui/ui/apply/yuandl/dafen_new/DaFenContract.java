package chui.swsd.com.cchui.ui.apply.yuandl.dafen_new;

import java.util.List;

import chui.swsd.com.cchui.model.DaFenConBean;
import chui.swsd.com.cchui.model.YdlTitleBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\10\11 0011.
 */

public interface DaFenContract {
    interface view{
        void onSuccess(List<YdlTitleBean> list);
        void onSuccess(DaFenConBean daFenConBean);
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSelectTitle(int category);
        void onSelectContent(String bid);
        void onSubmit(int category,String bid,String details);
    }
}
