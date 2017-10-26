package chui.swsd.com.cchui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.model.UserBean;
import chui.swsd.com.cchui.net.UrlAddress;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\8 0008.
 */

public class CommonUtil {
    /**
     *  toast显示
     * @param context 上下文
     * @param content 显示内容
     */
    public static void showToast(Context context, String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
    public static String subName(String name){
        return name.substring(name.length()-2,name.length());
    }
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    public static MProgressDialog configDialogDefault(Context context) {
        //新建一个Dialog
        MProgressDialog mMProgressDialog = new MProgressDialog(context);
        mMProgressDialog.setBackgroundWindowColor(R.color.b_t);
        mMProgressDialog.setCanceledOnTouchOutside(false);
        return mMProgressDialog;
    }
    //android获取一个用于打开Word文件的intent

    public static Intent getWordFileIntent(String param )

    {

        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addCategory("android.intent.category.DEFAULT");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param ));

        intent.setDataAndType(uri, "application/msword");

        return intent;

    }
    public static void saveUser(String sessionId,UserBean userBean,String pass){
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.PHONE,userBean.getPhone());  //手机号
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.USERNAME,userBean.getName());  //用户名
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.PASSWORD,pass);  //密码
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.ID,userBean.getId());  //ID
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.TOKEN,userBean.getToken());  //TOKEN
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.NAME,userBean.getName());  //姓名
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.PHOTO, userBean.getHeadimg());
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.SESSIONID,sessionId);  //SESSIONID
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.BASICSCORE,userBean.getBasis());//基础分
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.WORKSCORE,userBean.getWork());//工作分
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.LIFESCORE,userBean.getLife());//生活分
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.JOBTITLE,userBean.getJobtitle());//员工还是领导
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.COMID,userBean.getCompanyid());//公司ID
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.COMNAME,userBean.getCompanyname());//公司名称
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.MANAGERFLAG,userBean.getAccount());//是否是管理员
        BaseApplication.mSharedPrefUtil.putInt(SharedConstants.DAFENFLAG,userBean.getManage());//0为普通用户，1为打分
        BaseApplication.mSharedPrefUtil.putString(SharedConstants.DEPARTMENT,userBean.getDepartmentid());//部门名称
        BaseApplication. mSharedPrefUtil.commit();
    }
    public static int getScreen(String str){
        int screen = 5;
        if(!TextUtils.isEmpty(str)){
            if(str.equals("全部")){
                screen = 5;
            }else if(str.equals("报销")){
                screen = 4;
            }else if(str.equals("出差")){
                screen = 1;
            }else if(str.equals("外出")){
                screen = 2;
            }else if(str.equals("请假")){
                screen = 0;
            }else if(str.equals("加班")){
                screen = 3;
            }
        }

        return screen;
    }
    public static int getFt(String str){
        int ft = 2;
        if(!TextUtils.isEmpty(str)){
            if(str.equals("全部")){
                ft = 2;
            }else if(str.equals("审批中")){
                ft = 0;
            }else if(str.equals("审批完成")){
                ft = 1;
            }
        }
        return  ft;
    }
    public static String getStatus(int flag){
        if(flag == 0){
            return "发起申请";
        }else if(flag == 1){
            return "等待审批";
        }else if(flag == 2){
            return "正在审批";
        }else if(flag == 3){
            return "审批通过";
        }else{
            return "审批不通过";
        }
    }
    public static String getJjrStatus(int flag){
        if(flag == 0){
            return "否";
        }else{
            return "是";
        }
    }
    public static String getShStatus(int flag){
        if(flag == 2){
            return "审核中";
        }else if(flag == 3){
            return "审批通过";
        }else if(flag == 4){
            return "审批拒绝";
        }else{
            return null;
        }
    }
    public static int getRbStatus(String str){
        if(str.equals("日报")){
            return 0;
        }else if(str.equals("周报")){
            return 1;
        }else if(str.equals("月报")){
            return 2;
        }else{
            return 3;
        }
    }
    public static List<Node> getList(List<DepConBean> list){
         List<Node> mDatas = new ArrayList<Node>();
        for(int m=2;m<list.size()+2;m++){
            DepConBean depConBean = list.get(m-2);
            String s = m+"";
            mDatas.add(new Node(s,"",1+"",depConBean.getName()));
            for(DepConBean.UserBasisBean userBasisBean:depConBean.getUserBasis()){
                UserInfo userInfo = new UserInfo(userBasisBean.getRongid(), userBasisBean.getUsername(), Uri.parse(""));
                RongIM.getInstance().setCurrentUserInfo(userInfo);
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                mDatas.add(new Node(userBasisBean.getUserid()+"",userBasisBean.getRongid()+"",s,userBasisBean.getUsername()));
            }
        }
        return mDatas;
    }
    public static String getSubName(String name){
        String subName;
        if(name.length()>2){
            subName = name.substring(name.length()-2,name.length());
            return subName;
        }else{
            return name;
        }
    }
}
