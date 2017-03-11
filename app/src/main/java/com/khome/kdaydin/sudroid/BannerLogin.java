package com.khome.kdaydin.sudroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import static com.khome.kdaydin.sudroid.MainActivity.USRCRE;

public class BannerLogin extends AppCompatActivity {
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        mWebView = (WebView) findViewById(R.id.banner_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl("http://suis.sabanciuniv.edu/prod/twbkwbis.P_SabanciLogin");

        final ProgressDialog progress = ProgressDialog.show(this,"BannerWeb","Açılıyor",true,true);
        progress.show();
        mWebView.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(mWebView.getUrl().matches("http://suis.sabanciuniv.edu/prod/twbkwbis.P_SabanciLogin")){
                    progress.dismiss();

                    String username = getSharedPreferences(USRCRE, Context.MODE_PRIVATE).getString("USERNAME","");
                    String pass = getSharedPreferences(USRCRE,Context.MODE_PRIVATE).getString("PASS","");

                    final String js = "javascript:" +
                            "document.getElementById('UserID').value = '" + username + "';"  +
                            "document.getElementsByName('PIN')[0].value = '" + pass + "';"   +
                            "document.getElementsByName('loginform')[0][2].click()";

                    view.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                        }
                    });
                }
                else if (mWebView.getUrl().matches("http://suis.sabanciuniv.edu/index.html")){
                    mWebView.loadUrl("http://suis.sabanciuniv.edu/prod/twbkwbis.P_SabanciLogin");

                }
            }

        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
