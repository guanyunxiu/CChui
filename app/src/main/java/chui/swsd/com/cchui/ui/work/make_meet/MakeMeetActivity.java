package chui.swsd.com.cchui.ui.work.make_meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhl.library.FlowTagLayout;
import com.hhl.library.OnTagSelectListener;
import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.GroupAdapter;
import chui.swsd.com.cchui.adapter.SelectPeopleAdapter;
import chui.swsd.com.cchui.adapter.TagAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\10 0010.
 */

public class MakeMeetActivity extends BaseActivity implements MakeMeetContract.view{
    @BindView(R.id.flow_layout)
    FlowTagLayout flowTagLayout;
    @BindView(R.id.hyzt_name)
    EditText hyztName;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.sub_btn)
    Button subBtn;
    private String TAG = "MakeMeetActivity";
    private TimePickerView  pvCustomTime;
    private OptionsPickerView pvNoLinkOptions;
    TagAdapter tagAdapter;
    List<Node> lists = new ArrayList<>();
    MakeMeetPresenter makeMeetPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_make_meet;
    }

    @Override
    protected void initViews() {
        initTitle(true,"预约开会");
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        makeMeetPresenter = new MakeMeetPresenter(this,this);
        initCustomTimePicker();//初始化开始时间选择器
        initNoLinkOptionsPicker();//初始化预估时间选择器
    }

    @Override
    protected void updateViews() {
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagAdapter(this);
        flowTagLayout.setAdapter(tagAdapter);
        lists.add(new Node());
        tagAdapter.onlyAddAll(lists);
        tagAdapter.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
            @Override
            public void onAddItemClicked(int position,List<Node> mDataList) {
                if(position == mDataList.size()-1) {
                    Intent intent = new Intent(MakeMeetActivity.this, SelectPeopleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) mDataList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                   // startActivity(new Intent(MakeMeetActivity.this, SelectPeopleActivity.class),bundle);
                }else{
                    mDataList.remove(position);
                    lists.remove(position);
                    tagAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.start_time_tv, R.id.time_tv, R.id.sub_btn,R.id.add_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_time_tv:
                pvCustomTime.show();  //显示开会时间
                break;
            case R.id.time_tv:
                pvNoLinkOptions.show(); //显示开会时间段
                break;
            case R.id.sub_btn:
                mMProgressDialog = CommonUtil.configDialogDefault(this);
                mMProgressDialog.show("提交中...");
                onSubmit();
                break;
            case R.id.add_img:
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) lists);
                startActivity(new Intent(this, SelectPeopleActivity.class),bundle);
                break;
        }
    }
    public void onSubmit(){
        String title = hyztName.getText().toString();
        String startTime = startTimeTv.getText().toString();
        String userTime = timeTv.getText().toString();
        if(TextUtils.isEmpty(title)){
            CommonUtil.showToast(this,"标题不能为空");
            return;
        }
        if(TextUtils.isEmpty(startTime)){
            CommonUtil.showToast(this,"开始时间不能为空");
            return;
        }
        if(TextUtils.isEmpty(userTime)){
            CommonUtil.showToast(this,"预估时间不能为空");
            return;
        }
        if(lists.size() == 1){
            CommonUtil.showToast(this,"请选择参会人员");
            return;
        }
        List<String> listId = new ArrayList<>();
        for(int i=0;i<lists.size()-1;i++){
            listId.add(lists.get(i).getId()+"");
        }
        makeMeetPresenter.onSubmit(title,startTime,userTime,listId);
    }
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
                startTimeTv.setText(DateUtil.getTime(date));
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
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
    private void initNoLinkOptionsPicker() {// 不联动的多级选项
        final List<String> list = new ArrayList<>();
        list.add("10分钟");
        list.add("20分钟");
        list.add("30分钟");
        list.add("40分钟");
        list.add("50分钟");
        list.add("60分钟");
        list.add("1小时-2小时之间");
        list.add("2小时以上");
        pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                timeTv.setText(list.get(options1));
            }
        }).build();
        pvNoLinkOptions.setPicker(list);
    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(List<Node> list){
      /*  flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagAdapter(this);
        flowTagLayout.setAdapter(tagAdapter);*/
        lists = list;
        list.add(new Node());

        tagAdapter.clearAndAddAll();

        tagAdapter.onlyAddAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"发布成功");
        EventBus.getDefault().post("会议查询");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
}
