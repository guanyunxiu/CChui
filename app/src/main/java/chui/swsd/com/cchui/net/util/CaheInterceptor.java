package chui.swsd.com.cchui.net.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import java.io.IOException;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * caheInterceptor
 * Created by Tamic on 2016-08-09.
 */
public class CaheInterceptor implements Interceptor {

    private Context context;

    public CaheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtil.isNetworkAvailable(context)) {
            Response response = chain.proceed(request);
            // read from cache for 60 s
            int maxAge = 1;
            Log.i("session",BaseApplication.mSharedPrefUtil.getString(SharedConstants.SESSIONID,"")+"--------------------------");
           // String cacheControl = request.cacheControl().toString();
          //  Log.e("Tamic", "60s load cahe" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .addHeader("JSESSIONID", BaseApplication.mSharedPrefUtil.getString(SharedConstants.SESSIONID,""))
                    .build();
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   // Toast.makeText(context, "当前无网络! 为你智能加载缓存", Toast.LENGTH_SHORT).show();
                }
            });
            Log.e("Tamic", " no network load cahe");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            //set cahe times is 3 days
            int maxStale = 60 * 60 * 24 * 5;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .addHeader("JSESSIONID", BaseApplication.mSharedPrefUtil.getString(SharedConstants.SESSIONID,""))
                    .build();
        }
    }
}
