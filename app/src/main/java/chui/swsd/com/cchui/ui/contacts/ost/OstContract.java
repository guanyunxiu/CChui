package chui.swsd.com.cchui.ui.contacts.ost;

import java.util.List;

import chui.swsd.com.cchui.model.DepConBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class OstContract {
   public interface view{
       void onSuccess(List<DepConBean> list);
       void onFail();
   }
    interface  presenter{
        void onSelect();
    }
}
