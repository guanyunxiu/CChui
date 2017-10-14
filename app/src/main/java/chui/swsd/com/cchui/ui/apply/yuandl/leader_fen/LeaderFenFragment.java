package chui.swsd.com.cchui.ui.apply.yuandl.leader_fen;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.ChaFenItemAdapter;
import chui.swsd.com.cchui.adapter.GroupAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\18 0018.
 */

public class LeaderFenFragment extends BaseFragment implements LeaderFenContract.view{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    ChaFenItemAdapter chaFenItemAdapter;
    private String departname;
    LeaderFenPresenter leaderFenPresenter;
    List<YdlScoreBean> depConBeanList = new ArrayList<>();
    public static LeaderFenFragment newInstance(String departname) {
        LeaderFenFragment fragment = new LeaderFenFragment();
        Bundle bundle = new Bundle();
        bundle.putString("departname", departname);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_chafen;
    }

    @Override
    protected void initViews() {
        departname = getArguments().getString("departname");
        leaderFenPresenter = new LeaderFenPresenter(this,mContext);
        setAdapter();
    }
    public void setAdapter(){
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        chaFenItemAdapter = new ChaFenItemAdapter(depConBeanList);
        chaFenItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyclerview.setAdapter(chaFenItemAdapter);
    }
    @Override
    protected void updateViews() {
        leaderFenPresenter.onSelectDepCon(departname);
    }


    @Override
    public void onSuccess(List<DepartmentBean> list) {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccessCon(List<YdlScoreBean> depconList) {
        if(depconList.size()>0) {
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            chaFenItemAdapter.setNewData(depconList);
        }else{
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }
    }
}
