package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.GoodseditAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.common.Constant;
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
public class GoodsEditActivity extends BaseFragmentActivity implements  RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.saleing_btn)
    RadioButton saleing_btn;
    @Bind(R.id.offshelf_btn)
    RadioButton offshelf_btn;
    @Bind(R.id.goodsedit_title)
    RadioGroup goodsedit_title;
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
    GoodseditAdapter goodseditAdapter;
    List<GoodsModel> goodsList = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_edit);
        ButterKnife.bind(this);
        goodsedit_title.setOnCheckedChangeListener(this);
        goodsList = new ArrayList<>();
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setPrice(1000);
        goodsModel.setStock(100);
        goodsModel.setTitle("qwqeqwwqeqewqewq");
        goodsList.add(goodsModel);
        goodsModel = new GoodsModel();
        goodsModel.setPrice(1000);
        goodsModel.setStock(100);
        goodsModel.setTitle("qwqeqwwqeqewqewq");
        goodsList.add(goodsModel);
        goodsModel = new GoodsModel();
        goodsModel.setPrice(1000);
        goodsModel.setStock(100);
        goodsModel.setTitle("qwqeqwwqeqewqewq");
        goodsList.add(goodsModel);
        goodsModel = new GoodsModel();
        goodsModel.setPrice(1000);
        goodsModel.setStock(100);
        goodsModel.setTitle("qwqeqwwqeqewqewq");
        goodsList.add(goodsModel);
        goodseditAdapter = new GoodseditAdapter(this, goodsList);
        _goodsedit_listview.getRefreshableView().setAdapter(goodseditAdapter);
        _header_back.setOnClickListener(this);
        getGoodsData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void getGoodsData() {
        String url = Constant.GOODSLIST_INTERFACE;
        Map<String, String> paras = new HashMap<>();
        paras.put("type", "1");

        HttpParaUtils utils = new HttpParaUtils();
        String urlString = utils.getHttpGetUrl(url, paras);

        GsonRequest<List<GoodsModel>> goodsListRequest =
                new GsonRequest<>(
                        Request.Method.GET,
                        urlString,
                        new TypeToken<List<GoodsModel>>() {
                        },
                        null,
                        goodsListListener,
                        errorListener);

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
        if (v.getId() == R.id.header_back) {
            finish();
        }
    }

//    public void onPageSelected(int position) {
//        if (position == 0) {
//            saleing_btn.setChecked(true);
//            offshelf_btn.setChecked(false);
//
//        } else if (position == 1) {
//            saleing_btn.setChecked(false);
//            offshelf_btn.setChecked(true);
//        }
//    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        if (checkedId == R.id.saleing_btn) {
//          //  _circlePageIndicator.setCurrentItem(0);
//        } else if (checkedId == R.id.offshelf_btn) {
//          //  _circlePageIndicator.setCurrentItem(1);
//
//        }
    }
}
