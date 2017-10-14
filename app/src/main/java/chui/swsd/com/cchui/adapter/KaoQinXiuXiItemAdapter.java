package chui.swsd.com.cchui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.KaoQinListBean;
import chui.swsd.com.cchui.ui.mine.kaoqin.MyKaoQinActivity;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class KaoQinXiuXiItemAdapter extends BaseQuickAdapter<KaoQinListBean.WorkdateBean> {
    public KaoQinXiuXiItemAdapter(MyKaoQinActivity myKaoQinActivity, List<KaoQinListBean.WorkdateBean> list) {
        super(R.layout.activity_kaoqin_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, KaoQinListBean.WorkdateBean workdateBean) {
        ((TextView) helper.getView(R.id.time_tv)).setText(workdateBean.getDate());
    }
}
