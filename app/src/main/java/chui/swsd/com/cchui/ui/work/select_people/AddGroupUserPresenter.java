package chui.swsd.com.cchui.ui.work.select_people;

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
import chui.swsd.com.cchui.model.GroupListBean;
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
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class AddGroupUserPresenter implements AddGroupUserContract.presenter {
    AddGroupUserContract.view view;
    Context context;
    public AddGroupUserPresenter(AddGroupUserContract.view view,Context context){
        this.view = view;
        this.context = context;
    }

    /***
     * 添加群成员
     * @param id 群ID
     * @param listId 群成员信息
     */
    @Override
    public void onAddGroupUser(int id, List<String> listId) {
        RequestParams params = new RequestParams(UrlAddress.ADDGROUPINFO);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("groupid", id+""));

        for(int i=0;i<listId.size();i++){
            list.add(new KeyValue("idandname"+(i+1), listId.get(i)));

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
                    view.onSuccess(0);
                }else{
                    view.onFail(0);
                }
            }
            @Override
            public void onFail() {
                view.onFail(0);
            }
        });
    }

    @Override
    public void onDeleteGroupUser(int id, List<Integer> listId) {
        RequestParams params = new RequestParams(UrlAddress.DELEGROUPINFO);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("groupid", id+""));

        for(int i=0;i<listId.size();i++){
            list.add(new KeyValue("ids"+(i+1), listId.get(i)));

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
                    view.onSuccess(1);
                }else{
                    view.onFail(1);
                }
            }
            @Override
            public void onFail() {
                view.onFail(1);
            }
        });
    }
}
