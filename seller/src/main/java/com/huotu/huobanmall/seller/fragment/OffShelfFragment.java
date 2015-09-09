package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.GoodsAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.MJGoodModel;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OffShelfFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OffShelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffShelfFragment extends BaseFragment {

    @Bind(R.id.goods_offshelf_listView)
    PullToRefreshListView _goodsOffshelfListview;
    List<GoodsModel> _goodsList = null;
    GoodsAdapter _goodsAdapter = null;

    ListView _listView =null;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OffShelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OffShelfFragment newInstance() {
        OffShelfFragment fragment = new OffShelfFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    public OffShelfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_off_shelf, container, false);
        ButterKnife.bind( this ,rootView);

        _goodsOffshelfListview.setMode(PullToRefreshBase.Mode.BOTH);
        _goodsOffshelfListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                getData();
            }
        });

        //_goodsOffshelfListview.setRefreshing();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //_goodsOffshelfListview.setRefreshing();
        //_goodsOffshelfListview.setRefreshing(true);
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

    protected void getData(){
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        Map<String,String> maps = new HashMap<>();
        maps.put("type", "2");
        String url = Constant.GOODSLIST_INTERFACE;
        url = httpParaUtils.getHttpGetUrl(url,maps);

//        if( _progressDialog !=null){
//            _progressDialog.dismiss();
//            _progressDialog=null;
//        }

//        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder( this.getActivity(), this.getActivity().getSupportFragmentManager())
//                .setMessage("正在获取数据，请稍等...")
//                .setCancelable(false)
//                .setCancelableOnTouchOutside(false);
//        _progressDialog = (ProgressDialogFragment) builder.show();

        GsonRequest<MJGoodModel> goodsListRequest=new GsonRequest<MJGoodModel>(Request.Method.GET,
                url,
                MJGoodModel.class,
                null,
                goodslistListener,
                errorListener
        );

        VolleyRequestManager.getRequestQueue().add(goodsListRequest);
    }

    Response.Listener<MJGoodModel> goodslistListener =new Response.Listener<MJGoodModel>() {
        @Override
        public void onResponse(MJGoodModel mjGoodModel) {
//            if( _progressDialog !=null){
//                _progressDialog.dismiss();
//                _progressDialog=null;
//            }

            _goodsOffshelfListview.onRefreshComplete();

            if( mjGoodModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder(OffShelfFragment.this.getActivity(), OffShelfFragment.this.getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjGoodModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( OffShelfFragment.this.getActivity() , OffShelfFragment.this.getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            _goodsList= mjGoodModel.getResultData().getList();
            _goodsAdapter = new GoodsAdapter(OffShelfFragment.this.getActivity(), _goodsList);
            _goodsOffshelfListview.getRefreshableView().setAdapter(_goodsAdapter);
        }
    };

    Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
//            if(_progressDialog!=null){
//                _progressDialog.dismiss();
//                _progressDialog=null;
//            }

            _goodsOffshelfListview.onRefreshComplete();

            volleyError.printStackTrace();

            SimpleDialogFragment.createBuilder( OffShelfFragment.this.getActivity() , OffShelfFragment.this.getActivity().getSupportFragmentManager())
                    .setTitle("错误信息")
                    .setMessage( volleyError.getMessage() )
                    .setNegativeButtonText("关闭")
                    .show();
        }
    };
}
