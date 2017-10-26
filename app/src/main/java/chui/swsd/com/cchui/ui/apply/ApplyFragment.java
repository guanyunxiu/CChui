package chui.swsd.com.cchui.ui.apply;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.ui.apply.file.FileManagerActivity;
import chui.swsd.com.cchui.ui.apply.gonggao.GongGaoActivity;
import chui.swsd.com.cchui.ui.apply.huiyi.HuiYiMainActivity;
import chui.swsd.com.cchui.ui.apply.kaoqin.KaoQinActivity;
import chui.swsd.com.cchui.ui.apply.rizhi.RrZhiActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.ShenPiActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.YuanDLActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 应用
 * Created by Administrator on 2017\8\7 0007.
 */

public class ApplyFragment extends BaseFragment {
    @BindView(R.id.kqdk_tv)
    TextView kqdkTv;
    @BindView(R.id.ydl_tv)
    TextView ydlTv;
    @BindView(R.id.rizhi_tv)
    TextView rizhiTv;
    @BindView(R.id.gonggao_tv)
    TextView gonggaoTv;
    @BindView(R.id.shenpi_tv)
    TextView shenpiTv;
    @BindView(R.id.hyyy_tv)
    TextView hyyyTv;
    @BindView(R.id.file_manager_tv)
    TextView fileManagerTv;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_apply;
    }

    @Override
    protected void initViews() {
        //initTitle(false, "应用");
        titleName.setText("应用");
    }

    @Override
    protected void updateViews() {
        //Toast.makeText(mContext,"应用",Toast.LENGTH_SHORT).show();
    }


    @OnClick({R.id.kqdk_tv, R.id.ydl_tv, R.id.rizhi_tv, R.id.gonggao_tv, R.id.shenpi_tv, R.id.hyyy_tv, R.id.file_manager_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kqdk_tv:
                startActivity(new Intent(getActivity(), KaoQinActivity.class));
                break;
            case R.id.ydl_tv:
                startActivity(new Intent(getActivity(), YuanDLActivity.class));
               // CommonUtil.showToast(mContext,"暂未开放");
                break;
            case R.id.rizhi_tv:
                startActivity(new Intent(getActivity(), RrZhiActivity.class));
                break;
            case R.id.gonggao_tv:
                startActivity(new Intent(getActivity(), GongGaoActivity.class));
                break;
            case R.id.shenpi_tv:
                startActivity(new Intent(getActivity(), ShenPiActivity.class));
                break;
            case R.id.hyyy_tv:
                startActivity(new Intent(getActivity(), HuiYiMainActivity.class));
                break;
            case R.id.file_manager_tv:
                startActivity(new Intent(getActivity(), FileManagerActivity.class));
                break;
        }
    }
}
