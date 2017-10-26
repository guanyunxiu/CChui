package chui.swsd.com.cchui.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.model.DepConBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.contacts.ost.OstContract;
import chui.swsd.com.cchui.ui.mine.about_me.AboutMeActivity;
import chui.swsd.com.cchui.ui.mine.kaoqin.MyKaoQinActivity;
import chui.swsd.com.cchui.ui.mine.my_info.MyInfoActivity;
import chui.swsd.com.cchui.ui.mine.my_jcxx.JcxxActivity;
import chui.swsd.com.cchui.ui.mine.set.SetActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.phoneNumber;

/**
 * 内容：我的
 * Created by 关云秀 on 2017\8\7 0007.
 */

public class MineFragment extends BaseFragment{
    @BindView(R.id.imageview)
    CircleImageView imageview;
    @BindView(R.id.info_rv)
    RelativeLayout infoRv;
    @BindView(R.id.my_kq_lv)
    LinearLayout myKqLv;
    @BindView(R.id.my_jcxx_lv)
    LinearLayout myJcxxLv;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.my_set_lv)
    LinearLayout mySetLv;
    @BindView(R.id.my_help_tv)
    TextView myHelpTv;
    @BindView(R.id.my_abut_lv)
    LinearLayout myAbutLv;
    @BindView(R.id.photo_tv)
    TextView photoTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.depart_tv)
    TextView departTv;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {
       // initTitle(false, "我的");
        titleName.setText("我的");
        initData();
    }
    public void initData(){
        String heading = BaseApplication.mSharedPrefUtil.getString(SharedConstants.PHOTO,"");
        String name = BaseApplication.mSharedPrefUtil.getString(SharedConstants.NAME,"");
        String departName = BaseApplication.mSharedPrefUtil.getString(SharedConstants.DEPARTMENT,"");
        if(TextUtils.isEmpty(heading)){
            photoTv.setText(CommonUtil.subName(name));
            photoTv.setVisibility(View.VISIBLE);
            imageview.setVisibility(View.GONE);
        }else{
            photoTv.setVisibility(View.GONE);
            imageview.setVisibility(View.VISIBLE);
            Glide.with(this).load(UrlAddress.URLAddress+heading).into(imageview);
        }
        nameTv.setText(name);
        if(departName.equals("0")){
            departTv.setText("");
        }else {
            departTv.setText(departName);
        }
    }

    @Override
    protected void updateViews() {
    }


    @OnClick({R.id.info_rv, R.id.my_kq_lv, R.id.my_jcxx_lv, R.id.my_set_lv, R.id.my_help_tv, R.id.my_abut_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_rv:
                startActivity(new Intent(getActivity(), MyInfoActivity.class));
                break;
            case R.id.my_kq_lv:
                startActivity(new Intent(getActivity(), MyKaoQinActivity.class));
                break;
            case R.id.my_jcxx_lv:
                startActivity(new Intent(getActivity(), JcxxActivity.class));
                break;
            case R.id.my_set_lv:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.my_help_tv:
                String phone = myHelpTv.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            case R.id.my_abut_lv:
                startActivity(new Intent(getActivity(), AboutMeActivity.class));
                break;
        }
    }

}
