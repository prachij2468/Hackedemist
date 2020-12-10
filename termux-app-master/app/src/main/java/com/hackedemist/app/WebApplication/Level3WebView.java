package com.hackedemist.app.WebApplication;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.termux.R;

public class Level3WebView extends Activity {

    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_web_view);

        myWebView= findViewById(R.id.WebView3);




        myWebView.loadUrl("http://game.hackedemist.com/sql3");

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        // myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setWebViewClient(new WebViewClient());



    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack())
        {
            myWebView.goBack();
        }
        else {
            finish();
        }

    }
}
