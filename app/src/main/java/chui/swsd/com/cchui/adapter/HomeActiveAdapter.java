package chui.swsd.com.cchui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chui.swsd.com.cchui.R;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class HomeActiveAdapter extends BaseQuickAdapter<String> {
    public HomeActiveAdapter(List<String> list) {
        super(R.layout.fragment_sp_content_item,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, String homeMenu) {
        //helper.setImageResource(R.id.icon_img,homeMenu.getImage());
       // helper.setText(R.id.title_tv,homeMenu.getDesp());
    }
}
