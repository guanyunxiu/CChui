package chui.swsd.com.cchui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;

/**
 * 内容：公告adapter
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class MeetAdapter extends BaseQuickAdapter<GroupBean> {
    public MeetAdapter(List<GroupBean> list) {
        super(R.layout.fragment_meet_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, GroupBean groupBean) {
       // ((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
    }



}
