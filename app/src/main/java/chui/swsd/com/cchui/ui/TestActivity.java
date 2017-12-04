package chui.swsd.com.cchui.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.HomeActiveAdapter;
import chui.swsd.com.cchui.base.BaseActivity;

/**
 * 内容：
 * Created by 关云秀 on 2017\11\27 0027.
 */

public class TestActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.rlv)
    RecyclerView rlv;
    HomeActiveAdapter homeActiveAdapter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test;
    }

    @Override
    protected void initViews() {
        rlv.setLayoutManager(new LinearLayoutManager(this));
        homeActiveAdapter = new HomeActiveAdapter(getMenuActive());
        rlv.setAdapter(homeActiveAdapter);
        homeActiveAdapter.setOnLoadMoreListener(this);
        homeActiveAdapter.openLoadMore(5,true);
        homeActiveAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }
    public List<String> getMenuActive(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add("sss");
        }
        return  list;
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public void onLoadMoreRequested() {
        homeActiveAdapter.notifyDataChangedAfterLoadMore(getMenuActive(), true);//更新数据
    }
}
