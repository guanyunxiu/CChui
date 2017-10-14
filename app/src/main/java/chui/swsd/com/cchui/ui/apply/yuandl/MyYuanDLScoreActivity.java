package chui.swsd.com.cchui.ui.apply.yuandl;

import android.content.Intent;
import android.os.Bundle;
import android.os.WorkSource;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity;

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
        myYuanDLPresenter.onSelectScore();
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
        }
    }

    @Override
    public void onFail() {

    }
}
