package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class FileSelectItemAdapter extends BaseQuickAdapter<String> {
    Context context;
    public FileSelectItemAdapter(Context context, List<String> list) {
        super(R.layout.activity_file_word,list);
        this.context = context;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final String str) {
        //((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
        String title = str.substring(str.lastIndexOf("/")+1);
        String hz = str.substring(str.lastIndexOf(".")+1);
        ((TextView) helper.getView(R.id.world_tv)).setText(title);
        if(hz.contains("ppt")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ppt,0,0,0);
        }else if(hz.contains("doc")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.word,0,0,0);
        }else if(hz.contains("xls")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.excel,0,0,0);
        }else if(hz.contains("txt")){
            ((TextView) helper.getView(R.id.world_tv)).setCompoundDrawablesWithIntrinsicBounds(R.mipmap.txt,0,0,0);
        }
        ((ImageView) helper.getView(R.id.del_img)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onDelItemClicked(str);
            }
        });
    }

    public interface OnItemClickListener {
        void onDelItemClicked(String str);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
