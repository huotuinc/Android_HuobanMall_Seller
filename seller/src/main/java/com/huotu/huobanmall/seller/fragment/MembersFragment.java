package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.huotu.huobanmall.seller.activity.WebViewActivity;
import com.huotu.huobanmall.seller.adapter.MembersFragmentPageAdapter;
import com.huotu.huobanmall.seller.bean.MJMemberStatisticModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MembersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembersFragment extends BaseFragment implements View.OnClickListener{
    private OnFragmentInteractionListener mListener;

    @Bind(R.id.members_statis1)
    RelativeLayout _membersstatis1;
    @Bind(R.id.members_statis2)
    RelativeLayout _membersstatis2;

    @Bind(R.id.members_viewPager)
    ViewPager _viewPager;

    MembersFragmentPageAdapter _membersFragmentAdapter;

    MJMemberStatisticModel _data;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MembersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MembersFragment newInstance() {
        MembersFragment fragment = new MembersFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MembersFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_members, container, false);
        ButterKnife.bind(this, rootView);

        _membersstatis1.setOnClickListener(this);
        _membersstatis2.setOnClickListener(this);

        initFragments();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    List<BaseFragment> _fragments;
    FragmentManager _fragmentManager;
    @Bind(R.id.members_indicator)
    TabPageIndicator _indicator;

    protected void initFragments(){
        MemberLineChartFragment fragment1 = new MemberLineChartFragment();
        MemberLineChartFragment fragment2 = new MemberLineChartFragment();
        MemberLineChartFragment fragment3 = new MemberLineChartFragment();
        _fragments = new ArrayList<>();
        _fragments.add(fragment1);
        _fragments.add(fragment2);
        _fragments.add(fragment3);
        _fragmentManager = this.getActivity().getSupportFragmentManager();
        _membersFragmentAdapter = new MembersFragmentPageAdapter(_fragments, _fragmentManager);
        _viewPager.setAdapter(_membersFragmentAdapter);

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

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.members_statis1){
            String url = "http://m.cnblogs.com";
            Intent intent = new Intent();
            intent.setClass(getActivity(), WebViewActivity.class);
            intent.putExtra(Constant.Extra_Url, url);
            ActivityUtils.getInstance().showActivity(getActivity(), intent);
        }else if( v.getId()==R.id.members_statis2){
            String url = "http://www.baidu.com";
            Intent intent = new Intent();
            intent.setClass(getActivity(), WebViewActivity.class);
            intent.putExtra(Constant.Extra_Url, url);
            ActivityUtils.getInstance().showActivity(getActivity(), intent);
        }
    }

    protected  void getData(){
        String url = Constant.USERREPORT_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url ,null);

        GsonRequest<MJMemberStatisticModel> userReportRequest = new GsonRequest<MJMemberStatisticModel>(
                Request.Method.GET,
                url,
                MJMemberStatisticModel.class,
                null,
                userReportListner,
                errorListener
        );

        this.showProgressDialog("","正在获取数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(userReportRequest);
    }

    protected Response.Listener<MJMemberStatisticModel> userReportListner = new Response.Listener<MJMemberStatisticModel>() {
        @Override
        public void onResponse(MJMemberStatisticModel mjMemberStatisticModel  ) {
            MembersFragment.this.closeProgressDialog();

            if( mjMemberStatisticModel==null){
                DialogUtils.showDialog(MembersFragment.this.getActivity(), MembersFragment.this.getFragmentManager(), "错误信息", "请求数据失败", "关闭");
                return;
            }
            if( mjMemberStatisticModel.getSystemResultCode()!=1){
                DialogUtils.showDialog( MembersFragment.this.getActivity(), MembersFragment.this.getFragmentManager() ,"错误信息", mjMemberStatisticModel.getSystemResultDescription(),"关闭" );
                return;
            }

            if(mjMemberStatisticModel.getResultCode() == Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity(MembersFragment.this.getActivity(), LoginActivity.class);
                return;
            }
            if( mjMemberStatisticModel.getResultCode() != 1){
                DialogUtils.showDialog( MembersFragment.this.getActivity(), MembersFragment.this.getFragmentManager() ,"错误信息", mjMemberStatisticModel.getResultDescription() ,"关闭" );
                return;
            }

            _data=mjMemberStatisticModel;


            //_orderPulltorefreshScrollView.onRefreshComplete();

            // SimpleDialogFragment.createBuilder(getActivity(), getFragmentManager())
            // .setMessage("oooooooo").show();
        }
    };

    protected Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            MembersFragment.this.closeProgressDialog();

