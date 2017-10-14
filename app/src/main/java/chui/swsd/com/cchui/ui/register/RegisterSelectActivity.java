package chui.swsd.com.cchui.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.maning.mndialoglibrary.MProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_content.SpShaiShuaiActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\23 0023.
 */

public class RegisterSelectActivity extends BaseActivity implements RegisterContract.View {
    @BindView(R.id.radiobtn1)
    RadioButton radiobtn1;
    @BindView(R.id.radiobtn2)
    RadioButton radiobtn2;
    @BindView(R.id.sub_btn)
    Button subBtn;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    private String userid;
    RegisterPresenter registerPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register_select;
    }

    @Override
    protected void initViews() {
        initTitle(true, "选择角色");
        registerPresenter = new RegisterPresenter(this,this);
        userid = getIntent().getStringExtra("userid");
        Log.i("userid",userid+"&&&222");
    }

    @Override
    protected void updateViews() {

    }

    @OnClick(R.id.sub_btn)
    public void onClick() {
        RadioButton rb = (RadioButton) this.findViewById(radioGroup.getCheckedRadioButtonId());
        String radioStr = rb.getText().toString();
        if(radioStr.equals(getResources().getString(R.string.register_29))){//管理员
            Intent intent = new Intent(this,RigsterThreeActivity.class);
            intent.putExtra("userid",userid);
            startActivity(intent);
        }else if(radioStr.equals(getResources().getString(R.string.register_30))){//普通用户
            Intent intent = new Intent(this,RigsterTwoActivity.class);
            intent.putExtra("userid",userid);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(int id) {
        mMProgressDialog.dismiss();
        Constants.judgeFlag = 0;
        Intent intent = new Intent(this,RigsterThreeActivity.class);
        intent.putExtra("userid",id);
        startActivity(intent);
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(List<CompanyBean> companyBeanList) {

    }
}
