package chui.swsd.com.cchui.ui.work.make_meet;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\21 0021.
 */

public interface MakeMeetContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(String title,String starttime,String usedtime,List<String> listId);
    }
}
