package chui.swsd.com.cchui.ui.apply.shenpi.sp_content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.ui.contacts.ContractsFragment;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.contacts.ost.OstFragment;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\24 0024.
 */

public class SpContentActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"待我审批的", "我已审批的"};
    MyPagerAdapter adapter;
    private String ztstr,typestr;
    public static int typePos;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_content;
    }

    @Override
    protected void initViews() {
        initTitle(true,"我审批的");
        mFagments.add(SpContentFragment.newInstance(0));
        mFagments.add(SpContentFragment.newInstance(1));
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
            typePos = position;
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("position",position+"****");
            return mFagments.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sx, menu);
        final MenuItem item = menu.findItem(R.id.action_edit);
        TextView textView = (TextView) item.getActionView().findViewById(R.id.sx_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpContentActivity.this,SpShaiShuaiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ztstr",ztstr);
                bundle.putString("typestr",typestr);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
                overridePendingTransition(R.anim.fade_entry, R.anim.hold);
            }
        });
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                ztstr = data.getStringExtra("ztstr");
                typestr = data.getStringExtra("typestr");
                int ztInt = CommonUtil.getFt(ztstr);
                int typeInt = CommonUtil.getScreen(typestr);
                //spContentFragment.onSelect(typeInt,ztInt);
            }
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }

}