////           SimpleDialogFragment.createBuilder( getActivity() , getFragmentManager() )
////                    .setTitle("错误信息")
//                    .setMessage( volleyError.getMessage())
//                    .setNegativeButtonText("关闭")
//                    .show();
        }
    };


    public static class MemberLineChartFragment extends BaseFragment{

        @Bind(R.id.members_lineChart)
        LineChart _memberLineChart;

        MJMemberStatisticModel _data=null;


        public MemberLineChartFragment (){
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_memberchart , container, false);
            ButterKnife.bind(this, rootView);

            return rootView;
        }

        protected void initData(){

            _memberLineChart.setBackgroundColor(Color.WHITE);
            _memberLineChart.setDescription("line chart description");
            _memberLineChart.setNoDataText("no date to show chart");
            _memberLineChart.getAxisRight().setEnabled(false);

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
            _memberLineChart.setData(data);
            _memberLineChart.animateX(3000, Easing.EasingOption.EaseInOutQuart);
        }

        @Override
        public void onResume() {
            super.onResume();
            initData();
        }

        public void setData( MJMemberStatisticModel data , int type ){
            _data=data;
            if( _data == null || _data.getResultData()==null ) return;

            List<Object> xData1=null;
            List<Object> xData2=null;
            List<Object> yData1=null;
            List<Object> yData2=null;
            if( type == 1 ){
                xData1 = (ArrayList) _data.getResultData().getTodayMemberTimes();
                yData1=(ArrayList)_data.getResultData().getTodayMemberAmounts();
                xData2 =(ArrayList)_data.getResultData().getTodayPartnerTimes();
                yData2 = (ArrayList)_data.getResultData().getTodayPartnerAmounts();
            }else if( type == 2){
                xData1 =(ArrayList) _data.getResultData().getWeekMemberTimes();
                yData1=(ArrayList)_data.getResultData().getTodayMemberAmounts();
                xData2 =(ArrayList)_data.getResultData().getTodayPartnerTimes();
                yData2 = (ArrayList)_data.getResultData().getTodayPartnerAmounts();
            }
            setLineChartData( _memberLineChart , xData1 , yData1,xData2,yData2 );
        }

        protected void setLineChartData( LineChart lineChart , List<Object> xData1 , List<Object> yData1,
                                         List<Object> xData2 , List<Object> yData2 ){
            if( xData1==null || yData1==null )return;

            int bgColor=0xFFFFFFFF;
            int gridColor=0xFFD3D3D3;
            int lineColor1=0xFF0094FF;
            int lineColor2 =0xFFFF3C00;
            int circleColor=0xFFFFFFFF;

            lineChart.setGridBackgroundColor(gridColor);
            lineChart.setBackgroundColor(bgColor);
            lineChart.setDescription("");
            lineChart.setNoDataText("暂无数据");
            lineChart.getAxisRight().setEnabled(false);

            List<String> xValues1= new ArrayList<String>();
            List<Entry> yValues1=new ArrayList<>();
            int count = xData1.size();
            for(int i=0;i< count ;i++){
                if( null == xData1.get(i)) continue;
                Object x = xData1.get(i);
                xValues1.add( String.valueOf( x));
                Object y = yData1.get(i);
                Entry item=new Entry( Float.valueOf( y.toString()) , i );
                yValues1.add(item);
            }
            LineDataSet dataSet1 =new LineDataSet( yValues1 ,"");
            dataSet1.setCircleColor(circleColor);
            dataSet1.setCircleSize(4);
            dataSet1.setDrawCircleHole(true);
            dataSet1.setDrawValues(false);
            dataSet1.setLineWidth(2);
            dataSet1.setColor(lineColor1);
            dataSet1.setValueTextSize(14);
            dataSet1.setValueTextColor(Color.GREEN);
            dataSet1.setDrawCubic(true);

            List<String> xValues2 =new ArrayList<>();
            List<Entry> yValues2 =new ArrayList<>();
            count = xData2.size();
            for(int i=0;i< count ;i++){
                if( null == xData2.get(i))continue;
                Object x = xData2.get(i);
                xValues2.add( String.valueOf( x));
                Object y = yData2.get(i);
                Entry item=new Entry ( Float.valueOf(y.toString()) , i );
                yValues2.add(item);
            }
            LineDataSet dataSet2 =new LineDataSet( yValues2 ,"");
            dataSet2.setCircleColor(circleColor);
            dataSet2.setCircleSize(4);
            dataSet2.setDrawCircleHole(true);
            dataSet2.setDrawValues(false);
            dataSet2.setLineWidth(2);
            dataSet2.setColor(lineColor2);
            dataSet2.setValueTextSize(14);
            dataSet2.setValueTextColor(Color.GREEN);
            dataSet2.setDrawCubic(true);

            ArrayList<LineDataSet> dataSets =new ArrayList<>();
            dataSets.add(dataSet1);
            dataSets.add(dataSet2);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(0xFFFFFFFF);

            YAxis yAxis = lineChart.getAxisLeft();
            yAxis.setTextColor(0xFFFFFFFF);

            lineChart.getLegend().setEnabled(false);

            LineData data =new LineData( xValues1 , dataSets );
            lineChart.setData(data);
            lineChart.animateX(2000, Easing.EasingOption.EaseInOutQuart);
        }
    }



}
