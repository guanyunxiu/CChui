package chui.swsd.com.cchui.ui.apply.kaoqin;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.KaoQinBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\4 0004.
 */

public class KaoQinPresenter implements KaoQinContract.presenter {
    KaoQinContract.view view;
    Context context;
    public KaoQinPresenter(KaoQinContract.view view, Context context){
        this.view = view;
        this.context = context;
    }

    /**
     * 打卡
     * @param address 地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param outwork  是否是外勤（0正常，1外勤）
     * @param category 上班标志（0是上班，1是下班）
     */
    @Override
    public void onSubmit(String address, double longitude, double latitude,int outwork,int category) {
        Map<String,Object> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("address",address);
        maps.put("longitude",longitude);
        maps.put("latitude",latitude);
        maps.put("outwork",outwork);
        maps.put("category",category);
        RetrofitClient.getInstance(context).createBaseApi().post3(UrlAddress.SIGNINSERT
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            view.onSuccess(responseBody.getCode());
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onSelect() {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.SIGNTODAY
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            String s = new Gson().toJson(responseBody.getList());
                            List<KaoQinBean> kaoQinBeanList = new Gson().fromJson(s, new TypeToken<List<KaoQinBean>>() {
                            }.getType());
                            view.onSuccess(kaoQinBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
