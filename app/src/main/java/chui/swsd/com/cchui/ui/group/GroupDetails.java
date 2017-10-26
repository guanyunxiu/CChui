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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.kevin.crop.UCrop;
import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.GridAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.chat.ChatActivity;
import chui.swsd.com.cchui.ui.mine.set.SetActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.OperationRong;
import chui.swsd.com.cchui.utils.PhotoUtil;
import chui.swsd.com.cchui.widget.DemoGridView;
import chui.swsd.com.cchui.widget.DialogWithYesOrNoUtils;
import chui.swsd.com.cchui.widget.SelectableRoundedImageView;
import chui.swsd.com.cchui.widget.switchbutton.SwitchButton;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class GroupDetails extends BaseActivity implements AddGroupContract.view{
    AddGroupPresenter addGroupPresenter;
    @BindView(R.id.gridview)
    DemoGridView gridview;
    @BindView(R.id.group_member_size)
    TextView groupMemberSize;
    @BindView(R.id.group_member_size_item)
    RelativeLayout groupMemberSizeItem;
    @BindView(R.id.group_header)
    SelectableRoundedImageView groupHeader;
    @BindView(R.id.ll_group_port)
    LinearLayout llGroupPort;
    @BindView(R.id.group_name)
    TextView groupName;
    @BindView(R.id.ll_group_name)
    LinearLayout llGroupName;
    @BindView(R.id.ac_ll_group_announcement_divider)
    LinearLayout acLlGroupAnnouncementDivider;
    @BindView(R.id.group_code)
    LinearLayout groupCode;
    @BindView(R.id.group_displayname_text)
    TextView groupDisplaynameText;
    @BindView(R.id.group_displayname)
    LinearLayout groupDisplayname;
    @BindView(R.id.group_quit)
    Button groupQuit;
    @BindView(R.id.group_dismiss)
    Button groupDismiss;
    private String id;
    private int groupId;
    MProgressDialog mMProgressDialog;
    private String newGroupName;
    /** 选择头像相册选取 */
    private static final int REQUESTCODE_PICK = 1;
    /** 选择头像拍照选取 */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;
    File currentFile,outFile;
    Bitmap photoBitmap;
    GroupListBean groupListBean2;
    private boolean isCreated;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_detail_group;
    }

    @Override
    protected void initViews() {
        initTitle(true, "群组信息");
        EventBus.getDefault().register(this);
        id = getIntent().getStringExtra("id");
        mMProgressDialog = CommonUtil.configDialogDefault(GroupDetails.this);
        mMProgressDialog.show("正在查询...");
        addGroupPresenter = new AddGroupPresenter(this, this);
        addGroupPresenter.onSelectDetails(id);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public void onSuccess(int flag) {
        mMProgressDialog.dismiss();
      if(flag == 2){
          EventBus.getDefault().post("群组查询");
          CommonUtil.showToast(this,"解散并删除成功");
          RongIM.getInstance().getConversation(Conversation.ConversationType.GROUP, id, new RongIMClient.ResultCallback<Conversation>() {
              @Override
              public void onSuccess(Conversation conversation) {
                  RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP, id, new RongIMClient.ResultCallback<Boolean>() {
                      @Override
                      public void onSuccess(Boolean aBoolean) {
                          RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id, null);
                      }

                      @Override
                      public void onError(RongIMClient.ErrorCode e) {

                      }
                  });
              }

              @Override
              public void onError(RongIMClient.ErrorCode e) {

              }
          });
          ChatActivity.chatActivity.finish();
          finish();
      }else if(flag == 5){
          EventBus.getDefault().post("群组查询");
          CommonUtil.showToast(this,"删除并退出成功");
          RongIM.getInstance().getConversation(Conversation.ConversationType.GROUP, id, new RongIMClient.ResultCallback<Conversation>() {
              @Override
              public void onSuccess(Conversation conversation) {
                  RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP, id, new RongIMClient.ResultCallback<Boolean>() {
                      @Override
                      public void onSuccess(Boolean aBoolean) {
                          RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, id, null);
                      }

                      @Override
                      public void onError(RongIMClient.ErrorCode e) {

                      }
                  });
              }

              @Override
              public void onError(RongIMClient.ErrorCode e) {

              }
          });
          ChatActivity.chatActivity.finish();
          finish();
      }
    }

    @Override
    public void onFail(int flag) {
        mMProgressDialog.dismiss();
        if(flag == 2){
            CommonUtil.showToast(this,"操作失败");
        }else if(flag == 3){
            CommonUtil.showToast(this,"修改名称失败");
        }
    }

    @Override
    public void onSuccess(int flag,GroupListBean groupListBean) {
        mMProgressDialog.dismiss();
        if(flag == 1){//查询群组信息
            groupListBean2 = groupListBean;
            mMProgressDialog.dismiss();
            groupId = groupListBean.getId();
            int id = BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0);
            if(groupListBean.getUserid()  == id){//我建的群
                isCreated = true;
            }else{//不是我建的群
                isCreated = false;
            }
            gridview.setAdapter(new GridAdapter(this, groupListBean.getUserSimples(),groupId,isCreated));
            groupMemberSize.setText("全部群成员("+groupListBean.getUserSimples().size()+")");
            groupName.setText(groupListBean.getName());
            if(!TextUtils.isEmpty(groupListBean.getHeadimg())){
                Glide.with(this).load(UrlAddress.URLAddress + groupListBean.getHeadimg()).into(groupHeader);
            }

            if(isCreated){//我建的群
                groupDismiss.setVisibility(View.VISIBLE);
                groupQuit.setVisibility(View.GONE);
            }else{//不是我建的群
                groupDismiss.setVisibility(View.GONE);
                groupQuit.setVisibility(View.VISIBLE);
            }
        }else if(flag == 3){//修改群昵称
            CommonUtil.showToast(this,"修改名称成功");
            groupName.setText(newGroupName);
            EventBus.getDefault().post("群组查询");
            RongIM.getInstance().refreshGroupInfoCache(new Group(groupListBean.getRongid(), groupListBean.getName(), Uri.parse(UrlAddress.URLAddress+groupListBean.getHeadimg())));
        }else if(flag == 4){//修改群头像
            CommonUtil.showToast(this,"修改头像成功");
            groupHeader.setImageBitmap(photoBitmap);
            EventBus.getDefault().post("群组查询");
            RongIM.getInstance().refreshGroupInfoCache(new Group(groupListBean.getRongid(), groupListBean.getName(), Uri.parse(UrlAddress.URLAddress+groupListBean.getHeadimg())));
        }



    }

    @OnClick({R.id.group_quit, R.id.group_dismiss,R.id.ll_group_name,R.id.ll_group_port})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_quit://删除并退出
                NormalDialogStyleOne(1,"确认退出群组");
                break;
            case R.id.group_dismiss://删除并解散群
                NormalDialogStyleOne(0,"确认解散群组");
                break;
            case R.id.ll_group_name:
                if(groupListBean2.getUserid() == BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)){
                    DialogWithYesOrNoUtils.getInstance().showEditDialog(this, getString(R.string.new_group_name), getString(R.string.confirm), new DialogWithYesOrNoUtils.DialogCallBack() {
                        @Override
                        public void executeEvent() {
                        }
                        @Override
                        public void executeEditEvent(String editText) {
                            if (TextUtils.isEmpty(editText)) {
                                return;
                            }
                            if (editText.length() < 2 && editText.length() > 10) {
                                CommonUtil.showToast(GroupDetails.this,"群名称应为 2-10 字");
                                return;
                            }

                            if (AndroidEmoji.isEmoji(editText) && editText.length() < 4) {
                                CommonUtil.showToast(GroupDetails.this,"群名称表情过短");
                                return;
                            }
                            newGroupName = editText;
                            //LoadDialog.show(mContext);
                            // request(UPDATE_GROUP_NAME);
                            addGroupPresenter.onUpdateGroupName(groupId,newGroupName);
                        }
                        @Override
                        public void updatePassword(String oldPassword, String newPassword) {

                        }
                    });
                }

                break;
            case R.id.ll_group_port:
                if(groupListBean2.getUserid() == BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)) {
                    selPhoto();
                }
                break;
        }
    }
    private void NormalDialogStyleOne(final int flag, String str) {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.isTitleShow(false).content(str)//
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
                        if(flag == 0){
                            mMProgressDialog.show("正在解散...");
                            addGroupPresenter.onDeleteGroup(groupId);
                        }else if(flag == 1){
                            mMProgressDialog.show("正在退出...");
                            List<Integer> listId = new ArrayList<Integer>();
                            listId.add(BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0));
                            addGroupPresenter.onDeleteGroupUser(groupId,listId);
                        }
                        dialog.dismiss();
                    }
                });
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

    /**
     * 头像选择
     */
    public void doCamera() {
        outFile = PhotoUtil.openCamera(this,PHOTO_REQUEST_TAKEPHOTO);
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
                    photoBitmap = bitmap;
                    currentFile = PhotoUtil.saveBitmapFile(bitmap);
                    mMProgressDialog.show("正在修改...");
                    addGroupPresenter.onUpdateGroupFile(groupId,currentFile);
                }
                break;
            //拍照
            case PHOTO_REQUEST_TAKEPHOTO:
                PhotoUtil.startCropActivity(this,Uri.fromFile(outFile),0);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(String flag){
       if(flag.equals("成功")){
           addGroupPresenter.onSelectDetails(id);
       }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

}
