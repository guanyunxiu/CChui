package chui.swsd.com.cchui.ui.apply.yuandl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.OnTreeNodeClickListener;
import com.multilevel.treelist.TreeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.SimpleTreeRecyclerAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.dafen.DaFenActivity;
import chui.swsd.com.cchui.ui.contacts.ost.OstContract;
import chui.swsd.com.cchui.ui.contacts.ost.OstFragment;
import chui.swsd.com.cchui.ui.contacts.ost.OstPresenter;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\16 0016.
 */

public class ContractsActivity extends BaseActivity implements OstContract.view{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private TreeRecyclerAdapter mAdapter;
    private int typeFlag;
    OstPresenter ostPresenter;
    protected List<Node> mDatas = new ArrayList<Node>();
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_contract;
    }

    @Override
    protected void initViews() {
        initTitle(true,"选择人员");
        typeFlag = getIntent().getIntExtra("typeFlag",0);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ostPresenter = new OstPresenter(this,this);
        ostPresenter.onSelect();

    }
    public void onClick(){
        mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
               // CommonUtil.showToast(ContractsActivity.this,position+"***");
                if(!node.getpId().equals("1")) {
                    if(typeFlag == 0){//跳转到打分页面
                        Intent intent = new Intent(ContractsActivity.this, DaFenActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", node.getName());
                        bundle.putString("id", (String) node.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("name", node.getName());
                        intent.putExtra("id", (String) node.getId());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
    }
    @Override
    protected void updateViews() {

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(List<DepConBean> list) {
        if(list.size()>0) {
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            mDatas = CommonUtil.getList(list);
            //第一个参数  RecyclerView
            //第二个参数  上下文
            //第三个参数  数据集
            //第四个参数  默认展开层级数 0为不展开
            //第五个参数  展开的图标
            //第六个参数  闭合的图标
            mAdapter = new SimpleTreeRecyclerAdapter(recyclerview, this,
                    mDatas, 1, R.mipmap.icon_bottom_arr, R.mipmap.icon_right_arr, false);
            recyclerview.setAdapter(mAdapter);
            onClick();
        }else{
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }
    }

    @Override
    public void onFail() {
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }
}
