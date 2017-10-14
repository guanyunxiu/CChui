package chui.swsd.com.cchui.ui.apply.huiyi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.GongGaoBean;
import chui.swsd.com.cchui.model.HuiYiBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\21 0021.
 */

public class HuiYiListPresenter implements HuiYiListContract.presenter {
    HuiYiListContract.view view;
    Context context;
    public HuiYiListPresenter(HuiYiListContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSelect(int page, int type) {
        Map<String,Object> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("page", page+"");
        maps.put("type",type+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.MARKSEL
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
                            List<HuiYiBean> huiYiBeanList = new Gson().fromJson(s, new TypeToken<List<HuiYiBean>>() {
                            }.getType());
                            view.onSuccess(huiYiBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onSelectSing(int id) {
        Map<String,Object> maps = new HashMap<>();
        maps.put("id",id+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.MARKSELONE
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
                            HuiYiBean huiYiBean = new Gson().fromJson(s, new TypeToken<HuiYiBean>() {
                            }.getType());
                            view.onSuccess(huiYiBean);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
