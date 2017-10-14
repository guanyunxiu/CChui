package chui.swsd.com.cchui.ui.apply.shenpi.sp_qingjia;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\4 0004.
 */

public interface SpLeaveContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(String category, String starttime, String endtime, String days, String reason, List<String> integerList);
    }
}
