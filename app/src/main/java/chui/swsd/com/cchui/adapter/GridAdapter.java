package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.work.select_people.SelectPeopleActivity;
import chui.swsd.com.cchui.widget.SelectableRoundedImageView;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\6 0006.
 */

public class GridAdapter extends BaseAdapter {

    private List<GroupListBean.UserSimple> list;
    Context context;
    private boolean isCreated;
    private int groupId;

    public GridAdapter(Context context, List<GroupListBean.UserSimple> list,int groupId,boolean isCreated) {
        if (list.size() >= 31) {
            this.list = list.subList(0, 30);
        } else {
            this.list = list;
        }

        this.context = context;
        this.groupId = groupId;
        this.isCreated = isCreated;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.social_chatsetting_gridview_item, parent, false);
        }
        SelectableRoundedImageView iv_avatar = (SelectableRoundedImageView) convertView.findViewById(R.id.iv_avatar);
        TextView tv_username = (TextView) convertView.findViewById(R.id.tv_username);
        ImageView badge_delete = (ImageView) convertView.findViewById(R.id.badge_delete);

        // 最后一个item，减人按钮
        if (position == getCount() - 1 && isCreated) {
            tv_username.setText("");
            badge_delete.setVisibility(View.GONE);
            iv_avatar.setImageResource(R.mipmap.icon_btn_deleteperson);

            iv_avatar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SelectPeopleActivity.class);//移除群组成员
                    intent.putExtra("flag",3);
                    intent.putExtra("groupId",groupId);
                    intent.putExtra("data", (Serializable) list);
                    context.startActivity(intent);
                }

            });
        } else if ((isCreated && position == getCount() - 2) || (!isCreated && position == getCount() - 1)) {
            tv_username.setText("");
            badge_delete.setVisibility(View.GONE);
            iv_avatar.setImageResource(R.mipmap.jy_drltsz_btn_addperson);

            iv_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("groupId",groupId+"***");
                    Intent intent = new Intent(context, SelectPeopleActivity.class);//添加群组成员
                    intent.putExtra("flag",2);
                    intent.putExtra("groupId",groupId);
                    intent.putExtra("data", (Serializable) list);
                    context.startActivity(intent);

                }
            });
        } else { // 普通成员
            final GroupListBean.UserSimple bean = list.get(position);
            tv_username.setText(bean.getUsername());
            if(!TextUtils.isEmpty(bean.getUserheadimg())){
                Glide.with(context).load(UrlAddress.URLAddress + bean.getUserheadimg()).into(iv_avatar);
            }
            iv_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  RongIM.getInstance().startPrivateChat(this,bean.get,node.getName());
                  /*  UserInfo userInfo = new UserInfo(bean.getUserId(), bean.getName(), TextUtils.isEmpty(bean.getPortraitUri().toString()) ? Uri.parse(RongGenerate.generateDefaultAvatar(bean.getName(), bean.getUserId())) : bean.getPortraitUri());
                    Intent intent = new Intent(context, UserDetailActivity.class);
                    Friend friend = CharacterParser.getInstance().generateFriendFromUserInfo(userInfo);
                    intent.putExtra("friend", friend);
                    intent.putExtra("conversationType", Conversation.ConversationType.GROUP.getValue());
                    //Groups not Serializable,just need group name
                    intent.putExtra("groupName", mGroup.getName());
                    intent.putExtra("type", CLICK_CONVERSATION_USER_PORTRAIT);
                    context.startActivity(intent);*/
                }

            });

        }

        return convertView;
    }

    @Override
    public int getCount() {
        if (isCreated) {
            return list.size() + 2;
        } else {
            return list.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<GroupListBean.UserSimple> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}