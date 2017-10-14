package chui.swsd.com.cchui.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.contacts.ost.OstContract;
import chui.swsd.com.cchui.ui.contacts.ost.OstPresenter;
import chui.swsd.com.cchui.ui.group.AddGroupContract;
import chui.swsd.com.cchui.ui.group.AddGroupPresenter;
import chui.swsd.com.cchui.utils.CommonUtil;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupNotificationMessageData;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.GroupNotificationMessage;
import io.rong.message.ImageMessage;

/**
 * 融云相关监听 事件集合类
 * Created by AMing on 16/1/7.
 * Company RongCloud
 */
public class SealAppContext implements
        RongIM.UserInfoProvider,
        RongIM.GroupInfoProvider,
         AddGroupContract.view
     {

    private static final int CLICK_CONVERSATION_USER_PORTRAIT = 1;
         AddGroupPresenter addGroupPresenter;
         private Context mContext;
    private final static String TAG = "SealAppContext";
    public static final String UPDATE_FRIEND = "update_friend";
    public static final String UPDATE_RED_DOT = "update_red_dot";
    public static final String UPDATE_GROUP_NAME = "update_group_name";
    public static final String UPDATE_GROUP_MEMBER = "update_group_member";
    public static final String GROUP_DISMISS = "group_dismiss";
         public SealAppContext(Context mContext) {
             this.mContext = mContext;

         }
         public void init(){
             initListener();
             addGroupPresenter = new AddGroupPresenter(this, mContext);

         }
         private void initListener() {
             RongIM.setUserInfoProvider(this, true);
             RongIM.setGroupInfoProvider(this, true);
         }
         @Override
         public Group getGroupInfo(String s) {
             addGroupPresenter.onSelectDetails(s);
             return null;
         }

         @Override
         public UserInfo getUserInfo(String s) {

             return null;
         }

         @Override
         public void onSuccess(int flag) {

         }

         @Override
         public void onFail(int flag) {

         }

         @Override
         public void onSuccess(int flag, GroupListBean groupListBean) {
               Group group = new Group(groupListBean.getRongid(),groupListBean.getName(),Uri.parse(UrlAddress.URLAddress+groupListBean.getHeadimg()));
               RongIM.getInstance().refreshGroupInfoCache(group);
         }

     }
