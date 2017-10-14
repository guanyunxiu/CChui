package chui.swsd.com.cchui.ui.apply.rizhi.red_rizhi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.FragmentAdapter;
import chui.swsd.com.cchui.base.BaseFragment;


/**
 * 内容：
 * Created by 关云秀 on 2017\8\18 0018.
 */

public class RedRiZhiFragment extends BaseFragment {

    @BindView(R.id.rece_tv)
    TextView receTv;
    @BindView(R.id.rece_lv)
    LinearLayout receLv;
    @BindView(R.id.sends_tv)
    TextView sendTv;
    @BindView(R.id.send_lv)
    LinearLayout sendLv;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private int questionIndex;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_red_rizhi;
    }

    @Override
    protected void initViews() {
        initTitle(true, "日志");
        //setHasOptionsMenu(true);
        initFragment();
        viewpager.setAdapter(fragmentAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewpager.setOffscreenPageLimit(2);
        viewpager.setCurrentItem(0);
        setSelection(0);
        receLv.setOnClickListener(new MyOnClickListener(0));
        sendLv.setOnClickListener(new MyOnClickListener(1));
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_rb, menu);
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.all_item) {
            titRiTv.setText("全部");
            return true;
        }else if(id == R.id.rb_item){
            titRiTv.setText("日报");
            return true;
        }else if(id == R.id.zb_item){
            titRiTv.setText("周报");
            return true;
        }else if(id == R.id.yb_item){
            titRiTv.setText("月报");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    protected void updateViews() {

    }

    public void initFragment() {
        mFragmentList.add(RedReceiverRzFragment.newInstance("0"));
        mFragmentList.add(RedSendRzFragment.newInstance());
        fragmentAdapter = new FragmentAdapter(
                getChildFragmentManager(), mFragmentList);
    }


    private class MyOnClickListener implements View.OnClickListener {
        private int index;
        public MyOnClickListener(int i) {
            this.index = i;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rece_lv:
                    viewpager.setCurrentItem(index);
                    break;
                case R.id.send_lv:
                    viewpager.setCurrentItem(index);
                    break;
            }
        }
    }

    private int currentIndex;

    private void setSelection(int index) {
        questionIndex = index;
        switch (index) {
            case 0:
                receTv.setTextColor(getActivity().getResources().getColor(R.color.blue));
                sendTv.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                break;
            case 1:
                receTv.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                sendTv.setTextColor(getActivity().getResources().getColor(R.color.blue));
                break;
        }
        currentIndex = index;
    }

}
