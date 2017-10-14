package chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import chui.swsd.com.cchui.adapter.GroupAdapter;
import chui.swsd.com.cchui.adapter.ImageItemAdapter;
import chui.swsd.com.cchui.adapter.TagAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.ui.img_browse.ImagePagerActivity;
import chui.swsd.com.cchui.ui.work.make_meet.MakeMeetActivity;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * 内容：写日报
 * Created by 关云秀 on 2017\8\18 0018.
 */

public class WriteRbActivity extends BaseSwipeBackActivity implements WriteRzContract.view{
    @BindView(R.id.flow_layout)
    FlowTagLayout flowTagLayout;
    @BindView(R.id.wcgz_et)
    EditText wcgzEt;
    @BindView(R.id.wgz_tv)
    EditText wgzTv;
    @BindView(R.id.xietiao_et)
    EditText xietiaoEt;
    @BindView(R.id.beizhu_et)
    EditText beizhuEt;
    @BindView(R.id.p_sel_tv)
    TextView pSelTv;
    @BindView(R.id.photo_rlv)
    RecyclerView photoRlv;
    @BindView(R.id.sub_btn)
    Button subBtn;
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private ArrayList<String> mSelectPath = new ArrayList<String>();
    ImageItemAdapter imageItemAdapter;
    TagAdapter tagAdapter;
    List<Node> lists = new ArrayList<>();
    WriteRzPresenter writeRzPresenter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_write_rb;
    }

    @Override
    protected void initViews() {
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        initTitle(true, "日报");
        writeRzPresenter = new WriteRzPresenter(this,this);

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
                    Intent intent = new Intent(WriteRbActivity.this, SelectPeopleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) mDataList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    // startActivity(new Intent(MakeMeetActivity.this, SelectPeopleActivity.class),bundle);
                }else{
                    mDataList.remove(position);
                    tagAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.p_sel_tv, R.id.sub_btn,R.id.add_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.p_sel_tv:
                pickImage();
                break;
            case R.id.sub_btn:
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
        String complish = wcgzEt.getText().toString();
        String unfish = wgzTv.getText().toString();
        String coordinate = xietiaoEt.getText().toString();
        String remark = beizhuEt.getText().toString();
        if(TextUtils.isEmpty(complish)){
            CommonUtil.showToast(this,"今日完成工作不能为空");
            return;
        }
        if(lists.size() == 1){//最后一个为空
            CommonUtil.showToast(this,"发送给谁？");
            return;
        }
        List<String> listId = new ArrayList<>();
        for(int i=0;i<lists.size()-1;i++){
            listId.add(lists.get(i).getId()+"and"+lists.get(i).getName());
        }
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("提交中...");
        writeRzPresenter.onSubmit(listId,complish,unfish,"",coordinate,remark,0,mSelectPath);
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
                            ActivityCompat.requestPermissions(WriteRbActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
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
                        Intent intent = new Intent(WriteRbActivity.this, ImagePagerActivity.class);
                        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, mSelectPath);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,i);
                        WriteRbActivity.this.startActivity(intent);
                    }
                });
            }
        }
    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(List<Node> list){
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
        CommonUtil.showToast(this,"发送成功");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"发送失败");
        finish();
    }
}
