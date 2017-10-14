package chui.swsd.com.cchui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.FileManagerBean;
import chui.swsd.com.cchui.model.GroupBean;

/**
 * 内容：公告adapter
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class FileAdapter extends BaseQuickAdapter<FileManagerBean> {
    private int flag;
    public FileAdapter(List<FileManagerBean> list,int flag) {
        super(R.layout.fragment_file_item,list);
        this.flag = flag;
    }
    @Override
    protected void convert(BaseViewHolder helper, FileManagerBean fileManagerBean) {
        helper.setText(R.id.title_tv,"标题："+fileManagerBean.getTitle());
        helper.setText(R.id.time_tv,fileManagerBean.getTime());
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<fileManagerBean.getUser().size();i++){
            FileManagerBean.UserBean userBean = fileManagerBean.getUser().get(i);
            if(i != fileManagerBean.getUser().size()-1){
                stringBuilder.append(userBean.getName()+"，");
            }else{
                stringBuilder.append(userBean.getName());
            }
        }
        if(flag == 1){//我收到的
            helper.setText(R.id.name_tv,fileManagerBean.getTime());
            ((TextView) helper.getView(R.id.name_tv)).setText("发送人："+stringBuilder.toString());
        }else if(flag == 0){//我发送的
            ((TextView) helper.getView(R.id.name_tv)).setText("接收人："+stringBuilder.toString());
        }
    }
}
