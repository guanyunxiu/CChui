package chui.swsd.com.cchui.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.maning.mndialoglibrary.MProgressDialog;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.ui.mine.set.SetActivity;
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

public class RigsterActivity extends BaseActivity implements RegisterContract.View{
    @BindView(R.id.phone_et)
    ClearWriteEditText phoneEt;
    @BindView(R.id.name_et)
    ClearWriteEditText nameEt;
    @BindView(R.id.pass_et)
    ClearWriteEditText passEt;
    @BindView(R.id.qrpass_et)
    ClearWriteEditText qrpassEt;
    @BindView(R.id.sub_btn)
    Button subBtn;
    RegisterPresenter registerPresenter;
     MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        registerPresenter = new RegisterPresenter(this,this);
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    if (AMUtils.isMobile(s.toString().trim())) {
                        AMUtils.onInactive(RigsterActivity.this, phoneEt);
                    } else {
                        CommonUtil.showToast(RigsterActivity.this,"非法手机号");
                    }
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


    @OnClick(R.id.sub_btn)
    public void onClick() {
        String phone = phoneEt.getText().toString();
        String name = nameEt.getText().toString();
        String pass = passEt.getText().toString();
        String qrpass = qrpassEt.getText().toString();
        if(TextUtils.isEmpty(phone)){
            phoneEt.setShakeAnimation();
            CommonUtil.showToast(this,"手机号不能为空");
            return;
        }
        if(TextUtils.isEmpty(name)){
            nameEt.setShakeAnimation();
            CommonUtil.showToast(this,"姓名不能为空");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            passEt.setShakeAnimation();
            CommonUtil.showToast(this,"密码不为空");
            return;
        }
        if(pass.length()<6){
            CommonUtil.showToast(this,"密码不能小于六位");
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
        mMProgressDialog = CommonUtil.configDialogDefault(RigsterActivity.this);
        mMProgressDialog.show("正在提交...");
        registerPresenter.onSubmit(name,phone,pass,0,"","","");
       // NormalDialogStyleOne(phone,name,pass);
       /* Intent intent = new Intent(RigsterActivity.this,RegisterSelectActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("phone",phone);
        intent.putExtra("pass",pass);
        startActivity(intent);
        finish();*/
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(int id) {
        Log.i("userid",id+"&&&");
        mMProgressDialog.dismiss();
        Constants.judgeFlag = 0;
        Intent intent = new Intent(this,RegisterSelectActivity.class);
        intent.putExtra("userid",id+"");
        startActivity(intent);
        // CommonUtil.showToast(this,"注册成功");
        // startActivity(new Intent(RigsterActivity.this, LoginActivity.class));
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }

    @Override
    public void onSuccess(List<CompanyBean> companyBeanList) {

    }

    private void NormalDialogStyleOne(final String phone, final String name, final String pass) {
        final NormalDialog dialog = new NormalDialog(this);

        dialog.content("是否是公司管理员?")//
                .style(NormalDialog.STYLE_TWO)//
                .btnNum(2)
                .btnText("否", "是")//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {//取消
                        Intent intent = new Intent(RigsterActivity.this,RigsterTwoActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("phone",phone);
                        intent.putExtra("pass",pass);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {//确定
                            mMProgressDialog = CommonUtil.configDialogDefault(RigsterActivity.this);
                            mMProgressDialog.show("正在提交...");
                            registerPresenter.onSubmit(name,phone,pass,1,"","","");
                            dialog.dismiss();
                    }
                });
    }
}
