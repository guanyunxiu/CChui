package chui.swsd.com.cchui.ui.apply.yuandl.leader_fen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.ui.contacts.ContractsFragment;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\18 0018.
 */

public class LeaderChaFen extends BaseActivity implements LeaderFenContract.view{
    LeaderFenPresenter leaderFenPresenter;
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles;
    MyPagerAdapter adapter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_contracts;
    }

    @Override
    protected void initViews() {
       initTitle(true,"员工积分");
        leaderFenPresenter = new LeaderFenPresenter(this,this);
        leaderFenPresenter.onSelectDep();

    }

    @Override
    protected void updateViews() {

    }

    @Override
    public void onSuccess(List<DepartmentBean> list) {
        String[] mTitles = new String[list.size()];
        for(int i=0;i<list.size();i++){
            mTitles[i] = list.get(i).getName();
            mFagments.add(LeaderFenFragment.newInstance(list.get(i).getId()+""));
        }
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setViewPager(viewPager, mTitles);
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccessCon(List<YdlScoreBean> depconList) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFagments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFagments.get(position);
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
