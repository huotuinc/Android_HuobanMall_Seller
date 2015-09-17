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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.LoginActivity;
import com.huotu.huobanmall.seller.activity.SalesDetailActivity;
import com.huotu.huobanmall.seller.adapter.OrderFragmentPageAdapter;
import com.huotu.huobanmall.seller.adapter.SalesFragmentPageAdapter;
import com.huotu.huobanmall.seller.bean.MJBillStatisticModel;
import com.huotu.huobanmall.seller.bean.MJSaleStatisticModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.Date;
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
public class SalesFragment extends BaseFragment implements View.OnClickListener{
    @Bind(R.id.sales_total)
    TextView _sales_total;
    @Bind(R.id.sales_info_count)
    TextView _sales_info_count;
    @Bind(R.id.sales_indicator)
    TabPageIndicator _indicator;
    @Bind(R.id.sales_viewPager)
    ViewPager _viewPager;
    @Bind(R.id.sales_statis1)
    RelativeLayout _sales_statis1;

    MJSaleStatisticModel _data;
    SalesLineChartFragment _fragment1=null;
    SalesLineChartFragment _fragment2=null;
    SalesLineChartFragment _fragment3=null;
    List<BaseFragment> _fragments;
    FragmentManager _fragmentManager;
    SalesFragmentPageAdapter _salesFragmentAdapter;
    int _currentIndx = 0;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SalesFragment.
     */
    public static SalesFragment newInstance() {
        SalesFragment fragment = new SalesFragment();
        Bundle args = new Bundle();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sales, container, false);

        ButterKnife.bind(this, rootView);

