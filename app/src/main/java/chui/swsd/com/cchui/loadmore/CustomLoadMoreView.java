package chui.swsd.com.cchui.loadmore;


import android.content.Context;

import chui.swsd.com.cchui.R;

/**
 * Created by BlingBling on 2016/10/15.
 */

public final class CustomLoadMoreView extends LoadMoreView {

    public CustomLoadMoreView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
