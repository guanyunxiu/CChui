package chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.maning.mndialoglibrary.MProgressDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.SpDetailsItemAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.model.ApprovedBean;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.model.BaoXiaoDetailsBean;
import chui.swsd.com.cchui.model.ChuChaiDetailsBean;
import chui.swsd.com.cchui.model.GoOutDetailsBean;
import chui.swsd.com.cchui.model.JiaBanDetailsBean;
import chui.swsd.com.cchui.model.LeaveDetailsBean;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：出差内容
 * Created by 关云秀 on 2017\8\25 0025.
 */

public class SpCCDetailsActivity extends BaseActivity implements SpDetailsContract.view{
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.nums_tv)
    TextView numsTv;
    @BindView(R.id.dep_tv)
    TextView depTv;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.start_tv)
    TextView startTv;
    @BindView(R.id.end_tv)
    TextView endTv;
    @BindView(R.id.reason_tv)
    TextView reasonTv;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.days_tv)
    TextView daysTv;
    @BindView(R.id.status_lv)
    LinearLayout statusLv;
    @BindView(R.id.agree_tv)
    TextView agreeTv;
    @BindView(R.id.refuse_tv)
    TextView refuseTv;
    SpDetailsItemAdapter spDetailsItemAdapter;
    SpDetailsPresenter spDetailsPresenter;
    int id,status;
    List<ApprovedBean> list = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    ChuChaiDetailsBean chuChaiDetailsBean2;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_cc_details;
    }

    @Override
    protected void initViews() {
        initTitle(true,"出差");
        id = getIntent().getIntExtra("id",0);
        status = getIntent().getIntExtra("spStatus", 0);
        spDetailsPresenter = new SpDetailsPresenter(this,this);
        initAdapter();
    }

    @Override
    protected void updateViews() {
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("正在查询...");
        spDetailsPresenter.onSubmit(1,id);
        if (status == 1) {
            statusLv.setVisibility(View.VISIBLE);
        } else {
            statusLv.setVisibility(View.GONE);
        }
    }
    private void initAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spDetailsItemAdapter = new SpDetailsItemAdapter(list);
        recyclerView.setAdapter(spDetailsItemAdapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(LeaveDetailsBean leaveDetailsBean) {

    }

    @Override
    public void onSuccess(ChuChaiDetailsBean chuChaiDetailsBean) {
        mMProgressDialog.dismiss();
       initData(chuChaiDetailsBean);
    }

    @Override
    public void onSuccess(GoOutDetailsBean goOutDetailsBean) {

    }

    @Override
    public void onSuccess(JiaBanDetailsBean jiaBanDetailsBean) {

    }

    @Override
    public void onSuccess(BaoXiaoDetailsBean baoXiaoDetailsBean) {

    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"提交成功");
        EventBus.getDefault().post("查询");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"执行失败");
    }
    public void initData(ChuChaiDetailsBean chuChaiDetailsBean){
        initTitle(true,chuChaiDetailsBean.getUserHead().getName()+"的出差");
        nameTv.setText(chuChaiDetailsBean.getUserHead().getName());
        depTv.setText(chuChaiDetailsBean.getUserHead().getDepartment());
        typeTv.setText(chuChaiDetailsBean.getEvection().getAddress());
        startTv.setText(chuChaiDetailsBean.getEvection().getStarttime());
        endTv.setText(chuChaiDetailsBean.getEvection().getEndtime());
        daysTv.setText(chuChaiDetailsBean.getEvection().getDays()+"");
        reasonTv.setText(chuChaiDetailsBean.getEvection().getReason());
        spDetailsItemAdapter.setNewData(chuChaiDetailsBean.getApproved());
        numsTv.setText(chuChaiDetailsBean.getEvection().getNumber());
        statusTv.setText(chuChaiDetailsBean.getApprovedVo().getUsernames()+ CommonUtil.getShStatus(chuChaiDetailsBean.getApprovedVo().getTypes()));
        chuChaiDetailsBean2 = chuChaiDetailsBean;
    }
    @OnClick({R.id.refuse_tv, R.id.status_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refuse_tv:  //同意
                NormalDialogStyleOne(0);
                break;
            case R.id.status_lv: //拒绝
                NormalDialogStyleOne(1);
                break;
        }
    }
    private void NormalDialogStyleOne(final int flag ) {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false).content("是否确定提交?")//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {//取消
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {//确定
                        mMProgressDialog.show("正在提交...");
                        spDetailsPresenter.onSpSubmit(1,chuChaiDetailsBean2.getEvection().getAuditid(),flag);
                        dialog.dismiss();
                    }
                });
    }
}
