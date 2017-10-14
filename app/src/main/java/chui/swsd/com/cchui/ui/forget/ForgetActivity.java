package chui.swsd.com.cchui.ui.forget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.maning.mndialoglibrary.MProgressDialog;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.ui.login.LoginActivity;
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

public class ForgetActivity extends BaseSwipeBackActivity implements ForgetContract.view{
    @BindView(R.id.phone_et)
    ClearWriteEditText phoneEt;
    @BindView(R.id.pass_et)
    ClearWriteEditText passEt;
    @BindView(R.id.qrpass_et)
    ClearWriteEditText qrpassEt;
    @BindView(R.id.sub_btn)
    Button subBtn;
    ForgetPresenter forgetPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initViews() {
        forgetPresenter = new ForgetPresenter(this,this);
    }

    @Override
    protected void updateViews() {

    }


    @OnClick(R.id.sub_btn)
    public void onClick() {
        String user = phoneEt.getText().toString();
        String pass = passEt.getText().toString();
        String qrpass = qrpassEt.getText().toString();
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
        if(TextUtils.isEmpty(qrpass)){
            qrpassEt.setShakeAnimation();
            CommonUtil.showToast(this,"请再次输入密码");
            return;
        }
        if(!pass.equals(qrpass)){
            CommonUtil.showToast(this,"两次输入的密码不一致，请重新输入");
            return;
        }
        mMProgressDialog = CommonUtil.configDialogDefault(ForgetActivity.this);
        mMProgressDialog.show("修改中...");
        forgetPresenter.onSubmit(user,pass);
    }
    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"修改成功");
        startActivity(new Intent(ForgetActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
}
