package chui.swsd.com.cchui.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import chui.swsd.com.cchui.model.JsonBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\14 0014.
 */

public class AreaUtil {

    public  static ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
