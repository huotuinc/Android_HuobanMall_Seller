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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.LoginActivity;
import com.huotu.huobanmall.seller.activity.SalesDetailActivity;
import com.huotu.huobanmall.seller.activity.TopSalesActivity;
import com.huotu.huobanmall.seller.activity.WebViewActivity;
import com.huotu.huobanmall.seller.adapter.DataStatisticFragmentAdapter;
import com.huotu.huobanmall.seller.adapter.OrderFragmentPageAdapter;
import com.huotu.huobanmall.seller.adapter.PurchaseOfGoodsAdapter;
import com.huotu.huobanmall.seller.bean.MJBillStatisticModel;
import com.huotu.huobanmall.seller.bean.MJMemberStatisticModel;
import com.huotu.huobanmall.seller.bean.PurchaseOfGoods;
import com.huotu.huobanmall.seller.common.Constant;

import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.huotu.huobanmall.seller.widget.MJMarkerView;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单统计
 */
public class OrderFragment extends BaseFragment implements View.OnClickListener {

    //private OnFragmentInteractionListener mListener;

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

    //List<PurchaseOfGoods> _purchaseOfGoods;
    //PurchaseOfGoodsAdapter _purchaseAdapter;
    LineChartFragment _fragment1 = null;
    LineChartFragment _fragment2 = null;
    LineChartFragment _fragment3 = null;
    List<BaseFragment> _fragments;
    FragmentManager _fragmentManager;
    @Bind(R.id.order_indicator)
    TabPageIndicator _indicator;
    @Bind(R.id.order_info_count)
    TextView _order_info_count;
    @Bind(R.id.order_total)
    TextView _orderTotal;

    MJBillStatisticModel _data=null;
    int _currentIdx = 0;
    boolean _isFirst =true;
    View _rootView;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if( _rootView ==null) {
            _rootView = inflater.inflate(R.layout.fragment_order, container, false);
            ButterKnife.bind(this, _rootView);

            initData();
            order_statis1.setOnClickListener(this);
        }else{
            ViewGroup parentView = (ViewGroup)_rootView.getParent();
            if( parentView !=null ){
                parentView.removeView(_rootView);
            }
        }

