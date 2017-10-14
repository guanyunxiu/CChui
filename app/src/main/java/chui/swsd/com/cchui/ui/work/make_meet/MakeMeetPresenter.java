package chui.swsd.com.cchui.ui.work.make_meet;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.net.FailMsg;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.net.util.BaseResponse;
import chui.swsd.com.cchui.net.util.BaseSubscriber;
import chui.swsd.com.cchui.net.util.ExceptionHandle;
import chui.swsd.com.cchui.net.util.RetrofitClient;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\21 0021.
 */

public class MakeMeetPresenter implements MakeMeetContract.presenter {
    MakeMeetContract.view view;
    Context context;
    public MakeMeetPresenter(MakeMeetContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(String title, String starttime, String usedtime, List<String> listId) {
        Map<String,String> maps = new HashMap<>();
        maps.put("title",title);
        maps.put("starttime",starttime);
        maps.put("usedtime",usedtime);
        maps.put("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)+"");
        for(int i=0;i<listId.size();i++){
            maps.put("id"+(i+1),listId.get(i));
        }
        RetrofitClient.getInstance(context).createBaseApi().post(UrlAddress.MAKEINSERT
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
