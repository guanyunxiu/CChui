package chui.swsd.com.cchui.ui.mine.my_jcxx;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.ui.apply.yuandl.update_fen.UpdateJcFenActivity;
import chui.swsd.com.cchui.ui.group.GroupDetails;
import chui.swsd.com.cchui.ui.mine.my_info.SeldataContract;
import chui.swsd.com.cchui.ui.mine.my_info.SeldataPresenter;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：基础信息内容
 * Created by 关云秀 on 2017\8\15 0015.
 */

public class JcxxActivity extends BaseSwipeBackActivity implements SeldataContract.view {
    @BindView(R.id.xueli_tv)
    TextView xueliTv;
    @BindView(R.id.nike_lv)
    LinearLayout nikeLv;
    @BindView(R.id.zhicheng_tv)
    TextView zhichengTv;
    @BindView(R.id.jineng_tv)
    TextView jinengTv;
    @BindView(R.id.techeng_tv)
    TextView techengTv;
    @BindView(R.id.level_tv)
    TextView levelTv;
    @BindView(R.id.gongling_tv)
    TextView gonglingTv;
    SeldataPresenter seldataPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_jcxx;
    }

    @Override
    protected void initViews() {
        initTitle(true, "基础信息");
    }

    @Override
    protected void updateViews() {
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("正在查询...");
        seldataPresenter = new SeldataPresenter(this,this);
        seldataPresenter.onSelect(BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHONE,""));
    }
    public void initData(UserBean userBean){
        xueliTv.setText(userBean.getEducation()+"");
        zhichengTv.setText(userBean.getJobtitle()+"");
        jinengTv.setText(userBean.getSkill()+"");
        //techengTv.setText(userBean.getForte()+"");
        levelTv.setText(userBean.getLevel());
        gonglingTv.setText(userBean.getUsedtime());
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

    @Override
    public void onSuccess(UserBean userBean) {
        mMProgressDialog.dismiss();
        if(userBean != null) {
            initData(userBean);
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_update_info:
                startActivity(new Intent(this,UpdateJcFenActivity.class));
                return true;
            case R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
