package chui.swsd.com.cchui.ui.apply.yuandl.dafen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseFragment;
import chui.swsd.com.cchui.widget.ProgressWebView;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\17 0017.
 */

public class DaFenConFragment extends BaseFragment {
    @BindView(R.id.webview)
    ProgressWebView mWebView;
    String url ;
    public static DaFenConFragment newInstance(String url) {
        DaFenConFragment fragment = new DaFenConFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_dafen_con;
    }

    @Override
    protected void initViews() {
        url = getArguments().getString("url");
    }

    @Override
    protected void updateViews() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        initListener();
    }
    public void initListener(){
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {  //重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) { // 重写此方法可以让webview处理https请求
                handler.proceed();
            }

        });
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        mWebView.stopLoading();
        mWebView.destroy();
        super.onDestroy();
    }
}
