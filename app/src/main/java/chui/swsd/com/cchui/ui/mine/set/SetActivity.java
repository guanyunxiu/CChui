package chui.swsd.com.cchui.ui.mine.set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.maning.mndialoglibrary.MProgressDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.receiver.JpushReceiver;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.ui.login.ManagerLoginActivity;
import chui.swsd.com.cchui.ui.register.RigsterThreeActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.DataCleanManager;
import cn.jpush.android.api.JPushInterface;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\14 0014.
 */

public class SetActivity extends BaseSwipeBackActivity implements UpdatePassContract.view{
    @BindView(R.id.update_tv)
    TextView updateTv;
    @BindView(R.id.fankui_tv)
    TextView fankuiTv;
    @BindView(R.id.bbgx_tv)
    TextView bbgxTv;
    @BindView(R.id.clear_tv)
    TextView clearTv;
    @BindView(R.id.exit_btn)
    Button exitBtn;
    @BindView(R.id.hc_size_tv)
    TextView hcSizeTv;
    @BindView(R.id.manager_tv)
    TextView managerTv;
    @BindView(R.id.manager_lv)
    LinearLayout managerLv;
    UpdatePassPresenter updatePassPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViews() {
        initTitle(true, "设置");
        updatePassPresenter = new UpdatePassPresenter(this,this);
        int account = BaseApplication.mSharedPrefUtil.getInt(SharedConstants.MANAGERFLAG,0);
        if(account == 0){//普通用户
            managerLv.setVisibility(View.GONE);
        }else{ //1为管理员
            managerLv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void updateViews() {
        getCache();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @OnClick({R.id.update_tv, R.id.fankui_tv, R.id.bbgx_tv, R.id.clear_lv, R.id.exit_btn,R.id.manager_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_tv:
                startActivity(new Intent(SetActivity.this,UpdatePassActivity.class));
                break;
            case R.id.fankui_tv:
                startActivity(new Intent(SetActivity.this,FeedBackActivity.class));
                break;
            case R.id.bbgx_tv:
                break;
            case R.id.clear_lv:
                cleanCache();
                break;
            case R.id.exit_btn:
                NormalDialogStyleOne();
                break;
            case R.id.manager_tv:
                startActivity(new Intent(this, ManagerLoginActivity.class));
                break;
        }
    }
    /*
   * 获取缓存
   * */
    public void getCache() {
        String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        hcSizeTv.setText(totalCacheSize);
    }
    private void NormalDialogStyleOne() {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false).content("是否确定退出程序?")//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {//取消
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {//确定
                        mMProgressDialog = CommonUtil.configDialogDefault(SetActivity.this);
                        mMProgressDialog.show("正在退出...");
                        updatePassPresenter.onLogot();//用户退出
                        dialog.dismiss();
                    }
                });
    }
    /*
   * 清理缓存
   * */
    public void cleanCache() {
        DataCleanManager.clearAllCache(this);
        Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
        getCache();
    }
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        BaseApplication.removeActivity();
        BaseApplication.mSharedPrefUtil.clear();
        JPushInterface.stopPush(SetActivity.this);//停止推送
        startActivity(new Intent(SetActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
}