        return _rootView;
    }

    protected  void getData(){
        String url = Constant.ORDERREPORT_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url ,null);

        GsonRequest<MJBillStatisticModel> orderReportRequest = new GsonRequest<MJBillStatisticModel>(
                Request.Method.GET,
                url,
                MJBillStatisticModel.class,
                null,
                billReportListner,
                this
        );

        this.showProgressDialog("", "正在获取数据，请稍等...");

        VolleyRequestManager.AddRequest(orderReportRequest);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View v) {
        if( v.getId()==R.id.order_statis1){
            Intent intent = new Intent(this.getActivity(), TopSalesActivity.class);
            ActivityUtils.getInstance().showActivity(this.getActivity(),intent);
        }
    }

    protected Response.Listener<MJBillStatisticModel> billReportListner = new Response.Listener<MJBillStatisticModel>() {
        @Override
        public void onResponse(MJBillStatisticModel mjBillStatisticModel  ) {
            OrderFragment.this.closeProgressDialog();

            if( mjBillStatisticModel==null){
                DialogUtils.showDialog( OrderFragment.this.getActivity(), OrderFragment.this.getFragmentManager() ,"错误信息", "请求数据失败","关闭" );
                return;
            }
            if( mjBillStatisticModel.getSystemResultCode()!=1){
                DialogUtils.showDialog( OrderFragment.this.getActivity(), OrderFragment.this.getFragmentManager() ,"错误信息", mjBillStatisticModel.getSystemResultDescription(),"关闭" );
                return;
            }

            if(mjBillStatisticModel.getResultCode() == Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity(OrderFragment.this.getActivity(), LoginActivity.class);
                return;
            }
            if( mjBillStatisticModel.getResultCode() != 1){
                DialogUtils.showDialog( OrderFragment.this.getActivity(), OrderFragment.this.getFragmentManager() ,"错误信息", mjBillStatisticModel.getResultDescription() ,"关闭" );
                return;
            }

            _data=mjBillStatisticModel;

            Long totalAmount = _data.getResultData().getTotalAmount();
            _orderTotal.setText(String.valueOf(totalAmount));
            if( _currentIdx== 0){
                Long todayAmount = _data.getResultData().getTodayAmount();
                _order_info_count.setText( String.valueOf( todayAmount ) );
            }else if( _currentIdx==1){
                Long weekAmount = _data.getResultData().getWeekAmount();
                _order_info_count.setText( String.valueOf( weekAmount ) );
            }else if( _currentIdx==2){
                Long monthAmount = _data.getResultData().getMonthAmount();
                _order_info_count.setText( String.valueOf(monthAmount) );
            }

            _fragment1.setData( _data , 1 );
            _fragment2.setData(_data,2);
            _fragment3.setData(_data, 3);
            _orderFragmentAdapter.notifyDataSetChanged();
        }
    };

    protected void setData(){
    }

    private void initData(){

        //_orderPulltorefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
        //    @Override
        //    public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
        //        getData();
        //    }
        //});

        _indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                _currentIdx = position;

                if (_data == null || _data.getResultData() == null) return;
                if (position == 0) {
                    Long count = _data.getResultData().getTodayAmount();
                    _order_info_count.setText(String.valueOf(count));
                    //_orderFragmentAdapter.notifyDataSetChanged();
                    _fragment1.setData(_data, 1);
                } else if (position == 1) {
                    Long count = _data.getResultData().getWeekAmount();
                    _order_info_count.setText(String.valueOf(count));
                    _fragment2.setData(_data, 2);
                    //_orderFragmentAdapter.notifyDataSetChanged();
                } else if (position == 2) {
                    Long count = _data.getResultData().getMonthAmount();
                    _order_info_count.setText(String.valueOf(count));
                    //_orderFragmentAdapter.notifyDataSetChanged();
                    _fragment3.setData(_data, 3);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initFragments();

        //getData();
    }

    protected void initFragments(){
        _fragment1 = new LineChartFragment();
        _fragment2 = new LineChartFragment();
        _fragment3 = new LineChartFragment();
        _fragments = new ArrayList<>();
        _fragments.add(_fragment1);
        _fragments.add(_fragment2);
        _fragments.add(_fragment3);
        _fragmentManager = this.getActivity().getSupportFragmentManager();
        _orderFragmentAdapter = new OrderFragmentPageAdapter(_fragments, _fragmentManager);
        _viewPager.setAdapter(_orderFragmentAdapter);

        _indicator.setViewPager(_viewPager);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if( isVisibleToUser && _isFirst){
            getData();
            _isFirst=false;
        }
    }

    public static class LineChartFragment extends BaseFragment{

        @Bind(R.id.order_lineChart)
        LineChart _orderLineChart;

        MJBillStatisticModel _data=null;
        int _type=1;

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
            int bgColor=0xFFFFFFFF;
            _orderLineChart.setBackgroundColor( bgColor );
            _orderLineChart.setDrawGridBackground(false);
            _orderLineChart.setDescription("");
            _orderLineChart.setNoDataText("暂无数据");
            _orderLineChart.getAxisRight().setEnabled(false);
            MJMarkerView mv = new MJMarkerView( getActivity() , R.layout.custom_marker_view);
            _orderLineChart.setMarkerView(mv);
        }

        @Override
        public void onResume() {
            super.onResume();

            setData(_data,_type);
        }

        public void setData( MJBillStatisticModel data ,int type ){
            _data=data;
            _type=type;
            if( _data==null || _data.getResultData() == null ) return;

            if( _orderLineChart==null) return;
            //if( this.isResumed()) {
                if( type == 1) {
                    setLineChartData(_orderLineChart, (ArrayList) _data.getResultData().getTodayTimes() , _data.getResultData().getTodayAmounts());
                }else if( type == 2){
                    setLineChartData(_orderLineChart ,(ArrayList) _data.getResultData().getWeekTimes() , _data.getResultData().getWeekAmounts() );
                }else if( type==3){
                    setLineChartData(_orderLineChart, (ArrayList)_data.getResultData().getMonthTimes(),_data.getResultData().getMonthAmounts());
                }
            //}
        }

        protected void setLineChartData( LineChart lineChart , List<Object> xData1 , List<Integer> yData1 ){
            if( xData1==null || yData1==null )return;

            int bgColor=0xFFFFFFFF;
            int gridColor=0xFFD3D3D3;
            int lineColor =0xFFFF3C00;
            int circleColor=0xFFFFFFFF;
            int textColor = 0xFF000000;

            lineChart.setGridBackgroundColor(gridColor);
            lineChart.setBackgroundColor(bgColor);
            lineChart.setDescription("");
            lineChart.setNoDataText("暂无数据");
            //lineChart.getAxisRight().setEnabled(false);

            List<String> xValues1= new ArrayList<String>();

            List<Entry> yValues1=new ArrayList<>();
            int count = xData1.size();
            //int startIndex = 0;
            for(int i=0;i< count ;i++){
                Object x = xData1.get(i);

                if( x instanceof Date){
                    int day = ((Date)x).getDate();
//                    if(i==0 && day != 0 ){
//                        xValues1.add("0日");
//                        startIndex =1;
//                    }
                    xValues1.add( String.valueOf( day ) + "日");
                }else{
//                    if(i==0 && x != 0  ){
//                        xValues1.add("0时");
//                        startIndex =1;
//                    }
                    xValues1.add( String.valueOf( x ) +"时" );
                }

                int y = yData1.get(i);
                Entry item=new Entry( y , i );
                yValues1.add(item);
            }

            LineDataSet dataSet =new LineDataSet( yValues1 ,"");
            //dataSet.setCircleColor(circleColor);
            dataSet.setCircleColors(new int[]{Color.rgb(255, 60, 00)});
            dataSet.setCircleSize(5);
            //dataSet.setDrawCircleHole(true);
            dataSet.setLineWidth(2);
            dataSet.setColor(lineColor);
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(textColor);
            dataSet.setDrawCubic(true);
            dataSet.setDrawValues(false);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(textColor);


            YAxis yAxis1 = lineChart.getAxisRight();
            yAxis1.setTextColor(0xFFFFFFFF);
            yAxis1.setEnabled(true);
            yAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

            YAxis yAxis = lineChart.getAxisLeft();
            yAxis.setTextColor(0xFF000000);
            //yAxis.setStartAtZero(false);

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
