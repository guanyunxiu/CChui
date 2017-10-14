package chui.swsd.com.cchui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.model.BaoXiaoDetailsBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class BaoXiaoDetailsItemAdapter extends BaseQuickAdapter<BaoXiaoDetailsBean.ApplayinfoBean> {
    public BaoXiaoDetailsItemAdapter(List<BaoXiaoDetailsBean.ApplayinfoBean> list) {
        super(R.layout.activity_sp_bx_details_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, final BaoXiaoDetailsBean.ApplayinfoBean baoXiaoBean) {
        int pos = helper.getLayoutPosition();
        ((TextView) helper.getView(R.id.title_tv)).setText("报销明细("+(pos+1)+")");
        ((TextView) helper.getView(R.id.money_tv)).setText(baoXiaoBean.getMoney()+"");
        ((TextView) helper.getView(R.id.type_tv)).setText(baoXiaoBean.getCategory());
        ((TextView) helper.getView(R.id.details_tv)).setText(baoXiaoBean.getDescribes());

    }
}
