package chui.swsd.com.cchui.ui.apply.yuandl;

import android.content.Intent;
import android.os.Bundle;
import android.os.WorkSource;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpContentActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpFqContentActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpShaiShuaiActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：我的源动力分数
 * Created by 关云秀 on 2017\8\16 0016.
 */

public class MyYuanDLScoreActivity extends BaseSwipeBackActivity implements MyYuanDLContracts.view{
    @BindView(R.id.zf_score_tv)
    TextView zfScoreTv;
    @BindView(R.id.jcf_tv)
    TextView jcfTv;
    @BindView(R.id.gzf_tv)
    TextView gzfTv;
    @BindView(R.id.shf_tv)
    TextView shfTv;
    @BindView(R.id.user_tv)
    TextView userTv;
    @BindView(R.id.zf_score_img)
    ImageView zfScoreImg;
    @BindView(R.id.tsjcf_btn)
    Button tsjcfBtn;
    MyYuanDLPresenter myYuanDLPresenter;
    private TimePickerView  pvCustomTime;
    MenuItem menuItem;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_ydl;
    }

    @Override
    protected void initViews() {
        initTitle(true, "我的积分");
        myYuanDLPresenter = new MyYuanDLPresenter(this,this);
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation1.setDuration(3000);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        zfScoreImg.setAnimation(alphaAnimation1);
        alphaAnimation1.start();
    }

    @Override
    protected void updateViews() {
        initCustomTimePicker();
        myYuanDLPresenter.onSelectScore(DateUtil.beforeTime());
    }

    @OnClick(R.id.tsjcf_btn)
    public void onClick() {
        Intent intent = new Intent(this,UpdateJcFenActivity.class);
        intent.putExtra("flag",1);
        startActivity(intent);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(YdlScoreBean ydlScoreBean) {
        if(ydlScoreBean != null) {
            int allScore = ydlScoreBean.getBasis() + ydlScoreBean.getScoail() + ydlScoreBean.getCompany() + ydlScoreBean.getUser();
            zfScoreTv.setText(allScore + "");
            jcfTv.setText("基础分：" + ydlScoreBean.getBasis());
            gzfTv.setText("公司价值：" + ydlScoreBean.getCompany());
            shfTv.setText("社会价值：" + ydlScoreBean.getScoail());
            userTv.setText("员工分：" + ydlScoreBean.getUser());
        }else{
            zfScoreTv.setText(0 + "");
            jcfTv.setText("基础分：" + 0);
            gzfTv.setText("公司价值：" + 0);
            shfTv.setText("社会价值：" + 0);
            userTv.setText("员工分：" + 0);
        }
    }

    @Override
    public void onFail() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_time, menu);
        menuItem = menu.findItem(R.id.item_time);
        menuItem.setTitle(DateUtil.beforeTime());
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_time:
                pvCustomTime.show();
                break;
         }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 初始化时间
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR)-2, selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                menuItem.setTitle(DateUtil.getTime4(date));
                myYuanDLPresenter.onSelectScore(DateUtil.getTime4(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
}
