package chui.swsd.com.cchui.ui.apply.yuandl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.ui.apply.yuandl.dafen_new.DaFenMainActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.leader_fen.LeaderChaFen;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：源动力
 * Created by 关云秀 on 2017\8\16 0016.
 */

public class YuanDLActivity extends BaseSwipeBackActivity {
    @BindView(R.id.dafen_btn)
    Button dafenBtn;
    @BindView(R.id.ckfs_btn)
    Button ckfsBtn;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_ydl;
    }

    @Override
    protected void initViews() {
        initTitle(true, "源动力");
    }

    @Override
    protected void updateViews() {
        if(BaseApplication.mSharedPrefUtil.getInt(SharedConstants.DAFENFLAG,0) == 0){
            dafenBtn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.dafen_btn, R.id.ckfs_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dafen_btn:
                int day = Integer.parseInt(DateUtil.getDayTime());
                Log.i("daytime",day+"***");
               // startActivity(new Intent(this, DaFenMainActivity.class));
                if(day>=1 && day<=15) {
                    startActivity(new Intent(this, DaFenMainActivity.class));
                }else{
                    CommonUtil.showToast(this,"不在打分时间段内");
                }
                //我不打分
                break;
            case R.id.ckfs_btn:
                int flag = BaseApplication.mSharedPrefUtil.getInt(SharedConstants.DAFENFLAG,0);
                if(flag == 1){//领导
                    startActivity(new Intent(this,LeaderChaFen.class));
                }else {//员工
                    startActivity(new Intent(this, MyYuanDLScoreActivity.class));
                }
                break;
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
