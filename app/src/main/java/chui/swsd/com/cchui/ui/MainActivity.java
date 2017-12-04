package chui.swsd.com.cchui.ui;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.multilevel.treelist.Node;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.SealAppContext;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.apply.ApplyFragment;
import chui.swsd.com.cchui.ui.contacts.ContractsFragment;
import chui.swsd.com.cchui.ui.contacts.ost.OstContract;
import chui.swsd.com.cchui.ui.contacts.ost.OstPresenter;
import chui.swsd.com.cchui.ui.info.InfoFragment;
import chui.swsd.com.cchui.ui.mine.MineFragment;
import chui.swsd.com.cchui.ui.work.WorkFragment;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.DragPointView;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017\8\7 0007.
 */

public class MainActivity extends BaseActivity implements OstContract.view, DragPointView.OnDragListencer,
        IUnReadMessageObserver
{
    @BindView(R.id.id_content)
    FrameLayout idContent;
    @BindView(R.id.tab_info)
    TextView tabInfo;
    @BindView(R.id.tab_contacts)
    TextView tabContacts;
    @BindView(R.id.tab_work)
    TextView tabWork;
    @BindView(R.id.tab_apply)
    TextView tabApply;
    @BindView(R.id.tab_mine)
    TextView tabMine;
    @BindView(R.id.coordinatorLayout)
    LinearLayout coordinatorLayout;
    @BindView(R.id.message_num)
    DragPointView mUnreadNumView;
    private InfoFragment infoFragment;
    private ContractsFragment contractsFragment;
    private WorkFragment workFragment;
    private ApplyFragment applyFragment;
    private MineFragment mineFragment;
    private long mExitTime = 0;
    OstPresenter ostPresenter;
    private Conversation.ConversationType[] mConversationsTypes = null;
    public static boolean isActivity = false;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        isActivity = false;
        ostPresenter = new OstPresenter(this,this);
        //ostPresenter.onSelect();
        //SealAppContext.init(this);
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        setSelect(0);
        getPermission();
        CretinAutoUpdateUtils.getInstance(MainActivity.this).check();

    }

    @Override
    protected void updateViews() {
        initSetNum();
    }
    public void initSetNum(){
        mUnreadNumView.setDragListencer(this);
        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };

        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
        mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP,
                Conversation.ConversationType.PUBLIC_SERVICE,
                Conversation.ConversationType.APP_PUBLIC_SERVICE,
                Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.DISCUSSION
        };
    }
    @OnClick({R.id.tab_info_lv, R.id.tab_contacts_lv, R.id.tab_work_lv, R.id.tab_apply_lv, R.id.tab_mine_lv})
    void bottomClick(View view) {
        resetImgs();
        switch (view.getId()) {
            case R.id.tab_info_lv:
                setSelect(0);
                break;
            case R.id.tab_contacts_lv:
                setSelect(1);
                break;
            case R.id.tab_work_lv:
                setSelect(2);
                break;
            case R.id.tab_apply_lv:
                setSelect(3);
                break;
            case R.id.tab_mine_lv:
                setSelect(4);
                break;
        }
    }
    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i)
        {
            case 0:
                if (infoFragment == null)
                {
                    infoFragment = new InfoFragment();
                    transaction.add(R.id.id_content, infoFragment);
                } else
                {
                    transaction.show(infoFragment);
                }
                tabInfo.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (contractsFragment == null)
                {
                    contractsFragment = new ContractsFragment();
                    transaction.add(R.id.id_content, contractsFragment);
                } else
                {
                    transaction.show(contractsFragment);

                }
                tabContacts.setVisibility(View.VISIBLE);
                break;
            case 2:
                if (workFragment == null)
                {
                    workFragment = new WorkFragment();
                    transaction.add(R.id.id_content, workFragment);
                } else
                {
                    transaction.show(workFragment);
                }
                tabWork.setVisibility(View.VISIBLE);
                break;
            case 3:
                if (applyFragment == null)
                {
                    applyFragment = new ApplyFragment();
                    transaction.add(R.id.id_content, applyFragment);
                } else
                {
                    transaction.show(applyFragment);
                }
                tabApply.setVisibility(View.VISIBLE);
                break;
            case 4:
                if (mineFragment == null)
                {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.id_content, mineFragment);
                } else
                {
                    transaction.show(mineFragment);
                }
                tabMine.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        transaction.commit();
    }
    private void hideFragment(FragmentTransaction transaction)
    {
        if (infoFragment != null)
        {
            transaction.hide(infoFragment);
        }
        if (contractsFragment != null)
        {
            transaction.hide(contractsFragment);
        }
        if (workFragment != null)
        {
            transaction.hide(workFragment);
        }
        if (applyFragment != null)
        {
            transaction.hide(applyFragment);
        }
        if (mineFragment != null)
        {
            transaction.hide(mineFragment);
        }
    }
    /**
     * 切换图片至暗色
     */
    private void resetImgs()
    {   tabInfo.setVisibility(View.GONE);
        tabContacts.setVisibility(View.GONE);
        tabWork.setVisibility(View.GONE);
        tabApply.setVisibility(View.GONE);
        tabMine.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivity = true;
    }

    //判断权限
    private void getPermission() {
        new RxPermissions(this).request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {

                        } else {

                        }
                    }
                });
    }

    @Override
    public void onSuccess(List<DepConBean> list) {
        getList(list);
    }

    @Override
    public void onFail() {

    }
    public void getList(List<DepConBean> list){
        for(int m=2;m<list.size()+2;m++){
            DepConBean depConBean = list.get(m-2);
            for(DepConBean.UserBasisBean userBasisBean:depConBean.getUserBasis()){
                UserInfo userInfo = new UserInfo(userBasisBean.getRongid(),userBasisBean.getUsername(), Uri.parse(UrlAddress.URLAddress+userBasisBean.getHeadimg()));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
            }
        }
    }
    @Override
    public void onCountChanged(int count) {
        if (count == 0) {
            mUnreadNumView.setVisibility(View.GONE);
        } else if (count > 0 && count < 100) {
            mUnreadNumView.setVisibility(View.VISIBLE);
            mUnreadNumView.setText(String.valueOf(count));
        } else {
            mUnreadNumView.setVisibility(View.VISIBLE);
            mUnreadNumView.setText(R.string.no_read_message);
        }
    }

    @Override
    public void onDragOut() {
        mUnreadNumView.setVisibility(View.GONE);
        CommonUtil.showToast(this,"清除成功");
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations != null && conversations.size() > 0) {
                    for (Conversation c : conversations) {
                        RongIM.getInstance().clearMessagesUnreadStatus(c.getConversationType(), c.getTargetId(), null);
                    }
                }
            }
            @Override
            public void onError(RongIMClient.ErrorCode e) {
            }
        }, mConversationsTypes);
    }


    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(String flag){
        if(flag.equals("群组查询")){
            ostPresenter.onSelect();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}
