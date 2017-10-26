package chui.swsd.com.cchui.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.inter.RongInterface;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.YuanDLActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity;
import chui.swsd.com.cchui.ui.forget.ForgetActivity;
import chui.swsd.com.cchui.ui.register.RegisterSelectActivity;
import chui.swsd.com.cchui.ui.register.RigsterActivity;
import chui.swsd.com.cchui.ui.register.RigsterFourActivity;
import chui.swsd.com.cchui.ui.register.RigsterThreeActivity;
import chui.swsd.com.cchui.utils.AMUtils;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.Installation;
import chui.swsd.com.cchui.utils.RongConnectUtil;
import chui.swsd.com.cchui.widget.ClearWriteEditText;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class LoginActivity extends BaseActivity implements LoginContract.view,RongInterface{
    @BindView(R.id.phone_et)
    ClearWriteEditText phoneEt;
    @BindView(R.id.pass_et)
    ClearWriteEditText passEt;
    @BindView(R.id.reg_tv)
    TextView regTv;
    @BindView(R.id.forget_tv)
    TextView forgetTv;
    @BindView(R.id.login_btn)
    Button loginBtn;
    LoginPresenter loginPresenter;
    MProgressDialog mMProgressDialog;
    UserBean userBean2;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }
    @Override
    protected void initViews() {
        loginPresenter = new LoginPresenter(this,this);
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    AMUtils.onInactive(LoginActivity.this, phoneEt);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    protected void updateViews() {

    }
    @OnClick({R.id.reg_tv, R.id.forget_tv, R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_tv:
                startActivity(new Intent(LoginActivity.this, RigsterActivity.class));
                break;
            case R.id.forget_tv:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.login_btn:
                String user = phoneEt.getText().toString();
                String pass = passEt.getText().toString();
                if(TextUtils.isEmpty(user)){
                    phoneEt.setShakeAnimation();
                    CommonUtil.showToast(this,"手机号不能为空");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    passEt.setShakeAnimation();
                    CommonUtil.showToast(this,"密码不为空");
                    return;
                }
                mMProgressDialog = CommonUtil.configDialogDefault(LoginActivity.this);
                mMProgressDialog.show("登录中...");
                loginPresenter.onLogin(user,pass);
                break;
        }
    }

    @Override
    public void onSuccess(UserBean userBean) {
        userBean2 = userBean;
        BaseApplication.mSharedPrefUtil.putBoolean(SharedConstants.LOGINFLAG,true);
        BaseApplication.mSharedPrefUtil.commit();
        String cllientid = BaseApplication.mSharedPrefUtil.getString(SharedConstants.CLIENTID,"");
        //标签
        JPushInterface.setAlias(LoginActivity.this, cllientid, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("tuisong","123***"+s);
                JPushInterface.resumePush(LoginActivity.this);
            }
        });
        Log.i("token",userBean.getToken());
        Constants.judgeFlag = 1;
        if (userBean.getAudit() == 0) {
            Constants.judgeFen = false;
        }else{
            Constants.judgeFen = true;
        }
        RongConnectUtil rongConnectUtil = new RongConnectUtil(this);
        rongConnectUtil.connect(userBean.getToken());

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();

    }
    //表示融云连接成功
    @Override
    public void onConnSuccess() {
        mMProgressDialog.dismiss();
        if(userBean2.getCompanyid() == 0){//没有添加公司信息
            Intent intent = new Intent(this, RegisterSelectActivity.class);
            intent.putExtra("userid", userBean2.getId()+"");
            startActivity(intent);
        }else if(userBean2.getDmft() == 0 && userBean2.getAccount() == 1){//没有添加部门
            Intent intent = new Intent(this, RigsterFourActivity.class);
            intent.putExtra("cid", userBean2.getCompanyid());
            startActivity(intent);
        }else {
            if (userBean2.getAudit() == 0) {//判断是否填写了基础分数
                startActivity(new Intent(LoginActivity.this, UpdateJcFenActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }
        finish();
    }

    @Override
    public void onConnFail() {

    }
}
