package chui.swsd.com.cchui.ui.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.apply.gonggao.GongGaoActivity;
import chui.swsd.com.cchui.ui.apply.gonggao.ReleaseGongGaoActivity;
import chui.swsd.com.cchui.ui.apply.rizhi.write_rizhi.WriteRbActivity;
import chui.swsd.com.cchui.ui.contacts.group.GroupContract;
import chui.swsd.com.cchui.ui.contacts.group.GroupPrecenter;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

/**
 * 内容：消息列表
 * Created by 关云秀 on 2017\8\7 0007.
 */

public class InfoFragment extends BaseFragment{
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_info;
    }
    @Override
    protected void initViews() {
       // initTitle(false,"消息");
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_addgrou);
        titleName.setText("消息");
        setHasOptionsMenu(true);
        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                .build();
        if (fragment != null) {
            fragment.setUri(uri);
        }
    }

    @Override
    protected void updateViews() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_group:
                        Intent intent = new Intent(mContext, SelectPeopleActivity.class);
                        intent.putExtra("flag",1);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addgrou, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_group:
               // startActivity(new Intent(GongGaoActivity.this,ReleaseGongGaoActivity.class));
                Intent intent = new Intent(mContext, SelectPeopleActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
        }
        return true;
    }


}
