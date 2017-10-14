package chui.swsd.com.cchui.ui.apply.shenpi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_baoxiao.SpBaoxiaoActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_chuchai.SpChuChaiActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpContentActivity;

import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpFqContentActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_jiaban.SpJiabanActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_qingjia.SpLeaveActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_waichu.SpWaiChuActivity;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：审批
 * Created by 关云秀 on 2017\8\23 0023.
 */

public class ShenPiActivity extends BaseSwipeBackActivity {
    @BindView(R.id.fq_tv)
    TextView fqTv;
    @BindView(R.id.sp_tv)
    TextView spTv;
    @BindView(R.id.qj_tv)
    TextView qjTv;
    @BindView(R.id.cc_tv)
    TextView ccTv;
    @BindView(R.id.wc_tv)
    TextView wcTv;
    @BindView(R.id.jb_tv)
    TextView jbTv;
    @BindView(R.id.bx_tv)
    TextView bxTv;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_shenpi;
    }

    @Override
    protected void initViews() {
       initTitle(true,"审批");
    }

    @Override
    protected void updateViews() {

    }

    @OnClick({R.id.fq_tv, R.id.sp_tv, R.id.qj_tv, R.id.cc_tv, R.id.wc_tv, R.id.jb_tv, R.id.bx_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fq_tv:
                startActivity(new Intent(ShenPiActivity.this, SpFqContentActivity.class));
                break;
            case R.id.sp_tv:
                startActivity(new Intent(ShenPiActivity.this, SpContentActivity.class));
                break;
            case R.id.qj_tv:
                startActivity(new Intent(ShenPiActivity.this, SpLeaveActivity.class));
                break;
            case R.id.cc_tv:
                startActivity(new Intent(ShenPiActivity.this, SpChuChaiActivity.class));
                break;
            case R.id.wc_tv:
                startActivity(new Intent(ShenPiActivity.this, SpWaiChuActivity.class));
                break;
            case R.id.jb_tv:
                startActivity(new Intent(ShenPiActivity.this, SpJiabanActivity.class));
                break;
            case R.id.bx_tv:
                startActivity(new Intent(ShenPiActivity.this, SpBaoxiaoActivity.class));
                break;
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
