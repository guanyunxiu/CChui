package chui.swsd.com.cchui.ui.mine.set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.ui.forget.ForgetActivity;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.utils.AMUtils;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.ClearWriteEditText;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\29 0029.
 */

public class UpdatePassActivity extends BaseSwipeBackActivity implements  UpdatePassContract.view{
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.old_pass_et)
    ClearWriteEditText oldPassEt;
    @BindView(R.id.newpass_et)
    ClearWriteEditText newpassEt;
    @BindView(R.id.qrpass_et)
    ClearWriteEditText qrpassEt;
    @BindView(R.id.sub_btn)
    Button subBtn;
    UpdatePassPresenter updatePassPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_updatepass;
    }

    @Override
    protected void initViews() {
        updatePassPresenter = new UpdatePassPresenter(this,this);

    }

    @Override
    protected void updateViews() {

    }
    @OnClick(R.id.sub_btn)
    public void onClick() {
        String oldpass = oldPassEt.getText().toString();
        String pass = newpassEt.getText().toString();
        String qrpass = qrpassEt.getText().toString();

        if(TextUtils.isEmpty(oldpass)){
            oldPassEt.setShakeAnimation();
            CommonUtil.showToast(this,"旧密码不为空");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            newpassEt.setShakeAnimation();
            CommonUtil.showToast(this,"请输入新密码");
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
        updatePassPresenter.onSubmit(oldpass,pass);
        mMProgressDialog = CommonUtil.configDialogDefault(UpdatePassActivity.this);
        mMProgressDialog.showNoText();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(UpdatePassActivity.this,"修改成功");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
}
