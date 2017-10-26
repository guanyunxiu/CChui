package chui.swsd.com.cchui.ui.work.shenpi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.NoticeAdapter;
import chui.swsd.com.cchui.adapter.ShenpiAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpBXDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpCCDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpJBDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpQjDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpWCDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpContentContract;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpContentPresenter;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpFqContentActivity;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.work.notice.NoticeFragment;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;
import chui.swsd.com.cchui.widget.MultiStateView;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class ShenpiFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,SpContentContract.view{
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    ShenpiAdapter shenpiAdapter;
    private int mCurrentCounter = 0;
    SpContentPresenter spContentPresenter;
    private int page = 1;
    List<ShenPiBean> listData = new ArrayList<>();
    public static ShenpiFragment newInstance() {
        ShenpiFragment fragment = new ShenpiFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);

        initAdapter();
    }
    private void initAdapter() {
        spContentPresenter = new SpContentPresenter(this,mContext);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        shenpiAdapter = new ShenpiAdapter(listData);
        shenpiAdapter.setOnLoadMoreListener(this);
        shenpiAdapter.openLoadMore(5,true);
        shenpiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(shenpiAdapter);
        mCurrentCounter = shenpiAdapter.getData().size();
        shenpiAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ShenPiBean shenPiBean = listData.get(i);
                Intent intent = null;
                if(shenPiBean.getCategory() == 0){//请假
                    intent = new Intent(mContext, SpQjDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 1){//出差
                    intent = new Intent(mContext, SpCCDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 2){//外出
                    intent = new Intent(mContext, SpWCDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 3){//加班
                    intent = new Intent(mContext, SpJBDetailsActivity.class);
                }else if(shenPiBean.getCategory() == 4){//报销
                    intent = new Intent(mContext, SpBXDetailsActivity.class);
                }
                intent.putExtra("id",shenPiBean.getIds());
                intent.putExtra("spStatus", 1);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void updateViews() {
        spContentPresenter.onSubmit(page,1,5,2,0);//待我审批的
    }

    //上拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        listData.clear();
        spContentPresenter.onSubmit(page,1,5,2,0);//待我审批的
    }
    //下拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        spContentPresenter.onSubmit(page,1,5,2,0);//待我审批的
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
        if( list.size() < Constants.PAGESIZE){//数据加载完毕
            shenpiAdapter.notifyDataChangedAfterLoadMore(false);
        }
        listData.addAll(list);
    }

    @Override
    public void onFail() {
        mSwipeRefreshLayout.setRefreshing(false);
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }
    @Subscribe          //订阅事件FirstEvent
    public void onEventMainThread(String str) {
        if (str.equals("查询")) {
            page = 1;
            listData.clear();
            spContentPresenter.onSubmit(page,1,5,2,0);//待我审批的
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

}
