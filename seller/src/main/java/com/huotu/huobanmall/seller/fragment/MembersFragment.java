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
import com.github.mikephil.charting.utils.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.ConsumeStatisticsActivity;
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
import com.huotu.huobanmall.seller.utils.StringUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.huotu.huobanmall.seller.widget.MJMarkerView;
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
    View _rootView;
    boolean _isFirst=true;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MembersFragment.
     */
    public static MembersFragment newInstance() {
        MembersFragment fragment = new MembersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MembersFragment() {
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
        if( _rootView ==null) {
            _rootView = inflater.inflate(R.layout.fragment_members, container, false);
            ButterKnife.bind(this, _rootView);

            _membersstatis1.setOnClickListener(this);
            _membersstatis2.setOnClickListener(this);

            initFragments();
        }else{
            ViewGroup parentView = (ViewGroup)_rootView.getParent();
            if( parentView !=null ){
                parentView.removeView(_rootView);
            }
        }
        return _rootView;
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

        _indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( _data ==null || _data.getResultData()==null ) return;
                if(position==0){
                    Long today_fxs = _data.getResultData().getTodayMemberAmount();
                    Long today_menber = _data.getResultData().getTodayPartnerAmount();
                    _member_fxsCount2.setText( String.valueOf( today_fxs) );
                    _member_memberCount2.setText( String.valueOf( today_menber ) );
                }else if( position==1){
                    Long week_fxsCount = _data.getResultData().getWeekPartnerAmount();
                    Long week_MemberCount = _data.getResultData().getWeekMemberAmount();
                    _member_fxsCount2.setText( String.valueOf( week_fxsCount ));
                    _member_memberCount2.setText( String.valueOf( week_MemberCount ));
                }else if( position==2){
                    Long month_fxs = _data.getResultData().getMonthPartnerAmount();
                    Long month_MemberCount = _data.getResultData().getMonthMemberAmount();
                    _member_fxsCount2.setText( String.valueOf( month_fxs ));
                    _member_memberCount2.setText( String.valueOf( month_MemberCount) );
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.members_statis1){
            Intent intent = new Intent();
            intent.setClass(getActivity(), RebateStatisticsActivity.class);
            ActivityUtils.getInstance().showActivity(getActivity(), intent);
        }else if( v.getId()==R.id.members_statis2){
            Intent intent = new Intent();
            intent.setClass(getActivity(), ConsumeStatisticsActivity.class);
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
                //errorListener
                this
        );

        this.showProgressDialog("", "正在获取数据，请稍等...");

        VolleyRequestManager.AddRequest(userReportRequest);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if( isVisibleToUser && _isFirst){
            getData();
            _isFirst =false;
        }
    }

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
            MJMarkerView mv = new MJMarkerView( getActivity() , R.layout.custom_marker_view);
            _memberLineChart.setMarkerView(mv);
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
            int gridColor=0xFFD3D3D3;
            int lineColor2 = 0xFF0094FF;
            int lineColor1 =0xFFFF3C00;
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
                        x = String.valueOf( xObj)+"时";
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
                dataSet1.setCircleColor(lineColor1);
                dataSet1.setCircleSize(5);
                dataSet1.setDrawCircleHole(true);
                dataSet1.setDrawValues(false);
                dataSet1.setLineWidth(2);
                dataSet1.setColor(lineColor1);
                dataSet1.setValueTextSize(12);
                //dataSet1.setValueTextColor(Color.GREEN);
                dataSet1.setDrawCubic(true);
                dataSet1.setCircleColorHole(Color.WHITE);
                //
                dataSet1.setValueFormatter(new DefaultValueFormatter(0));

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
                        x= String.valueOf( xObj)+"时";
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
                dataSet2.setCircleColor(lineColor2);
                dataSet2.setCircleSize(5);
                dataSet2.setDrawCircleHole(true);
                dataSet2.setDrawValues(false);
                dataSet2.setLineWidth(2);
                dataSet2.setColor(lineColor2);
                dataSet2.setValueTextSize(12);
                //dataSet2.setValueTextColor(Color.GREEN);
                dataSet2.setDrawCubic(true);
                dataSet2.setCircleColorHole(Color.WHITE);

                dataSets.add(dataSet2);
            }

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(textColor);

            YAxis yAxis1 = lineChart.getAxisRight();
            yAxis1.setTextColor(0xFFFFFFFF);
            yAxis1.setEnabled(true);

            yAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

            YAxis yAxis = lineChart.getAxisLeft();
            yAxis.setTextColor(0xFF000000);

            lineChart.setBorderColor(gridColor);
            lineChart.setDrawBorders(true);
            lineChart.getLegend().setEnabled(false);

            LineData data =new LineData( xValue , dataSets );
            lineChart.setData(data);
            lineChart.animateX(2000, Easing.EasingOption.EaseInOutQuart);
        }
    }

}
