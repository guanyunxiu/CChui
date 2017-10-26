package chui.swsd.com.cchui.utils;

import android.net.Uri;
import android.util.Log;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.inter.RongInterface;
import chui.swsd.com.cchui.net.UrlAddress;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\28 0028.
 */

public class RongConnectUtil {
     RongInterface rongInterface;
    public RongConnectUtil(RongInterface rongInterface){
        this.rongInterface = rongInterface;
    }
    public void connect(String token) {
            //String token2 = "vzwEVWzpBgXbjYBfeEK4bCu5IJfaqW6cX6bgXkvCqYnXtgHxo4AGuXVtVPdh9j3TZ4B89v8HHX6PbpyS4ZutmA==";
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }
                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
                    rongInterface.onConnSuccess();
                    setSerUserInfor(userid);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "--fail" + errorCode.getMessage());
                    rongInterface.onConnFail();
                }
            });
    }
    /**
     * 设置用户头像
     * @param userId 用户ID
     */
    /**
     * 设置用户头像
     * @param userId 用户ID
     */
    public void setSerUserInfor(String userId){
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.USERID,userId);//融云的userid
        BaseApplication. mSharedPrefUtil.commit();

        Log.i("rongPhoto",BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHOTO,"")+"********");
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(userId, BaseApplication.mSharedPrefUtil.getString(SharedConstants.NAME,""), Uri.parse(UrlAddress.URLAddress+BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHOTO,""))));
        RongIM.getInstance().setMessageAttachedUserInfo(true);
         /* Group groupInfo = new Group("001","工作群", Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493698139722&di=e4e4e8601579afa1d0427596ea3e581d&imgtype=0&src=http%3A%2F%2Fglwkh.com%2Fimages%2FHotel%2F20141131156451.jpg"));
            RongIM.getInstance().refreshGroupInfoCache(groupInfo);*/


    }
}
