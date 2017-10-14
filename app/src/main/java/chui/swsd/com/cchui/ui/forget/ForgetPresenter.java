package chui.swsd.com.cchui.ui.forget;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class ForgetPresenter implements ForgetContract.presenter {
    ForgetContract.view view;
    Context context;
    public ForgetPresenter(ForgetContract.view view, Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(String phone, String pass) {
        Map<String,String> maps = new HashMap<>();
        maps.put("phone",phone);
        maps.put("password",pass);
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.FORGETPASS
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
