package chui.swsd.com.cchui.net;

import android.util.Log;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.UUID;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;

/**
 * 作者： 关云秀 on 2017/2/22.
 * 描述：
 */
public class Xutils {

    public void upload(RequestParams params, final RequestMethod requestMethod){
        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        params.addHeader("cookie","JSESSIONID="+ BaseApplication.mSharedPrefUtil.getString(SharedConstants.SESSIONID,""));
        params.addHeader("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override      //D2CC4F8AE46E567E618924FA7C44BB1F
            public void onSuccess(String result) {
                //加载成功回调，返回获取到的数据
                Log.i("success", "onSuccess: " + result);
                if(requestMethod != null) {
                    requestMethod.onSuccess(result);
                }
            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onCancelled(Callback.CancelledException cex) {
                if (requestMethod!=null){
                    requestMethod.onFail();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (requestMethod!=null){
                    requestMethod.onFail();
                }

            }
        });
    }
    public void post(RequestParams params, final RequestMethod requestMethod){
        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        params.addHeader("cookie","JSESSIONID="+ BaseApplication.mSharedPrefUtil.getString(SharedConstants.SESSIONID,""));
        params.addHeader("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override      //D2CC4F8AE46E567E618924FA7C44BB1F
            public void onSuccess(String result) {
                //加载成功回调，返回获取到的数据
                Log.i("success", "onSuccess: " + result);
                if(requestMethod != null) {
                    requestMethod.onSuccess(result);
                }
            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onCancelled(Callback.CancelledException cex) {
                if (requestMethod!=null){
                    requestMethod.onFail();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (requestMethod!=null){
                    requestMethod.onFail();
                }

            }
        });
    }
}
