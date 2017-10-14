package chui.swsd.com.cchui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.RiZhiListBean;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class RedRiZhiItemAdapter extends BaseQuickAdapter<RiZhiListBean> {
    public RedRiZhiItemAdapter(List<RiZhiListBean> list) {
        super(R.layout.fragment_red_rizhi_item,list);
    }

    @Override
    protected void convert(BaseViewHolder helper, RiZhiListBean riZhiListBean) {
        //((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
        helper.setText(R.id.name_photo_tv, CommonUtil.getSubName(riZhiListBean.getUserHead().getName()));
        helper.setText(R.id.name_tv,riZhiListBean.getUserHead().getName());
        helper.setText(R.id.time_tv, DateUtil.getTime5(Long.parseLong(riZhiListBean.getTime())));
        helper.setText(R.id.con_tv,riZhiListBean.getAccomplish());
        if(riZhiListBean.getCategory() == 0){//日报
            helper.setImageResource(R.id.type_img,R.mipmap.rz_03);
        }else if(riZhiListBean.getCategory() == 1){//周报
            helper.setImageResource(R.id.type_img,R.mipmap.rz_04);
        }else if(riZhiListBean.getCategory() == 2){//月报
            helper.setImageResource(R.id.type_img,R.mipmap.rz_05);
        }
    }
}
