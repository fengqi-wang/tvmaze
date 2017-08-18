package com.fengqi.tvmaze.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fengqi.tvmaze.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fengqi on 2017-08-16.
 */

public class EpDetailActivity extends AppCompatActivity {
    private static final String EPISODE_URL = "episode_url";

    @BindView(R.id.webview) WebView mWebView;
    @BindView(R.id.back_btn) ImageView mBackButton;
    @BindView(R.id.forward_btn) ImageView mForwardButton;
    @BindView(R.id.progress) ProgressBar mProgressBar;

    public static void viewEpisode(Context cxt, String url) {
        Intent intent = new Intent(cxt, EpDetailActivity.class);
        intent.putExtra(EPISODE_URL, url);

        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.webpage);
        ButterKnife.bind(this);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUri(Uri.parse(url));
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handleUri(request.getUrl());
            }

            private boolean handleUri(final Uri uri) {
                String currentHost = Uri.parse(mWebView.getUrl()).getHost();
                if (uri.getHost().equalsIgnoreCase(currentHost)) {
                    return false;
                } else {
                    final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mBackButton.setVisibility(View.INVISIBLE);
                mForwardButton.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.INVISIBLE);
                if(mWebView.canGoBack())
                    mBackButton.setVisibility(View.VISIBLE);
                if(mWebView.canGoForward())
                    mForwardButton.setVisibility(View.VISIBLE);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView.canGoBack())
                    mWebView.goBack();
            }
        });
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView.canGoForward())
                    mWebView.goForward();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = getIntent();
        final String url = intent.getExtras().getString(EPISODE_URL);

        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
