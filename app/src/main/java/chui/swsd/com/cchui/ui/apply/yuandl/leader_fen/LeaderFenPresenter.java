package chui.swsd.com.cchui.ui.apply.yuandl.leader_fen;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.BaoXiaoDetailsBean;
import chui.swsd.com.cchui.model.ChuChaiDetailsBean;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.model.GoOutDetailsBean;
import chui.swsd.com.cchui.model.JiaBanDetailsBean;
import chui.swsd.com.cchui.model.LeaveDetailsBean;
import chui.swsd.com.cchui.model.YdlScoreBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\11 0011.
 */

public class LeaderFenPresenter implements LeaderFenContract.presenter {
    LeaderFenContract.view view;
    Context context;
    public LeaderFenPresenter(LeaderFenContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSelectDep() {
        Map<String,String> maps = new HashMap<>();
        maps.put("cid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0)+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.DEPSEL
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
    public void onSelectDepCon(String departname,String time) {
        Map<String,String> maps = new HashMap<>();
        maps.put("cid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0)+"");
        maps.put("did",departname);
        maps.put("time",time);
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.DEPSELBYNAME
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
                            List<YdlScoreBean> departmentBeanList = new Gson().fromJson(s, new TypeToken<List<YdlScoreBean>>() {
                            }.getType());
                            view.onSuccessCon(departmentBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
