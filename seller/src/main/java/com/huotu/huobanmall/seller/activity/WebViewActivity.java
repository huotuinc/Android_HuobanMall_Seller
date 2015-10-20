package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/26.
 */
public class WebViewActivity extends BaseFragmentActivity implements View.OnClickListener{
    @Bind(R.id.webView_page)
    PullToRefreshWebView _pullToRefreshWebViewPage;
    WebView _webView;
    @Bind(R.id.header_title)
    public TextView _webViewTitle;
    String _url;
    @Bind(R.id.header_back)
    Button header_back;
    @Bind(R.id.header_operate)
    public TextView header_operate;
    Handler _handler =new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        header_operate.setVisibility(View.GONE);
        header_back.setOnClickListener(this);
        _webViewTitle.setText("商城首页");

        _pullToRefreshWebViewPage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
            @Override
            public void onRefresh(PullToRefreshBase<WebView> refreshView) {
                refreshView.getRefreshableView().loadUrl(_url);
            }
        });

        _webView = _pullToRefreshWebViewPage.getRefreshableView();
        _webView.getSettings().setJavaScriptEnabled(true);
        _webView.setWebViewClient(new SellerWebViewClient());

        if( null != getIntent() && getIntent().hasExtra(Constant.Extra_Url )){
            _url= getIntent().getStringExtra( Constant.Extra_Url );
            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if( WebViewActivity.this.isFinishing() )return;
                    _pullToRefreshWebViewPage.setRefreshing(true);
                }
            },1000);
        }
        if( null != getIntent() && getIntent().hasExtra(Constant.Extra_Title )){
            String title= getIntent().getStringExtra( Constant.Extra_Title );
            _webViewTitle.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.header_back){
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private class SellerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }



        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            WebViewActivity.this._pullToRefreshWebViewPage.onRefreshComplete();
        }
    }

    private static class SellerWebChromeClient extends WebChromeClient{
        TextView _tv;
        PullToRefreshWebView _webview;
        public SellerWebChromeClient(TextView tv , PullToRefreshWebView webView){
            _tv=tv;
            _webview=webView;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            _tv.setText(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                _webview.onRefreshComplete();
            }
        }
    }
}
