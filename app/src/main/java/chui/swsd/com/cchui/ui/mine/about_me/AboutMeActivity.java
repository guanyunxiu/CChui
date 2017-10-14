package chui.swsd.com.cchui.ui.mine.about_me;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\23 0023.
 */

public class AboutMeActivity extends BaseActivity {
    @BindView(R.id.version_tv)
    TextView versionTv;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initViews() {
       initTitle(true,"关于我们");
    }

    @Override
    protected void updateViews() {
        versionTv.setText("版本号：V"+getVersion());
    }
    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
    public String getVersion() {
        String version = null;
        try {
                 PackageManager manager = this.getPackageManager();
                 PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                  version = info.versionName;
                 return version;
          } catch (Exception e) {
                    e.printStackTrace();
                  
          }
        return version;
    }
}
