package chui.swsd.com.cchui.ui.group;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.kevin.crop.UCrop;
import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.broadcast.BroadcastManager;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.login.LoginActivity;
import chui.swsd.com.cchui.ui.mine.my_info.SeldataPresenter;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.PhotoUtil;
import chui.swsd.com.cchui.widget.ClearWriteEditText;
import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class AddGroupActivity extends BaseActivity implements AddGroupContract.view{
    @BindView(R.id.img_Group_portrait)
    AsyncImageView imgGroupPortrait;
    @BindView(R.id.create_groupname)
    ClearWriteEditText createGroupname;
    @BindView(R.id.create_ok)
    Button createOk;
    /** 选择头像相册选取 */
    private static final int REQUESTCODE_PICK = 1;
    /** 选择头像拍照选取 */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;
    File currentFile,outFile;
    AddGroupPresenter addGroupPresenter;
    MProgressDialog mMProgressDialog;
    List<Node> listNode = new ArrayList<>();
    GroupListBean groupListBean2;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_create_group;
    }

    @Override
    protected void initViews() {
       initTitle(true,"创建群组");
        listNode =  (List) getIntent().getSerializableExtra("list");
        addGroupPresenter = new AddGroupPresenter(this,this);
        Log.i("listNode",listNode.size()+"****");
    }

    @Override
    protected void updateViews() {

    }

    @OnClick({R.id.img_Group_portrait, R.id.create_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_Group_portrait:
                selPhoto();
                break;
            case R.id.create_ok:
                String mGroupName = createGroupname.getText().toString();
                if (TextUtils.isEmpty(mGroupName)) {
                    CommonUtil.showToast(this,"群昵称不能为空");
                    break;
                }
                if (mGroupName.length() == 1) {
                    CommonUtil.showToast(this,"群昵称不能少于2个字");
                    return;
                }
                if (AndroidEmoji.isEmoji(mGroupName)) {
                    if (mGroupName.length() <= 2) {
                        CommonUtil.showToast(this,"群昵称不能少于2个字");
                        return;
                    }
                }
                List<String> stringList = new ArrayList<>();
                for(Node node:listNode){
                    if(!node.getId().equals(BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0))){
                        stringList.add(node.getId()+"and"+node.getName());
                    }
                }
                mMProgressDialog = CommonUtil.configDialogDefault(this);
                mMProgressDialog.show("创建中...");
                addGroupPresenter.onSubmit(mGroupName,currentFile,stringList);
                break;
        }
    }
    /**
     * 头像选择
     */
    public void doCamera() {
        outFile = PhotoUtil.openCamera(this,PHOTO_REQUEST_TAKEPHOTO);
    }
    private void selPhoto() {
        final String[] stringItems = {"拍照", "从相册选择",};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){//拍照
                    doCamera();

                }else if(position == 1){//从相册选择
                    doSDCardPermission();
                }
                dialog.dismiss();
            }
        });
    }
    public void doSDCardPermission() {
        PhotoUtil.openPic(this,REQUESTCODE_PICK);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  进行判断是那个操作跳转回来的，如果是裁剪跳转回来的这块就要把图片现实到View上，其他两种的话都把数据带入裁剪界面
        switch (requestCode) {
            //相册
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                Uri uri;
                uri = data.getData();
                PhotoUtil.startCropActivity(this,uri,0);
                break;
            case UCrop.REQUEST_CROP:    // 裁剪图片结果
                if(data != null) {
                    Bitmap bitmap = PhotoUtil.getBitmap(data, this);
                    imgGroupPortrait.setImageBitmap(bitmap);
                    currentFile = PhotoUtil.saveBitmapFile(bitmap);
                }
                break;
            //拍照
            case PHOTO_REQUEST_TAKEPHOTO:
                PhotoUtil.startCropActivity(this,Uri.fromFile(outFile),0);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(int flag) {

    }

    @Override
    public void onFail(int flag) {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"创建失败");
    }

    @Override
    public void onSuccess(int flag,GroupListBean groupListBean) {
        groupListBean2 = groupListBean;
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"创建成功");
        EventBus.getDefault().post("群组查询");
        //刷新群组信息
        RongIM.getInstance().refreshGroupInfoCache(new Group(groupListBean.getRongid(), groupListBean.getName(), Uri.parse(UrlAddress.URLAddress+groupListBean.getHeadimg())));
        BroadcastManager.getInstance(this).sendBroadcast("REFRESH_GROUP_UI");
        RongIM.getInstance().startGroupChat(this, groupListBean.getRongid()+"", groupListBean.getName());
        //刷新自己的信息
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(BaseApplication.mSharedPrefUtil.getString(SharedConstants.USERID,""), BaseApplication.mSharedPrefUtil.getString(SharedConstants.NAME,""), Uri.parse(UrlAddress.URLAddress+BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHOTO,""))));
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        finish();
    }


}
