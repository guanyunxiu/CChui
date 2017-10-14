package chui.swsd.com.cchui.ui.apply.shenpi.sp_content;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.MyRadioGroup;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\24 0024.
 */

public class SpShaiShuaiActivity extends BaseSwipeBackActivity {

    @BindView(R.id.zt_tv)
    TextView ztTv;
    @BindView(R.id.radiogroup_01)
    RadioGroup radioGroup01;
    @BindView(R.id.radiogroup_02)
    MyRadioGroup radioGroup02;
    @BindView(R.id.sub_btn)
    Button subBtn;
    String ztstr, typestr;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb21)
    RadioButton rb21;
    @BindView(R.id.rb22)
    RadioButton rb22;
    @BindView(R.id.rb23)
    RadioButton rb23;
    @BindView(R.id.rb24)
    RadioButton rb24;
    @BindView(R.id.rb25)
    RadioButton rb25;
    @BindView(R.id.rb26)
    RadioButton rb26;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sp_wsxsx;
    }

    @Override
    protected void initViews() {
        initTitle(true, "筛选");
        ztstr = getIntent().getStringExtra("ztstr");
        typestr = getIntent().getStringExtra("typestr");
        if(SpContentActivity.typePos == 1){
            radioGroup01.setVisibility(View.VISIBLE);
            ztTv.setVisibility(View.VISIBLE);
        }else if(SpContentActivity.typePos ==0){
            radioGroup01.setVisibility(View.GONE);
            ztTv.setVisibility(View.GONE);
        }
        setDataSel();
    }
    public void setDataSel(){
        if(!TextUtils.isEmpty(ztstr)) {
            if (rb1.getText().equals(ztstr)) {
                rb1.setChecked(true);
            } else if (rb2.getText().equals(ztstr)) {
                rb2.setChecked(true);
            } else if (rb3.getText().equals(ztstr)) {
                rb3.setChecked(true);
            }
        }
        if(!TextUtils.isEmpty(typestr)) {
            if (rb21.getText().equals(typestr)) {
                rb21.setChecked(true);
            } else if (rb22.getText().equals(typestr)) {
                rb22.setChecked(true);
            } else if (rb23.getText().equals(typestr)) {
                rb23.setChecked(true);
            } else if (rb24.getText().equals(typestr)) {
                rb24.setChecked(true);
            } else if (rb25.getText().equals(typestr)) {
                rb25.setChecked(true);
            } else if (rb26.getText().equals(typestr)) {
                rb26.setChecked(true);
            }
        }
    }

    public void initRadioGroup() {
        radioGroup01.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) SpShaiShuaiActivity.this.findViewById(radioButtonId);
                //更新文本内容，以符合选中项
                CommonUtil.showToast(SpShaiShuaiActivity.this, rb.getText() + "&&&");
            }
        });
    }

    @Override
    protected void updateViews() {

    }


    @OnClick(R.id.sub_btn)
    public void onClick() {
        RadioButton rb = (RadioButton) SpShaiShuaiActivity.this.findViewById(radioGroup01.getCheckedRadioButtonId());
        RadioButton rb2 = (RadioButton) SpShaiShuaiActivity.this.findViewById(radioGroup02.getCheckedRadioButtonId());
        Intent intent = getIntent();
        intent.putExtra("ztstr", rb.getText());
        intent.putExtra("typestr", rb2.getText());
        setResult(RESULT_OK, intent);
        EventBus.getDefault().post("筛选,"+rb.getText()+","+rb2.getText());
        finish();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }
}
