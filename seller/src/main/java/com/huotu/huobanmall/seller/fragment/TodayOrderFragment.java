package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.huotu.huobanmall.seller.Interface.IIndexFragmentInteractionListener;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.MJNewTodayModel;
import com.huotu.huobanmall.seller.bean.PurchaseOfGoods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayOrderFragment extends TodayFragment implements View.OnClickListener{

    @Bind(R.id.todayOrder_lineChart)
    LineChart _orderLineChart;
    @Bind(R.id.ll_todayOrder_member)
    LinearLayout _ll_members;
    @Bind(R.id.ll_todayOrder_fxs)
    LinearLayout _ll_fxs;
    @Bind(R.id.todayOrder_OrderCount)
    TextView _orderCount;
    @Bind(R.id.todayOrder_MemberCount)
    TextView _memberCount;
    @Bind(R.id.todayOrder_fxsCount)
    TextView _fxsCount;

    MJNewTodayModel _data=null;

    private IIndexFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayOrderFragment newInstance(String param1, String param2) {
        TodayOrderFragment fragment = new TodayOrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TodayOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today_order, container, false);

        ButterKnife.bind(this, rootView);

        _ll_members.setOnClickListener(this);
        _ll_fxs.setOnClickListener(this);

        //setLineChartData();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSwitch( int position ) {
        if (mListener != null) {
            mListener.switchFragment( position );
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (IIndexFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IIndexFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if( v.getId()== R.id.ll_todayOrder_member){
            onSwitch(1);
        }else if( v.getId() ==R.id.ll_todayOrder_fxs){
            onSwitch(2);
        }
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }


    @Override
    public void onResume() {
        super.onResume();

        if(_data==null)return;

        _orderCount.setText( String .valueOf( _data.getResultData().getTodayOrderAmount() ));
        _memberCount.setText( String.valueOf(_data.getResultData().getTodayMemberAmount()) );
        _fxsCount.setText( String.valueOf( _data.getResultData().getTodayPartnerAmount() ) );

        setLineChartData(_orderLineChart, _data.getResultData().getOrderHour(), _data.getResultData().getOrderAmount());
    }

    public void setData(MJNewTodayModel model){
        _data=model;
       // if(_data ==null) return;

//        _orderCount.setText( String .valueOf( _data.getResultData().getTodayOrderAmount() ));
//        _memberCount.setText( String.valueOf(_data.getResultData().getTodayMemberAmount()) );
//        _fxsCount.setText( String.valueOf( _data.getResultData().getTodayPartnerAmount() ) );
//
//        setLineChartData( _orderLineChart, _data.getResultData().getOrderHour() , _data.getResultData().getOrderAmount() );
    }
}
