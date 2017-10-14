package chui.swsd.com.cchui.ui.apply.huiyi;

import android.widget.TextView;

import com.hhl.library.FlowTagLayout;
import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.TagSelectAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.model.HuiYiBean;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\21 0021.
 */

public class HuiYiDetailsActivity extends BaseActivity implements HuiYiListContract.view{
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
    HuiYiListPresenter huiYiListPresenter;
    private int id;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_filedetails;
    }

    @Override
    protected void initViews() {
        initTitle(true, "预约开会");
        id = getIntent().getIntExtra("id",0);
        huiYiListPresenter = new HuiYiListPresenter(this,this);
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("查询中...");
        huiYiListPresenter.onSelectSing(id);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public void onSuccess(List<HuiYiBean> huiYiBeanList) {

    }

    @Override
    public void onSuccess(HuiYiBean huiYiBean) {
        mMProgressDialog.dismiss();
        setData(huiYiBean);
    }
    public void setData(HuiYiBean huiYiBean){
        contentTv.setText(huiYiBean.getTitle());
        startTimeTv.setText(huiYiBean.getStarttime());
        endTimeTv.setText(huiYiBean.getUsedtime());
        setAdapter(huiYiBean.getUser());
    }

    public void setAdapter(List<HuiYiBean.UserBean> list){
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagSelectAdapter(this);
        flowTagLayout.setAdapter(tagAdapter);
        for(HuiYiBean.UserBean userBean:list){
            Node node = new Node();
            node.setName(userBean.getName());
            lists.add(node);
        }
        tagAdapter.onlyAddAll(lists);
    }
    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
}
