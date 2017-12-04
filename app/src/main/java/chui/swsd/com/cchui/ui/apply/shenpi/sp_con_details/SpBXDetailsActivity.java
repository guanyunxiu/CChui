package chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.maning.mndialoglibrary.MProgressDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.BaoXiaoDetailsItemAdapter;
import chui.swsd.com.cchui.adapter.PhotoItemAdapter;
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
import chui.swsd.com.cchui.model.PhotoBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.img_browse.ImagePagerActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import retrofit2.BaseUrl;

/**
 * 内容：报销内容
 * Created by 关云秀 on 2017\8\25 0025.
 */

public class SpBXDetailsActivity extends BaseActivity implements SpDetailsContract.view{
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.nums_tv)
    TextView numsTv;
    @BindView(R.id.dep_tv)
    TextView depTv;
    @BindView(R.id.details_rv)
    RecyclerView detailsRv;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.photo_recyclerview)
    RecyclerView photoRecycler;
    @BindView(R.id.summoney_tv)
    TextView sumMoneyTv;
    @BindView(R.id.status_lv)
    LinearLayout statusLv;
    @BindView(R.id.agree_tv)
    TextView agreeTv;
    @BindView(R.id.refuse_tv)
    TextView refuseTv;
    SpDetailsItemAdapter spDetailsItemAdapter;
    BaoXiaoDetailsItemAdapter baoXiaoDetailsItemAdapter;
    SpDetailsPresenter spDetailsPresenter;
    PhotoItemAdapter photoItemAdapter;
    int id,status;
    List<ApprovedBean> listApproved = new ArrayList<>();
    List<BaoXiaoDetailsBean.ApplayinfoBean> listInfo = new ArrayList<>();
    List<PhotoBean> photoBeanList = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    BaoXiaoDetailsBean baoXiaoDetailsBean2;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_bx_details;
    }
    @Override
    protected void initViews() {
        initTitle(true,"报销");
        id = getIntent().getIntExtra("id",0);
        status = getIntent().getIntExtra("spStatus", 0);
        spDetailsPresenter = new SpDetailsPresenter(this,this);
        initAdapter();
    }
    @Override
    protected void updateViews() {
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("正在查询...");
        spDetailsPresenter.onSubmit(4,id);
        if (status == 1) {
            statusLv.setVisibility(View.VISIBLE);
        } else {
            statusLv.setVisibility(View.GONE);
        }
    }
    private void initAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spDetailsItemAdapter = new SpDetailsItemAdapter(listApproved);
        recyclerView.setAdapter(spDetailsItemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        photoRecycler.setHasFixedSize(true);
        photoRecycler.setLayoutManager(linearLayoutManager);
        photoItemAdapter = new PhotoItemAdapter(this,photoBeanList);
        photoRecycler.setAdapter(photoItemAdapter);

        detailsRv.setHasFixedSize(true);
        detailsRv.setLayoutManager(new LinearLayoutManager(this));
        baoXiaoDetailsItemAdapter = new BaoXiaoDetailsItemAdapter(listInfo);
        detailsRv.setAdapter(baoXiaoDetailsItemAdapter);

        photoItemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ArrayList<String> urls = new ArrayList<>();
                for (PhotoBean photosBean : photoBeanList) {
                    urls.add(UrlAddress.URLAddress+photosBean.getPath());
                }
                Intent intent = new Intent(SpBXDetailsActivity.this, ImagePagerActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, i);
                startActivity(intent);
            }
        });
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

    }

    @Override
    public void onSuccess(JiaBanDetailsBean jiaBanDetailsBean) {

    }

    @Override
    public void onSuccess(BaoXiaoDetailsBean baoXiaoDetailsBean) {
        mMProgressDialog.dismiss();
        initData(baoXiaoDetailsBean);
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
    public void initData(BaoXiaoDetailsBean baoXiaoDetailsBean){
        initTitle(true,baoXiaoDetailsBean.getUserHead().getName()+"的报销");
        nameTv.setText(baoXiaoDetailsBean.getUserHead().getName());
        depTv.setText(baoXiaoDetailsBean.getUserHead().getDepartment());
        numsTv.setText(baoXiaoDetailsBean.getNumber());
        statusTv.setText(baoXiaoDetailsBean.getApprovedVo().getUsernames()+ CommonUtil.getShStatus(baoXiaoDetailsBean.getApprovedVo().getTypes()));
        double sum = 0;
        for(BaoXiaoDetailsBean.ApplayinfoBean applayinfoBean:baoXiaoDetailsBean.getApplayinfo()){
            sum = sum + applayinfoBean.getMoney();
        }
        sumMoneyTv.setText(sum+"");//报销总金额
        spDetailsItemAdapter.setNewData(baoXiaoDetailsBean.getApproved());
        baoXiaoDetailsItemAdapter.setNewData(baoXiaoDetailsBean.getApplayinfo());
        photoItemAdapter.setNewData(baoXiaoDetailsBean.getApplayphoto());
        baoXiaoDetailsBean2 = baoXiaoDetailsBean;
        photoBeanList = baoXiaoDetailsBean.getApplayphoto();
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
                        spDetailsPresenter.onSpSubmit(4,baoXiaoDetailsBean2.getAuditid(),flag);
                        dialog.dismiss();
                    }
                });
    }
}
