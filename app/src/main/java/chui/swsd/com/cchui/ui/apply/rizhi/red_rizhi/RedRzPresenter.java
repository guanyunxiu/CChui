package chui.swsd.com.cchui.ui.apply.rizhi.red_rizhi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.RiZhiListBean;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\15 0015.
 */

public class RedRzPresenter implements RedRzContract.presenter {
    RedRzContract.view view;
    Context context;
    public RedRzPresenter(RedRzContract.view view,Context context){
        this.view = view;
        this.context = context;
    }

    /**
     *
     * @param starttime //开始时间
     * @param endtime //结束时间
     * @param category //0日报 1周报 2月报 3全部
     * @param page 页数
     * @param type 0：我发布的 1：我收到的
     * @param listId 发送人ID
     */
    @Override
    public void onSubmit(String starttime, String endtime, int category, int page, int type, List<String> listId) {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("starttime",starttime+"");
        maps.put("endtime",endtime+"");
        maps.put("category",category+"");
        maps.put("page",page+"");
        maps.put("type",type+"");
        if(listId.size()>0){
            for(int i=0;i<listId.size();i++){
                maps.put("id"+(i+1),listId.get(i));
            }
        }
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.REPORTSEL
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
                            List<RiZhiListBean> riZhiListBeanList = new Gson().fromJson(s, new TypeToken<List<RiZhiListBean>>() {
                            }.getType());
                            view.onSuccess(riZhiListBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
