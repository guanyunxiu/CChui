package chui.swsd.com.cchui.ui.work;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.ui.contacts.ContractsFragment;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.contacts.ost.OstFragment;
import chui.swsd.com.cchui.ui.work.file.FileFragment;
import chui.swsd.com.cchui.ui.work.meet.MeetFragment;
import chui.swsd.com.cchui.ui.work.notice.NoticeFragment;
import chui.swsd.com.cchui.ui.work.shenpi.ShenpiFragment;

/**
 * 内容：工作
 * Created by 关云秀 on 2017\8\7 0007.
 */

public class WorkFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"审批", "公告","会议预约","文件管理"};
    MyPagerAdapter adapter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initViews() {
        String comName =  BaseApplication.mSharedPrefUtil.getString(SharedConstants.COMNAME,"");
        //initTitle(false, comName);
        titleName.setText(comName);
        mFagments.add(ShenpiFragment.newInstance());
        mFagments.add(NoticeFragment.newInstance());
        mFagments.add(MeetFragment.newInstance());
        mFagments.add(FileFragment.newInstance());
        adapter = new MyPagerAdapter(getChildFragmentManager());
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
