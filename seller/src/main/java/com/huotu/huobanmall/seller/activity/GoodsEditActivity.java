package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.common.Constants;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品编辑页面
 */
public class GoodsEditActivity extends BaseFragmentActivity {
    @Bind(R.id.header_title)
    TextView _header_title;
    @Bind(R.id.header_back)
    TextView _header_back;
    @Bind(R.id.goodsedit_all)
    RadioButton goodsedit_all;
    @Bind(R.id.goodsedit_delete)
    Button _goodsedit_delete;
    @Bind(R.id.goodsedit_onshelf)
    Button _goodsedit_onshelf;
    @Bind(R.id.goodsedit_offshelf)
    Button _goodsedit_offshelf;
    @Bind(R.id.goodsedit_listview)
    PullToRefreshListView _goodsedit_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_edit);
        ButterKnife.bind(this);
        _header_title.setText("产品编辑");
        _header_back.setOnClickListener(this);

        getGoodsData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void getGoodsData(){
        String url = Constants.GOODSLIST_INTERFACE;
        Map<String,String> paras = new HashMap<>();
        paras.put("type", "1");

        HttpParaUtils utils = new HttpParaUtils();
        String urlString = utils.getHttpGetUrl(url, paras);

        GsonRequest< List<GoodsModel> > goodsListRequest =
                new GsonRequest<>(
                Request.Method.GET,
                urlString ,
                new TypeToken<List<GoodsModel>>(){},
                null ,
                goodsListListener ,
                errorListener );

        VolleyRequestManager.getRequestQueue().add(goodsListRequest);
    }

    private Response.Listener<List<GoodsModel>> goodsListListener = new Response.Listener<List<GoodsModel>>() {
        @Override
        public void onResponse(List<GoodsModel> goodsModels) {

        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if( v.getId() == R.id.header_back){
           finish();
        }
    }
}
