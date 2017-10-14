package chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi;

import java.io.File;
import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\15 0015.
 */

public interface WriteRzContract {
    interface view{
        void onSuccess();
        void onFail();
    }
    interface presenter{
        void onSubmit(List<String> spList,String accomplish,String unfinished,String plan,String coordinate,String remark,int category,List<String> fileList);
    }
}
