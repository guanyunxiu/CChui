package chui.swsd.com.cchui.ui.work.meet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.HuiYiListAdapter;
import chui.swsd.com.cchui.adapter.MeetAdapter;
import chui.swsd.com.cchui.adapter.NoticeAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.HuiYiBean;
import chui.swsd.com.cchui.ui.apply.huiyi.HuiYiDetailsActivity;
import chui.swsd.com.cchui.ui.apply.huiyi.HuiYiListContract;
import chui.swsd.com.cchui.ui.apply.huiyi.HuiYiListPresenter;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.work.file.FileDetailsActivity;
import chui.swsd.com.cchui.ui.work.make_meet.MakeMeetActivity;
import chui.swsd.com.cchui.ui.work.notice.NoticeFragment;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class MeetFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,HuiYiListContract.view{
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    HuiYiListAdapter huiYiListAdapter;
    List<HuiYiBean> huiYiBeanList2 = new ArrayList<>();
    HuiYiListPresenter huiYiListPresenter;
    private int page = 1;
    public static MeetFragment newInstance() {
        MeetFragment fragment = new MeetFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_meet_yy;
    }

    @Override
    protected void initViews() {
        huiYiListPresenter = new HuiYiListPresenter(this,mContext);
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
        huiYiListPresenter.onSelect(page,1);
    }

    //上拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        huiYiListPresenter.onSelect(page,1);
    }
    //下拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        huiYiListPresenter.onSelect(page,1);
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

    }
}