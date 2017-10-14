package chui.swsd.com.cchui.ui.register;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.CompanyBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;
import chui.swsd.com.cchui.utils.CommonUtil;
import retrofit2.BaseUrl;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    RegisterContract.View view;
    private Context context;
    public RegisterPresenter(RegisterContract.View view, Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(String name, String phone, String pass,int account,String companyname,String companyid,String inbox) {
        Map<String,String> maps = new HashMap<>();
        maps.put("name",name);
        maps.put("phone",phone);
        maps.put("password",pass);
      /*  maps.put("account",account+"");
        if(account == 0){
            maps.put("companyname",companyname+"");
            maps.put("companyid",companyid+"");
            maps.put("inbox",inbox+"");
        }*/
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.REGISTER
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            view.onSuccess(responseBody.getId());
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    /**
     * 添加公司
     * @param name  公司名称
     * @param province 省
     * @param city 市
     * @param county 区
     * @param address 详细地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param starttime 上班时间
     * @param endtime 下班时间
     * @param scale 人员规模
     * @param account 管理员账号
     * @param pass 管理员密码
     * @param inbox 邀请码
     */
    @Override
    public void onTwoSubmit(String userid,String name, String province, String city, String county, String address, String longitude, String latitude, String starttime, String endtime, String scale, String account, String pass, String inbox) {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid",userid+"");
        maps.put("accountid",userid+"");
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

        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.COMPANYINSERT
                , maps, new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        view.onFail();
                    }
                    @Override
                    public void onNext(BaseResponse responseBody) {
                        if(FailMsg.showMsg(context,responseBody.getCode())) {
                            view.onSuccess(responseBody.getId());
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    /**
     * 添加部门
     * @param cid 公司ID
     * @param depList 部门名称
     */
    @Override
    public void onThreeSubmit(String cid, List<DepartmentBean> depList) {
        Map<String,String> maps = new HashMap<>();
        maps.put("cid",cid);
        for(int i=0;i<depList.size();i++){
            maps.put("name"+(i+1),depList.get(i).getName());
        }
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

    @Override
    public void onSelectCom() {
        Map<String,String> maps = new HashMap<>();
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.COMPANYSEL
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
                            List<CompanyBean> companyBeanList = new Gson().fromJson(s, new TypeToken<List<CompanyBean>>() {
                            }.getType());
                            view.onSuccess(companyBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }

    /**
     * 更新用户信息
     * @param userid 用户ID
     * @param companyid 公司ID
     * @param companyname 公司名字
     * @param inbox 邀请码
     */
    @Override
    public void onUpdateData(String userid, int companyid, String companyname, String inbox) {
        Map<String,String> maps = new HashMap<>();
        maps.put("id",userid+"");
        maps.put("companyid",companyid+"");
        maps.put("companyname",companyname);
        maps.put("inbox",inbox+"");
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.COMUPDATEDATA
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
