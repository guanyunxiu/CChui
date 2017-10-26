package chui.swsd.com.cchui.ui.mine.my_info;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.kevin.crop.UCrop;
import com.maning.mndialoglibrary.MProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.JsonBean;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.utils.AreaUtil;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.GetJsonDataUtil;
import chui.swsd.com.cchui.utils.PhotoUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 内容：个人资料
 * Created by 关云秀 on 2017\8\14 0014.
 */

public class MyInfoActivity extends BaseSwipeBackActivity implements SeldataContract.view{
    @BindView(R.id.imageview)
    CircleImageView imageview;
    @BindView(R.id.photo_lv)
    LinearLayout photoLv;
    @BindView(R.id.bumen_tv)
    TextView bumenTv;
    @BindView(R.id.bumen_lv)
    LinearLayout bumenLv;
    @BindView(R.id.zhiwu_tv)
    TextView zhiwuTv;
    @BindView(R.id.zhiwu_lv)
    LinearLayout zhiwuLv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.phone_lv)
    LinearLayout phoneLv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.sex_lv)
    LinearLayout sexLv;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.area_lv)
    LinearLayout areaLv;
    @BindView(R.id.sub_btn)
    Button subBtn;
    @BindView(R.id.photo_tv)
    TextView photoTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;

    /** 选择头像相册选取 */
    private static final int REQUESTCODE_PICK = 1;
    /** 选择头像拍照选取 */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;
    File currentFile,outFile;
    SeldataPresenter seldataPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initViews() {
        initTitle(true,"我的信息");
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("正在查询...");
        seldataPresenter = new SeldataPresenter(this,this);
        seldataPresenter.onSelect(BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHONE,""));
    }

    @Override
    protected void updateViews() {

    }


    @OnClick({R.id.photo_lv, R.id.sex_lv, R.id.area_lv, R.id.sub_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_lv:
                selPhoto();
                break;
            case R.id.sex_lv:
                ActionSheetDialogNoTitle();
                break;
            case R.id.area_lv:
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
            case R.id.sub_btn:
                mMProgressDialog.show("正在修改...");
                seldataPresenter.onUpdate(sexTv.getText().toString(),areaTv.getText().toString(),currentFile);
                break;
        }
    }


    private void ActionSheetDialogNoTitle() {
        final String[] stringItems = {"男", "女",};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
               // T.showShort(mContext, stringItems[position]);
                sexTv.setText(stringItems[position]);
                dialog.dismiss();
            }
        });
    }
    private void selPhoto() {
        final String[] stringItems = {"拍照", "从相册选择",};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){//拍照
                    doCamera();
                }else if(position == 1){//从相册选择
                    doSDCardPermission();
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 头像选择
     */
    public void doCamera() {
        outFile = PhotoUtil.openCamera(this,PHOTO_REQUEST_TAKEPHOTO);
    }

    public void doSDCardPermission() {
        PhotoUtil.openPic(this,REQUESTCODE_PICK);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  进行判断是那个操作跳转回来的，如果是裁剪跳转回来的这块就要把图片现实到View上，其他两种的话都把数据带入裁剪界面
        switch (requestCode) {
            //相册
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                Uri uri;
                uri = data.getData();
                PhotoUtil.startCropActivity(this,uri,0);
                break;
            case UCrop.REQUEST_CROP:    // 裁剪图片结果
                if(data != null) {
                    Bitmap bitmap = PhotoUtil.getBitmap(data, this);
                    imageview.setImageBitmap(bitmap);
                    imageview.setVisibility(View.VISIBLE);
                    photoTv.setVisibility(View.GONE);
                    currentFile = PhotoUtil.saveBitmapFile(bitmap);
                }
                break;
            //拍照
            case PHOTO_REQUEST_TAKEPHOTO:
                PhotoUtil.startCropActivity(this,Uri.fromFile(outFile),0);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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


    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+"-"+
                        options2Items.get(options1).get(options2)+"-"+
                        options3Items.get(options1).get(options2).get(options3);
                areaTv.setText(tx);
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
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(UserBean userBean) {
        mMProgressDialog.dismiss();
       if(TextUtils.isEmpty(userBean.getHeadimg())){
           photoTv.setText(CommonUtil.subName(userBean.getName()));
           photoTv.setVisibility(View.VISIBLE);
           imageview.setVisibility(View.GONE);
       }else{
           photoTv.setVisibility(View.GONE);
           imageview.setVisibility(View.VISIBLE);
           Glide.with(this).load(UrlAddress.URLAddress+userBean.getHeadimg()).into(imageview);
           //修改头像信息
           BaseApplication.mSharedPrefUtil.putString(SharedConstants.PHOTO,userBean.getHeadimg());  //photo
           BaseApplication. mSharedPrefUtil.commit();
           //修改融云信息
           RongIM.getInstance().setCurrentUserInfo(new UserInfo(BaseApplication.mSharedPrefUtil.getString(SharedConstants.USERID,""), BaseApplication.mSharedPrefUtil.getString(SharedConstants.NAME,""), Uri.parse(UrlAddress.URLAddress+BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHOTO,""))));
           RongIM.getInstance().setMessageAttachedUserInfo(true);
       }
        nameTv.setText(userBean.getName());
        if(BaseApplication.mSharedPrefUtil.getString(SharedConstants.DEPARTMENT,"").equals("0")){
            bumenTv.setText("");
        }else {
            bumenTv.setText(BaseApplication.mSharedPrefUtil.getString(SharedConstants.DEPARTMENT, ""));
        }
        zhiwuTv.setText(userBean.getJobtitle());
        phoneTv.setText(userBean.getPhone());
        if(!TextUtils.isEmpty(userBean.getSex())) {
            sexTv.setText(userBean.getSex());
        }else{
            sexTv.setText("男");
        }
        if(!TextUtils.isEmpty(userBean.getAddress())) {
            areaTv.setText(userBean.getAddress());
        }
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        //再查询一次
        seldataPresenter.onSelect(BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHONE,""));
        CommonUtil.showToast(this,"修改成功");
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
}
