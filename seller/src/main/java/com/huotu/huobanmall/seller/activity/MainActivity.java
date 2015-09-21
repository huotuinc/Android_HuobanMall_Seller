package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.huotu.huobanmall.seller.Interface.IIndexFragmentInteractionListener;
import com.huotu.huobanmall.seller.adapter.TodayDataFragmentAdapter;
import com.huotu.huobanmall.seller.bean.MJNewTodayModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.fragment.BaseFragment;
import com.huotu.huobanmall.seller.fragment.TodayDistributorsFragment;
import com.huotu.huobanmall.seller.fragment.TodayMemberFragment;
import com.huotu.huobanmall.seller.fragment.TodayOrderFragment;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity implements IIndexFragmentInteractionListener{
    @Bind(R.id.main_todyMoney)
    TextView main_TodayMoney;
    @Bind(R.id.main_totalMoney)
    TextView main_TotalMoney;
    @Bind(R.id.main_menu_cpgl)
    Button main_menu_cpgl;
    @Bind(R.id.main_menu_ddgl)
    Button main_menu_ddgl;
    @Bind(R.id.main_menu_gdtj)
    Button main_menu_gdtj;
    @Bind(R.id.main_menu_szgl)
    Button main_menu_szgl;

    Long existTime=0L;
    Integer waitForExistSecond=2000;

    //TodayDistributorsFragment _todayDistributorsFragments;
    //TodayMemberFragment _todayMemberFragments;
    //TodayOrderFragment _todayOrderFragments;
    //List<BaseFragment> _fragments;
    //@Bind(R.id.main_pager)
    //ViewPager _viewPager;
    //@Bind(R.id.main_indicator)
    //CirclePageIndicator _indicator;
    //TodayDataFragmentAdapter _fragmentAdapter;
    @Bind(R.id.main_lineChart)
    LineChart _mainChart;
    @Bind(R.id.main_OrderCount)
    TextView _todayOrderCount;
    @Bind(R.id.main_MemberCount)
    TextView _todayMemberCount;
    @Bind(R.id.main_fxsCount)
    TextView _todayFxsCount;
    @Bind(R.id.ll_todayOrder_order)
    LinearLayout _llOrder;
    @Bind(R.id.ll_todayOrder_member)
    LinearLayout _llMember;
    @Bind(R.id.ll_todayOrder_fxs)
    LinearLayout _llFxs;
    @Bind(R.id.main_order_space)
    ImageView _space1;
    @Bind(R.id.main_member_space)
    ImageView _space2;
    @Bind(R.id.main_fxs_space)
    ImageView _space3;
    @Bind(R.id.header_logo)
    NetworkImageView _ivLogo;
    @Bind(R.id.header_operate)
    ImageButton _ibShop;

    MJNewTodayModel _data=null;
    //当前选中的位置
    int _currentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //String formatText = "￥%.2f";
        //CountUpTimerView countUpView =new CountUpTimerView( main_todyMoney , formatText , 1000.33f,5000.45f, 3000,100);
        //countUpView.start();

        //initTodayData();

        _llOrder.setOnClickListener(this);
        _llMember.setOnClickListener(this);
        _llFxs.setOnClickListener(this);
        main_menu_cpgl.setOnClickListener(this);
        main_menu_ddgl.setOnClickListener(this);
        main_menu_gdtj.setOnClickListener(this);
        main_menu_szgl.setOnClickListener(this);
        _ibShop.setOnClickListener(this);

        String logoUrl = PreferenceHelper.readString( this, Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_LOGO,"");
        BitmapLoader.create().displayUrl(this, _ivLogo , logoUrl);

        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void getData(){
        String url = Constant.NEWTODAY_INTERFACE;
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        url =  httpParaUtils.getHttpGetUrl(url,null);
        GsonRequest<MJNewTodayModel> newTodayRequest=new GsonRequest<MJNewTodayModel>(
                Request.Method.GET,
                url,
                MJNewTodayModel.class,
                null,
                newTodayModelListener,
                this
        );

        this.showProgressDialog("", "正在获取数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(newTodayRequest);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if( v.getId() == R.id.main_menu_cpgl){
            ActivityUtils.getInstance().showActivity(this, GoodsActivity.class);
        }else if( v.getId() == R.id.main_menu_ddgl){//订单管理

            Intent intent = new Intent( this , OrderActivity.class);
            //intent.putExtra(Constant.Extra_Url, "http://www.baidu.com");
            ActivityUtils.getInstance().showActivity(this, intent);
        }else if( v.getId() == R.id.main_menu_gdtj){
            ActivityUtils.getInstance().showActivity(this, MoreStatisticActivity.class);
        }else if( v.getId() ==R.id.main_menu_szgl){
            ActivityUtils.getInstance().showActivity(this, SettingActivity.class);
        }else if( v.getId() == R.id.ll_todayOrder_order){
            _currentIndex=0;
            setLineChart();
        }else if(v.getId() == R.id.ll_todayOrder_member){
            _currentIndex=1;
            setLineChart();
        }else if(v.getId()==R.id.ll_todayOrder_fxs){//分销商
            _currentIndex=2;
            setLineChart();
        }else if( v.getId() == R.id.header_operate){//导航到商户首页
            ActivityUtils.getInstance().showActivity(this, WebViewActivity.class);
        }
    }

    protected void setLineChart(){
        if(_data==null || _data.getResultData() ==null  )return;

        _todayOrderCount.setText( String.valueOf( _data.getResultData().getTodayOrderAmount()));
        _todayMemberCount.setText(String.valueOf( _data.getResultData().getTodayMemberAmount() ));
        _todayFxsCount.setText( String.valueOf(_data.getResultData().getTodayPartnerAmount()));

        if(_currentIndex==0){
            _space1.setVisibility(View.VISIBLE);
            //_space1.setBackgroundResource(R.color.main_header_bg);
            _space2.setVisibility(View.GONE);
            //_space2.setBackgroundColor(Color.WHITE);
            _space3.setVisibility(View.GONE);
            //_space3.setBackgroundColor(Color.WHITE);
            setLineChartData(_mainChart, _data.getResultData().getOrderHour(), _data.getResultData().getOrderAmount());
        }else if( _currentIndex==1){
            _space1.setVisibility(View.GONE);
            //_space1.setBackgroundColor(Color.WHITE);
            _space2.setVisibility(View.VISIBLE);
            //_space2.setBackgroundResource(R.color.main_header_bg);
            _space3.setVisibility(View.GONE);
            //_space3.setBackgroundColor(Color.WHITE);
            setLineChartData(_mainChart, _data.getResultData().getMemberHour(), _data.getResultData().getMemberAmount());
        }else if(_currentIndex==2){
            _space1.setVisibility(View.GONE);
            //_space1.setBackgroundColor(Color.WHITE);
            _space2.setVisibility(View.GONE);
            //_space2.setBackgroundColor(Color.WHITE);
            _space3.setVisibility(View.VISIBLE);
            //_space3.setBackgroundResource(R.color.main_header_bg);
            setLineChartData(_mainChart, _data.getResultData().getPartnerHour(), _data.getResultData().getPartnerAmount());
        }
    }

    protected void setLineChartData( LineChart lineChart , List<Integer> xData , List<Integer> yData ){
        if( xData==null || yData==null )return;

        int bg=0xFFFFFFFF;
        int gridBg=0x56A5EA;
        int lineColor =0xFFFF3C00;

        //lineChart.setGridBackgroundColor( gridBg );
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(bg);
        lineChart.setDescription("");
        lineChart.setNoDataText("暂无数据");
        //lineChart.getAxisRight().setEnabled(false);

        List<String> xValues= new ArrayList<String>();
        List<Entry> yValues=new ArrayList<>();
        int count = xData.size();
        for(int i=0;i< count ;i++){
            if( null == xData.get(i) ) continue;
            int x = xData.get(i);
            xValues.add( String.valueOf( x));
            int y = yData.get(i);
            Entry item=new Entry( y , i );
            yValues.add(item);
        }
        LineDataSet dataSet =new LineDataSet( yValues ,"");

        dataSet.setCircleColors(new int[]{Color.rgb(255, 255, 255)});
        dataSet.setCircleSize(5);
        //dataSet.setDrawCircleHole(true);
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(4);
        dataSet.setColor(lineColor);
        dataSet.setValueTextSize(14);
        dataSet.setValueTextColor(Color.GREEN);
        dataSet.setDrawCubic(true);
        dataSet.setCircleColor(lineColor);
        dataSet.setCircleColorHole(bg);
        dataSet.setDrawCircleHole(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        //xAxis.setDrawGridLines(false);

        //xAxis.setTextColor(0xFFFFFFFF);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setStartAtZero(true);
        yAxis.setLabelCount(4, false);
        yAxis.setDrawGridLines(false);
        //yAxis.setTextColor(0xFFFFFFFF);

        //lineChart.getAxisRight().setDrawGridLines(false);

        lineChart.getAxisRight().setDrawLabels(false);

        lineChart.getLegend().setEnabled(false);

        LineData data =new LineData(xValues ,dataSet );
        lineChart.setData(data);
        lineChart.animateX(2000, Easing.EasingOption.EaseInOutQuart);
    }

//    protected void initTodayData(){
//        _todayDistributorsFragments = new TodayDistributorsFragment();
//        _todayMemberFragments = new TodayMemberFragment();
//        _todayOrderFragments = new TodayOrderFragment();
//        _fragments=new ArrayList<>();
//        _fragments.add(_todayOrderFragments);
//        _fragments.add(_todayMemberFragments);
//        _fragments.add(_todayDistributorsFragments);
//        _fragmentAdapter=new TodayDataFragmentAdapter(_fragments, getSupportFragmentManager());
//        _viewPager.setAdapter(_fragmentAdapter);
//        _indicator.setViewPager(_viewPager);

//    }

    @Override
    public void switchFragment(int position) {
        //_indicator.setCurrentItem(position);
    }

    Response.Listener<MJNewTodayModel> newTodayModelListener = new Response.Listener<MJNewTodayModel>() {
        @Override
        public void onResponse(MJNewTodayModel mjNewTodayModel) {
            MainActivity.this.closeProgressDialog();

            if(null == mjNewTodayModel){
                DialogUtils.showDialog(MainActivity.this, MainActivity.this.getSupportFragmentManager()
                ,"错误信息","请求失败","关闭");
                return;
            }
            if(mjNewTodayModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(MainActivity.this, MainActivity.this.getSupportFragmentManager()
                        ,"错误信息",mjNewTodayModel.getSystemResultDescription(),"关闭");
                return;
            }else if( mjNewTodayModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(MainActivity.this,LoginActivity.class);
                return;
            }else if( mjNewTodayModel.getResultCode() != 1){
                DialogUtils.showDialog(MainActivity.this, MainActivity.this.getSupportFragmentManager()
                        ,"错误信息",mjNewTodayModel.getResultDescription(),"关闭");
                return;
            }

            main_TodayMoney.setText("￥"+String.valueOf(mjNewTodayModel.getResultData().getTodaySales()));
            main_TotalMoney.setText(String.valueOf(mjNewTodayModel.getResultData().getTotalSales()));

            _data=mjNewTodayModel;
            //_todayOrderFragments.setData(mjNewTodayModel);
            //_todayMemberFragments.setData( mjNewTodayModel);
            //_todayDistributorsFragments.setData(mjNewTodayModel);
            setLineChart();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
            long currentTime = System.currentTimeMillis();
            if( currentTime - existTime > waitForExistSecond ){
                ToastUtils.showLong("再按一次退出程序");
                existTime = currentTime;
            }else{
                try {
                    this.finish();
                }finally {
                    System.exit(0);
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
