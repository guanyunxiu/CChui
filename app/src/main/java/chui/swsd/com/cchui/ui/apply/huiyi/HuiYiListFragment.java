package chui.swsd.com.cchui.ui.apply.huiyi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.HuiYiListAdapter;
import chui.swsd.com.cchui.adapter.NoticeAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.HuiYiBean;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.work.file.FileDetailsActivity;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\30 0030.
 */

public class HuiYiListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,HuiYiListContract.view{
    private int mCurrentCounter = 0;
    HuiYiListPresenter huiYiListPresenter;
    public static HuiYiListFragment newInstance(int flag) {
        HuiYiListFragment fragment = new HuiYiListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("typeFlag",flag);
        fragment.setArguments(bundle);
        return fragment;
    }
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    HuiYiListAdapter huiYiListAdapter;
    List<HuiYiBean> huiYiBeanList2 = new ArrayList<>();
    private int page = 1;
    private int typeFlag;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_huiyi;
    }

    @Override
    protected void initViews() {
        huiYiListPresenter = new HuiYiListPresenter(this,mContext);
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        typeFlag = getArguments().getInt("typeFlag");
        initAdapter();
    }
    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        huiYiListAdapter = new HuiYiListAdapter(huiYiBeanList2);
        //noticeAdapter.setLoadingView(new CustomLoadMoreView(mContext));
        huiYiListAdapter.setOnLoadMoreListener(this);
        huiYiListAdapter.openLoadMore(5,true);
        huiYiListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(huiYiListAdapter);
        mCurrentCounter = huiYiListAdapter.getData().size();
        huiYiListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                HuiYiBean huiYiBean = huiYiBeanList2.get(i);
                Intent intent = new Intent(mContext,HuiYiDetailsActivity.class);
                intent.putExtra("id",huiYiBean.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void updateViews() {
        huiYiListPresenter.onSelect(page,typeFlag);
    }

    //上拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        huiYiListPresenter.onSelect(page,typeFlag);
    }
    //下拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        huiYiListPresenter.onSelect(page,typeFlag);
    }

    @Override
    public void onSuccess(List<HuiYiBean> huiYiBeanList) {
        if(page == 1 && huiYiBeanList.size() == 0){//无数据
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
            mSwipeRefreshLayout.setRefreshing(false);
        }else if(page == 1 && huiYiBeanList.size()>0){//有数据，相当于下拉刷新
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            huiYiListAdapter.setNewData(huiYiBeanList);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(huiYiBeanList.size()>0 && page > 1){//有数据，加载更多
            huiYiListAdapter.notifyDataChangedAfterLoadMore(huiYiBeanList, true);//更新数据
        }
        mCurrentCounter = huiYiListAdapter.getItemCount();
        if( huiYiBeanList.size() < Constants.PAGESIZE){
            huiYiListAdapter.notifyDataChangedAfterLoadMore(false);
        }
        huiYiBeanList2.addAll(huiYiBeanList);
    }

    @Override
    public void onSuccess(HuiYiBean huiYiBean) {

    }

    @Override
    public void onFail() {
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(String flag){
         if(flag.equals("会议查询")){
             page = 1;
             huiYiListPresenter.onSelect(page,typeFlag);
         }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}
