package chui.swsd.com.cchui.ui.apply.gonggao;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.GongGaoBean;
import chui.swsd.com.cchui.model.KaoQinBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\20 0020.
 */

public class GongGaoPresenter implements GongGaoContract.presenter {
    GongGaoContract.view view;
    Context context;
    public GongGaoPresenter(GongGaoContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onRelease(String title, String content, List<String> stringList) {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("username", BaseApplication.mSharedPrefUtil.getString(SharedConstants.USERNAME,"")+"");
        maps.put("title",title);
        maps.put("content",content);
        for(int i=0;i<stringList.size();i++){
            maps.put("id"+(i+1),stringList.get(i));
        }
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.INFORMINSERT
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

    /**
     * 查看公告
     * @param page 页数
     * @param type 类型：0为我发布的，1为我收到的
     */
    @Override
    public void onSelect(int page, int type) {
        Map<String,Object> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("page", page+"");
        maps.put("type",type+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.INFORMSEL
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
                            List<GongGaoBean> gongGaoBeanList = new Gson().fromJson(s, new TypeToken<List<GongGaoBean>>() {
                            }.getType());
                            view.onSuccess(gongGaoBeanList);
                            view.onSuccess();
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    /**
     * 查看单个公告
     * @param id 公告ID
     */
    @Override
    public void onSelectSing(int id) {
        Map<String,Object> maps = new HashMap<>();
        maps.put("id", id+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.INFORMSELONE
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
                            GongGaoBean gongGaoBean = new Gson().fromJson(s, new TypeToken<GongGaoBean>() {
                            }.getType());
                            view.onSuccess(gongGaoBean);
                            view.onSuccess();
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
