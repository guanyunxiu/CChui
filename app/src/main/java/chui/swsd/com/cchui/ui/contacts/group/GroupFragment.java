package chui.swsd.com.cchui.ui.contacts.group;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.GroupAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;
import chui.swsd.com.cchui.widget.RecycleViewDivider;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

/**
 * 内容：群组
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class GroupFragment extends BaseFragment implements GroupContract.view{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    GroupAdapter groupAdapter;
    GroupPrecenter groupPrecenter;
    private String TAG = "GroupFragment";
    List<GroupListBean> groupListBeen1 = new ArrayList<>();
    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_group;
    }

    @Override
    protected void initViews() {
        groupPrecenter = new GroupPrecenter(this,mContext);
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        groupAdapter = new GroupAdapter(groupListBeen1,mContext);
        recyclerview.setAdapter(groupAdapter);
        groupAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                GroupListBean groupListBean = groupListBeen1.get(i);
                RongIM.getInstance().startGroupChat(mContext, groupListBean.getRongid()+"", groupListBean.getName());
            }
        });

    }

    @Override
    protected void updateViews() {
        groupPrecenter.onSelect();
    }
    public static List<GroupBean> getList(){
        List<GroupBean> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            GroupBean groupBean = new GroupBean();
            groupBean.setName("一想天开"+i);
            list.add(groupBean);
        }

        return list;
    }

    @Override
    public void onSuccess(List<GroupListBean> groupListBeen) {
        if(groupListBeen.size()>0) {
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            groupListBeen1 = groupListBeen;
            groupAdapter.setNewData(groupListBeen);
            for (GroupListBean groupListBean : groupListBeen) {
                RongIM.getInstance().refreshGroupInfoCache(new Group(groupListBean.getRongid(), groupListBean.getName(), Uri.parse(UrlAddress.URLAddress + groupListBean.getHeadimg())));
            }
        }else {
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }

    }

    @Override
    public void onFail() {
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(String flag){
        if(flag.equals("群组查询")){
            groupPrecenter.onSelect();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}
