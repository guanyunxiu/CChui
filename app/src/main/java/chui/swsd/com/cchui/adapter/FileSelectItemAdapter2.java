package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.FileDetailsBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class FileSelectItemAdapter2 extends BaseQuickAdapter<FileDetailsBean.FilesBean> {
    Context context;
    public FileSelectItemAdapter2(Context context, List<FileDetailsBean.FilesBean> list) {
        super(R.layout.activity_file_word2,list);
        this.context = context;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final FileDetailsBean.FilesBean str) {
        //((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
        String title = str.getName().substring(0,str.getName().lastIndexOf("."));
        ((TextView) helper.getView(R.id.world_tv)).setText(title);
        if(str.getSuffix().contains("ppt")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ppt,0,0,0);
        }else if(str.getSuffix().contains("doc")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.word,0,0,0);
        }else if(str.getSuffix().contains("xls")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.excel,0,0,0);
        }else if(str.getSuffix().contains("txt")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.txt,0,0,0);
        }
    }

}
