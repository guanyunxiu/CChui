package chui.swsd.com.cchui.ui.group;

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

public class AddGroupPresenter implements AddGroupContract.presenter {
    AddGroupContract.view view;
    Context context;
    public AddGroupPresenter(AddGroupContract.view view, Context context){
        this.view = view;
        this.context = context;
    }

    @Override
    public void onSubmit(String name, File file, List<String> stringList) {
        RequestParams params = new RequestParams(UrlAddress.INSERTGROUPS);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)));
        list.add(new KeyValue("name", name));
        if(file != null) {
            list.add(new KeyValue("file", file));
        }
        for(int i=0;i<stringList.size();i++){
            list.add(new KeyValue("idandname"+(i+1), stringList.get(i)));
            Log.i("idandname",stringList.get(i));
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
                    String s = new Gson().toJson(baseResponse.getObj());
                    GroupListBean groupListBean = new Gson().fromJson(s, new TypeToken<GroupListBean>() {
                    }.getType());
                    view.onSuccess(0,groupListBean);
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
    public void onSelectDetails(String id) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id", id+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.SELGROUPINFO
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail(1);
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            String s = new Gson().toJson(responseBody.getObj());
                            GroupListBean groupListBean = new Gson().fromJson(s, new TypeToken<GroupListBean>() {
                            }.getType());
                            view.onSuccess(1,groupListBean);
                        }else{
                            view.onFail(1);
                        }
                    }
                });
    }

    @Override
    public void onDeleteGroup(int id) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id", id+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.DELETEGROUPS
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail(2);
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            view.onSuccess(2);
                        }else{
                            view.onFail(2);
                        }
                    }
                });
    }

    /**
     * 修改群昵称
     * @param id 群ID
     * @param name 群昵称
     */
    @Override
    public void onUpdateGroupName(int id, String name) {
        RequestParams params = new RequestParams(UrlAddress.UPDATEGROUPS);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("id",id+""));
        list.add(new KeyValue("name", name));

        MultipartBody multipartBody = new MultipartBody(list,"UTF-8");
        params.setRequestBody(multipartBody);
        new Xutils().upload(params, new RequestMethod() {
            @Override
            public void onSuccess(String str) {
                Log.i("onSuccess",str);
                BaseResponse baseResponse = new Gson().fromJson(str, new TypeToken<BaseResponse>() {
                }.getType());
                if(FailMsg.showMsg(context,baseResponse.getCode())){
                    String s = new Gson().toJson(baseResponse.getObj());
                    GroupListBean groupListBean = new Gson().fromJson(s, new TypeToken<GroupListBean>() {
                    }.getType());
                    view.onSuccess(3,groupListBean);
                }else{
                    view.onFail(3);
                }
            }
            @Override
            public void onFail() {
                view.onFail(3);
            }
        });
    }

    /***
     * 修改群头像
     * @param id 群ID
     * @param file 群头像
     */
    @Override
    public void onUpdateGroupFile(int id, File file) {
        RequestParams params = new RequestParams(UrlAddress.UPDATEGROUPS);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("id",id+""));
        list.add(new KeyValue("file", file));

        MultipartBody multipartBody = new MultipartBody(list,"UTF-8");
        params.setRequestBody(multipartBody);
        new Xutils().upload(params, new RequestMethod() {
            @Override
            public void onSuccess(String str) {
                Log.i("onSuccess",str);
                BaseResponse baseResponse = new Gson().fromJson(str, new TypeToken<BaseResponse>() {
                }.getType());
                if(FailMsg.showMsg(context,baseResponse.getCode())){
                    String s = new Gson().toJson(baseResponse.getObj());
                    GroupListBean groupListBean = new Gson().fromJson(s, new TypeToken<GroupListBean>() {
                    }.getType());
                    view.onSuccess(4,groupListBean);

                }else{
                    view.onFail(4);
                }
            }
            @Override
            public void onFail() {
                view.onFail(4);
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
                    view.onSuccess(5);
                }else{
                    view.onFail(5);
                }
            }
            @Override
            public void onFail() {
                view.onFail(5);
            }
        });
    }
}
