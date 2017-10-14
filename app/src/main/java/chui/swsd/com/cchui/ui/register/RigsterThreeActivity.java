package chui.swsd.com.cchui.ui.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.maning.mndialoglibrary.MProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.model.JsonBean;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.utils.AreaUtil;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;
import chui.swsd.com.cchui.utils.GetJsonDataUtil;
import chui.swsd.com.cchui.widget.ClearWriteEditText;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class RigsterThreeActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.sub_btn)
    Button subBtn;
    @BindView(R.id.com_name_et)
    EditText comNameEt;
    @BindView(R.id.address_et)
    TextView addressEt;
    @BindView(R.id.rygm_et)
    TextView rygmEt;
    @BindView(R.id.gl_account_et)
    EditText glAccountEt;
    @BindView(R.id.account_pass_et)
    EditText accountPassEt;
    @BindView(R.id.yqm_et)
    EditText yqmEt;
    @BindView(R.id.add_details_et)
    EditText addDetailsEt;
    @BindView(R.id.starttime_et)
    TextView startTimeTv;
    @BindView(R.id.endtime_et)
    TextView endTimeTv;
    private OptionsPickerView pvNoLinkOptions;
    MProgressDialog mMProgressDialog;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private TimePickerView  pvCustomTime;
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private int startFlag = 0;
    private String province,city,county;
    RegisterPresenter registerPresenter;
    private String userid;
    private int managerFlag;//判断是修改还是添加
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register_three2;
    }

    @Override
    protected void initViews() {
        userid = getIntent().getStringExtra("userid");
        managerFlag = getIntent().getIntExtra("managerFlag",0);
        initTitle(true, "创建公司");

        registerPresenter = new RegisterPresenter(this, this);
        initNoLinkOptionsPicker();
        initCustomTimePicker();
    }

    @Override
    protected void updateViews() {

    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(int id) {
        mMProgressDialog.dismiss();
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.COMID,id);//公司ID
        BaseApplication. mSharedPrefUtil.commit();
        Intent intent = new Intent(this,RigsterFourActivity.class);
        intent.putExtra("cid",id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(List<CompanyBean> companyBeanList) {

    }


    private void initNoLinkOptionsPicker() {// 不联动的多级选项
        final List<String> list = new ArrayList<>();
        list.add("1-10人");
        list.add("11-20人");
        list.add("21-50人");
        list.add("51-100人");
        list.add("101-200人");
        list.add("501-1000人");
        list.add("1001-2000人");
        list.add("2000人以上");
        pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
              rygmEt.setText(list.get(options1));
            }
        }).build();
        pvNoLinkOptions.setPicker(list);
    }

    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+"-"+
                        options2Items.get(options1).get(options2)+"-"+
                        options3Items.get(options1).get(options2).get(options3);
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                county = options3Items.get(options1).get(options2).get(options3);
                addressEt.setText(tx);
                //Toast.makeText(JsonDataActivity.this,tx,Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        //pvOptions.setSelectOptions();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }
    /**
     * 地址选择
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了

                        //  Toast.makeText(JsonDataActivity.this,"Begin Parse Data",Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    // Toast.makeText(JsonDataActivity.this,"Parse Succeed",Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    ShowPickerView();
                    break;

                case MSG_LOAD_FAILED:
                    // Toast.makeText(JsonDataActivity.this,"Parse Failed",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = AreaUtil.parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    @OnClick({R.id.address_et, R.id.rygm_et,R.id.starttime_lv,R.id.endtime_lv,R.id.sub_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address_et:
                if (isLoaded){
                    ShowPickerView();
                }else {
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                }
                break;
            case R.id.rygm_et:
                pvNoLinkOptions.show();
                break;
            case R.id.starttime_lv:
                startFlag = 0;
                pvCustomTime.show();
                break;
            case R.id.endtime_lv:
                startFlag = 1;
                pvCustomTime.show();
                break;
            case R.id.sub_btn:
                submit();
                break;
        }
    }
    public void submit(){
        String company = comNameEt.getText().toString();
        String address = addressEt.getText().toString();
        String detailsAdd = addDetailsEt.getText().toString();
        String peoples = rygmEt.getText().toString();
        String account = glAccountEt.getText().toString();
        String pass = accountPassEt.getText().toString();
        String yqm = yqmEt.getText().toString();
        if(TextUtils.isEmpty(company)){
            CommonUtil.showToast(this,"请输入公司名称");
            return ;
        }
        if(address.equals(getResources().getString(R.string.register_19))){
                CommonUtil.showToast(this,"请选择公司地址");
            return;
        }
        if(TextUtils.isEmpty(detailsAdd)){
            CommonUtil.showToast(this,"请输入公司的详细地址");
            return;
        }
        if(peoples.equals(getResources().getString(R.string.register_20))){
            CommonUtil.showToast(this,"请选择人员规模");
            return;
        }
        if(TextUtils.isEmpty(account)){
            CommonUtil.showToast(this,"请输入管理员账号");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            CommonUtil.showToast(this,"请输入管理员密码");
            return;
        }
        if(TextUtils.isEmpty(yqm)){
            CommonUtil.showToast(this,"请输入邀请码");
            return;
        }
        long startTime = DateUtil.getDateToSecond5(startTimeTv.getText().toString());

        long endTime = DateUtil.getDateToSecond5(endTimeTv.getText().toString());
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("正在提交...");
        registerPresenter.onTwoSubmit(userid,company,province,city,county,detailsAdd, Constants.Longitude+"",Constants.Latitude+"",startTime+"",endTime+"",peoples,account,pass,yqm);

    }
    public void dismiss(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if(startFlag == 0) {
                    startTimeTv.setText(DateUtil.getHour2(date));
                }else if(startFlag == 1){
                    endTimeTv.setText(DateUtil.getHour2(date));
                }
            }
        })
                .setDate(selectedDate)
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
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public void isSoftInputShow(Activity activity) {
        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
