package chui.swsd.com.cchui.ui.apply.shenpi.sp_content;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\5 0005.
 */

public class SpContentPresenter implements SpContentContract.precsenter {
    SpContentContract.view view;
    Context context;
    public SpContentPresenter(SpContentContract.view view,Context context){
        this.view = view;
        this.context = context;
    }

    /**
     * 查询审批内容
     * @param page 页数
     * @param type 0：我发起的 1：我审批的
     * @param screen 5：全部 4：报销 1：出差 2.外出 0.请假 3.加班
     * @param ft 2：全部 0：审批中 1：审批完成
     * @param state 0:待审批的 1：我已审批的
     */
    @Override
    public void onSubmit(int page, int type, int screen, int ft,int state) {
        Map<String,String> maps = new HashMap<>();
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        maps.put("page",page+"");
        maps.put("type",type+"");
        maps.put("screen",screen+"");
        maps.put("ft",ft+"");
        maps.put("state",state+"");
        RetrofitClient.getInstance(context).createBaseApi().get(UrlAddress.WORKSEL
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
                            List<ShenPiBean> shenPiBeanList = new Gson().fromJson(s, new TypeToken<List<ShenPiBean>>() {
                            }.getType());
                            view.onSuccess(shenPiBeanList);
                        }else{
                            view.onFail();
                        }
                    }
                });
    }
}
