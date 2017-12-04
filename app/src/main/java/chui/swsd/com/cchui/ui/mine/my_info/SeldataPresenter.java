package chui.swsd.com.cchui.ui.mine.my_info;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.RequestMethod;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.Xutils;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class SeldataPresenter implements SeldataContract.presenter {
    SeldataContract.view view;
    Context context;
    public SeldataPresenter(SeldataContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSelect(String phone) {
        Map<String,String> maps = new HashMap<>();
        maps.put("phone",phone);
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.SELDATA
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
                            UserBean user = new Gson().fromJson(s, new TypeToken<UserBean>() {
                            }.getType());
                            view.onSuccess(user);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onUpdate(String sex, String address, File photo) {
        RequestParams params = new RequestParams(UrlAddress.UPDATEDATA);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("id", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)));
        list.add(new KeyValue("sex", sex));
        list.add(new KeyValue("address", address));
        list.add(new KeyValue("file", photo));
        MultipartBody multipartBody = new MultipartBody(list,"UTF-8");
        params.setRequestBody(multipartBody);
        new Xutils().upload(params, new RequestMethod() {
            @Override
            public void onSuccess(String str) {
                Log.i("onSuccess",str);
                BaseResponse baseResponse = new Gson().fromJson(str, new TypeToken<BaseResponse>() {
                }.getType());
                if(FailMsg.showMsg(context,baseResponse.getCode())){
                    view.onSuccess();
                }else{
                    view.onFail();
                }
            }
            @Override
            public void onFail() {
                view.onFail();
            }
        });


      /*  Map<String, RequestBody> maps = new HashMap<>();
        maps.put("id",RequestBody.create(MediaType.parse("multipart/form-data"), ""+BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)));
        if(photo != null) {
            maps.put("file", RequestBody.create(MediaType.parse("multipart/form-data"), photo));
        }
        maps.put("sex",RequestBody.create(MediaType.parse("multipart/form-data"), sex));
        maps.put("address",RequestBody.create(MediaType.parse("multipart/form-data"), address));

        maps.put("name",RequestBody.create(MediaType.parse("multipart/form-data"), "aaa"));
        maps.put("entrydate",RequestBody.create(MediaType.parse("multipart/form-data"), "3443a"));
        maps.put("birthday",RequestBody.create(MediaType.parse("multipart/form-data"), "2017.02.22"));
        maps.put("departmentid",RequestBody.create(MediaType.parse("multipart/form-data"), "3443a"));
        RetrofitClient.getInstance(context).createBaseApi().upload(UrlAddress.UPDATEDATA, maps, new BaseSubscriber<BaseResponse>(context) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                view.onFail();
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                if(FailMsg.showMsg(context,baseResponse.getCode())) {
                   // String s = new Gson().toJson(baseResponse.getObj());
                  *//*  UserBean user = new Gson().fromJson(s, new TypeToken<UserBean>() {
                    }.getType());*//*
                    view.onSuccess();
                }else{
                    view.onFail();
                }
            }
        });*/
    }

    @Override
    public void onRongSelect(String rongid) {
        Map<String,String> maps = new HashMap<>();
        maps.put("rongid",rongid);
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.SELRONGID
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
                            Log.i("response",s+"***");
                            UserBean user = new Gson().fromJson(s, new TypeToken<UserBean>() {
                            }.getType());
                            view.onSuccess(user);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
