package chui.swsd.com.cchui.ui.work.select_people;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class AddGroupUserContract {
   public interface view{
       void onSuccess(int flag);
       void onFail(int flag);
   }
    interface presenter{
        void onAddGroupUser(int id, List<String> listId);
        void onDeleteGroupUser(int id,List<Integer> listId);
    }
}
