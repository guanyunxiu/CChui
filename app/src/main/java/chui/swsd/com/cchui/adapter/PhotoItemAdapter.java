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
import chui.swsd.com.cchui.model.ApprovedBean;
import chui.swsd.com.cchui.model.PhotoBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class PhotoItemAdapter extends BaseQuickAdapter<PhotoBean> {
    Context context;
    public PhotoItemAdapter(Context context,List<PhotoBean> list) {
        super(R.layout.activity_photo_item,list);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, final PhotoBean photoBean) {
        ImageView img = (ImageView)helper.getView(R.id.img) ;
        Glide.with(context).load(UrlAddress.URLAddress+photoBean.getPath()).into(img);
    }

}
