package chui.swsd.com.cchui.ui.apply.yuandl.dafen_new;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
/**
 * 内容：
 * Created by 关云秀 on 2017\9\30 0030.
 */

public class DaFenMainActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"公司价值", "社会价值","员工价值"};
    MyPagerAdapter adapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_dafen_main;
    }

    @Override
    protected void initViews() {
        initTitle(true, "源动力");

        mFagments.add(DaFenFragment.newInstance(1));
        mFagments.add(DaFenFragment.newInstance(0));
        mFagments.add(DaFenFragment.newInstance(2));
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setViewPager(viewPager, mTitles);
    }

    @Override
    protected void updateViews() {

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
}
