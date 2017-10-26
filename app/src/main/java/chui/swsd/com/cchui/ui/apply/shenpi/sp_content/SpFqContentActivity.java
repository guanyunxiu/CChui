package chui.swsd.com.cchui.ui.apply.shenpi.sp_content;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.ShenpiAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpBXDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpCCDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpJBDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpQjDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpWCDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpContentActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpShaiShuaiActivity;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\25 0025.
 */

public class SpFqContentActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,SpContentContract.view{
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    ShenpiAdapter shenpiAdapter;
    private int mCurrentCounter = 0;
    private String ztstr,typestr;
    private int ztInt = 2,typeInt = 5;
    SpContentPresenter spContentPresenter;
    private int page = 1;
    List<ShenPiBean> listData = new ArrayList<>();
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_fqcontent;
    }

    @Override
    protected void initViews() {
        initTitle(true,"我发起的");
        spContentPresenter = new SpContentPresenter(this,this);

    }
    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shenpiAdapter = new ShenpiAdapter(listData);
        //noticeAdapter.setLoadingView(new CustomLoadMoreView(mContext));
        shenpiAdapter.setOnLoadMoreListener(this);
        shenpiAdapter.openLoadMore(5, true);
        shenpiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(shenpiAdapter);
        mCurrentCounter = shenpiAdapter.getData().size();
        shenpiAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ShenPiBean shenPiBean = listData.get(i);
                Intent intent = null;
                if(shenPiBean.getCategory() == 0){//请假
                     intent = new Intent(SpFqContentActivity.this, SpQjDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 1){//出差
                     intent = new Intent(SpFqContentActivity.this, SpCCDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 2){//外出
                     intent = new Intent(SpFqContentActivity.this, SpWCDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 3){//加班
                     intent = new Intent(SpFqContentActivity.this, SpJBDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 4){//报销
                     intent = new Intent(SpFqContentActivity.this, SpBXDetailsActivity.class);
                }
                intent.putExtra("id",shenPiBean.getIds());
                startActivity(intent);
            }
        });
    }


    @Override
    protected void updateViews() {
        spContentPresenter.onSubmit(page,0,typeInt,ztInt,0);
        initAdapter();
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        spContentPresenter.onSubmit(page,0,typeInt,ztInt,0);
    }
    //上拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        spContentPresenter.onSubmit(page,0,typeInt,ztInt,0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sx, menu);
        final MenuItem item = menu.findItem(R.id.action_edit);
        TextView textView = (TextView) item.getActionView().findViewById(R.id.sx_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpContentActivity.typePos = 0;
                Intent intent = new Intent(SpFqContentActivity.this,SpShaiShuaiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ztstr",ztstr);
                bundle.putString("typestr",typestr);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
                overridePendingTransition(R.anim.fade_entry, R.anim.hold);
            }
        });
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                ztstr = data.getStringExtra("ztstr");
                typestr = data.getStringExtra("typestr");
                ztInt = CommonUtil.getFt(ztstr);
                typeInt = CommonUtil.getScreen(typestr);
                mLoadStateManager.setState(LoadStateManager.LoadState.Init);
                listData.clear();
                spContentPresenter.onSubmit(page,0,typeInt,ztInt,0);
            }
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
    @Override
    public void onSuccess(List<ShenPiBean> list) {
        for(ShenPiBean shenPiBean:list){
            shenPiBean.setItemType(shenPiBean.getCategory());
        }
        if(page == 1 && list.size() == 0){//无数据
            mSwipeRefreshLayout.setRefreshing(false);
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }else if(page == 1 && list.size()>0){//有数据，相当于下拉刷新
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            shenpiAdapter.setNewData(list);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(list.size()>0 && page > 1){//有数据，加载更多
            shenpiAdapter.notifyDataChangedAfterLoadMore(list, true);//更新数据
        }
        mCurrentCounter = shenpiAdapter.getItemCount();
        if( list.size() < Constants.PAGESIZE){
            shenpiAdapter.notifyDataChangedAfterLoadMore(false);
        }
        listData.addAll(list);
    }
    @Override
    public void onFail() {
        mSwipeRefreshLayout.setRefreshing(false);
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }
}
