package chui.swsd.com.cchui.ui.contacts.ost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.OnTreeNodeClickListener;
import com.multilevel.treelist.TreeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.SimpleTreeRecyclerAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.apply.yuandl.ContractsActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.dafen.DaFenActivity;
import chui.swsd.com.cchui.widget.DensityUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;
import chui.swsd.com.cchui.widget.SpacesItemDecoration;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 内容：组织架构
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class OstFragment extends BaseFragment implements OstContract.view{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private TreeRecyclerAdapter mAdapter;
    OstPresenter ostPresenter;
    private int i = 10;
    public static List<Node> nodeList;
    public static OstFragment newInstance() {
        OstFragment fragment = new OstFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_ost;
    }

    @Override
    protected void initViews() {
        ostPresenter = new OstPresenter(this,mContext);
        ostPresenter.onSelect();
    }

    @Override
    protected void updateViews() {
    }
    public void onClick() {
        mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                // CommonUtil.showToast(ContractsActivity.this,position+"***");
                if (!node.getpId().equals("1")) {//跳转到聊天界面

                    RongIM.getInstance().startPrivateChat(mContext,node.getRongid(),node.getName());
                }
            }
        });
    }
    @Override
    public void onSuccess(List<DepConBean> list) {
        if(list.size()>0) {
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            //第一个参数  RecyclerView
            //第二个参数  上下文
            //第三个参数  数据集
            //第四个参数  默认展开层级数 0为不展开
            //第五个参数  展开的图标
            //第六个参数  闭合的图标
            mAdapter = new SimpleTreeRecyclerAdapter(recyclerview, mContext,
                    getList(list), 0, R.mipmap.icon_bottom_arr, R.mipmap.icon_right_arr, false);
            //recyclerview.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.height_10)));
            recyclerview.setAdapter(mAdapter);
            onClick();
        }else{
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }
    }
    List<Node> mDatas = new ArrayList<Node>();
    public List<Node> getList(List<DepConBean> list){
           for(int m=2;m<list.size()+2;m++){
             DepConBean depConBean = list.get(m-2);
               String s = m+"";
            mDatas.add(new Node(s,"",1+"",depConBean.getName()));
            for(DepConBean.UserBasisBean userBasisBean:depConBean.getUserBasis()){
                mDatas.add(new Node(userBasisBean.getUserid()+"",userBasisBean.getRongid()+"",s,userBasisBean.getUsername()));
            }
        }
        nodeList = mDatas;
        return mDatas;
    }
    @Override
    public void onFail() {
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }
}
