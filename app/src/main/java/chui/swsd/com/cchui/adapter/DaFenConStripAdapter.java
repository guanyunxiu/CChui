package chui.swsd.com.cchui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.DaFenConBean;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class DaFenConStripAdapter extends BaseQuickAdapter<String> {
    private Context context;
    public DaFenConStripAdapter(List<String> list, Context context) {
        super(R.layout.activity_dafen_strip_item,list);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, final String str) {
        final int position = helper.getAdapterPosition();
        helper.setText(R.id.strip_tv,str);
    }
}
