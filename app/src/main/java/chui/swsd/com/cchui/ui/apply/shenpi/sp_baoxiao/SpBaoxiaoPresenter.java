package chui.swsd.com.cchui.ui.apply.shenpi.sp_baoxiao;

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
import chui.swsd.com.cchui.model.BaoXiaoBean;
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
 * Created by 关云秀 on 2017\9\5 0005.
 */

public class SpBaoxiaoPresenter implements SpBaoxiaoContract.presenter {
    SpBaoxiaoContract.view view;
    Context context;
    public SpBaoxiaoPresenter(SpBaoxiaoContract.view view,Context context){
        this.view = view;
        this.context = context;
    }
    @Override
    public void onSubmit(List<BaoXiaoBean> listCon, ArrayList<String> listPhoto, List<String> listId) {
        RequestParams params = new RequestParams(UrlAddress.APPLAYINSERT);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("userid", BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0)));
        for(int i=0;i<listCon.size();i++){
            BaoXiaoBean baoXiaoBean = listCon.get(i);
            list.add(new KeyValue("money"+(i+1),baoXiaoBean.getMoney()+""));
            list.add(new KeyValue("category"+(i+1),baoXiaoBean.getType()+""));
            list.add(new KeyValue("describe"+(i+1),baoXiaoBean.getDetails()));
        }
        for(int i=0;i<listPhoto.size();i++){
            list.add(new KeyValue("file"+(i+1),new File(listPhoto.get(i))));
        }
        for(int i=0;i<listId.size();i++){
            list.add(new KeyValue("id"+(i+1),listId.get(i)));
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
                    view.onSuccess();
                }else{
                    view.onFail();
                }
            }
            @Override
            public void onFail() {
                view.onFail();
            }
        });
    }
}
