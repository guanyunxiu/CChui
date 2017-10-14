package chui.swsd.com.cchui.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.List;

import chui.swsd.com.cchui.model.PushBean;
import chui.swsd.com.cchui.model.ShenPiBean;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.ui.apply.file.FileDetailActivity;
import chui.swsd.com.cchui.ui.apply.huiyi.HuiYiDetailsActivity;
import chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi.RedDetailsRBActivity;
import chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi.RedDetailsYBActivity;
import chui.swsd.com.cchui.ui.apply.rizhi.reddetails_rizhi.RedDetailsZBActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpBXDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpCCDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpJBDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpQjDetailsActivity;
import chui.swsd.com.cchui.ui.apply.shenpi.sp_con_details.SpWCDetailsActivity;
import chui.swsd.com.cchui.ui.work.notice_details.NoticeDetailsActivity;
import cn.jpush.android.api.JPushInterface;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\28 0028.
 */

public class JpushReceiver extends BroadcastReceiver {
    static String type,objectid;
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i("JpushReceiver", json + "******json");
            if(!TextUtils.isEmpty(json)) {
                try {
                    Log.i("JpushReceiver", json + "333******json");
                    JSONObject jsonObject = new JSONObject(json);
                    type = jsonObject.getString("category");
                    objectid = jsonObject.getString("objectid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String jsons = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i("JpushReceiver", jsons + "*22*****33");
            if(MainActivity.isActivity) {
                Intent mainIntent = new Intent();
                mainIntent.setClass(context.getApplicationContext(), MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.getApplicationContext().startActivity(mainIntent);
            }
                if (type.equals("0")) { //请假
                    openShenpi(context, objectid, SpQjDetailsActivity.class);
                } else if (type.equals("1")) {//出差
                    openShenpi(context, objectid, SpCCDetailsActivity.class);
                } else if (type.equals("2")) {//外出
                    openShenpi(context, objectid, SpWCDetailsActivity.class);
                } else if (type.equals("3")) {//加班
                    openShenpi(context, objectid, SpJBDetailsActivity.class);
                } else if (type.equals("4")) {//报销
                    openShenpi(context, objectid, SpBXDetailsActivity.class);
                } else if (type.equals("51")) {//日报
                    openRz(context, objectid, RedDetailsRBActivity.class);
                } else if (type.equals("52")) {//周报
                    openRz(context, objectid, RedDetailsZBActivity.class);
                } else if (type.equals("52")) {//月报
                    openRz(context, objectid, RedDetailsYBActivity.class);
                } else if (type.equals("6")) {//公告
                    openIntent(context, objectid, NoticeDetailsActivity.class);
                } else if (type.equals("7")) {//会议预约
                    openIntent(context, objectid, HuiYiDetailsActivity.class);
                } else if (type.equals("8")) {//文件管理
                    openIntent(context, objectid, FileDetailActivity.class);
                } else if (type.equals("100")) {//退出登录

                }

        }
    }
    public void openShenpi(final Context context, String objectid, Class cl){
        final Intent goLogoIntent = new Intent();
        goLogoIntent.setClass(context.getApplicationContext(),cl);
        goLogoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goLogoIntent.putExtra("id", Integer.parseInt(objectid));
        goLogoIntent.putExtra("spStatus", 1);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                context.getApplicationContext().startActivity(goLogoIntent);
            }
        }, 1000);
    }
    public void openRz(final Context context, String objectid, Class cl){
        final Intent goLogoIntent = new Intent();
        goLogoIntent.setClass(context.getApplicationContext(),cl);
        goLogoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goLogoIntent.putExtra("id", Integer.parseInt(objectid));
        new Handler().postDelayed(new Runnable(){
            public void run() {
                context.getApplicationContext().startActivity(goLogoIntent);
            }
        }, 1000);
    }
    public void openIntent(final Context context, String objectid,Class cl){
        final Intent goLogoIntent = new Intent();
        goLogoIntent.setClass(context.getApplicationContext(),cl);
        goLogoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goLogoIntent.putExtra("id", Integer.parseInt(objectid));
        new Handler().postDelayed(new Runnable(){
            public void run() {
                context.getApplicationContext().startActivity(goLogoIntent);
            }
        }, 1000);
    }

}
