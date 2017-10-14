package chui.swsd.com.cchui.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.ApprovedBean;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class SpDetailsItemAdapter extends BaseQuickAdapter<ApprovedBean> {
    public SpDetailsItemAdapter(List<ApprovedBean> list) {
        super(R.layout.activity_sp_details_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, final ApprovedBean baoXiaoBean) {
         int pos = helper.getLayoutPosition();
        if(pos == mData.size()-1){
            ((ImageView) helper.getView(R.id.line_img)).setVisibility(View.GONE);
        }
        ((TextView)helper.getView(R.id.name_photo_tv)).setText(baoXiaoBean.getUsernames());
        ((TextView)helper.getView(R.id.name_tv)).setText(baoXiaoBean.getUsernames());
        ((TextView)helper.getView(R.id.type_tv)).setText(CommonUtil.getStatus(baoXiaoBean.getTypes()));
        ((TextView)helper.getView(R.id.time_tv)).setText(baoXiaoBean.getTimes());
        if(baoXiaoBean.getTypes() == 1 || baoXiaoBean.getTypes() == 2){
            ((TextView)helper.getView(R.id.time_tv)).setVisibility(View.GONE);
        }else{
            ((TextView)helper.getView(R.id.time_tv)).setVisibility(View.VISIBLE);
        }
    }

}
