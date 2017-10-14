package chui.swsd.com.cchui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.HuiYiBean;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：公告adapter
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class HuiYiListAdapter extends BaseQuickAdapter<HuiYiBean> {
    public HuiYiListAdapter(List<HuiYiBean> list) {
        super(R.layout.fragment_huiyi_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, HuiYiBean huiYiBean) {
        helper.setText(R.id.title_tv,huiYiBean.getTitle());
        helper.setText(R.id.num_tv,"("+huiYiBean.getUser().size()+")");
        helper.setText(R.id.time_tv,"开始时间："+huiYiBean.getStarttime());
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<huiYiBean.getUser().size();i++){
            if(i!=huiYiBean.getUser().size()-1){
                stringBuffer.append(huiYiBean.getUser().get(i).getName()+"、");
            }else{
                stringBuffer.append(huiYiBean.getUser().get(i).getName());
            }
        }
        helper.setText(R.id.name_tv,stringBuffer.toString());
        if(DateUtil.chaTime(huiYiBean.getStarttime()).equals("会议时间已到")){
            helper.setVisible(R.id.time2_tv,false);
        }else {
            helper.setText(R.id.time2_tv, DateUtil.chaTime(huiYiBean.getStarttime()) + "后开始");
        }

    }



}
