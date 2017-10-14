package chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.BaoXiaoDetailsBean;
import chui.swsd.com.cchui.model.ChuChaiDetailsBean;
import chui.swsd.com.cchui.model.GoOutDetailsBean;
import chui.swsd.com.cchui.model.JiaBanDetailsBean;
import chui.swsd.com.cchui.model.LeaveDetailsBean;
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
 * Created by 关云秀 on 2017\9\7 0007.
 */

public class SpDetailsPresenter implements SpDetailsContract.presenter {
    SpDetailsContract.view view;
    Context context;
    public SpDetailsPresenter(SpDetailsContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(final int flag, int id) {
        String address = null;
        Map<String,String> maps = new HashMap<>();
        maps.put("id",id+"");
        if(flag == 0){//请假
            address = UrlAddress.ONELEAVE;
        }else if(flag == 1){//出差
            address = UrlAddress.ONEEVECTION;
        }else if(flag == 2){//外出
            address = UrlAddress.ONEGOOUT;
        }else if(flag == 3){//加班
            address = UrlAddress.ONEOVERTIME;
        }else if(flag == 4){//报销
            address = UrlAddress.ONEAPPLAY;
        }
        RetrofitClient.getInstance(context).createBaseApi().get(address
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
                            if(flag == 0){//请假
                                LeaveDetailsBean leavedetails = new Gson().fromJson(s, new TypeToken<LeaveDetailsBean>() {
                                }.getType());
                                view.onSuccess(leavedetails);
                            }else if(flag == 1){
                                ChuChaiDetailsBean chuchai = new Gson().fromJson(s, new TypeToken<ChuChaiDetailsBean>() {
                                }.getType());
                                view.onSuccess(chuchai);
                            }else if(flag == 2){
                                GoOutDetailsBean goout = new Gson().fromJson(s, new TypeToken<GoOutDetailsBean>() {
                                }.getType());
                                view.onSuccess(goout);
                            }else if(flag == 3){
                                JiaBanDetailsBean jiaban = new Gson().fromJson(s, new TypeToken<JiaBanDetailsBean>() {
                                }.getType());
                                view.onSuccess(jiaban);
                            }else if(flag == 4){
                                BaoXiaoDetailsBean baoxiao = new Gson().fromJson(s, new TypeToken<BaoXiaoDetailsBean>() {
                                }.getType());
                                view.onSuccess(baoxiao);
                            }
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    /**
     * 提交审核
     * @param flag
     * @param uuid
     * @param ft
     */
    @Override
    public void onSpSubmit(final int flag,String uuid,int ft) {
        String address = null;
        Map<String,String> maps = new HashMap<>();
        maps.put("uuid",uuid+"");
        maps.put("auditid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("ft",ft+"");
        if(flag == 0){//请假
            address = UrlAddress.LEVELUPDATE;
        }else if(flag == 1){//出差
            address = UrlAddress.EVECTIONUPDATE;
        }else if(flag == 2){//外出
            address = UrlAddress.GOOUTUPDATE;
        }else if(flag == 3){//加班
            address = UrlAddress.OVERTIMEUPDATE;
        }else if(flag == 4){//报销
            address = UrlAddress.APPLAYUPDATE;
        }
        RetrofitClient.getInstance(context).createBaseApi().get(address
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
