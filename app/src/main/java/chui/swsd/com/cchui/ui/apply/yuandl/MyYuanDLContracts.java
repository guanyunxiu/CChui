package chui.swsd.com.cchui.ui.apply.yuandl;

import chui.swsd.com.cchui.model.YdlScoreBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\10\14 0014.
 */

public interface MyYuanDLContracts {
    interface view{
        void onSuccess(YdlScoreBean ydlScoreBean);
        void onFail();
    }
    interface presenter{
        void onSelectScore(String time);
    }
}
