package chui.swsd.com.cchui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.KaoQinListBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class KaoQinItemAdapter extends BaseQuickAdapter<KaoQinListBean.WorkdateBean> {
    public KaoQinItemAdapter(List<KaoQinListBean.WorkdateBean> list) {
        super(R.layout.activity_kaoqin_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, KaoQinListBean.WorkdateBean workdateBean) {
        helper.setText(R.id.time_tv,workdateBean.getDate());
    }
}
