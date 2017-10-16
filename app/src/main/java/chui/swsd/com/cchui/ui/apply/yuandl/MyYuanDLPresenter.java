package chui.swsd.com.cchui.ui.apply.yuandl;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\10\14 0014.
 */

public class MyYuanDLPresenter implements MyYuanDLContracts.presenter {
    MyYuanDLContracts.view view;
    Context mcontext;
    public MyYuanDLPresenter(MyYuanDLContracts.view view,Context context){
        this.view = view;
        this.mcontext = context;
    }
    @Override
    public void onSelectScore(String time) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("time",time);
        RetrofitClient.getInstance(mcontext).createBaseApi().get(UrlAddress.INTEGRAL
                , maps, new BaseSubscriber<BaseResponse>(mcontext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(mcontext,responseBody.getCode())) {
                            String s = new Gson().toJson(responseBody.getObj());
                            YdlScoreBean ydlScoreBean = new Gson().fromJson(s, new TypeToken<YdlScoreBean>() {
                            }.getType());
                            view.onSuccess(ydlScoreBean);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
