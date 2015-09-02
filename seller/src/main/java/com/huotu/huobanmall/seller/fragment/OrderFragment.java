package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.github.mikephil.charting.animation.Easing;
//import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.WebViewActivity;
import com.huotu.huobanmall.seller.adapter.DataStatisticFragmentAdapter;
import com.huotu.huobanmall.seller.adapter.OrderFragmentPageAdapter;
import com.huotu.huobanmall.seller.adapter.PurchaseOfGoodsAdapter;
import com.huotu.huobanmall.seller.bean.PurchaseOfGoods;
import com.huotu.huobanmall.seller.common.Constants;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    //@Bind(R.id.order_lineChart)
    //LineChart _orderLineChart;
    //@Bind(R.id.order_goods)
    //RecyclerView _orderGoods;
    //@Bind(R.id.order_weekValue)
    //TextView _orderWeekValue;
    //@Bind(R.id.order_monthValue)
    //TextView _orderMonthValue;
    //@Bind(R.id.order_pulltorefreshScrollView)
    //PullToRefreshScrollView _orderPulltorefreshScrollView;

    @Bind(R.id.order_statis1)
    RelativeLayout order_statis1;

    @Bind(R.id.order_viewPager)
    ViewPager _viewPager;

    OrderFragmentPageAdapter _orderFragmentAdapter;

    List<PurchaseOfGoods> _purchaseOfGoods;
    PurchaseOfGoodsAdapter _purchaseAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, rootView);

        initData();
        order_statis1.setOnClickListener(this);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


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

    @Override
    public void onClick(View v) {
        if( v.getId()==R.id.order_statis1){
            Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
            intent.putExtra(Constants.Extra_Url,"http://www.baidu.com");
            ActivityUtils.getInstance().showActivity(this.getActivity(),intent);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    protected Response.Listener<PurchaseOfGoods> purchaseOfGoodsListner = new Response.Listener<PurchaseOfGoods>() {
        @Override
        public void onResponse(PurchaseOfGoods purchaseOfGoods) {

            //_orderPulltorefreshScrollView.onRefreshComplete();

           // SimpleDialogFragment.createBuilder(getActivity(), getFragmentManager())
                   // .setMessage("oooooooo").show();
        }
    };

    protected Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //_orderPulltorefreshScrollView.onRefreshComplete();

////           SimpleDialogFragment.createBuilder( getActivity() , getFragmentManager() )
////                    .setTitle("错误信息")
//                    .setMessage( volleyError.getMessage())
//                    .setNegativeButtonText("关闭")
//                    .show();
        }
    };

    protected void getData(){
        String url = "http://www.baidu.com";
        GsonRequest<PurchaseOfGoods> dataRequest=new GsonRequest<>(Request.Method.GET ,
                url , PurchaseOfGoods.class , null , purchaseOfGoodsListner , errorListener);
        VolleyRequestManager.getRequestQueue().add(dataRequest);
    }

    private void initData(){

        //_orderPulltorefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
        //    @Override
        //    public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
        //        getData();
        //    }
        //});

        initFragments();

        setLineChartData();
    }

    List<BaseFragment> _fragments;
    FragmentManager _fragmentManager;
     @Bind(R.id.order_indicator)
     TabPageIndicator _indicator;

    protected void initFragments(){
        LineChartFragment fragment1 = new LineChartFragment();
        LineChartFragment fragment2 = new LineChartFragment();
        LineChartFragment fragment3 = new LineChartFragment();
        _fragments = new ArrayList<>();
        _fragments.add(fragment1);
        _fragments.add(fragment2);
        _fragments.add(fragment3);
        _fragmentManager = this.getActivity().getSupportFragmentManager();
        _orderFragmentAdapter = new OrderFragmentPageAdapter(_fragments, _fragmentManager);
        _viewPager.setAdapter(_orderFragmentAdapter);

        _indicator.setViewPager(_viewPager);
//        _orderFragment  = OrderFragment.newInstance();
//        _salesFragments = SalesFragment.newInstance();
//        _membersFragments = MembersFragment.newInstance();
//        _fragments = new ArrayList<>();
//        _fragments.add(_orderFragment);
//        _fragments.add(_salesFragments);
//        _fragments.add(_membersFragments);
//        _fragmentManager = this.getSupportFragmentManager();
//        _dataStatisticFragmentAdapter = new DataStatisticFragmentAdapter(_fragments,_fragmentManager);
//
//        _viewPager.setAdapter(_dataStatisticFragmentAdapter);
//        _circlePageIndicator.setViewPager(_viewPager);
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


    class LineChartFragment extends BaseFragment{

        @Bind(R.id.order_lineChart)
        LineChart _orderLineChart;


        public LineChartFragment (){
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_orderchart , container, false);
            ButterKnife.bind(this, rootView);
            initData();
            return rootView;
        }

        protected void initData(){
            _orderLineChart.setBackgroundColor(Color.WHITE);
            _orderLineChart.setDescription("line chart description");
            _orderLineChart.setNoDataText("no date to show chart");
            _orderLineChart.getAxisRight().setEnabled(false);

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
            _orderLineChart.setData(data);
            _orderLineChart.animateX(3000, Easing.EasingOption.EaseInOutQuart);
        }

        @Override
        public void onResume() {
            super.onResume();

        }
    }

}