        initData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

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

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.sales_statis1){
            ActivityUtils.getInstance().showActivity(this.getActivity(), SalesDetailActivity.class);
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

    protected void initData(){
        _sales_statis1.setOnClickListener(this);

        initFragments();

        _indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                _currentIndx = position;

                if( _data==null || _data.getResultData()==null ) return;
                if( position==0){
                    Float amount = _data.getResultData().getTotalAmount();
                    Float todayAmount = _data.getResultData().getTodayAmount();
                    _sales_total.setText( String.valueOf( amount ) );
                    _sales_info_count.setText( String.valueOf( todayAmount) );
                }else if( position==1){
                    Float amount = _data.getResultData().getTotalAmount();
                    Float weekAmount = _data.getResultData().getWeekAmount();
                    _sales_total.setText( String.valueOf( amount ));
                    _sales_info_count.setText( String.valueOf( weekAmount ));
                }else if( position==2){
                    Float amount = _data.getResultData().getTotalAmount();
                    Float monthAmount = _data.getResultData().getMonthAmount();
                    _sales_total.setText( String.valueOf( amount ));
                    _sales_info_count.setText( String.valueOf( monthAmount ));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getData();
    }

    protected void getData(){
        String url = Constant.SALESREPORT_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url ,null);

        GsonRequest<MJSaleStatisticModel> saleReportRequest = new GsonRequest<MJSaleStatisticModel>(
                Request.Method.GET,
                url,
                MJSaleStatisticModel.class,
                null,
                saleReportListner,
                errorListener
        );

        this.showProgressDialog("","正在获取数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(saleReportRequest);
    }

    protected void initFragments(){
        _fragment1 = new SalesLineChartFragment();
        _fragment2 = new SalesLineChartFragment();
        _fragment3 = new SalesLineChartFragment();
        _fragments = new ArrayList<>();
        _fragments.add(_fragment1);
        _fragments.add(_fragment2);
        _fragments.add(_fragment3);
        _fragmentManager = this.getActivity().getSupportFragmentManager();
        _salesFragmentAdapter = new SalesFragmentPageAdapter(_fragments, _fragmentManager);
        _viewPager.setAdapter(_salesFragmentAdapter);
        _indicator.setViewPager(_viewPager);
    }

    protected Response.Listener<MJSaleStatisticModel> saleReportListner = new Response.Listener<MJSaleStatisticModel>() {
        @Override
        public void onResponse(MJSaleStatisticModel mjSaleStatisticModel  ) {
            SalesFragment.this.closeProgressDialog();

            if( mjSaleStatisticModel==null){
                DialogUtils.showDialog(SalesFragment.this.getActivity(), SalesFragment.this.getFragmentManager(), "错误信息", "请求数据失败", "关闭");
                return;
            }
            if( mjSaleStatisticModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(SalesFragment.this.getActivity(), SalesFragment.this.getFragmentManager(), "错误信息", mjSaleStatisticModel.getSystemResultDescription(), "关闭");
                return;
            }

            if(mjSaleStatisticModel.getResultCode() == Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity(SalesFragment.this.getActivity(), LoginActivity.class);
                return;
            }
            if( mjSaleStatisticModel.getResultCode() != 1){
                DialogUtils.showDialog( SalesFragment.this.getActivity(), SalesFragment.this.getFragmentManager() ,"错误信息", mjSaleStatisticModel.getResultDescription() ,"关闭" );
                return;
            }

            _data=mjSaleStatisticModel;
            Float total = _data.getResultData().getTotalAmount();
            _sales_total.setText( String.valueOf( total ));
            if(_currentIndx==0) {
                Float todayAmount = _data.getResultData().getTodayAmount();
                _sales_info_count.setText( String.valueOf( todayAmount ));
            }else if( _currentIndx == 1){
                Float weekAmount = _data.getResultData().getWeekAmount();
                _sales_info_count.setText( String.valueOf( weekAmount ) );
            }else if( _currentIndx==2){
                Float monthAmount = _data.getResultData().getMonthAmount();
                _sales_info_count.setText( String.valueOf( monthAmount ));
            }
            //setLineChartData();
            _fragment1.setData(_data,1);
            _fragment2.setData(_data,2);
            _fragment3.setData(_data,3);
            _salesFragmentAdapter.notifyDataSetChanged();
        }
    };

    protected Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

            SalesFragment.this.closeProgressDialog();

            String message="";
            if( volleyError.networkResponse !=null){
                message = new String( volleyError.networkResponse.data);
            }else if( volleyError.getCause() !=null ) {
                message = volleyError.getCause().getMessage();
            }
            DialogUtils.showDialog(SalesFragment.this.getActivity(), SalesFragment.this.getFragmentManager(),"错误信息", message ,"关闭");


        }
    };

    public static class SalesLineChartFragment extends BaseFragment{

        @Bind(R.id.sales_lineChart)
        LineChart _saleslineChart;

        MJSaleStatisticModel _data;
        int _type=1;

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
            int bgColor=0xFFFFFFFF;
            _saleslineChart.setBackgroundColor(bgColor);
            _saleslineChart.setDrawGridBackground(false);
            _saleslineChart.setDescription("");
            _saleslineChart.setNoDataText("暂无数据");

        }

        @Override
        public void onResume() {
            super.onResume();

            setData(_data, _type);
        }

        public void setData( MJSaleStatisticModel data ,int type ){
            _data=data;
            _type=type;
            if( _data==null || _data.getResultData() == null ) return;

            if( this.isResumed()) {
                if( type == 1) {
                    setLineChartData( _saleslineChart, (ArrayList) _data.getResultData().getTodayTimes() , _data.getResultData().getTodayAmounts());
                }else if( type == 2){
                    setLineChartData(_saleslineChart ,(ArrayList) _data.getResultData().getWeekTimes() , _data.getResultData().getWeekAmounts() );
                }else if( type==3){
                    setLineChartData(_saleslineChart, (ArrayList)_data.getResultData().getMonthTimes(),_data.getResultData().getMonthAmounts());
                }
            }
        }

        protected void setLineChartData( LineChart lineChart , List<Object> xData1 , List<Float> yData1 ){
            if( xData1==null || yData1==null )return;

            //int bgColor=0xFFFFFFFF;
            int gridColor=0xFFD3D3D3;
            int lineColor =0xFFFF3C00;
            //int circleColor=0xFFFFFFFF;
            int textColor = 0xFF000000;

            //lineChart.setGridBackgroundColor(gridColor);
            //lineChart.setBackgroundColor(bgColor);
            //lineChart.setDescription("");
            //lineChart.setNoDataText("暂无数据");
            //lineChart.getAxisRight().setEnabled(false);

            List<String> xValues1= new ArrayList<String>();
            List<Entry> yValues1=new ArrayList<>();
            int count = xData1.size();
            for(int i=0;i< count ;i++){
                Object xObj = xData1.get(i);
                String x ="";
                if( xObj instanceof Date){
                     int day = ((Date)xObj ).getDate();
                     x = day+"日";
                }else {
                    x = xObj.toString();
                }

                xValues1.add( x );
                Float y = yData1.get(i);
                Entry item=new Entry( y , i );
                yValues1.add(item);
            }

            LineDataSet dataSet =new LineDataSet( yValues1 ,"");
            dataSet.setCircleColor(lineColor);
            //dataSet.setCircleColors(new int[]{Color.rgb(255, 60, 00)});
            dataSet.setCircleSize(5);
            //dataSet.setDrawCircleHole(true);
            dataSet.setLineWidth(3);
            dataSet.setColor(lineColor);
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(textColor);
            dataSet.setDrawCubic(true);
            dataSet.setDrawValues(false);
            dataSet.setCircleColorHole( Color.WHITE );
            dataSet.setDrawCircleHole(true);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(textColor);

            YAxis yAxis1 = lineChart.getAxisRight();
            yAxis1.setTextColor(0x34327882);
            yAxis1.setEnabled(true);
            yAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

            YAxis yAxis = lineChart.getAxisLeft();
            yAxis.setTextColor(0x34324222);

            lineChart.setBorderColor( gridColor );
            lineChart.setDrawBorders(true);
            //lineChart.getAxisLeft().setEnabled(false);

            lineChart.getLegend().setEnabled(false);

            LineData data =new LineData(xValues1 , dataSet );
            lineChart.setData(data);
            lineChart.animateX(2000, Easing.EasingOption.EaseInOutQuart);
        }

    }


}
