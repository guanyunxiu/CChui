package chui.swsd.com.cchui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.model.ClickEntity;
/**
 *
 */
public class ItemClickAdapter extends BaseMultiItemQuickAdapter<ClickEntity>  {


    public ItemClickAdapter(List<ClickEntity> data) {
        super(data);
        addItemType(ClickEntity.CLICK_ITEM_VIEW, R.layout.fragment_shenpi_item);
        addItemType(ClickEntity.CLICK_ITEM_CHILD_VIEW, R.layout.fragment_shenpi_item);
        addItemType(ClickEntity.LONG_CLICK_ITEM_VIEW, R.layout.fragment_shenpi_item);
        addItemType(ClickEntity.LONG_CLICK_ITEM_CHILD_VIEW, R.layout.fragment_shenpi_item);
        addItemType(ClickEntity.NEST_CLICK_ITEM_CHILD_VIEW, R.layout.fragment_shenpi_item);

    }


    @Override
    protected void convert(final BaseViewHolder helper, final ClickEntity item) {
        switch (helper.getItemViewType()) {
            case ClickEntity.CLICK_ITEM_VIEW:

                break;
            case ClickEntity.CLICK_ITEM_CHILD_VIEW:

                break;
            case ClickEntity.LONG_CLICK_ITEM_VIEW:

                break;
            case ClickEntity.LONG_CLICK_ITEM_CHILD_VIEW:

                break;
            case ClickEntity.NEST_CLICK_ITEM_CHILD_VIEW:

                break;
        }
    }


}
