package chui.swsd.com.cchui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.widget.LoadStateManager;
import chui.swsd.com.cchui.widget.MultiStateView;

/**
 * Created by Administrator on 2017\8\7 0007.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.multiStateView)
    protected MultiStateView multiStateView;
    @Nullable
    @BindView(R.id.map)
    public MapView mapView;
    public Toolbar toolbar;
    public TextView titleName;
    public LoadStateManager mLoadStateManager;
    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();
    /**
     * 更新视图控件
     */
    protected abstract void updateViews();
    protected BaseApplication getAppInstance() {
        return (BaseApplication) getApplication();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());

        getAppInstance().addActivity(this);
        ButterKnife.bind(this);
        if(mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        initToolBar();
        initViews();
        if(multiStateView != null) {
            initMulState();
        }
        updateViews();
    }
    public void initMulState(){
        //loadManager初始化
        mLoadStateManager = new LoadStateManager();
        mLoadStateManager.setOnStateChangeListener(new BaseStateManager.OnStateChangeListener<LoadStateManager.LoadState>() {
            @Override
            public void OnStateChange(LoadStateManager.LoadState state, Object obj) {
                switch (state) {
                    case Init:
                        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                        break;
                    case Success:
                        multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                        break;
                    case Failure:
                        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
                        break;
                    case NoData:
                        multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
                    default:
                        break;
                }
            }
        });
        mLoadStateManager.setState(LoadStateManager.LoadState.Init);
        multiStateView.setRefreshOnClickListener(new View.OnClickListener() {
            //TODO refreesh
            @Override
            public void onClick(View v) {
                mLoadStateManager.setState(LoadStateManager.LoadState.Init);
                updateViews();
            }
        });
    }
    protected void initToolBar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        titleName = (TextView)findViewById(R.id.title_name);
        setSupportActionBar(toolbar);
    }
    /**
     * 初始化 Toolbar
     *
     *
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initTitle( boolean homeAsUpEnabled, String title) {
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_arr_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(!TextUtils.isEmpty(title)) {
            titleName.setText(title);
        }
    }
    protected  void initTitle(Toolbar toolbar2,boolean homeAsUpEnabled){
        setSupportActionBar(toolbar2);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_arr_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    protected void initTitle(boolean homeAsUpEnabled, int resTitle) {
        initTitle( homeAsUpEnabled, getString(resTitle));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
