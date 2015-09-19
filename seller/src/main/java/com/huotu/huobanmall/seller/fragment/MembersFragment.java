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
import com.huotu.huobanmall.seller.activity.RebateStatisticsActivity;
import com.huotu.huobanmall.seller.activity.WebViewActivity;
import com.huotu.huobanmall.seller.adapter.MembersFragmentPageAdapter;
import com.huotu.huobanmall.seller.bean.MJMemberStatisticModel;
import com.huotu.huobanmall.seller.bean.TopConsumeModel;
import com.huotu.huobanmall.seller.bean.TopScoreModel;
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
import java.util.Objects;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends BaseFragment implements View.OnClickListener{
    private OnFragmentInteractionListener mListener;

    @Bind(R.id.members_statis1)
    RelativeLayout _membersstatis1;
    @Bind(R.id.members_statis2)
    RelativeLayout _membersstatis2;

    @Bind(R.id.members_firendCount2)
    TextView _member_fxsCount2;
    @Bind(R.id.members_memberCount2)
    TextView _member_memberCount2;
    @Bind(R.id.members_viewPager)
    ViewPager _viewPager;
    @Bind(R.id.members_indicator)
    TabPageIndicator _indicator;
    @Bind(R.id.members_total)
    TextView _members_total;
    @Bind(R.id.members_firendCount)
    TextView _members_firendCount;

    MemberLineChartFragment _fragment1=null;
    MemberLineChartFragment _fragment2=null;
    MemberLineChartFragment _fragment3=null;
    List<BaseFragment> _fragments=null;
    MembersFragmentPageAdapter _membersFragmentAdapter;
    FragmentManager _fragmentManager;

    MJMemberStatisticModel _data;
    int _currentIndex = 0;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MembersFragment.
     */
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

        getData();

        return rootView;
    }

    protected void initData(){

        initFragments();

        _indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                _currentIndex = position;
                if (_data == null || _data.getResultData() == null) return;
                if (position == 0) {
                    //Integer count = 0;
                    Long fxsCount = _data.getResultData().getTodayPartnerAmount();
                    Long memberCount = _data.getResultData().getTodayMemberAmount();
                    //_members_info_count.setText(String.valueOf(count));
                    _member_fxsCount2.setText(String.valueOf(fxsCount));
                    _member_memberCount2.setText(String.valueOf(memberCount));
                } else if (position == 1) {
                    //Integer count = 0;
                    Long fxsCount = _data.getResultData().getWeekPartnerAmount();
                    Long memberCount = _data.getResultData().getWeekMemberAmount();
                    //_members_info_count.setText(String.valueOf(count));
                    _member_fxsCount2.setText(String.valueOf(fxsCount));
                    _member_memberCount2.setText(String.valueOf(memberCount));
                } else if (position == 2) {
                    //Integer count = 0;
                    Long fxsCount = _data.getResultData().getMonthPartnerAmount();
                    Long memberCount = _data.getResultData().getMonthMemberAmount();
                    //_members_info_count.setText(String.valueOf(count));
                    _member_fxsCount2.setText(String.valueOf(fxsCount));
                    _member_memberCount2.setText(String.valueOf(memberCount));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }



    protected void initFragments(){
        _fragment1 = new MemberLineChartFragment();
        _fragment2 = new MemberLineChartFragment();
        _fragment3 = new MemberLineChartFragment();
        _fragments = new ArrayList<>();
        _fragments.add(_fragment1);
        _fragments.add(_fragment2);
        _fragments.add(_fragment3);
        _fragmentManager = this.getActivity().getSupportFragmentManager();
        _membersFragmentAdapter = new MembersFragmentPageAdapter(_fragments, _fragmentManager);
        _viewPager.setAdapter(_membersFragmentAdapter);

        _indicator.setViewPager(_viewPager);

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
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.members_statis1){
            //String url = "http://m.cnblogs.com";
            Intent intent = new Intent();
            intent.setClass(getActivity(), RebateStatisticsActivity.class);
            //intent.putExtra(Constant.Extra_Url, url);
            ActivityUtils.getInstance().showActivity(getActivity(), intent);
        }else if( v.getId()==R.id.members_statis2){
            //String url = "http://www.baidu.com";
            Intent intent = new Intent();
            intent.setClass(getActivity(), TopConsumeModel.class);
            //intent.putExtra(Constant.Extra_Url, url);
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

            Long total = _data.getResultData().getTotalMember();
            _members_total.setText( String.valueOf( total) );
            Long fxsAmount = _data.getResultData().getTotalPartner();
            _members_firendCount.setText( String.valueOf( fxsAmount));
            if(_currentIndex==0){
                Long today_fxs = _data.getResultData().getTodayMemberAmount();
                Long today_menber = _data.getResultData().getTodayPartnerAmount();
                _member_fxsCount2.setText( String.valueOf( today_fxs) );
                _member_memberCount2.setText( String.valueOf( today_menber ) );
            }else if( _currentIndex==1){
                Long week_fxsCount = _data.getResultData().getWeekPartnerAmount();
                Long week_MemberCount = _data.getResultData().getWeekMemberAmount();
                _member_fxsCount2.setText( String.valueOf( week_fxsCount ));
                _member_memberCount2.setText( String.valueOf( week_MemberCount ));
            }else if( _currentIndex==2){
                Long month_fxs = _data.getResultData().getMonthPartnerAmount();
                Long month_MemberCount = _data.getResultData().getMonthMemberAmount();
                _member_fxsCount2.setText( String.valueOf( month_fxs ));
                _member_memberCount2.setText( String.valueOf( month_MemberCount) );
            }

            _fragment1.setData(_data,1);
            _fragment2.setData(_data,2);
            _fragment3.setData(_data, 3);
            _membersFragmentAdapter.notifyDataSetChanged();

        }
    };

    protected Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            MembersFragment.this.closeProgressDialog();
            String message="";
            if( volleyError.networkResponse !=null){
                message = new String( volleyError.networkResponse.data);
            }else if( volleyError.getCause() !=null ) {
                message = volleyError.getCause().getMessage();
            }
            DialogUtils.showDialog(MembersFragment.this.getActivity(), MembersFragment.this.getFragmentManager(),"错误信息", message ,"关闭");

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

        int _type = 1;

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
            initData();

            return rootView;
        }

        protected void initData(){
            int bgColor=0xFFFFFFFF;

            _memberLineChart.setBackgroundColor(bgColor);
            _memberLineChart.setDrawGridBackground(false);
            _memberLineChart.setDescription("");
            _memberLineChart.setNoDataText("暂无数据");



        }

        @Override
        public void onResume() {
            super.onResume();

            setData(_data,_type);
        }

        public void setData( MJMemberStatisticModel data , int type ){
            _data=data;
            _type= type;
            if( _data == null || _data.getResultData()==null ) return;
            if( !this.isResumed() )return;

            List<Object> xData1=null;
            List<Object> xData2=null;
            List<Object> yData1=null;
            List<Object> yData2=null;
            if( type == 1 ){
                xData1 = (ArrayList) _data.getResultData().getTodayTimes();
                yData1 = (ArrayList)_data.getResultData().getTodayMemberAmounts();
                xData2 = (ArrayList)_data.getResultData().getTodayTimes();
                yData2 = (ArrayList)_data.getResultData().getTodayPartnerAmounts();
            }else if( type == 2){
                xData1 = (ArrayList) _data.getResultData().getWeekTimes();
                yData1 = (ArrayList)_data.getResultData().getWeekMemberAmounts();
                xData2 = (ArrayList)_data.getResultData().getWeekTimes();
                yData2 = (ArrayList)_data.getResultData().getWeekPartnerAmounts();
            }else if( type == 3 ){
                //if( _data.getResultData().getMonthMemberTimes() )
                xData1 =  (ArrayList)_data.getResultData().getMonthTimes();
                yData1 =  (ArrayList)_data.getResultData().getMonthMemberAmounts();

                xData2 =  (ArrayList)_data.getResultData().getMonthTimes();
                yData2 =  (ArrayList)_data.getResultData().getMonthPartnerAmounts();
            }

            setLineChartData( _memberLineChart , xData1 , yData1,xData2,yData2 );
        }

        protected void setLineChartData( LineChart lineChart , List<Object> xData1 , List<Object> yData1,
                                         List<Object> xData2 , List<Object> yData2 ){
            //if( xData1==null || yData1==null )return;

            int bgColor=0xFFFFFFFF;
            //int gridColor=0xFFD3D3D3;
            int lineColor1 = 0xFF0094FF;
            int lineColor2 =0xFFFF3C00;
            int textColor = 0xFF000000;
            //int circleColor=0xFFFFFFFF;
            List<String> xValue = new ArrayList<>();

            ArrayList<LineDataSet> dataSets =new ArrayList<>();

            if( xData1!=null && yData1 !=null ) {
                List<String> xValues1 = new ArrayList<String>();
                List<Entry> yValues1 = new ArrayList<>();
                int count = xData1.size();
                for (int i = 0; i < count; i++) {
                    if (null == xData1.get(i)) continue;
                    Object xObj = xData1.get(i);

                    String x = "";
                    if( xObj instanceof Date){
                        int day = ((Date)xObj).getDate();
                        x = String.valueOf(day)+"日";
                        xValues1.add( x );
                    }else {
                        x = String.valueOf( xObj);
                        xValues1.add(x);
                    }

                    //if( !xValue.contains( x )){
                        xValue.add(x);
                    //}

                    Object y = yData1.get(i);
                    Entry item = new Entry(Float.valueOf(y.toString()), i);
                    yValues1.add(item);
                }
                LineDataSet dataSet1 = new LineDataSet(yValues1, "");
                dataSet1.setCircleColor(lineColor1 );
                dataSet1.setCircleSize(5);
                dataSet1.setDrawCircleHole(true);
                dataSet1.setDrawValues(false);
                dataSet1.setLineWidth(2);
                dataSet1.setColor(lineColor1);
                dataSet1.setValueTextSize(14);
                dataSet1.setValueTextColor(Color.GREEN);
                dataSet1.setDrawCubic(true);
                dataSet1.setCircleColorHole(Color.WHITE);

                dataSets.add(dataSet1);
            }

            if( xData2!=null && yData2 !=null ) {
                List<String> xValues2 = new ArrayList<>();
                List<Entry> yValues2 = new ArrayList<>();
                int count = xData2.size();
                for (int i = 0; i < count; i++) {
                    if (null == xData2.get(i)) continue;
                    Object xObj = xData2.get(i);
                    String x ="";
                    if( xObj instanceof Date){
                        int day=((Date)xObj).getDate();
                        x = String.valueOf(day)+"日";
                    }else{
                        x= String.valueOf( xObj);
                    }
                    xValues2.add(x);

                    //if( !xValue.contains(x)){
                    //    xValue.add(x);
                    //}
                    Object y = yData2.get(i);
                    Entry item = new Entry(Float.valueOf(y.toString()), i);
                    yValues2.add(item);
                }
                LineDataSet dataSet2 = new LineDataSet(yValues2, "");
                dataSet2.setCircleColor( lineColor2 );
                dataSet2.setCircleSize(5);
                dataSet2.setDrawCircleHole(true);
                dataSet2.setDrawValues(false);
                dataSet2.setLineWidth(2);
                dataSet2.setColor(lineColor2);
                dataSet2.setValueTextSize(14);
                dataSet2.setValueTextColor(Color.GREEN);
                dataSet2.setDrawCubic(true);
                dataSet2.setCircleColorHole(Color.WHITE);

                dataSets.add(dataSet2);
            }

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor( textColor );

            YAxis yAxis = lineChart.getAxisLeft();
            yAxis.setTextColor(0xFFFFFFFF);

            lineChart.getLegend().setEnabled(false);

            LineData data =new LineData( xValue , dataSets );
            lineChart.setData(data);
            lineChart.animateX(2000, Easing.EasingOption.EaseInOutQuart);
        }
    }

}
