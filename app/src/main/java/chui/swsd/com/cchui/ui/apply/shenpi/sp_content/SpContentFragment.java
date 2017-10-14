package chui.swsd.com.cchui.ui.apply.shenpi.sp_content;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.ShenpiAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpBXDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpCCDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpJBDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpQjDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpWCDetailsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\24 0024.
 */

public class SpContentFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, SpContentContract.view {
    public static SpContentFragment newInstance(int flag) {
        SpContentFragment fragment = new SpContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("currentPos", flag);
        fragment.setArguments(bundle);
        return fragment;
    }
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    ShenpiAdapter shenpiAdapter;
    private int mCurrentCounter = 0;
    private int currentPos;//0待我审批的,1我已审批的
    SpContentPresenter spContentPresenter;
    private int page = 1;
    List<ShenPiBean> listData = new ArrayList<>();
    private int ztInt = 2, typeInt = 5;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_sp_content;
    }

    @Override
    protected void initViews() {
        currentPos = getArguments().getInt("currentPos", 0);
        EventBus.getDefault().register(this);
        spContentPresenter = new SpContentPresenter(this, mContext);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initAdapter();
    }

    private void initAdapter() {
        shenpiAdapter = new ShenpiAdapter(listData);
        //noticeAdapter.setLoadingView(new CustomLoadMoreView(mContext));
        shenpiAdapter.setOnLoadMoreListener(this);
        shenpiAdapter.openLoadMore(5, true);
        shenpiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//      pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(shenpiAdapter);
        mCurrentCounter = shenpiAdapter.getData().size();
        shenpiAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ShenPiBean shenPiBean = listData.get(i);
                Intent intent = null;
                if (shenPiBean.getCategory() == 0) {//请假
                    intent = new Intent(mContext, SpQjDetailsActivity.class);
                } else if (shenPiBean.getCategory() == 1) {//出差
                    intent = new Intent(mContext, SpCCDetailsActivity.class);
                } else if (shenPiBean.getCategory() == 2) {//外出
                    intent = new Intent(mContext, SpWCDetailsActivity.class);
                } else if (shenPiBean.getCategory() == 3) {//加班
                    intent = new Intent(mContext, SpJBDetailsActivity.class);
                } else if (shenPiBean.getCategory() == 4) {//报销
                    intent = new Intent(mContext, SpBXDetailsActivity.class);
                }
                intent.putExtra("id", shenPiBean.getIds());
                intent.putExtra("spStatus", 1);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void updateViews() {

        spContentPresenter.onSubmit(page, 1, typeInt, ztInt, currentPos);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        SpContentActivity.typePos = currentPos;
        super.setUserVisibleHint(isVisibleToUser);
    }

    //上拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        spContentPresenter.onSubmit(page, 1, typeInt, ztInt, currentPos);
    }

    //下拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        spContentPresenter.onSubmit(page, 1, typeInt, ztInt, currentPos);
    }

    @Override
    public void onSuccess(List<ShenPiBean> list) {
        for (ShenPiBean shenPiBean : list) {
            shenPiBean.setItemType(shenPiBean.getCategory());
        }
        if (page == 1 && list.size() == 0) {//无数据
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
            mSwipeRefreshLayout.setRefreshing(false);
        } else if (page == 1 && list.size() > 0) {//有数据，相当于下拉刷新
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            shenpiAdapter.setNewData(list);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (list.size() > 0 && page > 1) {//有数据，加载更多
            shenpiAdapter.notifyDataChangedAfterLoadMore(list, true);//更新数据
        }
        mCurrentCounter = shenpiAdapter.getItemCount();
        if (list.size() < Constants.PAGESIZE) {
            shenpiAdapter.notifyDataChangedAfterLoadMore(false);
        }
        listData.addAll(list);
    }

    @Override
    public void onFail() {
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }

    @Subscribe          //订阅事件FirstEvent
    public void onEventMainThread(String str) {
        if (str.equals("查询")) {
            page = 1;
            listData.clear();
            spContentPresenter.onSubmit(page, 1, typeInt, ztInt, currentPos);
        } else if (str.contains("筛选")) {
            String[] strs = str.split(",");
            ztInt = CommonUtil.getFt(strs[1]);
            typeInt = CommonUtil.getScreen(strs[2]);

            mLoadStateManager.setState(LoadStateManager.LoadState.Init);
            listData.clear();
            if (currentPos == 0) {//为0时状态为不需要，所以=2
                ztInt = 2;
            }
            spContentPresenter.onSubmit(page, 1, typeInt, ztInt, currentPos);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }



}
