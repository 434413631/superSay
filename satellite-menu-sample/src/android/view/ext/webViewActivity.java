/*
 * webViewActivity
 *
 * Version 1.0.0
 *
 * 2013-07-09
 *
 * Copyright (c) 2012, T-Systems.
 * All rights reserved.
 */
package android.view.ext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Declaration.
 *
 * @author jian.wang@t-systems.com
 */
public class webViewActivity extends Activity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.webview);
        webView=(WebView)findViewById(R.id.webView);
       WebSettings wesettings= webView.getSettings();
        wesettings.setJavaScriptEnabled(true);
        wesettings.setPluginsEnabled(true);
        wesettings.setSupportZoom(true);
        wesettings.setDisplayZoomControls(true);

        String url=getIntent().getExtras().getString("buyUrl");
        webView.loadUrl(url);

    }
}
