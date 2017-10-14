package chui.swsd.com.cchui.ui.apply.rizhi.red_rizhi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.RedRiZhiItemAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.RiZhiListBean;
import chui.swsd.com.cchui.model.ScreenBean;
import chui.swsd.com.cchui.popupwindow.ScreenPopup;
import chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi.RedDetailsRBActivity;
import chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi.RedDetailsYBActivity;
import chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi.RedDetailsZBActivity;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：我收到的（读的内容）
 * Created by 关云秀 on 2017\8\21 0021.
 */

public class RedReceiverRzFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,RedRzContract.view {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.screen_tv)
    TextView screenTv;
    @BindView(R.id.screen_lv)
    LinearLayout screenLv;
    @BindView(R.id.root_lv)
    LinearLayout rootLv;
    RedRiZhiItemAdapter redRiZhiItemAdapter;
    private int mCurrentCounter = 0;
    List<Node> listsInfo = new ArrayList<>();
    private String startTime,endTime,typeStr;
    RedRzPresenter redRzPresenter;
    private int page = 1;
    List<RiZhiListBean> listData = new ArrayList<>();
    List<String> stringListId = new ArrayList<>();
    private long startLong,endLong;
    private int typeInt = 3;
    public static RedReceiverRzFragment newInstance(String flag) {
        RedReceiverRzFragment fragment = new RedReceiverRzFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", "0");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_receiver_rz;
    }

    @Override
    protected void initViews() {
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        redRzPresenter = new RedRzPresenter(this,mContext);
        startLong = DateUtil.getMonthTime();
        endLong = DateUtil.getCurrent();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initAdapter();
    }
    private void initAdapter() {
        redRiZhiItemAdapter = new RedRiZhiItemAdapter(listData);
        //noticeAdapter.setLoadingView(new CustomLoadMoreView(mContext));
        redRiZhiItemAdapter.setOnLoadMoreListener(this);
        redRiZhiItemAdapter.openLoadMore(5, true);
        redRiZhiItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(redRiZhiItemAdapter);
        mCurrentCounter = redRiZhiItemAdapter.getData().size();
        redRiZhiItemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                RiZhiListBean riZhiListBean = listData.get(i);
                if(riZhiListBean.getCategory()==0) {//日报
                    Intent intent = new Intent(mContext, RedDetailsRBActivity.class);
                    intent.putExtra("id",riZhiListBean.getId());
                    startActivity(intent);
                }else if(riZhiListBean.getCategory()==1){//周报
                    Intent intent = new Intent(mContext, RedDetailsZBActivity.class);
                    intent.putExtra("id",riZhiListBean.getId());
                    startActivity(intent);
                }else if(riZhiListBean.getCategory()==2){//月报
                    Intent intent = new Intent(mContext, RedDetailsYBActivity.class);
                    intent.putExtra("id",riZhiListBean.getId());
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    protected void updateViews() {
        redRzPresenter.onSubmit(startLong+"",endLong+"",typeInt,page,1,stringListId);
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        redRzPresenter.onSubmit(startLong+"",endLong+"",typeInt,page,1,stringListId);
    }
    //上拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        redRzPresenter.onSubmit(startLong+"",endLong+"",typeInt,page,1,stringListId);
    }
    @Subscribe          //订阅事件FirstEvent
    public void onEventMainThread(ScreenBean screenBean) {
        if(screenBean.getType() == 0) {
            startTime = screenBean.getStartTime();
            endTime = screenBean.getEndTime();
            typeStr = screenBean.getTypeStr();
            stringListId.clear();
            for(int i=0;i<screenBean.getNodeList().size()-1;i++){
                stringListId.add(screenBean.getNodeList().get(i).getId());
            }
            listsInfo = screenBean.getNodeList();
            page = 1;
            if(!startTime.equals("请选择")){
                startLong = DateUtil.getTime6(startTime+" 00:00:01");
            }else{
                startLong = DateUtil.getMonthTime();
            }
            if(!endTime.equals("请选择")){
                endLong = DateUtil.getTime6(endTime+" 23:59:59");
            }else{
                endLong = DateUtil.getCurrent();
            }

            typeInt = CommonUtil.getRbStatus(typeStr);

            redRzPresenter.onSubmit(startLong+"",endLong+"",typeInt,page,1,stringListId);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
    @OnClick(R.id.screen_tv)
    public void onClick() {
             ScreenPopup  screenPopup = new ScreenPopup(mContext,listsInfo,startTime,endTime,typeStr);
             screenPopup.showAsDropDown(screenLv);
    }

    @Override
    public void onSuccess(List<RiZhiListBean> listBeanList) {
        Log.i("listbean",listBeanList.size()+"*****");
        if(page == 1 && listBeanList.size() == 0){//无数据
            redRiZhiItemAdapter.setNewData(listBeanList);
            mSwipeRefreshLayout.setRefreshing(false);
             mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }else if(page == 1 && listBeanList.size()>0){//有数据，相当于下拉刷新
              mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            redRiZhiItemAdapter.setNewData(listBeanList);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(listBeanList.size()>0 && page > 1){//有数据，加载更多
            redRiZhiItemAdapter.notifyDataChangedAfterLoadMore(listBeanList, true);//更新数据
        }
        if( listBeanList.size() < Constants.PAGESIZE){
            redRiZhiItemAdapter.notifyDataChangedAfterLoadMore(false);
        }
        listData.addAll(listBeanList);
    }

    @Override
    public void onFail() {
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
