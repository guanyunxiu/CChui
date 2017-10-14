package chui.swsd.com.cchui.ui.apply.gonggao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhl.library.FlowTagLayout;
import com.maning.mndialoglibrary.MProgressDialog;
import com.multilevel.treelist.Node;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.adapter.TagAdapter;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.model.GongGaoBean;
import chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi.WriteRbActivity;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\31 0031.
 */

public class ReleaseGongGaoActivity extends BaseActivity implements GongGaoContract.view {
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.con_et)
    EditText conEt;
    @BindView(R.id.sub_btn)
    Button subBtn;
    @BindView(R.id.flow_layout)
    FlowTagLayout flowTagLayout;
    List<Node> lists = new ArrayList<>();
    GongGaoPresenter gongGaoPresenter;
    TagAdapter tagAdapter;
    MProgressDialog mMProgressDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_release_gaongao;
    }

    @Override
    protected void initViews() {
        initTitle(true,"发布公告");
        //注册EventBus,先订阅才能传值
        EventBus.getDefault().register(this);
        gongGaoPresenter = new GongGaoPresenter(this,this);
    }

    @Override
    protected void updateViews() {
        initAdapter();
    }
    public void initAdapter(){
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagAdapter(this);
        flowTagLayout.setAdapter(tagAdapter);
        lists.add(new Node());
        tagAdapter.onlyAddAll(lists);
        tagAdapter.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
            @Override
            public void onAddItemClicked(int position,List<Node> mDataList) {
                if(position == mDataList.size()-1) {
                    Intent intent = new Intent(ReleaseGongGaoActivity.this, SelectPeopleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) mDataList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    // startActivity(new Intent(MakeMeetActivity.this, SelectPeopleActivity.class),bundle);
                }else{
                    mDataList.remove(position);
                    lists.remove(position);
                    tagAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    @OnClick({R.id.sub_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sub_btn:
                mMProgressDialog = CommonUtil.configDialogDefault(this);
                mMProgressDialog.show("提交中...");
                onSubmit();
                break;
        }
    }
    public void onSubmit(){
        String title = titleEt.getText().toString();
        String content = conEt.getText().toString();
        if(TextUtils.isEmpty(title)){
            CommonUtil.showToast(this,"标题不能为空");
            return;
        }
        if(TextUtils.isEmpty(content)){
            CommonUtil.showToast(this,"内容不能为空");
            return;
        }
        if(lists.size() == 1){
            CommonUtil.showToast(this,"接收人不能为空");
            return;
        }
        List<String> strList = new ArrayList<>();
        for(int i=0;i<lists.size()-1;i++){
            strList.add(lists.get(i).getId()+"and"+lists.get(i).getName());
        }


        gongGaoPresenter.onRelease(title,content,strList);
    }
    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(List<Node> list){
        lists = list;
        list.add(new Node());
        tagAdapter.clearAndAddAll();
        tagAdapter.onlyAddAll(list);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

    @Override
    public void onSuccess() {
        mMProgressDialog.dismiss();
        CommonUtil.showToast(this,"发布成功");
        finish();
    }

    @Override
    public void onFail() {
        mMProgressDialog.dismiss();
    }

    @Override
    public void onSuccess(List<GongGaoBean> gongGaoBeanList) {

    }

    @Override
    public void onSuccess(GongGaoBean gongGaoBean) {

    }
}
