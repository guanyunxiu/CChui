package chui.swsd.com.cchui.ui.apply.rizhi.red_rizhi;

import java.util.List;

import chui.swsd.com.cchui.model.RiZhiListBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\15 0015.
 */

public class RedRzContract {
    interface view{
        void onSuccess(List<RiZhiListBean> listBeanList);
        void onFail();
    }
    interface presenter{
        void onSubmit(String starttime,String endtime,int category,int page,int type,List<String> listId);
    }
}
