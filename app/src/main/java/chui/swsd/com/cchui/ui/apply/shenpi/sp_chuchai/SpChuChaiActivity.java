package chui.swsd.com.cchui.ui.apply.shenpi.sp_chuchai;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.hhl.library.FlowTagLayout;
import com.maning.mndialoglibrary.MProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.SpTagAdapter;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_qingjia.SpLeaveActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.ContractsActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\23 0023.
 */

public class SpChuChaiActivity extends BaseSwipeBackActivity implements SpChuChaiContract.view{
    @BindView(R.id.address_tv)
    EditText addressEt;
    @BindView(R.id.start_tv)
    TextView startTv;
    @BindView(R.id.start_lv)
    LinearLayout startLv;
    @BindView(R.id.end_tv)
    TextView endTv;
    @BindView(R.id.end_lv)
    LinearLayout endLv;
    @BindView(R.id.nums_tv)
    EditText numsTv;
    @BindView(R.id.reason_tv)
    EditText reasonTv;
    @BindView(R.id.flow_layout)
    FlowTagLayout flowTagLayout;
    @BindView(R.id.sub_btn)
    Button subBtn;
    private TimePickerView pvCustomTime;
    private int timeFlag = 0;
    SpTagAdapter tagAdapter;
    SpChuChaiPresenter spChuChaiPresenter;
    List<String> list = new ArrayList<>();
    List<String> listId = new ArrayList<>();
    List<String> listUser = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_chuchai;
    }

    @Override
    protected void initViews() {
        initTitle(true, "出差");
        spChuChaiPresenter = new SpChuChaiPresenter(this,this);
        initCustomTimePicker();//初始化时间
        initFlowTag();
    }

    @Override
    protected void updateViews() {

    }
    @OnClick({R.id.start_lv, R.id.end_lv,R.id.sub_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_lv:
                timeFlag = 0;
                pvCustomTime.show();  //显示开会时间
                break;
            case R.id.end_lv:
                timeFlag = 1;
                pvCustomTime.show();  //显示开会时间
                break;
            case R.id.sub_btn:
                submit();
                break;
        }
    }
    public void initFlowTag(){
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new SpTagAdapter(this);
        flowTagLayout.setAdapter(tagAdapter);
        list.add("aaa");
        tagAdapter.onlyAddAll(list);
        tagAdapter.setOnItemClickListener(new SpTagAdapter.OnItemClickListener() {
            @Override
            public void onAddItemClicked(int position,List<String> mDataList) {
                if(position == mDataList.size()-1) {
                    Intent intent = new Intent(SpChuChaiActivity.this,ContractsActivity.class);
                    intent.putExtra("typeFlag",1);
                    startActivityForResult(intent,0);
                }else{
                    list.remove(position);
                    listUser.remove(position);
                    listId.remove(position);
                    mDataList.remove(position);
                    tagAdapter.notifyDataSetChanged();
                }

            }
        });
    }
    /***
     * 提交
     */
    public void submit(){
        long startTime = 0,endTime = 0;
        if(TextUtils.isEmpty(addressEt.getText().toString())){
            CommonUtil.showToast(this,"地址不能为空");
            return;
        }
        if(startTv.getText().toString().equals("请选择")){
            CommonUtil.showToast(this,"请选择开始时间");
            return;
        }
        if(endTv.getText().toString().equals("请选择")){
            CommonUtil.showToast(this,"请选择结束时间");
            return;
        }
        if(TextUtils.isEmpty(numsTv.getText().toString())){
            CommonUtil.showToast(this,"请填写出差天数");
            return;
        }
        if(TextUtils.isEmpty(reasonTv.getText().toString())){
            CommonUtil.showToast(this,"请填写出差事由");
            return;
        }
        if(list.size() == 1){
            CommonUtil.showToast(this,"请选择审批人");
            return;
        }
        if(!TextUtils.isEmpty(startTv.getText().toString()) && !startTv.getText().toString().equals("请选择")) {
            startTime = DateUtil.getDateToSecond2(startTv.getText().toString());
        }

        if(!TextUtils.isEmpty(endTv.getText().toString()) && !endTv.getText().toString().equals("请选择")) {
            endTime = DateUtil.getDateToSecond2(endTv.getText().toString());
        }
        if(startTime>endTime){
            CommonUtil.showToast(this,"结束时间不能小于开始时间");
            return;
        }
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("提交中...");
        spChuChaiPresenter.onSubmit(addressEt.getText().toString(),startTv.getText().toString(),endTv.getText().toString(),numsTv.getText().toString(),reasonTv.getText().toString(),listUser);
    }

    /**
     * 初始化时间
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH),selectedDate.get(Calendar.HOUR_OF_DAY), selectedDate.get(Calendar.MINUTE));
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR)+1, selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (timeFlag == 0) {
                    startTv.setText(DateUtil.getTime1(date));
                }else{
                    endTv.setText(DateUtil.getTime1(date));
                }
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
                .setType(new boolean[]{true, true, true, true, false, false})
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(data != null) {
                String name = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                boolean flag = false;//遍历判断listId中是否有返回的ID
                for(String str:listId){
                    if(str.equals(id)){
                        flag = true;
                        break;
                    }
                }
                if(!flag) {
                    int idss = Integer.parseInt(id);
                    if(idss == BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)){
                        CommonUtil.showToast(this,"审批人不能为自己");
                    }else {
                        listId.add(id);
                        listUser.add(id + "and" + name + "and" + "photo");
                        list.remove("aaa");
                        list.add(name);
                        list.add("aaa");
                        tagAdapter.clearAndAddAll();
                        tagAdapter.onlyAddAll(list);
                    }
                }else{
                    CommonUtil.showToast(this,"审批人已在列表中");
                }
            }
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
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
