package chui.swsd.com.cchui.ui.apply.file.release_file;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.hhl.library.FlowTagLayout;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
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
import chui.swsd.com.cchui.adapter.FileSelectItemAdapter;
import chui.swsd.com.cchui.adapter.ImageItemAdapter;
import chui.swsd.com.cchui.adapter.TagAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.model.FileDetailsBean;
import chui.swsd.com.cchui.model.FileManagerBean;
import chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi.WriteRbActivity;
import chui.swsd.com.cchui.ui.img_browse.ImagePagerActivity;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.ui.mine.set.SetActivity;
import chui.swsd.com.cchui.ui.work.make_meet.MakeMeetActivity;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import cn.jpush.android.api.JPushInterface;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\30 0030.
 */

public class ReleaseFileActivity extends BaseActivity implements ReleaseFileContract.view{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_tv)
    EditText titleTv;
    @BindView(R.id.add_fj_tv)
    TextView addFjTv;
    @BindView(R.id.flow_layout)
    FlowTagLayout flowTagLayout;
    @BindView(R.id.sub_btn)
    Button subBtn;
    @BindView(R.id.fj_recyclerview)
    RecyclerView mRecyclerview;
    TagAdapter tagAdapter;
    List<Node> lists = new ArrayList<>();
    FileSelectItemAdapter fileSelectItemAdapter;
    private List<String> fileList = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    ReleaseFilePresenter releaseFilePresenter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_release_file;
    }

    @Override
    protected void initViews() {
        initTitle(true,"发布文件");
        releaseFilePresenter = new ReleaseFilePresenter(this,this);
        EventBus.getDefault().register(this);
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
                    Intent intent = new Intent(ReleaseFileActivity.this, SelectPeopleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) mDataList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    // startActivity(new Intent(MakeMeetActivity.this, SelectPeopleActivity.class),bundle);
                }else{
                    mDataList.remove(position);
                    lists.remove(position);
                    tagAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.add_fj_tv, R.id.sub_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_fj_tv:
                if(fileList.size()<=8) {
                    new LFilePicker()
                            .withActivity(this)
                            .withRequestCode(Constants.REQUESTCODE_FROM_ACTIVITY)
                            .withTitle("选择文件")
                            .withBackgroundColor("#0485f3")
                            .withMutilyMode(true)
                            .withFileFilter(new String[]{"txt", "docx","doc","xlsx","pdf","ppt","pptx"})
                            .start();
                }else{
                    CommonUtil.showToast(this,"一次最多只能发送8个文件");
                }
                break;
            case R.id.sub_btn:
                onSubmit();
                break;
        }
    }
    public void onSubmit(){
        String title = titleTv.getText().toString();
        if(TextUtils.isEmpty(title)){
            CommonUtil.showToast(this,"文件标题不能为空");
            return;
        }
        if(fileList.size() == 0){
            CommonUtil.showToast(this,"请选择要发布的文件");
            return;
        }
        if(lists.size()==1){
            CommonUtil.showToast(this,"接收人不能为空");
            return;
        }
        if(fileList.size()>8){
            CommonUtil.showToast(this,"一次最多只能上传8个文件");
            return;
        }
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("提交中...");
        List<String> listId = new ArrayList<>();
        for(int i=0;i<lists.size()-1;i++){
            listId.add(lists.get(i).getId()+"");
        }

        releaseFilePresenter.onSubmit(title,listId,fileList);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUESTCODE_FROM_ACTIVITY) {
                List<String> list = data.getStringArrayListExtra("paths");
                if(list.size()<=8) {
                    fileList.addAll(list);
                    mRecyclerview.setHasFixedSize(true);
                    //设置布局管理器
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mRecyclerview.setLayoutManager(linearLayoutManager);
                    fileSelectItemAdapter = new FileSelectItemAdapter(this, fileList);
                    fileSelectItemAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                    mRecyclerview.setAdapter(fileSelectItemAdapter);
                    fileSelectItemAdapter.setOnItemClickListener(new FileSelectItemAdapter.OnItemClickListener() {
                        @Override
                        public void onDelItemClicked(String str) {
                            NormalDialogStyleOne(str);
                        }
                    });
                    fileSelectItemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int i) {
                            Intent intent = CommonUtil.getWordFileIntent(fileList.get(i));
                            startActivity(intent);
                        }
                    });
                }else{
                    CommonUtil.showToast(this,"一次最多只能上传8个文件，请重新选择文件");
                }
            }
        }
    }
    private void NormalDialogStyleOne(final String str) {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false).content("是否确定删除?")//
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
                        fileList.remove(str);
                        fileSelectItemAdapter.setNewData(fileList);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"提交成功");
        EventBus.getDefault().post("文件查询");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }

    @Override
    public void onSuccess(List<FileManagerBean> fileManagerBeanList) {

    }

    @Override
    public void onSuccess(FileDetailsBean fileDetailsBean) {

    }
}
