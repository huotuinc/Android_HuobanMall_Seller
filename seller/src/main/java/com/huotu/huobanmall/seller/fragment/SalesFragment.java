package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.OrderFragmentPageAdapter;
import com.huotu.huobanmall.seller.adapter.SalesFragmentPageAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SalesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SalesFragment newInstance() {
        SalesFragment fragment = new SalesFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sales, container, false);

        ButterKnife.bind(this, rootView);



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initFragments();
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

    List<BaseFragment> _fragments;
    FragmentManager _fragmentManager;
    @Bind(R.id.sales_indicator)
    TitlePageIndicator _indicator;
    SalesFragmentPageAdapter _salesFragmentAdapter;
    @Bind(R.id.sales_viewPager)
    ViewPager _viewPager;

    protected void initFragments(){
        SalesLineChartFragment fragment1 = new SalesLineChartFragment();
        SalesLineChartFragment fragment2 = new SalesLineChartFragment();
        SalesLineChartFragment fragment3 = new SalesLineChartFragment();
        _fragments = new ArrayList<>();
        _fragments.add(fragment1);
        _fragments.add(fragment2);
        _fragments.add(fragment3);
        _fragmentManager = this.getActivity().getSupportFragmentManager();
        _salesFragmentAdapter = new SalesFragmentPageAdapter(_fragments, _fragmentManager);
        _viewPager.setAdapter(_salesFragmentAdapter);

        _indicator.setViewPager(_viewPager);
    }


    private void setLineChartData(){

        //_orderLineChart.setBackgroundColor(Color.WHITE);
        //_orderLineChart.setDescription("line chart description");
        //_orderLineChart.setNoDataText("no date to show chart");
        //_orderLineChart.getAxisRight().setEnabled(false);

        List<String> xValues= new ArrayList<String>();
        List<Entry> yValues=new ArrayList<>();
        Random r=new Random();
        for(int i=1;i<=7;i++){
            xValues.add( "7."+ i );
            float y = r.nextFloat()*100;
            Entry item=new Entry( y ,i);
            yValues.add(item);
        }
        LineDataSet dataset =new LineDataSet( yValues ,"");
        dataset.setCircleColor(Color.RED);
        dataset.setCircleSize(4);
        dataset.setDrawCircleHole(false);
        dataset.setDrawValues(true);
        dataset.setLineWidth(1);
        dataset.setColor(Color.BLUE);
        dataset.setValueTextSize(14);
        dataset.setValueTextColor(Color.GREEN);
        dataset.setDrawCubic(true);

        LineData data =new LineData(xValues ,dataset );

        //_orderLineChart.setData(data);

        //_orderLineChart.animateX(3000, Easing.EasingOption.EaseInOutQuart);
    }


    class SalesLineChartFragment extends BaseFragment{

        @Bind(R.id.sales_lineChart)
        LineChart _saleslineChart;

        public SalesLineChartFragment (){
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_saleslinechart , container, false);
            ButterKnife.bind(this, rootView);
            initData();
            return rootView;
        }

        protected void initData(){
            _saleslineChart.setBackgroundColor(Color.WHITE);
            _saleslineChart.setDescription("line chart description");
            _saleslineChart.setNoDataText("no date to show chart");
            _saleslineChart.getAxisRight().setEnabled(false);

            List<String> xValues= new ArrayList<String>();
            List<Entry> yValues=new ArrayList<>();
            Random r=new Random();
            for(int i=1;i<=7;i++){
                xValues.add( "7."+ i );
                float y = r.nextFloat()*100;
                Entry item=new Entry( y ,i);
                yValues.add(item);
            }
            LineDataSet dataset =new LineDataSet( yValues ,"");
            dataset.setCircleColor(Color.RED);
            dataset.setCircleSize(4);
            dataset.setDrawCircleHole(false);
            dataset.setDrawValues(true);
            dataset.setLineWidth(1);
            dataset.setColor(Color.BLUE);
            dataset.setValueTextSize(14);
            dataset.setValueTextColor(Color.GREEN);
            dataset.setDrawCubic(true);
            LineData data =new LineData(xValues ,dataset );
            _saleslineChart.setData(data);
            _saleslineChart.animateX(3000, Easing.EasingOption.EaseInOutQuart);
        }

        @Override
        public void onResume() {
            super.onResume();

        }
    }


}
