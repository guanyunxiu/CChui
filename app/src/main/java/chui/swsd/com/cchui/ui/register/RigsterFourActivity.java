package chui.swsd.com.cchui.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.BaoXiaoItemAdapter3;
import chui.swsd.com.cchui.adapter.DepartmentItemAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.ui.WelcomeActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\18 0018.
 */

public class RigsterFourActivity extends BaseActivity implements RegisterContract.View{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.add_tv)
    TextView addTv;
    @BindView(R.id.sub_btn)
    Button subBtn;
    DepartmentItemAdapter departmentItemAdapter;
    public static int i = 0;
    RegisterPresenter registerPresenter;
    private int cid; //公司ID
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_zc_department;
    }

    @Override
    protected void initViews() {
        initTitle(true,"添加部门");
        cid = getIntent().getIntExtra("cid",0);
        registerPresenter = new RegisterPresenter(this,this);
        initAdapter();
    }

    @Override
    protected void updateViews() {

    }
    public void initAdapter() {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        departmentItemAdapter = new DepartmentItemAdapter(this,getList());
        recyclerview.setAdapter(departmentItemAdapter);
    }

    public List<DepartmentBean> getList() {
        i++;
        List<DepartmentBean> list  = new ArrayList<DepartmentBean>();
        DepartmentBean baoxiaoBean = new DepartmentBean();
        baoxiaoBean.setId(i);
        list.add(baoxiaoBean);
        return list;
    }
    @OnClick({R.id.add_tv, R.id.sub_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_tv:
                List listDetails2 = DepartmentItemAdapter.datas;
                listDetails2.addAll(getList());
                departmentItemAdapter = new DepartmentItemAdapter(this,listDetails2);
                recyclerview.setAdapter(departmentItemAdapter);
                break;
            case R.id.sub_btn:
                List<DepartmentBean> list = departmentItemAdapter.datas;
                for(DepartmentBean departmentBean:list){
                    if(TextUtils.isEmpty(departmentBean.getName())){
                        CommonUtil.showToast(this,"部门名称不能为空");
                        return;
                    }
                }
                mMProgressDialog = CommonUtil.configDialogDefault(this);
                mMProgressDialog.show("正在提交...");
                registerPresenter.onThreeSubmit(cid+"",list);
                break;
        }
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"提交成功");
        if( Constants.judgeFlag == 0){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            if( Constants.judgeFen){
                startActivity(new Intent(this, MainActivity.class));
            }else{
                startActivity(new Intent(this, UpdateJcFenActivity.class));
            }
        }

        finish();
    }

    @Override
    public void onSuccess(int id) {

    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }

    @Override
    public void onSuccess(List<CompanyBean> companyBeanList) {

    }
}
