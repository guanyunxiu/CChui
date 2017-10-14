package chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.RiZhiListBean;
import chui.swsd.com.cchui.model.RzDetailsBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\16 0016.
 */

public class RedDetailsPresenter implements RedDetailsContract.presenter {
    RedDetailsContract.view view;
    Context context;
    public RedDetailsPresenter(RedDetailsContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(int id) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id", id+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.REPORTSELONE
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
                            RzDetailsBean rzDetailsBean = new Gson().fromJson(s, new TypeToken<RzDetailsBean>() {
                            }.getType());
                            view.onSuccess(rzDetailsBean);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
