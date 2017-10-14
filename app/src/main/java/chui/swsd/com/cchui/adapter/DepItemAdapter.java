package chui.swsd.com.cchui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.BaoXiaoBean;
import chui.swsd.com.cchui.model.DepartmentBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class DepItemAdapter extends BaseQuickAdapter<DepartmentBean> {
    public DepItemAdapter(List<DepartmentBean> list) {
        super(R.layout.activity_zc_department_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, final DepartmentBean departmentBean) {
        TextView delTv = (TextView) helper.getView(R.id.del_tv);
        ((TextView) helper.getView(R.id.name_tv)).setEnabled(false);
        ((TextView) helper.getView(R.id.title_tv)).setText("部门"+(helper.getAdapterPosition()+1));
        ((TextView) helper.getView(R.id.name_tv)).setText(departmentBean.getName());
        delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDelItemClicked(departmentBean);
                }
            }
        });
    }
    public interface OnItemClickListener {
        void onDelItemClicked(DepartmentBean departmentBean);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
