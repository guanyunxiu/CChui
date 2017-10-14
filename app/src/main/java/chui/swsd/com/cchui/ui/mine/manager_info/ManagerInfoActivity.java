package chui.swsd.com.cchui.ui.mine.manager_info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.ui.register.RigsterThreeActivity;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\19 0019.
 */

public class ManagerInfoActivity extends BaseActivity {
    @BindView(R.id.update_tv)
    TextView updateTv;
    @BindView(R.id.manager_tv)
    TextView managerTv;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_managerinfo;
    }

    @Override
    protected void initViews() {
      String comName =   BaseApplication.mSharedPrefUtil.getString(SharedConstants.COMNAME,"");
      initTitle(true,comName);
    }

    @Override
    protected void updateViews() {

    }

    @OnClick({R.id.update_tv, R.id.manager_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_tv:
                Intent intent =  new Intent(this, ManagerComActivity.class);
                startActivity(intent);
                break;
            case R.id.manager_tv:
                Intent intent2 =  new Intent(this, ManagerDepActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
