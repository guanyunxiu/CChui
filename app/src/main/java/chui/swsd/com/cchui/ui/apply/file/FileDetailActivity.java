package chui.swsd.com.cchui.ui.apply.file;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.maning.mndialoglibrary.MProgressDialog;

import org.xutils.common.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.FileSelectItemAdapter;
import chui.swsd.com.cchui.adapter.FileSelectItemAdapter2;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.model.FileDetailsBean;
import chui.swsd.com.cchui.model.FileManagerBean;
import chui.swsd.com.cchui.net.FileDown;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.apply.file.release_file.ReleaseFileContract;
import chui.swsd.com.cchui.ui.apply.file.release_file.ReleaseFilePresenter;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\22 0022.
 */

public class FileDetailActivity extends BaseActivity implements ReleaseFileContract.view{
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    FileSelectItemAdapter2 fileSelectItemAdapter2;
    ReleaseFilePresenter releaseFilePresenter;
    private int id;
    List<FileDetailsBean.FilesBean> fileList = new ArrayList<>();
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_file_details;
    }

    @Override
    protected void initViews() {
        initTitle(true, "文件详情");
        id = getIntent().getIntExtra("id",id);
        releaseFilePresenter = new ReleaseFilePresenter(this,this);
    }

    @Override
    protected void updateViews() {
        mMProgressDialog = CommonUtil.configDialogDefault(this);
        mMProgressDialog.show("查询中...");
        releaseFilePresenter.onSelectSing(id);
        initAdapter();
    }
    public void initAdapter(){
        mRecyclerView.setHasFixedSize(true);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        fileSelectItemAdapter2 = new FileSelectItemAdapter2(this,fileList);
        fileSelectItemAdapter2.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(fileSelectItemAdapter2);
        fileSelectItemAdapter2.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                FileDown fileDown = new FileDown();
                fileDown.prepareStart(fileList.get(i).getName());
                //下载
                fileDown.downloadFile(UrlAddress.URL+fileList.get(i).getPath());
                fileDown.setXutilDownLoadInterface(new FileDown.XutilDownLoadInterface() {
                    @Override
                    public void onWaiting() {
                    }
                    @Override
                    public void onStarted() {
                       //progressBar.setVisibility(View.VISIBLE);
                        mMProgressDialog.show("正在打开");
                        Log.i("video","***v开始 *");
                    }
                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        Log.i("video","***v进度*"+current);
                        // CommonUtil.showToast(mContext,"***v进度*"+current);
                        //progressBar.setMax((int) total);
                      //  progressBar.setProgress((int) current);
                    }
                    @Override
                    public void onSuccess(File result) {
                        //progressBar.setVisibility(View.GONE);
                        mMProgressDialog.dismiss();
                        Log.i("video","***v完成*"+result.getAbsolutePath());
                        try {
                            Intent intent = CommonUtil.getWordFileIntent(result.getAbsolutePath());
                            startActivity(intent);
                        }catch (ActivityNotFoundException e){
                            CommonUtil.showToast(FileDetailActivity.this,"请安装wps客户端");
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        mMProgressDialog.dismiss();
                        CommonUtil.showToast(FileDetailActivity.this,"打开失败");
                    }
                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
            }
        });
    }
    @Override
    public void onSuccess() {

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
        mMProgressDialog.dismiss();
        initData(fileDetailsBean);
    }
    public void initData(FileDetailsBean fileDetailsBean){
        titleTv.setText(fileDetailsBean.getTitle());
        timeTv.setText(fileDetailsBean.getTime());
        fileSelectItemAdapter2.setNewData(fileDetailsBean.getFiles());
        fileList.addAll(fileDetailsBean.getFiles());
    }
}
