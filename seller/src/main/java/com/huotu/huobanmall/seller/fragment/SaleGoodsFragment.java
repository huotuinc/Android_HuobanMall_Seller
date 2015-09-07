package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.GoodsAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;

import java.util.ArrayList;
import java.util.List;

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

    List<GoodsModel> goodsList = null;
    GoodsAdapter goodsAdapter = null;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaleGoodsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaleGoodsFragment newInstance() {
        SaleGoodsFragment fragment = new SaleGoodsFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sale_goods, container, false);
        ButterKnife.bind(this, rootView);
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
        goodsAdapter = new GoodsAdapter(this.getActivity(), goodsList);
        _goodsSaleListView.getRefreshableView().setAdapter(goodsAdapter);

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

}