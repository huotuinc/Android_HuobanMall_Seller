package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.huotu.huobanmall.seller.bean.MJGoodModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.common.Constant;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaleGoodsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaleGoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaleGoodsFragment extends BaseFragment {

    @Bind(R.id.goods_sale_listView)
    PullToRefreshListView _goodsSaleListView;

    ListView _listView;

    List<GoodsModel> _goodsList = null;
    GoodsAdapter _goodsAdapter = null;

    ProgressDialogFragment _progressDialog=null;

    OperateTypeEnum _type = OperateTypeEnum.REFRESH;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SaleGoodsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaleGoodsFragment newInstance() {
        SaleGoodsFragment fragment = new SaleGoodsFragment();
        return fragment;
    }

    public SaleGoodsFragment() {
        // Required empty public constructor
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

        _goodsSaleListView.setMode(PullToRefreshBase.Mode.BOTH);
        _listView = _goodsSaleListView.getRefreshableView();
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

        firstGetData();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //_goodsSaleListView.setRefreshing();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    protected void firstGetData(){
        if( _progressDialog !=null){
            _progressDialog.dismiss();
            _progressDialog=null;
        }
        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder( this.getActivity(), this.getActivity().getSupportFragmentManager())
                .setMessage("正在获取数据，请稍等...")
                .setCancelable(false)
                .setCancelableOnTouchOutside(false);
        _progressDialog = (ProgressDialogFragment) builder.show();

        _type= OperateTypeEnum.REFRESH;
        getData(_type);
    }


    protected void getData( OperateTypeEnum type ){
        Map<String,String> maps = new HashMap<>();
        if( type == OperateTypeEnum.REFRESH){
            //maps.put("lastProductId","");
        }else {
            String lastid = String.valueOf( _goodsList.get( _goodsList.size()-1).getGoodsId());
            maps.put("lastProductId", lastid);
        }

        HttpParaUtils httpParaUtils = new HttpParaUtils();
        maps.put("type", "1");
        String url = Constant.GOODSLIST_INTERFACE;
        url = httpParaUtils.getHttpGetUrl(url,maps);

        GsonRequest<MJGoodModel> goodsListRequest=new GsonRequest<MJGoodModel>(Request.Method.GET,
                url,
                MJGoodModel.class,
                null,
                goodslistListener,
                errorListener
                );

        goodsListRequest.setRetryPolicy(new DefaultRetryPolicy( Constant.REQUEST_TIMEOUT ,1,1.0f));

        VolleyRequestManager.getRequestQueue().add(goodsListRequest);
    }

    Response.Listener<MJGoodModel> goodslistListener =new Response.Listener<MJGoodModel>() {
        @Override
        public void onResponse(MJGoodModel mjGoodModel) {
            if( _progressDialog !=null){
                _progressDialog.dismiss();
                _progressDialog=null;
            }

            _goodsSaleListView.onRefreshComplete();

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
                ActivityUtils.getInstance().showActivity(SaleGoodsFragment.this.getActivity(), LoginActivity.class);
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

            if( mjGoodModel.getResultData() ==null || mjGoodModel.getResultData().getList()==null||mjGoodModel.getResultData().getList().size()<1){
                return;
            }

            if(_type == OperateTypeEnum.REFRESH){
                _goodsList.clear();
                _goodsList.addAll(mjGoodModel.getResultData().getList());
            }else{
                _goodsList.addAll(mjGoodModel.getResultData().getList());
            }
            _goodsAdapter.notifyDataSetChanged();
        }
    };

    Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if(_progressDialog!=null){
                _progressDialog.dismiss();
                _progressDialog=null;
            }

            _goodsSaleListView.onRefreshComplete();

            volleyError.printStackTrace();

            SimpleDialogFragment.createBuilder( SaleGoodsFragment.this.getActivity() , SaleGoodsFragment.this.getActivity().getSupportFragmentManager())
                    .setTitle("错误信息")
                    .setMessage( volleyError.getMessage() )
                    .setNegativeButtonText("关闭")
                    .show();
        }
    };

}
