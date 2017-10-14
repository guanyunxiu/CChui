package chui.swsd.com.cchui.ui.contacts.group;

import java.util.List;

import chui.swsd.com.cchui.model.GroupListBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public interface GroupContract {
    interface view{
        void onSuccess(List<GroupListBean> groupListBeen);
        void onFail();
    }
    interface precenter{
        void onSelect();
    }
}
