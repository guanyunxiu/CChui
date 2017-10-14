package chui.swsd.com.cchui.ui.apply.file;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.ui.apply.file.release_file.ReleaseFileActivity;
import chui.swsd.com.cchui.ui.apply.huiyi.HuiYiListFragment;
import chui.swsd.com.cchui.ui.apply.huiyi.HuiYiMainActivity;
import chui.swsd.com.cchui.ui.work.make_meet.MakeMeetActivity;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\30 0030.
 */

public class FileManagerActivity extends BaseActivity {
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
        initTitle(true, "文件管理");
        yykhTv.setText("发布文件");
        mFagments.add(FileManagerFragment.newInstance(1));
        mFagments.add(FileManagerFragment.newInstance(0));
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setViewPager(viewPager, mTitles);
    }

    @Override
    protected void updateViews() {

    }

    @OnClick(R.id.yykh_tv)
    public void onClick() {
       startActivity(new Intent(this, ReleaseFileActivity.class));
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
