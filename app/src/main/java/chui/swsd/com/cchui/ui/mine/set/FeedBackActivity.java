package chui.swsd.com.cchui.ui.mine.set;

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
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\23 0023.
 */

public class FeedBackActivity extends BaseActivity implements FeedBackContract.view{
    @BindView(R.id.id_feedback_et)
    EditText idFeedbackEt;
    @BindView(R.id.sub_btn)
    Button subBtn;
    FeedBackPresenter feedBackPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initViews() {
      initTitle(true,"意见反馈");
      feedBackPresenter = new FeedBackPresenter(this,this);
    }

    @Override
    protected void updateViews() {

    }

    @OnClick(R.id.sub_btn)
    public void onClick() {
        String con = idFeedbackEt.getText().toString();
        if(TextUtils.isEmpty(con)){
            CommonUtil.showToast(this,"内容不能为空");
            return;
        }
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("提交中...");
        feedBackPresenter.onSubmit(con);
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"提交成功");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
}
