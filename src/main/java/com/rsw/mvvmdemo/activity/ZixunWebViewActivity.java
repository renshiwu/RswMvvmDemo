package com.rsw.mvvmdemo.activity;

import android.content.Context;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.base.BaseActivity;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.rsw.mvvmdemo.databinding.ActivityWebBinding;
import com.rsw.mvvmdemo.viewmodel.ZixunWebViewViewModel;

/**
 * Created by RSW
 * on 2020/10/13
 */
public class ZixunWebViewActivity extends BaseActivity<ZixunWebViewViewModel, ActivityWebBinding> {

    private YinshiBean.ListBean bean;
    private String webStr = "";
    private Context context;
    private ZixunWebViewActivity activity;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initNativeView() {
        context = this;
        activity = this;
        bean = (YinshiBean.ListBean) getIntent().getSerializableExtra("item");
        init();
    }


    @Override
    protected void setListener() {

    }

    private void init() {
        webStr = bean.getContent();
        final WebSettings settings = binding.webViewX5.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setTextZoom(100);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        // 启用硬件加速
        binding.webViewX5.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUseWideViewPort(true); // 关键点
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSupportZoom(true); // 支持缩放
        settings.setLoadWithOverviewMode(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        binding.webViewX5.setWebChromeClient(new WebChromeClient());
        binding.webViewX5.setWebViewClient(mWebViewClient);
        String CSS_STYLE = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/normal.css\"/>";
        binding.webViewX5.loadDataWithBaseURL("about:blank", webStr + CSS_STYLE, "text/html", "utf-8", null);

        findViewById(R.id.lay_back).setOnClickListener(this);
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println();
            view.loadUrl(url);
            return true;
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lay_back) {
            finish();
        }
    }
}

