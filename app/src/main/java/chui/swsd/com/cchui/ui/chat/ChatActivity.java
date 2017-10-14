package chui.swsd.com.cchui.ui.chat;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.ui.group.GroupDetails;
import chui.swsd.com.cchui.utils.CommonUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\28 0028.
 */

public class ChatActivity extends BaseActivity {
    boolean isgroup = false;
    String rongId;
    public static ChatActivity chatActivity;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initViews() {
        chatActivity = this;
        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData()
                .getLastPathSegment().toUpperCase(Locale.US));
        if(mConversationType.equals(Conversation.ConversationType.GROUP)){
            rongId = getIntent().getData().getQueryParameter("targetId");
            initTitle(true, getIntent().getData().getQueryParameter("title"));
            isgroup = false;
        }else {
            initTitle(true, getIntent().getData().getQueryParameter("title"));
            isgroup = true;
        }
        RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
    }

    @Override
    protected void updateViews() {

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grouplist, menu);
        if (isgroup) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_grouplist:
              Intent intent = new Intent(this, GroupDetails.class);
                intent.putExtra("id",rongId);
                startActivity(intent);
        }
        return true;
    }
}
