package chui.swsd.com.cchui.ui.apply.yuandl.leader_fen;

import java.util.List;

import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.model.YdlScoreBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\11 0011.
 */

public interface LeaderFenContract {
    interface view{
        void onSuccess(List<DepartmentBean> list);
        void onFail();
        void onSuccessCon(List<YdlScoreBean> depconList);
    }
    interface presenter{
        void onSelectDep();
        void onSelectDepCon(String departname,String time);
    }
}
