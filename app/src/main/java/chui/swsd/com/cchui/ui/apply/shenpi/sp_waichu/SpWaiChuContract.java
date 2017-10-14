package chui.swsd.com.cchui.ui.apply.shenpi.sp_waichu;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public interface SpWaiChuContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(String starttime, String endtime, String usedtime, String reason, List<String> listId);
    }
}
