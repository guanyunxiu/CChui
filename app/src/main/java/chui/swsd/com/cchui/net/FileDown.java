package chui.swsd.com.cchui.net;

import android.os.Environment;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017\6\6 0006.
 */

public class FileDown {
    private File videoFile = null;
    public String fileName;
    public  void prepareStart(String videoPath){
        try{
            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator+"mybeat/";
            File file = new File(rootPath);
            if(!file.exists()){
                if(!file.mkdirs()){
                    throw new NullPointerException("创建 rootPath 失败，注意 6.0+ 的动态申请权限");
                }
            }
            videoFile =
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator+"mybeat/"+videoPath);
            Log.i("video",videoFile.getAbsolutePath()+"***444*");
            videoFile.createNewFile();


        }catch (Exception e){
            Log.d("zzzzz",e.toString());

        }
      //  return false;
    }
    public  void downloadFile(String videoPath) {
       // String path = fileName(videoPath);
        RequestParams requestParams = new RequestParams(videoPath);
        requestParams.setSaveFilePath(videoFile.getAbsolutePath());
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
                xutilDownLoadInterface.onWaiting();
            }

            @Override
            public void onStarted() {
                xutilDownLoadInterface.onStarted();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                xutilDownLoadInterface.onLoading(total,current,isDownloading);
            }

            @Override
            public void onSuccess(File result) {
                xutilDownLoadInterface.onSuccess(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                xutilDownLoadInterface.onError(ex,isOnCallback);

            }

            @Override
            public void onCancelled(CancelledException cex) {
                xutilDownLoadInterface.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                xutilDownLoadInterface.onFinished();
            }
        });
    }
    private XutilDownLoadInterface xutilDownLoadInterface;
    public void setXutilDownLoadInterface(XutilDownLoadInterface xutilDownLoadInterface) {
        this.xutilDownLoadInterface = xutilDownLoadInterface;
    }
    public interface XutilDownLoadInterface {
        void onWaiting();
        void onStarted();
        void onLoading(long total, long current, boolean isDownloading);
        void onSuccess(File result);
        void onError(Throwable ex, boolean isOnCallback);
        void onCancelled(Callback.CancelledException cex);
        void onFinished();
    }
    public static String fileName(String videoPath){
        String[] temp = videoPath.split("/");
        int i = videoPath.lastIndexOf("/");
        String str = videoPath.substring(0,i+1);
        String name = null;
        try {
             name = URLEncoder.encode(temp[temp.length-1],"utf-8").replaceAll("\\+","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("fileName",str+name);
        return str+name;
    }
}
