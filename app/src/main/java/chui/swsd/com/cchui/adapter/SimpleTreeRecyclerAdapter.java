package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.utils.CommonUtil;

/**
 * Created by zhangke on 2017-1-14.
 */
public class SimpleTreeRecyclerAdapter extends TreeRecyclerAdapter {
    private boolean chboolean;
    public SimpleTreeRecyclerAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand,boolean chboolean) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
        this.chboolean = chboolean;
    }

    public SimpleTreeRecyclerAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHoder(View.inflate(mContext, R.layout.fragment_ost_item,null));
    }
    public interface OnItemClickListener {
        void onItem(int position,Node node);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void onBindViewHolder(final Node node, RecyclerView.ViewHolder holder, final int position) {

        final MyHoder viewHolder = (MyHoder) holder;
        //todo do something
        if(chboolean) {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            if(node.isChecked()) {
               if (node.getParent() != null) {
                    setNodeParentChecked(node.getParent(), true);
                }
               // specialUpdate();
           }
        }else{
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(node,viewHolder.checkBox.isChecked());
                EventBus.getDefault().post("点击了");
            }
        });

        if (node.isChecked()){
            viewHolder.checkBox.setChecked(true);
        }else {
            viewHolder.checkBox.setChecked(false);
        }
      /*  if(node.getpId().equals("1")){//父节点
            if(node.getChildren().size() == 0){
                viewHolder.checkBox.setVisibility(View.INVISIBLE);
                viewHolder.numTv.setText(0+"");
            }else{
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            }
        }*/

        if (!node.getpId().equals("1")) {  //子
            viewHolder.iconImg.setVisibility(View.INVISIBLE);
            viewHolder.numTv.setVisibility(View.GONE);
            viewHolder.view.setVisibility(View.GONE);
            viewHolder.view_line.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setEnabled(true);
          /*  viewHolder.nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!node.getpId().equals("1")) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItem(position, node);
                        }
                    }
                }
            });*/
        } else {//父
            viewHolder.iconImg.setVisibility(View.VISIBLE);
            viewHolder.numTv.setVisibility(View.VISIBLE);
            viewHolder.numTv.setText(node.getChildren().size()+"");
            viewHolder.view.setVisibility(View.VISIBLE);
            viewHolder.view_line.setVisibility(View.GONE);
            if(node.getChildren().size()>0) {
                viewHolder.checkBox.setEnabled(true);
                viewHolder.iconImg.setImageResource(node.getIcon());
            }else{
                viewHolder.checkBox.setEnabled(false);
            }
        }
        viewHolder.nameTv.setText(node.getName());


    }

    class MyHoder extends RecyclerView.ViewHolder{

        public TextView nameTv,numTv;
        public ImageView iconImg;
        private View view,view_line;
        private CheckBox checkBox;
        private LinearLayout rootLv;
        public MyHoder(View itemView) {
            super(itemView);
            checkBox = (CheckBox)itemView.findViewById(R.id.cb_select_tree);
            nameTv = (TextView) itemView
                    .findViewById(R.id.name_tv);
            iconImg = (ImageView)itemView.findViewById(R.id.icon);
            numTv = (TextView)itemView.findViewById(R.id.num_tv);
            view = (View)itemView.findViewById(R.id.view);
            view_line = (View)itemView.findViewById(R.id.view_line);
            rootLv = (LinearLayout)itemView.findViewById(R.id.root_lv);
        }

    }
    private void specialUpdate() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

}
