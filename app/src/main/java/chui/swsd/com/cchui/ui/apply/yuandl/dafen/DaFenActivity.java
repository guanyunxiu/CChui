package chui.swsd.com.cchui.ui.apply.yuandl.dafen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.work.WorkFragment;
import chui.swsd.com.cchui.ui.work.file.FileFragment;
import chui.swsd.com.cchui.ui.work.meet.MeetFragment;
import chui.swsd.com.cchui.ui.work.notice.NoticeFragment;
import chui.swsd.com.cchui.ui.work.shenpi.ShenpiFragment;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\17 0017.
 */

public class DaFenActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"工作", "生活"};
    MyPagerAdapter adapter;
    private String name,id;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_dafen;
    }
    @Override
    protected void initViews() {
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        initTitle(false, "打分("+name+")");
        mFagments.add(DaFenConFragment.newInstance(UrlAddress.URL+"integral/view.action?name=work&userid="+id));
        mFagments.add(DaFenConFragment.newInstance(UrlAddress.URL+"integral/view.action?name=life&userid="+id));
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
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
