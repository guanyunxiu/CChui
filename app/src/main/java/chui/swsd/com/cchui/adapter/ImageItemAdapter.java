package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.multilevel.treelist.Node;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.widget.ExpandLayout;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class ImageItemAdapter extends BaseQuickAdapter<String> {
    Context context;
    public ImageItemAdapter(Context context,List<String> list) {
        super(R.layout.activity_image_item,list);
        this.context = context;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final String str) {
        //((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
        ImageView imageView = ((ImageView) helper.getView(R.id.img));
        ImageView delImg = (ImageView)helper.getView(R.id.del_img);
        Glide.with(context).load(str).into(imageView);
        delImg.setOnClickListener(new View.OnClickListener() {
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
