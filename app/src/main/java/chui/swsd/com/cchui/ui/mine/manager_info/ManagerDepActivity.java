package chui.swsd.com.cchui.ui.mine.manager_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.maning.mndialoglibrary.MProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.DepItemAdapter;
import chui.swsd.com.cchui.adapter.GroupAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.CompanyDetailBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.ui.apply.gonggao.GongGaoActivity;
import chui.swsd.com.cchui.ui.apply.gonggao.ReleaseGongGaoActivity;
import chui.swsd.com.cchui.ui.contacts.group.GroupPrecenter;
import chui.swsd.com.cchui.ui.group.GroupDetails;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.DialogWithYesOrNoUtils;
import io.rong.imkit.emoticon.AndroidEmoji;

/**
 * 内容：//查询部门
 * Created by 关云秀 on 2017\9\19 0019.
 */

public class ManagerDepActivity extends BaseActivity implements ManagerComContract.view{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    ManagerComPresenter managerComPresenter;
    DepItemAdapter depItemAdapter;
    List<DepartmentBean> beanList = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_manager_dep;
    }

    @Override
    protected void initViews() {
        initTitle(true,"部门列表");
        managerComPresenter = new ManagerComPresenter(this,this);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        depItemAdapter = new DepItemAdapter(beanList);
        recyclerview.setAdapter(depItemAdapter);
    }

    @Override
    protected void updateViews() {
        managerComPresenter.onSelectDep(BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0));
        depItemAdapter.setOnItemClickListener(new DepItemAdapter.OnItemClickListener() {
            @Override
            public void onDelItemClicked(final DepartmentBean departmentBean) {
                DialogWithYesOrNoUtils.getInstance().showEditDialog(ManagerDepActivity.this, getString(R.string.my_40), getString(R.string.confirm), new DialogWithYesOrNoUtils.DialogCallBack() {
                    @Override
                    public void executeEvent() {
                    }
                    @Override
                    public void executeEditEvent(String editText) {
                        if (TextUtils.isEmpty(editText)) {
                            CommonUtil.showToast(ManagerDepActivity.this,"部门名称不能为空");
                            return;
                        }
                        mMProgressDialog = CommonUtil.configDialogDefault(ManagerDepActivity.this);
                        mMProgressDialog.show("正在提交...");
                        managerComPresenter.onUpdateDep(departmentBean.getId(),editText);
                    }
                    @Override
                    public void updatePassword(String oldPassword, String newPassword) {

                    }
                });
            }
        });
    }

    @Override
    public void onSuccess(CompanyDetailBean companyDetailBean) {

    }

    @Override
    public void onSuccess(List<DepartmentBean> departmentBeanList) {
        depItemAdapter.setNewData(departmentBeanList);
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"提交成功");
        managerComPresenter.onSelectDep(BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0));
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_dep,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add_dep:
                DialogWithYesOrNoUtils.getInstance().showEditDialog(ManagerDepActivity.this, getString(R.string.my_40), getString(R.string.confirm), new DialogWithYesOrNoUtils.DialogCallBack() {
                    @Override
                    public void executeEvent() {
                    }
                    @Override
                    public void executeEditEvent(String editText) {
                        if (TextUtils.isEmpty(editText)) {
                            CommonUtil.showToast(ManagerDepActivity.this,"部门名称不能为空");
                            return;
                        }
                        mMProgressDialog = CommonUtil.configDialogDefault(ManagerDepActivity.this);
                        mMProgressDialog.show("正在提交...");
                        managerComPresenter.onAddDep(editText);
                    }
                    @Override
                    public void updatePassword(String oldPassword, String newPassword) {

                    }
                });
                break;
        }
        return true;
    }
}
