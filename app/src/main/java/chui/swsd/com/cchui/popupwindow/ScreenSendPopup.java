package chui.swsd.com.cchui.popupwindow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.hhl.library.FlowTagLayout;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.TagAdapter;
import chui.swsd.com.cchui.model.ScreenBean;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DateUtil;

/**
 * 内容：弹出筛选界面
 * Created by 关云秀 on 2017\8\22 0022.
 */

public class ScreenSendPopup extends PopupWindow implements View.OnClickListener{
    PopupWindow popupWindow;
    private TextView startTimeTv,endTimeTv,screenTv,resetTv;
    private Button subBtn;
    private  Context context;
    private TimePickerView  pvCustomTime;
    public ScreenSendPopup(Context context, String startTime, String endTime, String typeStr) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewContent = inflater.inflate(R.layout.activity_screen_send_popup, null);
        startTimeTv = (TextView) viewContent.findViewById(R.id.start_tv);
        endTimeTv = (TextView) viewContent.findViewById(R.id.endtime_tv);
        screenTv = (TextView) viewContent.findViewById(R.id.screen_tv);
        resetTv = (TextView) viewContent.findViewById(R.id.reset_tv);

        subBtn = (Button)viewContent.findViewById(R.id.sub_btn);
        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(viewContent);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(AbsListView.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(AbsListView.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.mystyle);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        initData(startTime,endTime,typeStr);  //初始化数据
        startTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(0);
            }
        });
        endTimeTv.setOnClickListener(this);
        screenTv.setOnClickListener(this);
        resetTv.setOnClickListener(this);
        subBtn.setOnClickListener(this);
    }
    //初始化数据
    public void initData(String startTime,String endTime,String typeStr) {
        if (!TextUtils.isEmpty(startTime)) {
            startTimeTv.setText(startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            endTimeTv.setText(endTime);
        }
        if (!TextUtils.isEmpty(typeStr)){
            screenTv.setText(typeStr);
       }

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.endtime_tv:
                setTime(1);
                break;
            case R.id.screen_tv:
                ActionSheetDialogNoTitle();
                break;
            case R.id.reset_tv:
                startTimeTv.setText("请选择");
                endTimeTv.setText("请选择");
                screenTv.setText("请选择");
                break;
            case R.id.sub_btn:
                long startTime = 0,endTime = 0;

                if(!TextUtils.isEmpty(startTimeTv.getText().toString()) && !startTimeTv.getText().toString().equals("请选择")) {
                     startTime = DateUtil.getDateToSecond(startTimeTv.getText().toString());
                }

                if(!TextUtils.isEmpty(endTimeTv.getText().toString()) && !endTimeTv.getText().toString().equals("请选择")) {
                     endTime = DateUtil.getDateToSecond(endTimeTv.getText().toString());
                }
                if(startTime>endTime){
                    CommonUtil.showToast(context,"结束时间不能小于开始时间");
                    return;
                }
                ScreenBean screenBean = new ScreenBean();
                screenBean.setStartTime(startTimeTv.getText().toString());
                screenBean.setEndTime(endTimeTv.getText().toString());
                screenBean.setTypeStr(screenTv.getText().toString());
                screenBean.setType(1);
                EventBus.getDefault().post(screenBean);
                EventBus.getDefault().unregister(this);//取消注册
                dismiss();
                break;
        }
    }

    /**
     * 选择日报周报月报
     */
    private void ActionSheetDialogNoTitle() {
        final String[] stringItems = {"全部", "日报","周报","月报"};
        final ActionSheetDialog dialog = new ActionSheetDialog(context, stringItems, null);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                screenTv.setText(stringItems[position]);
                dialog.dismiss();
            }
        });
    }

    /****
     * 选择时间
     * @param flag
     */
    public void setTime(final int flag){
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        final DatePicker date = new DatePicker(context);
        date.setCalendarViewShown(false);
        date.init(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        });
        final AlertDialog.Builder mDatePickerDialogBuilder = new AlertDialog.Builder(context);
        mDatePickerDialogBuilder.setView(date);
        mDatePickerDialogBuilder.setTitle("请选择日期");
        mDatePickerDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //只有点击确定按钮时，才更改时间，并设置在文本中显示
                int fromYear = date.getYear();
                int fromMonth = date.getMonth()+1;
                int fromDay = date.getDayOfMonth();
                if(flag == 0){
                    startTimeTv.setText(fromYear + "-" + fromMonth + "-" + fromDay);
                }else {
                    endTimeTv.setText(fromYear + "-" + fromMonth + "-" + fromDay);
                }
            }
        });
        mDatePickerDialogBuilder.setNegativeButton("取消",null);
        mDatePickerDialogBuilder.show();
    }

    @Override
    public void dismiss() {
        EventBus.getDefault().unregister(this);//取消注册
        super.dismiss();
    }
}
