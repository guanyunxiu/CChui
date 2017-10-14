package chui.swsd.com.cchui.ui.contacts;

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
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.contacts.ost.OstFragment;

/**
 * 内容：联系人
 * Created by 关云秀 on 2017\8\7 0007.
 */

public class ContractsFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"组织架构", "群组"};
    MyPagerAdapter adapter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_contracts;
    }

    @Override
    protected void initViews() {
        initTitle(false, "联系人");
        mFagments.add(OstFragment.newInstance());
        mFagments.add(GroupFragment.newInstance());
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
