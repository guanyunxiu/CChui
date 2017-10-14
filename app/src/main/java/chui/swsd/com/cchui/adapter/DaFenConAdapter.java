package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.DaFenBean;
import chui.swsd.com.cchui.model.DaFenConBean;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.model.GroupListBean;
import chui.swsd.com.cchui.net.UrlAddress;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class DaFenConAdapter extends BaseQuickAdapter<DaFenConBean.ContentsBean> {
    private Context context;
    DaFenConStripAdapter daFenConStripAdapter;
    public DaFenConAdapter(List<DaFenConBean.ContentsBean> list, Context context) {
        super(R.layout.fragment_dafen_con_item,list);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, final DaFenConBean.ContentsBean daFenBean) {
        final int position = helper.getAdapterPosition();
        helper.setText(R.id.title_tv,daFenBean.getTitle());

        RecyclerView rvList = helper.getView(R.id.strip_rlv);
        if(daFenBean.getStrip()!=null) {
            rvList.setVisibility(View.VISIBLE);
            rvList.setLayoutManager(new LinearLayoutManager(mContext));
            daFenConStripAdapter = new DaFenConStripAdapter(daFenBean.getStrip(), mContext);
            rvList.setAdapter(daFenConStripAdapter);
        }else{
                rvList.setVisibility(View.GONE);
        }
        if(daFenBean.getNodeList() != null) {
            if (daFenBean.getNodeList().size() == 0) {
                helper.setVisible(R.id.peoples_tv, false);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < daFenBean.getNodeList().size(); i++) {
                    if (i == daFenBean.getNodeList().size() - 1) {
                        stringBuilder.append(daFenBean.getNodeList().get(i).getName());
                    } else {
                        stringBuilder.append(daFenBean.getNodeList().get(i).getName() + "、");
                    }
                }
                helper.setVisible(R.id.peoples_tv, true);
                helper.setText(R.id.peoples_tv, stringBuilder.toString());
            }
        }
        if(daFenBean.getFt() == 0){//未评分
            helper.setText(R.id.score_btn, "去评分");
        }else if(daFenBean.getFt() == 1){//已评分
            helper.setText(R.id.score_btn, "已评分");
            helper.setBackgroundRes(R.id.score_btn,R.drawable.btn_bg2_gray);
        }
        helper.setOnClickListener(R.id.score_btn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onDelItemClicked(position);
            }
        });
    }
    public interface OnItemClickListener {
        void onDelItemClicked(int i);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
