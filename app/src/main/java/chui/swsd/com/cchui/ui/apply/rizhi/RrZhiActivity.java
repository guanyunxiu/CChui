package chui.swsd.com.cchui.ui.apply.rizhi;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.MediaMetadataCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.ui.apply.ApplyFragment;
import chui.swsd.com.cchui.ui.apply.rizhi.red_rizhi.RedRiZhiFragment;
import chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi.WriteRiZhiFragment;
import chui.swsd.com.cchui.ui.contacts.ContractsFragment;
import chui.swsd.com.cchui.ui.info.InfoFragment;
import chui.swsd.com.cchui.ui.mine.MineFragment;
import chui.swsd.com.cchui.ui.work.WorkFragment;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\18 0018.
 */

public class RrZhiActivity extends BaseActivity {
    @BindView(R.id.id_content)
    FrameLayout idContent;
    @BindView(R.id.xrz_lv)
    LinearLayout xrzLv;
    @BindView(R.id.krz_lv)
    LinearLayout krzLv;
    @BindView(R.id.xrz_img)
    ImageView xrzImg;
    @BindView(R.id.krz_img)
    ImageView krzImg;
    @BindView(R.id.xrz_tv)
    TextView xrzTv;
    @BindView(R.id.krz_tv)
    TextView krzTv;
    RedRiZhiFragment redRiZhiFragment;
    WriteRiZhiFragment writeRiZhiFragment;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_rizhi;
    }

    @Override
    protected void initViews() {
        setSelect(0);
    }

    @Override
    protected void updateViews() {

    }

    @OnClick({R.id.xrz_lv, R.id.krz_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xrz_lv:
                setSelect(0);
                break;
            case R.id.krz_lv:
                setSelect(1);
                break;
        }
    }
    private void setSelect(int i) {
        resetImgs();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i)
        {
            case 0:
                xrzImg.setImageResource(R.mipmap.rz_11);
                xrzTv.setTextColor(this.getResources().getColor(R.color.white));
                xrzLv.setBackgroundResource(R.color.blue);
                if (writeRiZhiFragment == null)
                {
                    writeRiZhiFragment = new WriteRiZhiFragment();
                    transaction.add(R.id.id_content, writeRiZhiFragment);
                } else
                {
                    transaction.show(writeRiZhiFragment);
                }

                break;
            case 1:
                krzImg.setImageResource(R.mipmap.rz_07);
                krzTv.setTextColor(this.getResources().getColor(R.color.white));
                krzLv.setBackgroundResource(R.color.blue);
                if (redRiZhiFragment == null)
                {
                    redRiZhiFragment = new RedRiZhiFragment();
                    transaction.add(R.id.id_content, redRiZhiFragment);
                } else
                {
                    transaction.show(redRiZhiFragment);

                }
                break;

            default:
                break;
        }

        transaction.commit();
    }
    private void hideFragment(FragmentTransaction transaction)
    {
        if (redRiZhiFragment != null)
        {
            transaction.hide(redRiZhiFragment);
        }
        if (writeRiZhiFragment != null)
        {
            transaction.hide(writeRiZhiFragment);
        }

    }
    /**
     * 切换图片至暗色
     */
    private void resetImgs()
    {
        xrzImg.setImageResource(R.mipmap.rz_06);
        xrzTv.setTextColor(this.getResources().getColor(R.color.blue));
        xrzLv.setBackgroundResource(R.color.white);

        krzImg.setImageResource(R.mipmap.rz_07_true);
        krzTv.setTextColor(this.getResources().getColor(R.color.blue));
        krzLv.setBackgroundResource(R.color.white);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
