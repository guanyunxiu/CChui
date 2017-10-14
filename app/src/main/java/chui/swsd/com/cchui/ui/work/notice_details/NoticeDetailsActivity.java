package chui.swsd.com.cchui.ui.work.notice_details;

import android.os.Bundle;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.GongGaoBean;
import chui.swsd.com.cchui.ui.apply.gonggao.GongGaoContract;
import chui.swsd.com.cchui.ui.apply.gonggao.GongGaoPresenter;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class NoticeDetailsActivity extends BaseSwipeBackActivity implements GongGaoContract.view {
    GongGaoPresenter gongGaoPresenter;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.company_tv)
    TextView companyTv;
    @BindView(R.id.release_peo_tv)
    TextView releasePeoTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    private int id;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_notice_details;
    }

    @Override
    protected void initViews() {
        initTitle(true, "公告详情");
        id = getIntent().getIntExtra("id", 0);
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("提交中...");
        gongGaoPresenter = new GongGaoPresenter(this, this);
        gongGaoPresenter.onSelectSing(id);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(List<GongGaoBean> gongGaoBeanList) {

    }

    @Override
    public void onSuccess(GongGaoBean gongGaoBean) {
        setData(gongGaoBean);
        mMProgressDialog.dismiss();
    }
    public void setData(GongGaoBean gongGaoBean){
        titleTv.setText(gongGaoBean.getTitle());
        contentTv.setText(gongGaoBean.getContent());
        String comName =  BaseApplication.mSharedPrefUtil.getString(SharedConstants.COMNAME,"");
        companyTv.setText(comName);
        releasePeoTv.setText(gongGaoBean.getUsername());
        timeTv.setText(gongGaoBean.getTime());
    }

}
