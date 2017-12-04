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

import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.inter.RongInterface;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.contacts.ost.OstContract;
import chui.swsd.com.cchui.ui.contacts.ost.OstPresenter;
import chui.swsd.com.cchui.ui.group.AddGroupContract;
import chui.swsd.com.cchui.ui.group.AddGroupPresenter;
import chui.swsd.com.cchui.ui.mine.my_info.SeldataContract;
import chui.swsd.com.cchui.ui.mine.my_info.SeldataPresenter;
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
        RongIM.GroupInfoProvider,RongIM.LocationProvider,RongIM.GroupUserInfoProvider,
         AddGroupContract.view,SeldataContract.view
     {

    private static final int CLICK_CONVERSATION_USER_PORTRAIT = 1;
         AddGroupPresenter addGroupPresenter;
         SeldataPresenter seldataPresenter;
         private Context mContext;
    private final static String TAG = "SealAppContext";
    public static final String UPDATE_FRIEND = "update_friend";
    public static final String UPDATE_RED_DOT = "update_red_dot";
    public static final String UPDATE_GROUP_NAME = "update_group_name";
    public static final String UPDATE_GROUP_MEMBER = "update_group_member";
    public static final String GROUP_DISMISS = "group_dismiss";
         private static SealAppContext mRongCloudInstance;
         public SealAppContext(Context mContext) {
             this.mContext = mContext;
             initListener();
         }
         public static void init(Context mContext){

             if (mRongCloudInstance == null) {
                 synchronized (SealAppContext.class) {

                     if (mRongCloudInstance == null) {
                         mRongCloudInstance = new SealAppContext(mContext);
                     }
                 }
             }

         }
         private void initListener() {
             addGroupPresenter = new AddGroupPresenter(this, mContext);
             seldataPresenter = new SeldataPresenter(this,mContext);
             RongIM.setUserInfoProvider(this, true);
             RongIM.setGroupInfoProvider(this, true);
             RongIM.setGroupUserInfoProvider(this,true);
             RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
             Log.i("response","有没有执行");
         }
         @Override
         public Group getGroupInfo(String s) {
             Log.i("responsegetUserInfo",s+"***group");
             addGroupPresenter.onSelectDetails(s);
             return null;
         }

         @Override
         public UserInfo getUserInfo(String s) {
             Log.i("responsegetUserInfo",s+"***user");
             seldataPresenter.onRongSelect(s);
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

         @Override
         public void onSuccess(UserBean userBean) {
             UserInfo userInfo = new UserInfo(userBean.getRongid(),userBean.getName(), Uri.parse(UrlAddress.URLAddress+userBean.getHeadimg()));
             RongIM.getInstance().refreshUserInfoCache(userInfo);
          //   RongIM.getInstance().refreshUserInfoCache(new UserInfo("userId", "啊明", Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png")));
         }

         @Override
         public void onSuccess() {

         }

         @Override
         public void onFail() {

         }

         @Override
         public void onStartLocation(Context context, LocationCallback locationCallback) {

         }

         @Override
         public GroupUserInfo getGroupUserInfo(String s, String s1) {
             Log.i("groupuserinfo",s+"*****"+s1);
             return null;
         }
     }
