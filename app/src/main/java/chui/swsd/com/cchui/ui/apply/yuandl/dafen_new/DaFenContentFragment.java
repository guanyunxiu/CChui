package chui.swsd.com.cchui.ui.apply.yuandl.dafen_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.DaFenConAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.model.DaFenConBean;
import chui.swsd.com.cchui.model.YdlTitleBean;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\30 0030.
 */

public class DaFenContentFragment extends BaseFragment implements DaFenContract.view {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.sub_tv)
    Button subBtn;
    @BindView(R.id.description_tv)
    TextView descriptionTv;
    DaFenConAdapter daFenConAdapter;
    int position = 0;
    private int flag,ft;
    private String title, bid, valTitle;
    DaFenPresenter daFenPresenter;
    List<DaFenConBean.ContentsBean> contentsBeanList = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    private int repeat;//0为不可重复，1为可重复
    List<Node> allNodeList = new ArrayList<>();
    public static DaFenContentFragment newInstance(int flag, String bid, String title, String valTitle,int ft) {
        DaFenContentFragment fragment = new DaFenContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);//最大的标题标志
        bundle.putString("bid", bid);
        bundle.putString("title", title);//最小的标题
        bundle.putString("valTitle", valTitle);//最小的标题
        bundle.putInt("ft",ft);//判断是否打过分
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_dafen_content;
    }

    @Override
    protected void initViews() {
        contentsBeanList.clear();
        flag = getArguments().getInt("flag");
        title = getArguments().getString("title");
        bid = getArguments().getString("bid");
        valTitle = getArguments().getString("valTitle");
        ft = getArguments().getInt("ft");
        if (TextUtils.isEmpty(valTitle)) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(valTitle);
        }
        if(ft == 0){
            subBtn.setText("提交");
        }else if(ft == 1){
            subBtn.setText("已提交");
        }
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        daFenPresenter = new DaFenPresenter(this, mContext);
        daFenPresenter.onSelectContent(bid);
    }

    @Override
    protected void updateViews() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setNestedScrollingEnabled(false);
        daFenConAdapter = new DaFenConAdapter(contentsBeanList, mContext);
        rvList.setAdapter(daFenConAdapter);
        daFenConAdapter.setOnItemClickListener(new DaFenConAdapter.OnItemClickListener() {
            @Override
            public void onDelItemClicked(int pos) {
                DaFenConBean.ContentsBean daFenBean = contentsBeanList.get(pos);
                if(daFenBean.getNodeList() != null) {
                    allNodeList.removeAll(daFenBean.getNodeList());//过滤掉当前点击的列人员
                }
                position = pos;
                Intent intent = new Intent(mContext, DaFenSelectPeopleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) daFenBean.getNodeList());
                bundle.putInt("typeflag", flag);
                bundle.putString("title", title);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Subscribe          //订阅事件FirstEvent,得到打分回来的人员
    public void onEventMainThread(List<Node> list) {
        Node node = list.get(0);
        if (node.getTitle().equals(title) && node.getTypeFlag() == flag) {//判断是不是当前的界面
            if (repeat == 0) {//不可重复
                boolean nodeflag = false;
                List<Node> delList = new ArrayList<>();
                for (Node node3 : allNodeList) {
                    for (Node node1 : list) {
                        if (node3.getId().equals(node1.getId())) {
                            delList.add(node1);
                            nodeflag = true;
                        }
                    }
                }
                if (nodeflag) {//查询到有添加重复的
                      CommonUtil.showToast(mContext,"添加重复的人员已删除");
                    list.removeAll(delList);
                }

                allNodeList.addAll(list);
                DaFenConBean.ContentsBean daFenBean = contentsBeanList.get(position);//得到当前选择打分人员的数据
                daFenBean.setNodeList(list);//设置它的打分人员
                daFenConAdapter.setNewData(contentsBeanList);
            } else if (repeat == 1) {//可重复
                DaFenConBean.ContentsBean daFenBean = contentsBeanList.get(position);//得到当前选择打分人员的数据
                daFenBean.setNodeList(list);//设置它的打分人员
                daFenConAdapter.setNewData(contentsBeanList);
            }
        }
    }

    @Override
    public void onSuccess(List<YdlTitleBean> list) {

    }

    @Override
    public void onSuccess(DaFenConBean daFenConBean) {
        repeat = daFenConBean.getRepeat();

        contentsBeanList.clear();
        if(!TextUtils.isEmpty(daFenConBean.getDescribe())) {
            descriptionTv.setVisibility(View.VISIBLE);
            descriptionTv.setText(daFenConBean.getDescribe());
        }else{
            descriptionTv.setVisibility(View.GONE);
        }
        List<DaFenConBean.ContentsBean> contentsBeanList2 = daFenConBean.getContents();
        if(contentsBeanList2.size()>0) {
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            for (DaFenConBean.ContentsBean contentsBean : contentsBeanList2) {
                contentsBean.setFt(ft);
            }
            contentsBeanList.addAll(contentsBeanList2);
            daFenConAdapter.setNewData(contentsBeanList);
        }else{
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }
    }

    @Override
    public void onSuccess() {
        CommonUtil.showToast(mContext,"提交成功");
        mMProgressDialog.dismiss();
        for(DaFenConBean.ContentsBean contentsBean:contentsBeanList){
            contentsBean.setFt(1);
        }
        daFenConAdapter.setNewData(contentsBeanList);
    }

    @Override
    public void onFail() {
        if(mMProgressDialog != null) {
            mMProgressDialog.dismiss();
        }
    }

    @OnClick(R.id.sub_tv)
    public void onClick() {
        StringBuffer stringBuffer = new StringBuffer();
        for(DaFenConBean.ContentsBean daFenBean:contentsBeanList){
            if(daFenBean.getNodeList()!=null) {
                stringBuffer.append(daFenBean.getValues()+",");
                for(int i=0;i<daFenBean.getNodeList().size();i++){
                    if(i!=daFenBean.getNodeList().size()-1) {
                        stringBuffer.append(daFenBean.getNodeList().get(i).getId() + ",");
                    }else{
                        stringBuffer.append(daFenBean.getNodeList().get(i).getId() + "GROUP");
                    }
                }
            }
        }
        Log.i("details",stringBuffer.toString());
        if(!TextUtils.isEmpty(stringBuffer.toString())){
            mMProgressDialog = CommonUtil.configDialogDefault(mContext);
            mMProgressDialog.show("正在提交...");
            daFenPresenter.onSubmit(flag,bid,stringBuffer.toString());
        }else{
            CommonUtil.showToast(mContext,"请选择打分人员，再进行提交");
        }

    }
}
