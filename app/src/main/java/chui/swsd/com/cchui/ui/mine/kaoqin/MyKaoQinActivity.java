package chui.swsd.com.cchui.ui.mine.kaoqin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.maning.mndialoglibrary.MProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.GroupAdapter;
import chui.swsd.com.cchui.adapter.KaoQinEarlyDateItemAdapter;
import chui.swsd.com.cchui.adapter.KaoQinItemAdapter;
import chui.swsd.com.cchui.adapter.KaoQinLateItemAdapter;
import chui.swsd.com.cchui.adapter.KaoQinQueKaDateItemAdapter;
import chui.swsd.com.cchui.adapter.KaoQinXiuXiItemAdapter;
import chui.swsd.com.cchui.adapter.KaoQinkgongDateItemAdapter;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.expandable.ExpandableLayout;
import chui.swsd.com.cchui.model.KaoQinListBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;
import chui.swsd.com.cchui.widget.ExpandLayout;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\14 0014.
 */

public class MyKaoQinActivity extends BaseSwipeBackActivity implements MyKaoQinContract.view{
    @BindView(R.id.imageview)
    CircleImageView imageview;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.photo_lv)
    LinearLayout photoLv;
    @BindView(R.id.cqts_tv)
    TextView cqtsTv;
    @BindView(R.id.cqts_rlv)
    RecyclerView cqtsRlv;
    @BindView(R.id.expandLayout)
    ExpandableLayout expandLayout;
    @BindView(R.id.xxts_tv)
    TextView xxtsTv;
    @BindView(R.id.xxts_rlv)
    RecyclerView xxtsRlv;
    @BindView(R.id.expandLayout2)
    ExpandableLayout expandLayout2;
    @BindView(R.id.cd_tv)
    TextView cdTv;
    @BindView(R.id.cd_rlv)
    RecyclerView cdRlv;
    @BindView(R.id.expandLayout3)
    ExpandableLayout expandLayout3;
    @BindView(R.id.zt_tv)
    TextView ztTv;
    @BindView(R.id.zt_rlv)
    RecyclerView ztRlv;
    @BindView(R.id.expandLayout4)
    ExpandableLayout expandLayout4;
    @BindView(R.id.qk_tv)
    TextView qkTv;
    @BindView(R.id.qk_rlv)
    RecyclerView qkRlv;
    @BindView(R.id.expandLayout5)
    ExpandableLayout expandLayout5;
    @BindView(R.id.kg_tv)
    TextView kgTv;
    @BindView(R.id.kg_rlv)
    RecyclerView kgRlv;
    @BindView(R.id.expandLayout6)
    ExpandableLayout expandLayout6;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photo_tv)
    TextView photoTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    KaoQinItemAdapter kaoQinItemAdapter;
    KaoQinXiuXiItemAdapter kaoQinXiuXiItemAdapter;
    KaoQinLateItemAdapter kaoQinLateItemAdapter;
    MyKaoQinPresenter myKaoQinPresenter;
    KaoQinEarlyDateItemAdapter kaoQinEarlyDateItemAdapter;
    KaoQinQueKaDateItemAdapter kaoQinQueKaDateItemAdapter;
    KaoQinkgongDateItemAdapter kaoQinkgongDateItemAdapter;
    private TimePickerView  pvCustomTime;
    MProgressDialog mMProgressDialog;
    KaoQinListBean kaoQinListBean2 = new KaoQinListBean();
    List<KaoQinListBean.WorkdateBean> workdateBeanList = new ArrayList<>();
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_kaoqin;
    }

    @Override
    protected void initViews() {
         initTitle(true,"我的考勤");
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("查询中...");
        initCustomTimePicker();//初始化时间

        timeTv.setText(DateUtil.getTime4());
        initData();
        myKaoQinPresenter = new MyKaoQinPresenter(this,this);
        myKaoQinPresenter.onSubmit(DateUtil.getTime4());
        setAdapter(kaoQinListBean2);
    }

    @Override
    protected void updateViews() {
    }
    public void initData(){
        String heading = BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHOTO,"");
        String name = BaseApplication.mSharedPrefUtil.getString(SharedConstants.NAME,"");
       if(TextUtils.isEmpty(heading)){
            photoTv.setText(CommonUtil.subName(name));
            photoTv.setVisibility(View.VISIBLE);
            imageview.setVisibility(View.GONE);
        }else{
            photoTv.setVisibility(View.GONE);
            imageview.setVisibility(View.VISIBLE);
            Glide.with(this).load(UrlAddress.URLAddress+heading).into(imageview);
        }
        nameTv.setText(name);
    }

   public void setAdapter(KaoQinListBean kaoQinBean){
       //出勤天数
       cqtsRlv.setHasFixedSize(true);
       cqtsRlv.setLayoutManager(new LinearLayoutManager(this));
       kaoQinItemAdapter = new KaoQinItemAdapter(kaoQinBean.getWorkdate());
       cqtsRlv.setAdapter(kaoQinItemAdapter);

       //休息天数
       xxtsRlv.setHasFixedSize(true);
       xxtsRlv.setLayoutManager(new LinearLayoutManager(this));
       kaoQinXiuXiItemAdapter = new KaoQinXiuXiItemAdapter(this,workdateBeanList);
       xxtsRlv.setAdapter(kaoQinXiuXiItemAdapter);
       //迟到
       cdRlv.setHasFixedSize(true);
       cdRlv.setLayoutManager(new LinearLayoutManager(this));
       kaoQinLateItemAdapter = new KaoQinLateItemAdapter(this,workdateBeanList);
       cdRlv.setAdapter(kaoQinLateItemAdapter);
       //早退
       ztRlv.setHasFixedSize(true);
       ztRlv.setLayoutManager(new LinearLayoutManager(this));
       kaoQinEarlyDateItemAdapter = new KaoQinEarlyDateItemAdapter(this,workdateBeanList);
       ztRlv.setAdapter(kaoQinEarlyDateItemAdapter);
       //缺卡
       qkRlv.setHasFixedSize(true);
       qkRlv.setLayoutManager(new LinearLayoutManager(this));
       kaoQinQueKaDateItemAdapter = new KaoQinQueKaDateItemAdapter(this,workdateBeanList);
       qkRlv.setAdapter(kaoQinQueKaDateItemAdapter);
       //旷工
       kgRlv.setHasFixedSize(true);
       kgRlv.setLayoutManager(new LinearLayoutManager(this));
       kaoQinkgongDateItemAdapter = new KaoQinkgongDateItemAdapter(this,workdateBeanList);
       kgRlv.setAdapter(kaoQinkgongDateItemAdapter);
   }

    @OnClick({R.id.time_tv, R.id.cqts_tv, R.id.xxts_tv, R.id.cd_tv, R.id.zt_tv, R.id.qk_tv, R.id.kg_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.time_tv:
                pvCustomTime.show();  //显示开会时间
                break;
            case R.id.cqts_tv:

                if (expandLayout.isExpanded()) {
                    expandLayout.collapse();
                } else {
                    expandLayout.expand();
                }
                break;
            case R.id.xxts_tv:
                if (expandLayout2.isExpanded()) {
                    expandLayout2.collapse();
                } else {
                    expandLayout2.expand();
                }
                break;
            case R.id.cd_tv:
                if (expandLayout3.isExpanded()) {
                    expandLayout3.collapse();
                } else {
                    expandLayout3.expand();
                }
                break;
            case R.id.zt_tv:
                if (expandLayout4.isExpanded()) {
                    expandLayout4.collapse();
                } else {
                    expandLayout4.expand();
                }
                break;
            case R.id.qk_tv:
                if (expandLayout5.isExpanded()) {
                    expandLayout5.collapse();
                } else {
                    expandLayout5.expand();
                }
                break;
            case R.id.kg_tv:
                if (expandLayout6.isExpanded()) {
                    expandLayout6.collapse();
                } else {
                    expandLayout6.expand();
                }
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(KaoQinListBean kaoQinBean) {
        mMProgressDialog.dismiss();
        setDefaultExpand();
        if(kaoQinBean!= null){
            kaoQinListBean2 = kaoQinBean;
            kaoQinItemAdapter.setNewData(kaoQinBean.getWorkdate());
            kaoQinXiuXiItemAdapter.setNewData(kaoQinBean.getBreakdate());
            kaoQinLateItemAdapter.setNewData(kaoQinBean.getLatedate());
            kaoQinEarlyDateItemAdapter.setNewData(kaoQinBean.getEarlydate());
            kaoQinQueKaDateItemAdapter.setNewData(kaoQinBean.getLackdate());
            kaoQinkgongDateItemAdapter.setNewData(kaoQinBean.getAbsenteeismdate());
            setDataSize();
        }else{
            setDefaultData();
            setAdapter(KaoQinListBean.getKaoQinList());
            kaoQinListBean2 = KaoQinListBean.getKaoQinList();
            CommonUtil.showToast(this,"当月未出勤");
        }

    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR)-1, selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH),selectedDate.get(Calendar.HOUR_OF_DAY), selectedDate.get(Calendar.MINUTE));
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                timeTv.setText(DateUtil.getTime3(date));
                mMProgressDialog = CommonUtil.configDialogDefault(MyKaoQinActivity.this);
                mMProgressDialog.show("查询中...");
                myKaoQinPresenter.onSubmit(DateUtil.getTime3(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
    public void setDataSize(){
        cqtsTv.setText(kaoQinListBean2.getWork()+"天");
        xxtsTv.setText(kaoQinListBean2.getBreaks()+"天");
        cdTv.setText(kaoQinListBean2.getLate()+"天");
        ztTv.setText(kaoQinListBean2.getEarly()+"天");
        qkTv.setText(kaoQinListBean2.getLack()+"天");
        kgTv.setText(kaoQinListBean2.getAbsenteeism()+"天");
    }
    public void setDefaultData(){
        cqtsTv.setText("0天");
        xxtsTv.setText("0天");
        cdTv.setText("0天");
        ztTv.setText("0天");
        qkTv.setText("0天");
        kgTv.setText("0天");

    }
    public void setDefaultExpand(){
        expandLayout.collapse();
        expandLayout2.collapse();
        expandLayout3.collapse();
        expandLayout4.collapse();
        expandLayout5.collapse();
        expandLayout6.collapse();
    }

}
