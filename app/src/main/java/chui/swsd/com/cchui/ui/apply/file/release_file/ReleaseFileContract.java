package chui.swsd.com.cchui.ui.apply.file.release_file;

import java.util.List;

import chui.swsd.com.cchui.model.FileDetailsBean;
import chui.swsd.com.cchui.model.FileManagerBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\22 0022.
 */

public interface ReleaseFileContract {
    interface view{
        void onSuccess();
        void onFail();
        void onSuccess(List<FileManagerBean> fileManagerBeanList);
        void onSuccess(FileDetailsBean fileDetailsBean);
    }
    interface presenter{
        void onSubmit(String title, List<String> listId,List<String> listFile);
        void onSelect(int page,int type);
        void onSelectSing(int id);
    }
}
