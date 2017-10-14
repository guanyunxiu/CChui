package chui.swsd.com.cchui.ui.mine.kaoqin;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.KaoQinListBean;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public class MyKaoQinPresenter implements MyKaoQinContract.presenter {
    MyKaoQinContract.view view;
    Context context;
    public MyKaoQinPresenter(MyKaoQinContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(String time) {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("time",time);
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.MONTHSIGH
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            String s = new Gson().toJson(responseBody.getObj());
                            KaoQinListBean kaoQinListBean = new Gson().fromJson(s, new TypeToken<KaoQinListBean>() {
                            }.getType());
                            view.onSuccess(kaoQinListBean);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
