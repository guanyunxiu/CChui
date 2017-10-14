package chui.swsd.com.cchui.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.ui.apply.yuandl.YuanDLActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity2;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.ui.login.LoginContract;
import chui.swsd.com.cchui.ui.login.LoginPresenter;
import chui.swsd.com.cchui.ui.register.RegisterSelectActivity;
import chui.swsd.com.cchui.ui.register.RigsterFourActivity;
import chui.swsd.com.cchui.ui.register.RigsterThreeActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.PermissionsChecker;
import chui.swsd.com.cchui.utils.RongConnectUtil;

public class WelcomeActivity extends AppCompatActivity implements LoginContract.view{
    LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loginPresenter = new LoginPresenter(this,this);
        startMainOrGuide();
    }



    //首次启动开启导航页
    private void startMainOrGuide() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                boolean flag = BaseApplication.mSharedPrefUtil.getBoolean(SharedConstants.LOGINFLAG,false);
                if(flag){
                    loginPresenter.onLogin(BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHONE,""),BaseApplication.mSharedPrefUtil.getString(SharedConstants.PASSWORD,""));
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
                }
            }
        }, 2000);
    }

    @Override
    public void onSuccess(UserBean userBean) {

        RongConnectUtil.connect(BaseApplication.mSharedPrefUtil.getString(SharedConstants.TOKEN,""));//连接融云服务器

        overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        Constants.judgeFlag = 1;
        if (userBean.getAudit() == 0) {//判断是否填写了基础分数
            Constants.judgeFen = false;
        }else{
            Constants.judgeFen = true;
        }
        if(userBean.getCompanyid() == 0){//没有添加公司信息
                Intent intent = new Intent(this, RegisterSelectActivity.class);
                intent.putExtra("userid", userBean.getId()+"");
                startActivity(intent);
        }else if(userBean.getDmft() == 0 && userBean.getAccount() == 1){//没有添加部门
                Intent intent = new Intent(this, RigsterFourActivity.class);
                intent.putExtra("cid", userBean.getCompanyid());
                startActivity(intent);
        }else {
            if (userBean.getAudit() == 0) {//判断是否填写了基础分数
                startActivity(new Intent(WelcomeActivity.this, UpdateJcFenActivity.class));
            } else {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        }
         finish();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {
        CommonUtil.showToast(this,"登录失败，请重新登录");
        finish();
        overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        RongConnectUtil.connect(BaseApplication.mSharedPrefUtil.getString(SharedConstants.TOKEN,""));//连接融云服务器
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
