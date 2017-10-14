package chui.swsd.com.cchui.ui.contacts.group;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class GroupPrecenter implements GroupContract.precenter {
    GroupContract.view view;
    Context context;
    public GroupPrecenter(GroupContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSelect() {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.SELMYGROUP
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
                            List<GroupListBean> listBeen = new Gson().fromJson(s, new TypeToken<List<GroupListBean>>() {
                            }.getType());
                            view.onSuccess(listBeen);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
