package chui.swsd.com.cchui.ui.apply.shenpi.sp_chuchai;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public class SpChuChaiContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(String address, String starttime, String endtime, String days, String reason, List<String> listId);
    }
}
