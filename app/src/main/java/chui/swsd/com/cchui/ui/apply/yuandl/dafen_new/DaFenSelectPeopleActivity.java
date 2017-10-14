package chui.swsd.com.cchui.ui.apply.yuandl.dafen_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.SimpleTreeRecyclerAdapter;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.ui.contacts.ost.OstContract;
import chui.swsd.com.cchui.ui.contacts.ost.OstPresenter;
import chui.swsd.com.cchui.ui.group.AddGroupActivity;
import chui.swsd.com.cchui.ui.work.select_people.AddGroupUserContract;
import chui.swsd.com.cchui.ui.work.select_people.AddGroupUserPresenter;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\11 0011.
 */

public class DaFenSelectPeopleActivity extends BaseSwipeBackActivity implements AddGroupUserContract.view,OstContract.view{
    private TreeRecyclerAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.sub_btn)
    Button subBtn;
    @BindView(R.id.sel_tv)
    TextView selTv;
    List<Node> sellist = new ArrayList();
    private List<Node> selNodes = new ArrayList<>();
    int flag = 0,typeFlag;
    String title;
    private int groupId;
    protected List<Node> mDatas = new ArrayList<Node>();
    protected List<GroupListBean.UserSimple> dataList = new ArrayList<>();
    AddGroupUserPresenter addGroupUserPresenter;
    OstPresenter ostPresenter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_people;
    }

    @Override
    protected void initViews() {

        ostPresenter = new OstPresenter(this,this);
        ostPresenter.onSelect();
        sellist = (List) getIntent().getSerializableExtra("list");
        flag = getIntent().getIntExtra("flag",0);
        typeFlag = getIntent().getIntExtra("typeflag",0);
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected void updateViews() {
        if(flag == 2){
            initTitle(true,"添加群组成员");
        }else if(flag == 3){
            initTitle(true,"移除群组成员");
        }else {
            initTitle(true, "联系人");
        }

    }
    private void initSelData(){
        if(sellist!=null) {
            for (Node node : mDatas) {
                for (Node str : sellist) {
                    if(!TextUtils.isEmpty(str.getName())) {
                            if (str.getId().equals(node.getId()) && !node.getpId().equals("1")) {
                                node.setChecked(true);
                            }
                    }
                }
            }
        }
      //  setNodeParentChecked();
    }

    public void selNodes(){//勾选已选中的人
        selNodes.clear();
        final List<Node> allNodes = mAdapter.getAllNodes();
        for (int i = 0; i < allNodes.size(); i++) {
            if (allNodes.get(i).isChecked() && allNodes.get(i).getIcon() == -1){
                allNodes.get(i).setTitle(title);
                allNodes.get(i).setTypeFlag(typeFlag);
                selNodes.add(allNodes.get(i));
            }
        }
    }
    @OnClick(R.id.sub_btn)
    public void onClick() {
        if(flag == 0){
            selNodes();
            EventBus.getDefault().post(selNodes);
        }else if(flag == 1){  //建群
            if(selNodes.size()>0) {
                Intent intent = new Intent(this, AddGroupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) selNodes);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                CommonUtil.showToast(this, "请至少邀请一位好友创建群组");
            }
        }else if(flag == 2){//添加群成员
            List<String> userList = new ArrayList<>();
            for(Node node:selNodes){
                userList.add(node.getId()+"and"+node.getName());
            }
            addGroupUserPresenter.onAddGroupUser(groupId,userList);
        }else if(flag == 3){//移除群组成员
            List<Integer> userList = new ArrayList<>();
            for(Node node:selNodes){
                userList.add(Integer.parseInt(node.getId()));
            }
            addGroupUserPresenter.onDeleteGroupUser(groupId,userList);
        }

        finish();
    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(String event){
        //idAddressTv.setText(event);//获取事件中传递的参数
        selNodes();
        selTv.setText("已选择"+selNodes.size()+"人");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }


    protected void setNodeParentChecked(){
        boolean isChecked = true;
        boolean isflag = true;
        for (Node children : mDatas) {//所有结点进行比较
            if(children.getpId().equals(1)) {//查询
                for (Node str : sellist) {

                         isflag = false;
                        if(children.getId().equals(str.getId())){
                            isflag = true;
                            break;
                        }
                }
                if(!isflag){
                    isChecked = false;
                    children.setChecked(false);
                    break;
                }else{
                    children.setChecked(true);
                }
            }
       }

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(int flag) {
        if(flag == 0) {
            CommonUtil.showToast(this, "添加成功");
        }else if(flag == 1){
            CommonUtil.showToast(this,"移除成功");
        }
        EventBus.getDefault().post("成功");
         finish();
    }

    @Override
    public void onSuccess(List<DepConBean> list) {
        if(list.size()>0) {
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            mDatas = CommonUtil.getList(list);
            initData();
        }else{
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }
    }

    @Override
    public void onFail() {
        mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
    }

    @Override
    public void onFail(int flag) {
        if(flag == 0) {
            CommonUtil.showToast(this, "添加失败");
        }else if(flag == 1){
            CommonUtil.showToast(this,"移除失败");
        }
        finish();
    }
    public void initData(){
        if(flag == 2){//加群减人
            dataList = (List<GroupListBean.UserSimple>) getIntent().getSerializableExtra("data");//获得已有的人
            groupId = getIntent().getIntExtra("groupId",0);//得到群组ID
            addGroupUserPresenter = new AddGroupUserPresenter(this,this);
            List<Node> deleteDatas = new ArrayList<Node>();
            for(Node node:mDatas){
                for(GroupListBean.UserSimple userSimple:dataList){
                    Log.i("nodeid",node.getId()+"*********"+userSimple.getUserid());
                    int id = Integer.parseInt(node.getId());
                    if (!node.getpId().equals("1")) {
                        if (id == userSimple.getUserid()) {
                            deleteDatas.add(node);
                        }
                   }else{
                        deleteDatas.add(node);
                    }

                }
            }

            mDatas.removeAll(deleteDatas);
        }else if(flag == 3){//移除群组成员
            int userid = BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0);
            dataList = (List<GroupListBean.UserSimple>) getIntent().getSerializableExtra("data");//获得已有的人
            groupId = getIntent().getIntExtra("groupId",0);//得到群组ID
            addGroupUserPresenter = new AddGroupUserPresenter(this,this);
            List<Node> addNode = new ArrayList<Node>();
            for(Node node:mDatas) {
                for (GroupListBean.UserSimple userSimple : dataList) {
                    Log.i("nodeid", node.getId() + "*********" + userSimple.getUserid());
                    int id = Integer.parseInt(node.getId());
                    if (!node.getpId().equals("1")) {
                        if (id == userSimple.getUserid() && id != userid) {
                            addNode.add(node);
                        }
                    }
                }
            }
                mDatas = addNode;
        }

        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        initSelData();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //第一个参数  RecyclerView
        //第二个参数  上下文
        //第三个参数  数据集
        //第四个参数  默认展开层级数 0为不展开
        //第五个参数  展开的图标
        //第六个参数  闭合的图标
        mAdapter = new SimpleTreeRecyclerAdapter(recyclerview, this,
                mDatas, 1, R.mipmap.icon_bottom_arr, R.mipmap.icon_right_arr, true);
        //recyclerview.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.height_10)));
        recyclerview.setAdapter(mAdapter);
        selNodes();
        selTv.setText("已选择"+selNodes.size()+"人");
    }
}
