package chui.swsd.com.cchui.ui.apply.yuandl.update_fen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import chui.swsd.com.cchui.R;
import chui.swsd.com.cchui.base.BaseActivity;
import chui.swsd.com.cchui.base.BaseApplication;
import chui.swsd.com.cchui.base.BaseSwipeBackActivity;
import chui.swsd.com.cchui.config.SharedConstants;
import chui.swsd.com.cchui.net.UrlAddress;
import chui.swsd.com.cchui.ui.MainActivity;
import chui.swsd.com.cchui.utils.CommonUtil;
import chui.swsd.com.cchui.widget.ProgressWebView;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\17 0017.
 */

public class UpdateJcFenActivity extends BaseActivity {
    @BindView(R.id.webview)
    ProgressWebView mWebView;
    int flag;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_dafen_con;
    }

    @Override
    protected void initViews() {
        flag = getIntent().getIntExtra("flag",0);
        mWebView.getSettings().setTextZoom(100);
        mWebView.getSettings().setJavaScriptEnabled(true);
        int id = BaseApplication.mSharedPrefUtil.getInt(SharedConstants.ID,0);
        mWebView.loadUrl(UrlAddress.URL+"integral/view.action?name=basis&userid="+id+"&cid="+BaseApplication.mSharedPrefUtil.getInt(SharedConstants.COMID,0));
        // 设置Web视图
        mWebView.setWebViewClient(new MyWebViewClient());
        initListener();
    }

    @Override
    protected void updateViews() {

    }
    public void initListener(){
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {  //重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                //view.loadUrl(url);
                Log.i("WebView",url+"********");
                startActivity(new Intent(UpdateJcFenActivity.this, MainActivity.class));
                return true;
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                System.out.println("跳转Sort：" + url+"*******");
                // 分享
                if (url.contains(UrlAddress.URL+"user/basis.action")) {
                    CommonUtil.showToast(UpdateJcFenActivity.this,"提交成功");
                    if(flag == 0) {
                        startActivity(new Intent(UpdateJcFenActivity.this, MainActivity.class));
                        finish();
                    }else if(flag == 1){
                        finish();
                    }
                    return;
                }
                super.onLoadResource(view, url);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) { // 重写此方法可以让webview处理https请求
                handler.proceed();
            }
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
               // mWebview.setVisibility(View.INVISIBLE);
                System.out.println("跳转Sort：" +"错误*******");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                System.out.println("跳转Sort：" +"结束*******");
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                System.out.println("跳转Sort：" +"开始*******");
                super.onPageStarted(view, url, favicon);
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

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //    System.out.println(----------------------shouldOverrideUrlLoading 。。 url: + url);

            Log.i("WebView",url+"********");
          /*  if (url != null && url.contains(/m/phoneRegiste.do)) {
                Intent intent = new Intent(PublicWebView.this, RegisterByPhone.class);
                PublicWebView.this.startActivity(intent);

                finish();
                return true;
            }*/


            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);


        }
    }

    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
// webview.goBack();// 返回前一个页面
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
