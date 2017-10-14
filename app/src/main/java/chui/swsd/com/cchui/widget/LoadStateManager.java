package chui.swsd.com.cchui.widget;


import chui.swsd.com.cchui.base.BaseStateManager;

/**
 * Created by Administrator on 2016/11/3.
 */

public class LoadStateManager extends BaseStateManager<LoadStateManager.LoadState> {
    public static enum LoadState{
        Init,
        Success,
        Failure,
        NoData
        ;
    }
}
