package chui.swsd.com.cchui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.config.Constants;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.UpdateModel;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.utils.DateUtil;
import chui.swsd.com.cchui.utils.FontsOverride;
import chui.swsd.com.cchui.utils.SharedPrefUtil;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\7 0007.
 */

public class BaseApplication extends MultiDexApplication implements
        Thread.UncaughtExceptionHandler {
    private static BaseApplication mApplication;
    public static List<Activity> activityList = new LinkedList<Activity>();
    public static SharedPrefUtil mSharedPrefUtil;
    protected DbManager db;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mSharedPrefUtil = new SharedPrefUtil(this, SharedConstants.sharersinfor);
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/PingFang Bold.ttf");
        //启动异常捕获线程
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        initImageLoader();
        RongIM.init(this);//初始化融云
        JPushInterface.setDebugMode(true);//极光推送
        JPushInterface.init(this);
        iniMap();
        // 初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);
        SealAppContext sealAppContext = new SealAppContext(this);
        sealAppContext.init();
        codeUpdate();//版本更新
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }

    public static BaseApplication getInstance() {
        return mApplication;
    }

    public void removeActivity(Activity act, int index) {
        if (activityList != null && !activityList.isEmpty()) {
            activityList.remove(act);
        }
    }

    public void addActivity(Activity act) {
        if (activityList != null) {
            activityList.add(act);
        }
    }

    public List<Activity> getActivityList() {
        return activityList;
    }


    public static void removeActivity() {
        Log.i("activityList", activityList.size() + "***");
        //
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }


    //保存错误日志到本地文件夹
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (null == ex)
            return;

        setErrorInfor(thread, ex);
    }

    public void setErrorInfor(final Thread thread, final Throwable ex) {

        outLog(ex);
        if (null != defaultUncaughtExceptionHandler)
            defaultUncaughtExceptionHandler.uncaughtException(thread, ex);

    }

    //打印错误日志
    public void outLog(Throwable ex) {
        File sdFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
            sdFile = new File(sdCardDir, Constants.WORNGFILE);
            final int MAX_SIZE = 1 * 1024 * 1024; // 1M
            boolean append = true;
            if (sdFile.length() > MAX_SIZE) {
                append = false;
            }
            try {
                FileOutputStream fos = new FileOutputStream(sdFile, append);
                PrintWriter printWriter = new PrintWriter(fos);
                String bar = "----------------------------";
                printWriter.append(bar + DateUtil.curChinaFormatDate() + bar);//系統时间
                printWriter.append(bar + android.os.Build.MODEL + bar);//手机型号
                printWriter.append("\n");
                ex.printStackTrace(printWriter);
                printWriter.close();
                fos.close();  //关闭输出流

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient;
    private void iniMap() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(mAMapLocationListener);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为3分钟
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }
    //中国lbs太原市小店区140105
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (location != null) {
                Double geoLat = location.getLatitude();
                Double geoLng = location.getLongitude();
                Constants.Latitude = geoLat;//纬度
                Constants.Longitude = geoLng;//经度
                Log.i("sslocation",geoLat+"***"+geoLng);
            }
        }
    };
    private void codeUpdate(){
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                .setBaseUrl(UrlAddress.updatesdk)
                .setIgnoreThisVersion(false)
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG_WITH_BACK_DOWN )
                .setIconRes(R.mipmap.logo)
                .showLog(true)
                //设置下载时展示的应用吗
                .setAppName(getResources().getString(R.string.app_name))
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
                .setTransition(new UpdateModel())
                .build();
        CretinAutoUpdateUtils.init(builder);
    }
}
