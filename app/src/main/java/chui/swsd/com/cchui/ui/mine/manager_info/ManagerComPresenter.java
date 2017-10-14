package chui.swsd.com.cchui.ui.mine.manager_info;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.model.CompanyDetailBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\19 0019.
 */

public class ManagerComPresenter implements ManagerComContract.presenter {
    ManagerComContract.view view;
    Context context;
    public  ManagerComPresenter(ManagerComContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSelect(int cid) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id",cid+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.COMPANYSELONE
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
                            CompanyDetailBean companyDetailBean = new Gson().fromJson(s, new TypeToken<CompanyDetailBean>() {
                            }.getType());
                            view.onSuccess(companyDetailBean);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onSelectDep(int cid) {
        Map<String,String> maps = new HashMap<>();
        maps.put("cid",cid+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.DEPARTMENTSEL
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
                            List<DepartmentBean> departmentBeanList = new Gson().fromJson(s, new TypeToken<List<DepartmentBean>>() {
                            }.getType());
                            view.onSuccess(departmentBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    @Override
    public void onTwoSubmit(int userid, String name, String province, String city, String county, String address, String longitude, String latitude, String starttime, String endtime, String scale, String account, String pass, String inbox,List<String> listId) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id",userid+"");
        maps.put("name",name);
        maps.put("province",province);
        maps.put("city",city);
        maps.put("county",county);
        maps.put("address",address);
        maps.put("longitude",longitude);
        maps.put("latitude",latitude);
        maps.put("starttime",starttime);
        maps.put("endtime",endtime);
        maps.put("scale",scale);
        maps.put("account",account);
        maps.put("pass",pass);
        maps.put("inbox",inbox);
        for(int i=0;i<listId.size();i++){
            maps.put("id"+(i+1),listId.get(i));
        }
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.COMPANYUPDATE
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

    @Override
    public void onUpdateDep(int id, String name) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id",id+"");
        maps.put("name",name);
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.DEPUPDATE
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

    @Override
    public void onAddDep( String departmentBean) {
        Map<String,String> maps = new HashMap<>();
        maps.put("cid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0)+"");
        maps.put("name1",departmentBean);

        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.DEPARTMENTINSERT
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
}
