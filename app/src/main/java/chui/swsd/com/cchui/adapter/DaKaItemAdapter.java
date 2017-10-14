package chui.swsd.com.cchui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.KaoQinBean;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class DaKaItemAdapter extends BaseQuickAdapter<KaoQinBean> {
    public DaKaItemAdapter(List<KaoQinBean> list) {
        super(R.layout.activity_my_kaoqin_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, KaoQinBean kaoQinBean) {
        if(kaoQinBean.getCategory() == 0) {//上班
            ((TextView) helper.getView(R.id.time_tv)).setText("上班打卡时间："+ DateUtil.getTime(kaoQinBean.getTime()));
            ((TextView) helper.getView(R.id.s_bg_tv)).setText("上");
        }else{//下班
            ((TextView) helper.getView(R.id.time_tv)).setText("下班打卡时间："+DateUtil.getTime(kaoQinBean.getTime()));
            ((TextView) helper.getView(R.id.s_bg_tv)).setText("下");
        }
        ((TextView) helper.getView(R.id.address_tv)).setText(kaoQinBean.getAddress());
        TextView statusTv = ((TextView) helper.getView(R.id.status_tv));
        if(kaoQinBean.getState() == 0){
            statusTv.setVisibility(View.GONE);
        }else if(kaoQinBean.getState() == 1){
            statusTv.setVisibility(View.VISIBLE);
            statusTv.setText("迟到");
        }else if(kaoQinBean.getState() == 2){
            statusTv.setVisibility(View.VISIBLE);
            statusTv.setText("早退");
        }
    }
}
