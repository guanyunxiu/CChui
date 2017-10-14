package chui.swsd.com.cchui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.ScreenBean;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：公告adapter
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class ShenpiAdapter extends BaseMultiItemQuickAdapter<ShenPiBean> {
public ShenpiAdapter(List<ShenPiBean> data) {
        super(data);

        addItemType(ShenPiBean.QJ, R.layout.fragment_shenpi_qjitem);
        addItemType(ShenPiBean.CC, R.layout.fragment_shenpi_ccitem);
        addItemType(ShenPiBean.QC, R.layout.fragment_shenpi_wcitem);
        addItemType(ShenPiBean.JB, R.layout.fragment_shenpi_jbitem);
        addItemType(ShenPiBean.BX, R.layout.fragment_shenpi_item);
        }

    @Override
    protected void convert(final BaseViewHolder helper, final ShenPiBean item) {
            switch (helper.getItemViewType()) {
            case ShenPiBean.QJ:
                    helper.setText(R.id.name_tv,CommonUtil.getSubName(item.getUserHead().getName())); //头像
                    helper.setText(R.id.title_tv,item.getUserHead().getName()+"的请假");
                    helper.setText(R.id.title_type_tv,"请假类型："+item.getParam1());
                    helper.setText(R.id.start_time_tv,"开始时间："+item.getParam2());
                    if(item.getApproved().getTypes() == 3){//审批通过
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }else if(item.getApproved().getTypes() == 4){
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
                    else {//审批完成
                            helper.setText(R.id.status_tv, item.getApproved().getUsernames() + CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }

            break;
            case ShenPiBean.CC:
                    helper.setText(R.id.name_tv,CommonUtil.getSubName(item.getUserHead().getName())); //头像
                    helper.setText(R.id.title_tv,item.getUserHead().getName()+"的出差");
                    helper.setText(R.id.address_tv,"出差地点："+item.getParam1());
                    helper.setText(R.id.start_time_tv,"开始时间："+item.getParam2());
                    helper.setText(R.id.end_time_tv,"结束时间："+item.getParam3());
                    if(item.getApproved().getTypes() == 3){//审批通过
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }else if(item.getApproved().getTypes() == 4){
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
                    else {//审批完成
                            helper.setText(R.id.status_tv, item.getApproved().getUsernames() + CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
            break;
            case ShenPiBean.QC:
                    helper.setText(R.id.name_tv,CommonUtil.getSubName(item.getUserHead().getName())); //头像
                    helper.setText(R.id.title_tv,item.getUserHead().getName()+"的外出");
                    helper.setText(R.id.start_time_tv,"开始时间："+item.getParam1());
                    helper.setText(R.id.end_time_tv,"结束时间："+item.getParam2());
                    if(item.getApproved().getTypes() == 3){//审批通过
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }else if(item.getApproved().getTypes() == 4){
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
                    else {//审批完成
                            helper.setText(R.id.status_tv, item.getApproved().getUsernames() + CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
            break;
            case ShenPiBean.JB:
                    helper.setText(R.id.name_tv,CommonUtil.getSubName(item.getUserHead().getName())); //头像
                    helper.setText(R.id.title_tv,item.getUserHead().getName()+"的加班");
                    helper.setText(R.id.start_time_tv,"开始时间："+item.getParam1());
                    helper.setText(R.id.end_time_tv,"结束时间："+item.getParam2());
                    if(item.getApproved().getTypes() == 3){//审批通过
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }else if(item.getApproved().getTypes() == 4){
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
                    else {//审批完成
                            helper.setText(R.id.status_tv, item.getApproved().getUsernames() + CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
            break;
            case ShenPiBean.BX:
                    helper.setText(R.id.name_tv,CommonUtil.getSubName(item.getUserHead().getName())); //头像
                    helper.setText(R.id.title_tv,item.getUserHead().getName()+"的报销");
                    helper.setText(R.id.money_tv,"报销金额："+item.getParam3());
                    helper.setText(R.id.type_tv,"报销类型："+item.getParam1());
                    if(TextUtils.isEmpty(item.getParam2())){
                            helper.setText(R.id.details_tv, "报销明细：无");
                    }else {
                            helper.setText(R.id.details_tv, "报销明细：" + item.getParam2());
                    }
                    if(item.getApproved().getTypes() == 3){//审批通过
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }else if(item.getApproved().getTypes() == 4){
                            helper.setText(R.id.status_tv,CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
                    else {//审批完成
                            helper.setText(R.id.status_tv, item.getApproved().getUsernames() + CommonUtil.getShStatus(item.getApproved().getTypes()));
                    }
            break;
            }
       }
}
