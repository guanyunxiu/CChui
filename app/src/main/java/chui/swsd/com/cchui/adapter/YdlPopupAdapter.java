package chui.swsd.com.cchui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.DepartmentBean;
import chui.swsd.com.cchui.model.YdlTitleBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class YdlPopupAdapter extends BaseQuickAdapter<YdlTitleBean> {
    public YdlPopupAdapter(List<YdlTitleBean> list) {
        super(R.layout.activity_ydl_pupupwindow_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, final YdlTitleBean ydlTitleBean) {

        ((TextView) helper.getView(R.id.title_tv)).setText(ydlTitleBean.getName());

       /* delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDelItemClicked(departmentBean);
                }
            }
        });*/
    }
    public interface OnItemClickListener {
        void onDelItemClicked(DepartmentBean departmentBean);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
