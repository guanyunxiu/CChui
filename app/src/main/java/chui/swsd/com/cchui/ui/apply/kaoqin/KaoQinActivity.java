package chui.swsd.com.cchui.ui.apply.kaoqin;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.maning.mndialoglibrary.MProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.DaKaItemAdapter;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.KaoQinBean;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：考勤打卡
 * Created by 关云秀 on 2017\8\15 0015.
 */

public class KaoQinActivity extends BaseSwipeBackActivity implements LocationSource,
        AMapLocationListener,KaoQinContract.view{
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.daka_rlv)
    RecyclerView recyclerview;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.daka_lv)
    LinearLayout dakaLv;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.s_bg_tv)
    TextView sBgTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.daka_tv)
    TextView dakaTv;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    DaKaItemAdapter kaoQinItemAdapter;
    double longitude,latitude;//经度，纬度
    KaoQinPresenter kaoQinPresenter;
    MProgressDialog mMProgressDialog;
    private int nums = 0;
    private int category = 0;
    private int cateFlag = -1;//查询出来是否是进行了下班打卡
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_kaoqin;
    }

    @Override
    protected void initViews() {
        initTitle(true, "考勤打卡");
        kaoQinPresenter = new KaoQinPresenter(this,this);
        iniMap();
        new Thread(new ThreadShow()).start();
    }


    @Override
    protected void updateViews() {
        kaoQinPresenter.onSelect();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //上下班判断
                if(DateUtil.getDayHour()<Constants.timeFlag){
                    if(cateFlag == 0){
                        dakaLv.setBackgroundResource(R.mipmap.icon_daka_gray_bg);
                        dakaTv.setText("已签到");
                    }else{
                        dakaLv.setBackgroundResource(R.mipmap.icon_daka_bg);
                        dakaTv.setText("上班打卡");
                    }
                    category = 0;
                }else{
                    if(cateFlag == 1){
                        dakaLv.setBackgroundResource(R.mipmap.icon_daka_gray_bg);
                        dakaTv.setText("已签退");
                    }else{
                        dakaLv.setBackgroundResource(R.mipmap.icon_daka_bg);
                        dakaTv.setText("下班打卡");
                    }
                    category = 1;
                }
                timeTv.setText(DateUtil.getTime1());
            }
        }

    };
    long firstTime=0;
    @OnClick(R.id.daka_lv)
    public void onClick() {
        if(cateFlag != 1) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1000 * 5) {
                Log.i("dayHOUR",DateUtil.getDayHour()+"***");
                if(DateUtil.getDayHour()<Constants.timeFlag){
                    if(cateFlag == 0) {
                        CommonUtil.showToast(this,"已签到");
                    }else{
                        NormalDialogStyleTwo("是否进行签到", 0);
                    }
                }else{
                    NormalDialogStyleTwo("是否进行签退",1);
                }
                firstTime = System.currentTimeMillis();
            } else {
                CommonUtil.showToast(this, "打卡次数太频繁");
            }
        }else{
            CommonUtil.showToast(this,"今日已打卡完毕");
        }
    }
    private void NormalDialogStyleTwo(String str, final int categorys) {
        final NormalDialog dialog = new NormalDialog(this);
        dialog
                .content(str)
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        mMProgressDialog = CommonUtil.configDialogDefault(KaoQinActivity.this);
                        mMProgressDialog.show("正在打卡...");
                        kaoQinPresenter.onSubmit(locationTv.getText().toString(),longitude,latitude,0,categorys);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onSuccess(int code) {
        mMProgressDialog.dismiss();
        if(code == 0) {
            CommonUtil.showToast(this, "打卡成功");
            kaoQinPresenter.onSelect();
            dakaLv.setBackgroundResource(R.mipmap.icon_daka_gray_bg);
        }else if(code == 6){
            NormalDialogOutWork();
        }
    }
    private void NormalDialogOutWork() {
        final NormalDialog dialog = new NormalDialog(this);
        dialog
                .content("距离公司位置较远，是否是外出打卡")
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        mMProgressDialog = CommonUtil.configDialogDefault(KaoQinActivity.this);
                        mMProgressDialog.show("正在打卡...");
                        kaoQinPresenter.onSubmit(locationTv.getText().toString(),longitude,latitude,1,category);
                        dialog.dismiss();
                    }
                });
    }
    @Override
    public void onSuccess(List<KaoQinBean> kaoQinBeanList) {
        if(kaoQinBeanList.size()>0) {
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(linearLayoutManager1);
            kaoQinItemAdapter = new DaKaItemAdapter(kaoQinBeanList);
            kaoQinItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            recyclerview.setAdapter(kaoQinItemAdapter);
            KaoQinBean kaoQinBean = kaoQinBeanList.get(kaoQinBeanList.size() - 1);
            cateFlag = kaoQinBean.getCategory();
            if (kaoQinBean.getCategory() == 0) {//上班
                sBgTv.setText("下");
            } else if (kaoQinBean.getCategory() == 1) {//1是下班
                sBgTv.setText("");
                statusTv.setVisibility(View.VISIBLE);
                statusTv.setText("工作辛苦了，好好休息");
            }
            nums = kaoQinBeanList.size();
        }
    }

    @Override
    public void onFail() {
        if(mMProgressDialog != null) {
            mMProgressDialog.dismiss();
        }
    }

    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    System.out.println("send...");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }

    private void iniMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_location_true));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setZoomGesturesEnabled(false);
        //  aMap.getUiSettings().setAllGesturesEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        // aMap.setMyLocationType()
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                longitude = amapLocation.getLongitude();//经度
                latitude = amapLocation.getLatitude();//纬度
                locationTv.setText(amapLocation.getAddress());
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }


}
