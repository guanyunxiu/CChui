package chui.swsd.com.cchui.ui.apply.file.release_file;

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
import chui.swsd.com.cchui.model.FileDetailsBean;
import chui.swsd.com.cchui.model.FileManagerBean;
import chui.swsd.com.cchui.model.RiZhiListBean;
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
 * Created by 关云秀 on 2017\9\22 0022.
 */

public class ReleaseFilePresenter implements ReleaseFileContract.presenter {
    ReleaseFileContract.view view;
    Context context;
    public ReleaseFilePresenter(ReleaseFileContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(String title, List<String> listId, List<String> listFile) {
        RequestParams params = new RequestParams(UrlAddress.FILEINSERT);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("title",title));
        list.add(new KeyValue("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+""));
        for(int i=0;i<listId.size();i++){
            list.add(new KeyValue("id"+(i+1), listId.get(i)));
        }
        for(int i=0;i<listFile.size();i++){
            list.add(new KeyValue("file"+(i+1), new File(listFile.get(i))));
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

    @Override
    public void onSelect(int page, int type) {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("page",page+"");
        maps.put("type",type+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.FILESEL
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
                            List<FileManagerBean> fileManagerBeanList = new Gson().fromJson(s, new TypeToken<List<FileManagerBean>>() {
                            }.getType());
                            view.onSuccess(fileManagerBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
    @Override
    public void onSelectSing(int id) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id", id+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.FILESELONE
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
                           FileDetailsBean fileDetailsBean = new Gson().fromJson(s, new TypeToken<FileDetailsBean>() {
                            }.getType());
                            view.onSuccess(fileDetailsBean);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
