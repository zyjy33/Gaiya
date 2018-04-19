package com.yunsen.gaea.gaea;


import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yunsen.gaea.gaea.utils.WebUitls;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private LinearLayout rootView;
    private WebView webView;
    //记录用户首次点击返回键的时间
    private long mFirstTime = 0;
    private ProgressBar webProgress;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        rootView = (LinearLayout) findViewById(R.id.homeRootView);
        webProgress = (ProgressBar) findViewById(R.id.webProgress);
        webView = new WebView(this);
        rootView.addView(webView);
        webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void initData() {
        WebUitls.initWebView(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webProgress.setVisibility(View.VISIBLE);
                webView.getSettings().setBlockNetworkImage(true);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.getSettings().setBlockNetworkImage(false);
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    //设置wenView加载图片资源
                    webView.getSettings().setBlockNetworkImage(false);
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
                Log.e(TAG, "onPageFinished: " + url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                String data = "页面未找到！";
//                view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
                Log.e(TAG, "onReceivedError: "+failingUrl );
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 90) {
                    webProgress.setVisibility(View.INVISIBLE);
                } else {
                    webProgress.setVisibility(View.VISIBLE);
                    webProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        //盖亚Homehttp://183.62.138.31:6060/mobile/default.html
        webView.loadUrl("http://183.62.138.31:6060/mobile/default.html");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            rootView.removeView(webView);
            webView = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mFirstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mFirstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
