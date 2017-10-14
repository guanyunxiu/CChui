package chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi;

import android.content.Context;
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
 * Created by 关云秀 on 2017\9\15 0015.
 */

public class WriteRzPresenter implements WriteRzContract.presenter {
    WriteRzContract.view view;
    Context context;
    public WriteRzPresenter(WriteRzContract.view view,Context context){
        this.view = view;
        this.context = context;
    }

    /**
     * 上传日报
     * @param spList  接收人ID
     * @param accomplish 已完成
     * @param unfinished 未完成/工作总结
     * @param plan 工作计划
     * @param coordinate 需要协调工作
     * @param remark 备注
     * @param category 0日报 1周报 2月报
     * @param fileList 上传的文件
     */
    @Override
    public void onSubmit(List<String> spList, String accomplish, String unfinished, String plan, String coordinate, String remark, int category, List<String> fileList) {
        RequestParams params = new RequestParams(UrlAddress.REPORTINSERT);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+""));
        for(int i=0;i<spList.size();i++){
            list.add(new KeyValue("id"+(i+1), spList.get(i)));
        }
        list.add(new KeyValue("accomplish", accomplish+""));
        list.add(new KeyValue("unfinished", unfinished+""));
        list.add(new KeyValue("plan", plan+""));
        list.add(new KeyValue("coordinate", coordinate+""));
        list.add(new KeyValue("remark", remark+""));
        list.add(new KeyValue("category", category+""));
        for(int i=0;i<fileList.size();i++){
            list.add(new KeyValue("file"+(i+1), new File(fileList.get(i))));
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
        });
    }
}
