package chui.swsd.com.cchui.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhl.library.FlowTagLayout;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.FileAdapter;
import chui.swsd.com.cchui.adapter.YdlPopupAdapter;
import chui.swsd.com.cchui.model.FileManagerBean;
import chui.swsd.com.cchui.model.YdlTitleBean;
import chui.swsd.com.cchui.model.YdlValueBean;
import chui.swsd.com.cchui.ui.apply.file.FileDetailActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\30 0030.
 */

public class YdlPopup extends PopupWindow{
    RecyclerView recyclerView;
    YdlPopupAdapter ydlPopupAdapter;
    List<String> list = new ArrayList<>();
    public YdlPopup(final Context context, final List<YdlTitleBean> list, final int flagTitle) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewContent = inflater.inflate(R.layout.activity_ydl_popupwindow, null);
        recyclerView = viewContent.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ydlPopupAdapter = new YdlPopupAdapter(list);
        recyclerView.setAdapter(ydlPopupAdapter);
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
        ydlPopupAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                YdlValueBean ydlValueBean = new YdlValueBean();
                ydlValueBean.setFlagTitle(flagTitle);
                ydlValueBean.setYdlTitleBean(list.get(i));
                EventBus.getDefault().post(ydlValueBean);
                dismiss();
            }
        });
    }
}
