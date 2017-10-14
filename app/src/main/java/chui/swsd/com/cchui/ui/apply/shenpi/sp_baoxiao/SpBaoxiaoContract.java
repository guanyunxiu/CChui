package chui.swsd.com.cchui.ui.apply.shenpi.sp_baoxiao;

import java.util.ArrayList;
import java.util.List;

import chui.swsd.com.cchui.model.BaoXiaoBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public interface SpBaoxiaoContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(List<BaoXiaoBean> list, ArrayList<String> listPhoto,List<String> listId);
    }
}
