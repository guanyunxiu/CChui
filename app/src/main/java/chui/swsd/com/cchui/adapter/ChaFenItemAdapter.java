package chui.swsd.com.cchui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.expandable.ExpandableLayout;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.widget.ExpandLayout;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class ChaFenItemAdapter extends BaseQuickAdapter<YdlScoreBean> {
    public ChaFenItemAdapter(List<YdlScoreBean> list) {
        super(R.layout.activity_chafen_item,list);
    }
    @Override
    protected void convert(final BaseViewHolder helper, YdlScoreBean depConBean) {
        final ExpandableLayout expandLayout = ((ExpandableLayout) helper.getView(R.id.expandLayout));
        int allScore = depConBean.getBasis()+depConBean.getCompany()+depConBean.getScoail()+depConBean.getUser();
        helper.setText(R.id.name_tv,depConBean.getUsername());
        helper.setText(R.id.jichu_tv,"基础分："+depConBean.getBasis()+"");
        helper.setText(R.id.company_tv,"公司价值："+depConBean.getCompany()+"");//公司价值分
        helper.setText(R.id.sociology_tv,"社会价值："+depConBean.getScoail()+"");//社会价值分
        helper.setText(R.id.staff_tv,"员工："+depConBean.getUser()+"");//员工分
        helper.setText(R.id.allscore_tv,"总分："+allScore+"");//总分
        ((TextView) helper.getView(R.id.allscore_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandLayout.isExpanded()) {
                    expandLayout.collapse();
                } else {
                    expandLayout.expand();
                }
            }
        });
    }
}
