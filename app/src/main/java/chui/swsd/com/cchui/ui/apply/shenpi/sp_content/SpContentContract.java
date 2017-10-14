package chui.swsd.com.cchui.ui.apply.shenpi.sp_content;

import java.util.List;

import chui.swsd.com.cchui.model.ShenPiBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public interface SpContentContract {
    interface view{
        void onSuccess(List<ShenPiBean> list);
        void onFail();
    }
    interface precsenter{
        void onSubmit(int page,int type,int screen,int ft,int state);
    }
}
