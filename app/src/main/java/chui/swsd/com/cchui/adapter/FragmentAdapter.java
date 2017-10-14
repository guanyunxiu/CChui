package chui.swsd.com.cchui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chui.swsd.com.cchui.base.BaseFragment;

/**
 * Created by Administrator on 2016/4/14.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fm;
    public boolean update = true;
    public FragmentAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.i("TAG", "destroyItem___________");
        super.destroyItem(container, position, object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < getCount(); i++) {//通过遍历清除所有缓存
            final long itemId = getItemId(i);
            //得到缓存fragment的名字
            String name = makeFragmentName(container.getId(), itemId);
            //通过fragment名字找到该对象
            BaseFragment fragment = (BaseFragment) fm.findFragmentByTag(name);
            if (fragment != null) {
                //移除之前的fragment
                ft.remove(fragment);
            }
        }
        //重新添加新的fragment:最后记得commit
        ft.add(container.getId(), getItem(position)).attach(getItem(position)).commit();
        return getItem(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 得到缓存fragment的名字
     * @param viewId
     * @param id
     * @return
     */
    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
