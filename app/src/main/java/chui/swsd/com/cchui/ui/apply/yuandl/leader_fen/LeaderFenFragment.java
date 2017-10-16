package chui.swsd.com.cchui.ui.apply.yuandl.leader_fen;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.ChaFenItemAdapter;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.utils.DateUtil;
import chui.swsd.com.cchui.widget.LoadStateManager;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\18 0018.
 */

public class LeaderFenFragment extends BaseFragment implements LeaderFenContract.view {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.time_tv)
    TextView timeTv;
    ChaFenItemAdapter chaFenItemAdapter;
    @BindView(R.id.time_lv)
    LinearLayout timeLv;
    private String departname;
    LeaderFenPresenter leaderFenPresenter;
    List<YdlScoreBean> depConBeanList = new ArrayList<>();
    private TimePickerView  pvCustomTime;
    public static LeaderFenFragment newInstance(String departname) {
        LeaderFenFragment fragment = new LeaderFenFragment();
        Bundle bundle = new Bundle();
        bundle.putString("departname", departname);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_chafen;
    }

    @Override
    protected void initViews() {
        departname = getArguments().getString("departname");
        leaderFenPresenter = new LeaderFenPresenter(this, mContext);
        setAdapter();
        initCustomTimePicker();
    }

    public void setAdapter() {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        chaFenItemAdapter = new ChaFenItemAdapter(depConBeanList);
        chaFenItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyclerview.setAdapter(chaFenItemAdapter);
    }

    @Override
    protected void updateViews() {
        timeTv.setText(DateUtil.beforeTime());
        leaderFenPresenter.onSelectDepCon(departname,DateUtil.beforeTime());

    }


    @Override
    public void onSuccess(List<DepartmentBean> list) {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSuccessCon(List<YdlScoreBean> depconList) {
        if (depconList.size() > 0) {
            mLoadStateManager.setState(LoadStateManager.LoadState.Success);
            chaFenItemAdapter.setNewData(depconList);
        } else {
            mLoadStateManager.setState(LoadStateManager.LoadState.NoData);
        }
    }

    @OnClick(R.id.time_lv)
    public void onClick() {
        pvCustomTime.show();
    }
    /**
     * 初始化时间
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR)-2, selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                    timeTv.setText(DateUtil.getTime4(date));
                    leaderFenPresenter.onSelectDepCon(departname, DateUtil.getTime4(date));
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
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
}
