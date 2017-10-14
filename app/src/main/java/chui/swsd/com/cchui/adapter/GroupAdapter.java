package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class GroupAdapter extends BaseQuickAdapter<GroupListBean> {
    private Context context;
    public GroupAdapter(List<GroupListBean> list,Context context) {
        super(R.layout.fragment_group_item,list);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, GroupListBean groupBean) {
        ((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
        if(!TextUtils.isEmpty(groupBean.getHeadimg())){
            Glide.with(context).load(UrlAddress.URLAddress + groupBean.getHeadimg()).into(((ImageView) helper.getView(R.id.icon_img)));
        }
    }
}
