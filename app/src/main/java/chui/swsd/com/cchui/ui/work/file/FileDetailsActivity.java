package chui.swsd.com.cchui.ui.work.file;

import android.os.Bundle;
import android.widget.TextView;

import com.hhl.library.FlowTagLayout;
import com.multilevel.treelist.Node;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.TagAdapter;
import chui.swsd.com.cchui.adapter.TagSelectAdapter;
import chui.swsd.com.cchui.base.BaseActivity;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\28 0028.
 */

public class FileDetailsActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    @BindView(R.id.flow_layout)
    FlowTagLayout flowTagLayout;
    TagSelectAdapter tagAdapter;
    List<Node> lists = new ArrayList<>();
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_filedetails;
    }

    @Override
    protected void initViews() {
        initTitle(true, "预约开会");
    }

    @Override
    protected void updateViews() {
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagSelectAdapter(this);
        flowTagLayout.setAdapter(tagAdapter);
        Node node = new Node();
        node.setName("武帅龙");
        Node node2 = new Node();
        node2.setName("费旋");
        lists.add(node);
        lists.add(node2);
        tagAdapter.onlyAddAll(lists);
    }
}
