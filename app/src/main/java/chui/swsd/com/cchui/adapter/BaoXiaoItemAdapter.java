package chui.swsd.com.cchui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.model.GroupBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class BaoXiaoItemAdapter extends BaseQuickAdapter<BaoXiaoBean> {
    public BaoXiaoItemAdapter(List<BaoXiaoBean> list) {
        super(R.layout.activity_sp_baoxiao_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, final BaoXiaoBean baoXiaoBean) {
        TextView delTv = (TextView) helper.getView(R.id.del_tv);
        ((TextView) helper.getView(R.id.title_tv)).setText("报销明细("+baoXiaoBean.getId()+")");
        delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDelItemClicked(baoXiaoBean);
                }
            }
        });
    }
    public interface OnItemClickListener {
        void onDelItemClicked(BaoXiaoBean baoXiaoBean);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
