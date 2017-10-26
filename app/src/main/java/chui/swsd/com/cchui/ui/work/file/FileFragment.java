package chui.swsd.com.cchui.ui.work.file;

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
import chui.swsd.com.cchui.adapter.FileAdapter;
import chui.swsd.com.cchui.adapter.MeetAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.FileDetailsBean;
import chui.swsd.com.cchui.model.FileManagerBean;
import chui.swsd.com.cchui.ui.apply.file.FileDetailActivity;
import chui.swsd.com.cchui.ui.apply.file.release_file.ReleaseFileContract;
import chui.swsd.com.cchui.ui.apply.file.release_file.ReleaseFilePresenter;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.work.meet.MeetFragment;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class FileFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,ReleaseFileContract.view{
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    FileAdapter fileAdapter;
    ReleaseFilePresenter releaseFilePresenter;
    private int page = 1;
    List<FileManagerBean> fileManagerBeanList2 = new ArrayList<>();
    public static FileFragment newInstance() {
        FileFragment fragment = new FileFragment();
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
        releaseFilePresenter = new ReleaseFilePresenter(this,mContext);
        initAdapter();
    }
    private void initAdapter() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fileAdapter = new FileAdapter(fileManagerBeanList2,1);
        //noticeAdapter.setLoadingView(new CustomLoadMoreView(mContext));
        fileAdapter.setOnLoadMoreListener(this);
        fileAdapter.openLoadMore(5, true);
        fileAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(fileAdapter);
        fileAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                FileManagerBean fileManagerBean = fileManagerBeanList2.get(i);
                Intent intent = new Intent(mContext, FileDetailActivity.class);
                intent.putExtra("id",fileManagerBean.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void updateViews() {
        releaseFilePresenter.onSelect(page,1);
    }
    //上拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        fileManagerBeanList2.clear();
        releaseFilePresenter.onSelect(page,1);
    }
    //下拉加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        releaseFilePresenter.onSelect(page,1);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {
        mSwipeRefreshLayout.setRefreshing(false);
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }

    @Override
    public void onSuccess(List<FileManagerBean> fileManagerBeanList) {
        if(page == 1 && fileManagerBeanList.size() == 0){//无数据
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
            mSwipeRefreshLayout.setRefreshing(false);
        }else if(page == 1 && fileManagerBeanList.size()>0){//有数据，相当于下拉刷新
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            fileAdapter.setNewData(fileManagerBeanList);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(fileManagerBeanList.size()>0 && page > 1){//有数据，加载更多
            fileAdapter.notifyDataChangedAfterLoadMore(fileManagerBeanList, true);//更新数据
        }
        if( fileManagerBeanList.size() < Constants.PAGESIZE){
            fileAdapter.notifyDataChangedAfterLoadMore(false);
        }
        fileManagerBeanList2.addAll(fileManagerBeanList);
    }

    @Override
    public void onSuccess(FileDetailsBean fileDetailsBean) {

    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(String flagstr){
        if(flagstr.equals("文件查询")){
            page = 1;
            fileManagerBeanList2.clear();
            releaseFilePresenter.onSelect(page,1);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}