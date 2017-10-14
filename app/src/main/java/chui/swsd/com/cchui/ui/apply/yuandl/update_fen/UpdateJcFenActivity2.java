package chui.swsd.com.cchui.ui.apply.yuandl.update_fen;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.ProgressWebView;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\17 0017.
 */

public class UpdateJcFenActivity2 extends BaseActivity {
    @BindView(R.id.webview)
    WebView mWebView;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_dafen_con2;
    }

    @Override
    protected void initViews() {
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);  //支持js
        //是否可以缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.loadUrl("http://192.168.0.250:8888/Beat/integral/view.action?name=basis&userid=13");
        initListener();
    }

    @Override
    protected void updateViews() {

    }
    public void initListener(){
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("跳转Sort：" + url);
                return true;
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                System.out.println("跳转Sort：" + url+"*******");
                // 分享
                if (url.contains("http://192.168.0.250:8888/Beat/user/basis.action")) {
                    //url = url.substring(0, url.length() - 1) + "1";
                    view.stopLoading();
                    //view.loadUrl(url);
                    startActivity(new Intent(UpdateJcFenActivity2.this,MainActivity.class));
                    return;
                }
               /* if (url.contains("/webapp/shareCoupon_")
                        || url.contains("/webapp/shareRedpacket_")) {
                    view.stopLoading();
                   // ShareUtils.shareDialog(mContext, url);
                    return;
                }*/
                super.onLoadResource(view, url);
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
