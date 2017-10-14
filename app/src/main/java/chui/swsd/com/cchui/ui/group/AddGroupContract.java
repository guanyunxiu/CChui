package chui.swsd.com.cchui.ui.group;

import java.io.File;
import java.util.List;

import chui.swsd.com.cchui.model.GroupListBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class AddGroupContract {
    public interface view{
        void onSuccess(int flag);
        void onFail(int flag);
        void onSuccess(int flag,GroupListBean groupListBean);
    }
    interface presenter{
        void onSubmit(String name, File file,List<String> stringList);
        void onSelectDetails(String id);
        void onDeleteGroup(int id);
        void onUpdateGroupName(int id,String name);
        void onUpdateGroupFile(int id,File file);
        void onDeleteGroupUser(int id,List<Integer> listId);//删除群成员
    }
}
