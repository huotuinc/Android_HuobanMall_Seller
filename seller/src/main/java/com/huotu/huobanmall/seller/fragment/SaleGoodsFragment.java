package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.LoginActivity;
import com.huotu.huobanmall.seller.adapter.GoodsAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.GoodsOpeTypeEnum;
import com.huotu.huobanmall.seller.bean.MJGoodModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class SaleGoodsFragment extends BaseFragment {
    @Bind(R.id.goods_sale_listView)
    PullToRefreshListView _goodsSaleListView;

    List<GoodsModel> _goodsList = null;
    GoodsAdapter _goodsAdapter = null;

    OperateTypeEnum _type = OperateTypeEnum.REFRESH;
    //这个标识位，代表Fragment是否可见
    boolean _isVisiable =false;
    //这个标识位，代表UI是否初始化完成
    boolean _isPrepared = false;

    boolean _isFirst = true;

    Handler _handler = new Handler();

    View emptyView=null;
    boolean isSetEmptyView = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SaleGoodsFragment.
     */
    public static SaleGoodsFragment newInstance() {
        SaleGoodsFragment fragment = new SaleGoodsFragment();
        return fragment;
    }

    public SaleGoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if( _goodsList !=null ){
            outState.putSerializable("goodsList", (Serializable) _goodsList);
        }else{
            outState.putSerializable("goodsList",null);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sale_goods, container, false);
        ButterKnife.bind(this, rootView);

        _goodsList = new ArrayList<>();
        _goodsAdapter = new GoodsAdapter(this.getActivity(), _goodsList);
        _goodsSaleListView.getRefreshableView().setAdapter(_goodsAdapter);

        emptyView = new View(this.getActivity());
        emptyView.setBackgroundResource(R.mipmap.tpzw);
        //_goodsSaleListView.setEmptyView(emptyView);
        _goodsSaleListView.setMode(PullToRefreshBase.Mode.BOTH);
        _goodsSaleListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _type = OperateTypeEnum.REFRESH;
                getData( _type );
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _type = OperateTypeEnum.LOADMORE;
                getData( _type );
            }
        });

        _isPrepared=true;

        if( savedInstanceState !=null && savedInstanceState.containsKey("goodsList") &&  savedInstanceState.getSerializable("goodsList") !=null ){
            if(_goodsList==null){_goodsList=new ArrayList<>();}
            _goodsList.clear();
            _isFirst=false;
            _goodsList.addAll((List<GoodsModel>) savedInstanceState.getSerializable("goodsList"));
            _goodsAdapter.notifyDataSetChanged();
        }else {
            firstGetData();
        }

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if( getUserVisibleHint()){
            _isVisiable=true;
            firstGetData();
        }else {
            _isVisiable=false;
        }
    }

    protected void firstGetData(){

        if( _isPrepared && _isVisiable && _isFirst ) {
            //this.showProgressDialog("", "正在获取数据，请稍等...");
            //_type = OperateTypeEnum.REFRESH;
            //_isFirst=false;
            //getData(_type);
            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    _type = OperateTypeEnum.REFRESH;
                    _isFirst=false;
                    _goodsSaleListView.setRefreshing(true);
                }
            },1000);
        }
    }


    protected void getData( OperateTypeEnum type ){
        if( false == canConnect() ){
            this.closeProgressDialog();
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    _goodsSaleListView.onRefreshComplete();
                }
            });
            return;
        }

        Map<String,String> maps = new HashMap<>();
        if( type == OperateTypeEnum.REFRESH){
        }else {
            if( _goodsList !=null && _goodsList.size()>0 ) {
                String lastId = String.valueOf(_goodsList.get(_goodsList.size() - 1).getGoodsId());
                maps.put("lastProductId", lastId);
            }
        }

        HttpParaUtils httpParaUtils = new HttpParaUtils();
        maps.put("type", String.valueOf( GoodsOpeTypeEnum.ONSHELF.getIndex()) );
        String url = Constant.GOODSLIST_INTERFACE;
        url = httpParaUtils.getHttpGetUrl(url,maps);

        GsonRequest<MJGoodModel> goodsListRequest=new GsonRequest<MJGoodModel>(Request.Method.GET,
                url,
                MJGoodModel.class,
                null,
                goodslistListener,
                this
                );

        VolleyRequestManager.AddRequest(goodsListRequest);
    }

    Response.Listener<MJGoodModel> goodslistListener =new Response.Listener<MJGoodModel>() {
        @Override
        public void onResponse(MJGoodModel mjGoodModel) {
            SaleGoodsFragment.this.closeProgressDialog();
            _goodsSaleListView.onRefreshComplete();

            if (isSetEmptyView == false) {
                _goodsSaleListView.setEmptyView(emptyView);
                isSetEmptyView=true;
            }

            if( mjGoodModel==null){
                SimpleDialogFragment.createBuilder(SaleGoodsFragment.this.getActivity(), SaleGoodsFragment.this.getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( "获取数据失败" )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if( mjGoodModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder( SaleGoodsFragment.this.getActivity() , SaleGoodsFragment.this.getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjGoodModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(SaleGoodsFragment.this.getActivity(), LoginActivity.class);
                return;
            }
            else if( mjGoodModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( SaleGoodsFragment.this.getActivity() , SaleGoodsFragment.this.getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

//            if( mjGoodModel.getResultData() ==null || mjGoodModel.getResultData().getList()==null||mjGoodModel.getResultData().getList().size()<1){
//                return;
//            }

            if(_type == OperateTypeEnum.REFRESH){
                _goodsList.clear();
                if( mjGoodModel.getResultData()!=null && mjGoodModel.getResultData().getList()!=null && mjGoodModel.getResultData().getList().size()>0) {
                    _goodsList.addAll(mjGoodModel.getResultData().getList());
                }else{
                    ToastUtils.showLong(Constant.No_Data_Text,Gravity.BOTTOM);
                }
            }else{
                if( mjGoodModel.getResultData()!=null && mjGoodModel.getResultData().getList()!=null && mjGoodModel.getResultData().getList().size()>0 ) {
                    _goodsList.addAll(mjGoodModel.getResultData().getList());
                }else{
                    ToastUtils.showLong(Constant.No_Data_Text, Gravity.BOTTOM);
                }
            }
            _goodsAdapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        _goodsSaleListView.onRefreshComplete();
        super.onErrorResponse(volleyError);
    }
}
