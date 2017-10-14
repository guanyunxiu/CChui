package chui.swsd.com.cchui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GongGaoBean;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.widget.ExpandLayout;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class GonggaoItemAdapter extends BaseQuickAdapter<GongGaoBean> {
    public GonggaoItemAdapter(List<GongGaoBean> list) {
        super(R.layout.activity_gonggao_item,list);
    }
    @Override
    protected void convert(final BaseViewHolder helper, GongGaoBean gongGaoBean) {
        //((TextView) helper.getView(R.id.name_tv)).setText(groupBean.getName());
        helper.setText(R.id.title_tv,gongGaoBean.getTitle());
        helper.setText(R.id.content_tv,gongGaoBean.getContent());
        helper.setText(R.id.time_tv,gongGaoBean.getTime());
    }
}
