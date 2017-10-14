package chui.swsd.com.cchui.ui.login;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.utils.Installation;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class LoginPresenter  implements LoginContract.Presenter{
    LoginContract.view view;
    private Context context;
    public LoginPresenter(LoginContract.view view, Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onLogin(String phone, final String pass) {
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.CLIENTID,Installation.id(context));
        BaseApplication.mSharedPrefUtil.commit();
        Map<String,String> maps = new HashMap<>();
        maps.put("phone",phone);
        maps.put("password",pass);
        maps.put("clientid", Installation.id(context));
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.LOGIN
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            String s = new Gson().toJson(responseBody.getUser());
                            UserBean user = new Gson().fromJson(s, new TypeToken<UserBean>() {
                            }.getType());
                            CommonUtil.saveUser(responseBody.getSessionId(),user,pass);
                            view.onSuccess(user);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onManaLogin(String account, String pass) {
        Map<String,String> maps = new HashMap<>();
        maps.put("account",account);
        maps.put("pass",pass);
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.COMPANYLOGIN
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            view.onSuccess();
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
