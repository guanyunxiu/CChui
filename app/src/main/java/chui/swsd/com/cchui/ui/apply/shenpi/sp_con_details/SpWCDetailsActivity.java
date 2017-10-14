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
 * 内容：外出内容
 * Created by 关云秀 on 2017\8\25 0025.
 */

public class SpWCDetailsActivity extends BaseActivity implements SpDetailsContract.view{
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.nums_tv)
    TextView numsTv;
    @BindView(R.id.dep_tv)
    TextView depTv;
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
    GoOutDetailsBean goOutDetailsBean2;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_wc_details;
    }

    @Override
    protected void initViews() {
        initTitle(true,"外出");
        id = getIntent().getIntExtra("id",0);
        status = getIntent().getIntExtra("spStatus", 0);
        spDetailsPresenter = new SpDetailsPresenter(this,this);
        initAdapter();
    }

    @Override
    protected void updateViews() {
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("正在查询...");
        spDetailsPresenter.onSubmit(2,id);
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

    }

    @Override
    public void onSuccess(GoOutDetailsBean goOutDetailsBean) {
       mMProgressDialog.dismiss();
      initData(goOutDetailsBean);
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
        CommonUtil.showToast(this,"查询成功");
        EventBus.getDefault().post("查询");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"执行失败");
    }
    public void initData(GoOutDetailsBean goOutDetailsBean){
        if(goOutDetailsBean != null) {
            goOutDetailsBean2 = goOutDetailsBean;
            initTitle(true, goOutDetailsBean.getUserHead().getName() + "的外出");
            nameTv.setText(goOutDetailsBean.getUserHead().getName());
            depTv.setText(goOutDetailsBean.getUserHead().getDepartment());
            startTv.setText(goOutDetailsBean.getGoout().getStarttime());
            endTv.setText(goOutDetailsBean.getGoout().getEndtime());
            daysTv.setText(goOutDetailsBean.getGoout().getUsedtime());
            reasonTv.setText(goOutDetailsBean.getGoout().getSearon());
            numsTv.setText(goOutDetailsBean.getGoout().getNumber());
            statusTv.setText(goOutDetailsBean.getApprovedVo().getUsernames()+ CommonUtil.getShStatus(goOutDetailsBean.getApprovedVo().getTypes()));
            spDetailsItemAdapter.setNewData(goOutDetailsBean.getApproved());
        }
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
                        spDetailsPresenter.onSpSubmit(2,goOutDetailsBean2.getGoout().getAuditid(),flag);
                        dialog.dismiss();
                    }
                });
    }
}
