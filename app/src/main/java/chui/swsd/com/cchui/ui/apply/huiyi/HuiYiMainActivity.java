package chui.swsd.com.cchui.ui.apply.huiyi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.ui.work.make_meet.MakeMeetActivity;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\30 0030.
 */

public class HuiYiMainActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.yykh_tv)
    TextView yykhTv;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"我收到的", "我发出的"};
    MyPagerAdapter adapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_huiyi;
    }

    @Override
    protected void initViews() {
        initTitle(true, "会议");
        mFagments.add(HuiYiListFragment.newInstance(1));
        mFagments.add(HuiYiListFragment.newInstance(0));
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setViewPager(viewPager, mTitles);
    }

    @Override
    protected void updateViews() {

    }

    @OnClick(R.id.yykh_tv)
    public void onClick() {
        startActivity(new Intent(this, MakeMeetActivity.class));
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
