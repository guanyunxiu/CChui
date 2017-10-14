package chui.swsd.com.cchui.ui.apply.gonggao;

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
import chui.swsd.com.cchui.adapter.GonggaoItemAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.GongGaoBean;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.contacts.ost.OstFragment;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\31 0031.
 */

public class GongGaoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,GongGaoContract.view{
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    GonggaoItemAdapter gonggaoItemAdapter;
    GongGaoPresenter gongGaoPresenter;
    private int mCurrentCounter = 0;
    private int flag;
    List<GongGaoBean> gongGaoBeanList2 = new ArrayList<>();
    private int page = 1;
    public static GongGaoFragment newInstance(int flag) {
        GongGaoFragment fragment = new GongGaoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag",flag);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_gonggao;
    }

    @Override
    protected void initViews() {
        gongGaoPresenter = new GongGaoPresenter(this,mContext);
        flag = getArguments().getInt("flag");

        initAdapter();
    }
    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        gonggaoItemAdapter = new GonggaoItemAdapter(gongGaoBeanList2);
        //noticeAdapter.setLoadingView(new CustomLoadMoreView(mContext));
        gonggaoItemAdapter.setOnLoadMoreListener(this);
        gonggaoItemAdapter.openLoadMore(5,true);
        gonggaoItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//       pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(gonggaoItemAdapter);
        mCurrentCounter = gonggaoItemAdapter.getData().size();
        gonggaoItemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                //CommonUtil.showToast(this,"点击了");
                //noticeAdapter.getItem(i);
                GongGaoBean gongGaoBean = gongGaoBeanList2.get(i);
                Intent intent = new Intent(mContext, NoticeDetailsActivity.class);
                intent.putExtra("id",gongGaoBean.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void updateViews() {
        gongGaoPresenter.onSelect(page,flag);
    }

    //上拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        gongGaoPresenter.onSelect(page,flag);
    }
    //下拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        gongGaoPresenter.onSelect(page,flag);
    }
    @Override
    public void onSuccess() {
    }
    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(List<GongGaoBean> gongGaoBeanList) {
        if(page == 1 && gongGaoBeanList.size() == 0){//无数据
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
            mSwipeRefreshLayout.setRefreshing(false);
        }else if(page == 1 && gongGaoBeanList.size()>0){//有数据，相当于下拉刷新
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            gonggaoItemAdapter.setNewData(gongGaoBeanList);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(gongGaoBeanList.size()>0 && page > 1){//有数据，加载更多
            gonggaoItemAdapter.notifyDataChangedAfterLoadMore(gongGaoBeanList, true);//更新数据
        }
        mCurrentCounter = gonggaoItemAdapter.getItemCount();
        if( gongGaoBeanList.size() < Constants.PAGESIZE){
            gonggaoItemAdapter.notifyDataChangedAfterLoadMore(false);
        }
        gongGaoBeanList2.addAll(gongGaoBeanList);
    }

    @Override
    public void onSuccess(GongGaoBean gongGaoBean) {

    }
}
