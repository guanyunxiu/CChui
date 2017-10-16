package chui.swsd.com.cchui.ui.apply.yuandl.dafen_new;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.FragmentAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.model.DaFenBean;
import chui.swsd.com.cchui.model.DaFenConBean;
import chui.swsd.com.cchui.model.YdlTitleBean;
import chui.swsd.com.cchui.model.YdlValueBean;
import chui.swsd.com.cchui.popupwindow.ScreenPopup;
import chui.swsd.com.cchui.popupwindow.YdlPopup;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\30 0030.
 */

public class DaFenFragment extends BaseFragment implements DaFenContract.view{
    @BindView(R.id.screen_tv)
    TextView screenTv;
    @BindView(R.id.screen_lv)
    LinearLayout screenLv;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.current_tv)
    TextView currentTv;
    @BindView(R.id.total_tv)
    TextView totalTv;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    DaFenPresenter daFenPresenter;
    List<YdlTitleBean> listTitle;
    private int flagTitle;
    MProgressDialog mMProgressDialog;
    public static DaFenFragment newInstance(int flag) {
        DaFenFragment fragment = new DaFenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_dafen;
    }

    @Override
    protected void initViews() {
        flagTitle = getArguments().getInt("flag");
        daFenPresenter = new DaFenPresenter(this,mContext);
        daFenPresenter.onSelectTitle(flagTitle);
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        mMProgressDialog = CommonUtil.configDialogDefault(mContext);
        mMProgressDialog.show("查询中...");
    }

    @Override
    protected void updateViews() {

    }
    @OnClick(R.id.screen_tv)
    public void onClick() {
        YdlPopup ydlPopup = new YdlPopup(mContext,listTitle,flagTitle);
        ydlPopup.showAsDropDown(screenLv);
    }
    public void initSetAdapter(){
        fragmentAdapter = new FragmentAdapter(
                getChildFragmentManager(), mFagments);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //   setSelection(position);
                currentTv.setText((position+1)+"/");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
    }

    /**
     * 默认标题第一个
     */
    public void defaultTitle(){
        mFagments.clear();
        screenTv.setText(listTitle.get(0).getName());

        if(listTitle.get(0).getTitle2s().size()>0){
            totalTv.setText(listTitle.get(0).getTitle2s().size()+"");
            for(int i=0;i<listTitle.get(0).getTitle2s().size();i++){
                YdlTitleBean.Title2sBean title2sBean =  listTitle.get(0).getTitle2s().get(i);
                mFagments.add(DaFenContentFragment.newInstance(flagTitle,title2sBean.getBid(),title2sBean.getName(),title2sBean.getName(),title2sBean.getFt()));
            }
        }else{
            totalTv.setText(1+ "");
            mFagments.add(DaFenContentFragment.newInstance(flagTitle,listTitle.get(0).getBid(),"","",listTitle.get(0).getFt()));
        }
    }
    @Subscribe          //订阅事件FirstEvent
    public void onEventMainThread(YdlValueBean ydlValueBean ){
        if(ydlValueBean.getFlagTitle() == flagTitle) {
            YdlTitleBean ydlTitleBean = ydlValueBean.getYdlTitleBean();
            screenTv.setText(ydlTitleBean.getName());
            mFagments.clear();
            if (ydlTitleBean.getTitle2s().size() > 0) {
                totalTv.setText(ydlTitleBean.getTitle2s().size() + "");
                for (int i = 0; i < ydlTitleBean.getTitle2s().size(); i++) {
                    YdlTitleBean.Title2sBean title2sBean = ydlTitleBean.getTitle2s().get(i);
                    mFagments.add(DaFenContentFragment.newInstance(flagTitle, title2sBean.getBid(), title2sBean.getName(),title2sBean.getName(),title2sBean.getFt()));
                }
            } else {
                totalTv.setText(1+ "");
                mFagments.add(DaFenContentFragment.newInstance(flagTitle, ydlTitleBean.getBid(),"","",ydlTitleBean.getFt()));
            }
            fragmentAdapter.notifyDataSetChanged();
           // initSetAdapter();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
    @Override
    public void onSuccess(List<YdlTitleBean> list) {
        mMProgressDialog.dismiss();
        listTitle = list;
        defaultTitle();
        initSetAdapter();
    }

    @Override
    public void onSuccess(DaFenConBean daFenConBean) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }
}
