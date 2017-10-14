package chui.swsd.com.cchui.ui.apply.shenpi.sp_jiaban;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.RequestMethod;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.Xutils;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public class SpJiabanPresenter implements SpJiabanContract.presenter {
    SpJiabanContract.view view;
    Context context;
    public SpJiabanPresenter(SpJiabanContract.view view, Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(String starttime, String endtime, String usedtime, int isholiday, String way, String reason, List<String> listId) {
       /* RequestParams params = new RequestParams(UrlAddress.OVERTIMEINSERT);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+""));
        list.add(new KeyValue("starttime", starttime+""));
        list.add(new KeyValue("endtime", endtime+""));
        list.add(new KeyValue("usedtime", usedtime+""));
        list.add(new KeyValue("isholiday", isholiday+""));
        list.add(new KeyValue("way", way+""));
        list.add(new KeyValue("reason", reason+""));
        for(int i=0;i<listId.size();i++){
            list.add(new KeyValue("id"+(i+1), listId.get(i)));
        }
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
        });*/

        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("starttime",starttime);
        maps.put("endtime",endtime);
        maps.put("usedtime",usedtime);
        maps.put("isholiday",isholiday+"");
        maps.put("way",way);
        maps.put("reason",reason);
        for(int i=0;i<listId.size();i++){
            maps.put("id"+(i+1),listId.get(i));
        }
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.OVERTIMEINSERT
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
