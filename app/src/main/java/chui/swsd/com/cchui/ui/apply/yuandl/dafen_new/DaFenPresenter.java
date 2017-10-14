package chui.swsd.com.cchui.ui.apply.yuandl.dafen_new;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.DaFenConBean;
import chui.swsd.com.cchui.model.RiZhiListBean;
import chui.swsd.com.cchui.model.YdlTitleBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\10\11 0011.
 */

public class DaFenPresenter implements DaFenContract.presenter {
    DaFenContract.view view;
    Context context;
    public DaFenPresenter(DaFenContract.view view,Context mcontext){
        this.view = view;
        this.context = mcontext;
    }
    @Override
    public void onSelectTitle(int category) {
        Map<String,String> maps = new HashMap<>();
        maps.put("cid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0)+"");
        maps.put("category",category+"");

        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.POWERTITLE
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
                            Log.i("ydltitle",s);
                            List<YdlTitleBean> ydlTitleBeanList = new Gson().fromJson(s, new TypeToken<List<YdlTitleBean>>() {
                            }.getType());
                            view.onSuccess(ydlTitleBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onSelectContent(String bid) {
        Map<String,String> maps = new HashMap<>();
        maps.put("bid",bid+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.POWERCON
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
                            Log.i("ydltitle",s);
                            DaFenConBean daFenConBean = new Gson().fromJson(s, new TypeToken<DaFenConBean>() {
                            }.getType());
                            view.onSuccess(daFenConBean);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onSubmit(int category, String bid, String details) {
        Map<String,String> maps = new HashMap<>();
        maps.put("cid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0)+"");
        maps.put("category",category+"");
        maps.put("bid",bid);
        maps.put("details",details);
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.POWERGRADE
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
