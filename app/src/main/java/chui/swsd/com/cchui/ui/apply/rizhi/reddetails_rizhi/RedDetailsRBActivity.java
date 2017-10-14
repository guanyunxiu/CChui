package chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.maning.mndialoglibrary.MProgressDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.ImageItemAdapter;
import chui.swsd.com.cchui.adapter.PhotoItemAdapter;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.model.PhotoBean;
import chui.swsd.com.cchui.model.RzDetailsBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_baoxiao.SpBaoxiaoActivity;
import chui.swsd.com.cchui.ui.img_browse.ImagePagerActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：读日志的详细内容:日报
 * Created by 关云秀 on 2017\8\22 0022.
 */

public class RedDetailsRBActivity extends BaseSwipeBackActivity implements RedDetailsContract.view {
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.work_tv1)
    TextView workTv1;
    @BindView(R.id.work_tv2)
    TextView workTv2;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.work_tv3)
    TextView workTv3;
    @BindView(R.id.work_tv4)
    TextView workTv4;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.name_photo_tv)
    TextView namePhotoTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    private int id;
    RedDetailsPresenter redDetailsPresenter;
    PhotoItemAdapter imageItemAdapter;
    List<PhotoBean> photoBeanList = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_rizhi_details;
    }

    @Override
    protected void initViews() {
        initTitle(true, "日报");
        id = getIntent().getIntExtra("id", 0);
        redDetailsPresenter = new RedDetailsPresenter(this, this);
        setAdater();
    }

    public void setAdater() {
       //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        imageItemAdapter = new PhotoItemAdapter(this,photoBeanList);
        imageItemAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerview.setAdapter(imageItemAdapter);
        //mResultText.setText(sb.toString());
        imageItemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                List<String> stringList = new ArrayList<String>();
                String photo = UrlAddress.URLAddress+photoBeanList.get(i).getPath();
                stringList.add(photo);
                Intent intent = new Intent(RedDetailsRBActivity.this, ImagePagerActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (Serializable) stringList);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void updateViews() {
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("查询中...");
        redDetailsPresenter.onSubmit(id);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(RzDetailsBean rzDetailsBean) {
        mMProgressDialog.dismiss();
        initData(rzDetailsBean);
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
    public void initData(RzDetailsBean rzDetailsBean) {
        namePhotoTv.setText(CommonUtil.getSubName(rzDetailsBean.getUserHead().getName()));
        nameTv.setText(rzDetailsBean.getUserHead().getName());
        workTv1.setText(rzDetailsBean.getAccomplish());
        workTv2.setText(rzDetailsBean.getUnfinished());
        workTv3.setText(rzDetailsBean.getCoordinate());
        workTv4.setText(rzDetailsBean.getRemark());
        timeTv.setText(DateUtil.getTime5(Long.parseLong(rzDetailsBean.getTime()))+"");
        imageItemAdapter.setNewData(rzDetailsBean.getPhotos());
        photoBeanList = rzDetailsBean.getPhotos();
    }

}
