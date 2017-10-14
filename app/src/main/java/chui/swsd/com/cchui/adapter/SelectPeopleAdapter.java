package chui.swsd.com.cchui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.multilevel.treelist.Node;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupBean;

/**
 * 内容：公告adapter
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class SelectPeopleAdapter extends BaseQuickAdapter<Node> {
    public SelectPeopleAdapter(List<Node> list) {
        super(R.layout.activity_select_people_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, Node node) {
        ((TextView) helper.getView(R.id.name_tv)).setText(node.getName());
    }



}
