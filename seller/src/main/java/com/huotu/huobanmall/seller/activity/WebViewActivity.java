package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.Constants;
import com.huotu.huobanmall.seller.fragment.OrderFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/26.
 */
public class WebViewActivity extends BaseFragmentActivity {
    @Bind(R.id.webView_page)
    PullToRefreshWebView _pullToRefreshWebViewPage;

    WebView _webView;

    @Bind(R.id.header_title)
    public TextView _webViewTitle;

    String _url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        _webView = _pullToRefreshWebViewPage.getRefreshableView();
        _webView.getSettings().setJavaScriptEnabled(true);
        _webView.setWebViewClient(new SellerWebViewClient());
        _webView.setWebChromeClient(new SellerWebChromeClient(_webViewTitle , _pullToRefreshWebViewPage ));

        if( null != getIntent() && getIntent().hasExtra(Constants.Extra_Url )){
            _url= getIntent().getStringExtra( Constants.Extra_Url );
            _webView.loadUrl(_url);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private static class SellerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
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
