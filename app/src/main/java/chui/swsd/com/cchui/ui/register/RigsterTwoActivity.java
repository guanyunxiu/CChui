package chui.swsd.com.cchui.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.maning.mndialoglibrary.MProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.ClearWriteEditText;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class RigsterTwoActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.sub_btn)
    Button subBtn;
    RegisterPresenter registerPresenter;
    MProgressDialog mMProgressDialog;
    @BindView(R.id.com_name_tv)
    TextView comNameTv;
    @BindView(R.id.yqm_et)
    ClearWriteEditText yqmEt;
    private OptionsPickerView pvNoLinkOptions;
    private int cid;
    private String userid;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register_two;
    }

    @Override
    protected void initViews() {
        userid = getIntent().getStringExtra("userid");
        Log.i("userid",userid+"&&&333");
        registerPresenter = new RegisterPresenter(this, this);
        registerPresenter.onSelectCom();

    }

    @Override
    protected void updateViews() {

    }


    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this, "注册成功");
        if(Constants.judgeFlag == 0) {
            startActivity(new Intent(RigsterTwoActivity.this, LoginActivity.class));
        }else{
            startActivity(new Intent(this, UpdateJcFenActivity.class));
        }
        finish();
    }

    @Override
    public void onSuccess(int id) {

    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }

    @Override
    public void onSuccess(List<CompanyBean> companyBeanList) {
        initNoLinkOptionsPicker(companyBeanList);//查询出所有的公司
    }

    @OnClick({R.id.com_name_tv, R.id.sub_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.com_name_tv:
                pvNoLinkOptions.show();
                break;
            case R.id.sub_btn:
                String yqmStr = yqmEt.getText().toString();
                if(TextUtils.isEmpty(yqmStr)){
                    CommonUtil.showToast(this,"邀请码不能为空");
                    return;
                }
                mMProgressDialog = CommonUtil.configDialogDefault(this);
                mMProgressDialog.show("正在提交...");
                registerPresenter.onUpdateData(userid,cid,comNameTv.getText().toString(),yqmStr);
                break;
        }
    }
    private void initNoLinkOptionsPicker(final List<CompanyBean> companyBeanList) {// 不联动的多级选项
        if(companyBeanList.size()>0){
            comNameTv.setText(companyBeanList.get(0).getName());
            final List<String> list = new ArrayList<>();
            for(CompanyBean companyBean:companyBeanList){
                list.add(companyBean.getName());
            }
            pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    comNameTv.setText(list.get(options1));
                    cid =  companyBeanList.get(options1).getId();
                }
            }).build();
            pvNoLinkOptions.setPicker(list);
        }

    }
}
