package chui.swsd.com.cchui.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.maning.mndialoglibrary.MProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.ui.mine.manager_info.ManagerInfoActivity;
import chui.swsd.com.cchui.ui.register.RigsterThreeActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\18 0018.
 */

public class ManagerLoginActivity extends BaseActivity implements LoginContract.view{
    @BindView(R.id.user_et)
    EditText userEt;
    @BindView(R.id.pass_et)
    EditText passEt;
    @BindView(R.id.sub_btn)
    Button subBtn;
    LoginPresenter loginPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_mans_register;
    }

    @Override
    protected void initViews() {
        loginPresenter = new LoginPresenter(this,this);
        initTitle(true, "管理员登录");
    }

    @Override
    protected void updateViews() {

    }
    @OnClick(R.id.sub_btn)
    public void onClick() {
        String user = userEt.getText().toString();
        String pass = passEt.getText().toString();
        if(TextUtils.isEmpty(user)){
            CommonUtil.showToast(this,"请输入账号");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            CommonUtil.showToast(this,"请输入密码");
            return;
        }
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("登录中...");
        loginPresenter.onManaLogin(user,pass);

    }

    @Override
    public void onSuccess(UserBean userBean) {


    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        startActivity(new Intent(this, ManagerInfoActivity.class));
        finish();
    }

    @Override
    public void onFailer() {
        mMProgressDialog.dismiss();
    }
}
