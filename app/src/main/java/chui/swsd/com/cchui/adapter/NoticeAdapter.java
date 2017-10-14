package chui.swsd.com.cchui.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.loadmore.CustomLoadMoreView;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.ui.work.notice.NoticeFragment;

/**
 * 内容：公告adapter
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class NoticeAdapter extends BaseQuickAdapter<GroupBean> {
    public NoticeAdapter(List<GroupBean> list) {
        super(R.layout.fragment_notice_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, GroupBean groupBean) {
       // ((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
    }



}
