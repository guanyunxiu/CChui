package chui.swsd.com.cchui.ui.apply.shenpi.sp_baoxiao;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhl.library.FlowTagLayout;
import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.BaoXiaoItemAdapter;
import chui.swsd.com.cchui.adapter.BaoXiaoItemAdapter2;
import chui.swsd.com.cchui.adapter.BaoXiaoItemAdapter3;
import chui.swsd.com.cchui.adapter.ImageItemAdapter;
import chui.swsd.com.cchui.adapter.SpTagAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.model.GroupBean;
import chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi.WriteRbActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_qingjia.SpLeaveActivity;
import chui.swsd.com.cchui.ui.apply.yuandl.ContractsActivity;
import chui.swsd.com.cchui.ui.contacts.group.GroupFragment;
import chui.swsd.com.cchui.ui.img_browse.ImagePagerActivity;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\23 0023.
 */

public class SpBaoxiaoActivity extends BaseActivity implements SpBaoxiaoContract.view{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.add_tv)
    TextView addTv;
    @BindView(R.id.flow_layout)
    FlowTagLayout flowTagLayout;
    @BindView(R.id.photo_rlv)
    RecyclerView photoRlv;
    @BindView(R.id.sum_money_tv)
    TextView sumMoneyTv;
    @BindView(R.id.sub_btn)
    Button subBtn;
    /*@BindView(R.id.money_tv)
    TextView moneyTv;*/
    SpTagAdapter tagAdapter;
    ImageItemAdapter imageItemAdapter;
    List<String> list = new ArrayList<>();
    public static int i = 0;
    private ArrayList<String> mSelectPath = new ArrayList<String>();
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    BaoXiaoItemAdapter3 baoXiaoItemAdapter3;
    SpBaoxiaoPresenter spBaoxiaoPresenter;
    List<String> listId = new ArrayList<>();
    List<String> listUser = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_baoxiao;
    }
    @Override
    protected void initViews() {
        initTitle(true, "报销");
        spBaoxiaoPresenter = new SpBaoxiaoPresenter(this,this);
        initAdapter();
        initFlowTag();
    }
    @Override
    protected void updateViews() {
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
                    Intent intent = new Intent(SpBaoxiaoActivity.this,ContractsActivity.class);
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
    public void initAdapter() {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        baoXiaoItemAdapter3 = new BaoXiaoItemAdapter3(this,getList(),sumMoneyTv);
        recyclerview.setAdapter(baoXiaoItemAdapter3);
    }

    public List<BaoXiaoBean> getList() {
        i++;
        List<BaoXiaoBean> list  = new ArrayList<BaoXiaoBean>();
        BaoXiaoBean baoxiaoBean = new BaoXiaoBean();
        baoxiaoBean.setId(i);
        list.add(baoxiaoBean);
        return list;
    }
    @OnClick({R.id.p_sel_tv, R.id.sub_btn,R.id.add_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.p_sel_tv:
                pickImage();
                break;
            case R.id.sub_btn:
                mMProgressDialog = CommonUtil.configDialogDefault(this);
                List<BaoXiaoBean> list = BaoXiaoItemAdapter3.datas;
                for(BaoXiaoBean baoXiaoBean:list){
                   if(baoXiaoBean.getMoney() == 0){
                       CommonUtil.showToast(this,"报销金额不能为0");
                       return;
                   }
                    if(TextUtils.isEmpty(baoXiaoBean.getType())){
                        CommonUtil.showToast(this,"报销类型不能为空");
                        return;
                    }
                }
                if(listId.size() ==0){
                    CommonUtil.showToast(this,"请添加审批人");
                    return;
                }
                mMProgressDialog.show("提交中...");
                spBaoxiaoPresenter.onSubmit(BaoXiaoItemAdapter3.datas,mSelectPath,listUser);

                break;
            case R.id.add_tv:
                List listDetails2 = BaoXiaoItemAdapter3.datas;
                listDetails2.addAll(getList());
                baoXiaoItemAdapter3 = new BaoXiaoItemAdapter3(this,listDetails2,sumMoneyTv);
                recyclerview.setAdapter(baoXiaoItemAdapter3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){//发送人返回数据
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
        if(requestCode == REQUEST_IMAGE){//图片返回数据
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                photoRlv.setHasFixedSize(true);
                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                photoRlv.setLayoutManager(linearLayoutManager);
                imageItemAdapter = new ImageItemAdapter(this,mSelectPath);
                imageItemAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                photoRlv.setAdapter(imageItemAdapter);
                //mResultText.setText(sb.toString());
                imageItemAdapter.setOnItemClickListener(new ImageItemAdapter.OnItemClickListener() {
                    @Override
                    public void onDelItemClicked(String str) {
                        mSelectPath.remove(str);
                        imageItemAdapter.setNewData(mSelectPath);
                    }
                });
                imageItemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        //noticeAdapter.getItem(i);
                       /* mSelectPath.remove(i);
                        imageItemAdapter.setNewData(mSelectPath);*/
                        Intent intent = new Intent(SpBaoxiaoActivity.this, ImagePagerActivity.class);
                        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, mSelectPath);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,i);
                        SpBaoxiaoActivity.this.startActivity(intent);
                    }
                });
            }
        }
    }
    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }else {
            MultiImageSelector selector = MultiImageSelector.create(this);
            selector.showCamera(true);
            selector.count(9);
            selector.multi();
            selector.origin(mSelectPath);
            selector.start(this, REQUEST_IMAGE);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SpBaoxiaoActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
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
