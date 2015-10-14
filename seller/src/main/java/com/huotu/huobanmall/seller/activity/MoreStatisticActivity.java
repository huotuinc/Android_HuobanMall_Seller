package com.huotu.huobanmall.seller.activity;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.MJOtherStatisticModel;
import com.huotu.huobanmall.seller.bean.RoleEnum;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 更多统计 界面
 */
public class MoreStatisticActivity extends BaseFragmentActivity {
    @Bind(R.id.header_back)
    Button btnBack;
    @Bind(R.id.morestatistic_title2)
    RelativeLayout morestatistic_title2;
    @Bind(R.id.morestatistic_OrderCount)
    TextView moresta_ordercount;
    @Bind(R.id.morestatistic_menu_fxs)
    Button moresta_fxs;
    @Bind(R.id.morestatistic_menu_sp)
    Button moresta_sp;
    @Bind(R.id.morestatistic_menu_xsetj)
    Button moresta_xsetj;
    @Bind(R.id.morestatistic_menu_hytj)
    Button moresta_hytj;
    @Bind(R.id.morestatistic_menu_xsmx)
    Button moresta_xsmx;
    @Bind(R.id.morestatistic_menu_fltj)
    Button moresta_fljftj;
    @Bind(R.id.morestatistic_menu_xftj)
    Button moresta_xftj;
    @Bind(R.id.kb1)
    Button kb1;
    @Bind(R.id.kb2)
    Button kb2;
    @Bind(R.id.morestatistic_refresh)
    PullToRefreshScrollView morestat_refresh;

    Handler handler =new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_statistic);
        ButterKnife.bind(this);
        Drawable drawable1 = getResources().getDrawable(R.mipmap.fxs);
        drawable1.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        moresta_fxs.setCompoundDrawables(null, drawable1, null, null);
        Drawable drawable2= getResources().getDrawable(R.mipmap.sp);
        drawable2.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        moresta_sp.setCompoundDrawables(null, drawable2, null, null);
        Drawable drawable3 = getResources().getDrawable(R.mipmap.xsetj);
        drawable3.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        moresta_xsetj.setCompoundDrawables(null, drawable3, null, null);
        Drawable drawable4 = getResources().getDrawable(R.mipmap.hytj);
        drawable4.setBounds(0, 0,100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        moresta_hytj.setCompoundDrawables(null, drawable4, null, null);
        Drawable drawable5 = getResources().getDrawable(R.mipmap.xsmx);
        drawable5.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        moresta_xsmx.setCompoundDrawables(null, drawable5, null, null);
        Drawable drawable6 = getResources().getDrawable(R.mipmap.fltj);
        drawable6.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        moresta_fljftj.setCompoundDrawables(null, drawable6, null, null);
        Drawable drawable7 = getResources().getDrawable(R.mipmap.xftj);
        drawable7.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        moresta_xftj.setCompoundDrawables(null, drawable7, null, null);
        Drawable drawable8 = getResources().getDrawable(R.mipmap.kb);
        drawable8.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        kb1.setCompoundDrawables(null, drawable8, null, null);
        Drawable drawable9 = getResources().getDrawable(R.mipmap.kb);
        drawable9.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        kb2.setCompoundDrawables(null, drawable9, null, null);
        btnBack.setOnClickListener(this);
        morestatistic_title2.setOnClickListener(this);
        moresta_fljftj.setOnClickListener(this);
        moresta_fxs.setOnClickListener(this);
        moresta_hytj.setOnClickListener(this);
        moresta_sp.setOnClickListener(this);
        moresta_xftj.setOnClickListener(this);
        moresta_xsetj.setOnClickListener(this);
        moresta_xsmx.setOnClickListener(this);

        morestat_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getData();
            }
        });

        //getData();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                morestat_refresh.setRefreshing(true);
            }
        },1000);
    }


    @Override
    protected void onResume() {
        morestat_refresh.onRefreshComplete();
        super.onResume();
    }

    protected void getData(){
        if( false== canConnect()){
            this.closeProgressDialog();
            return;
        }

        String url = Constant.OTHERSTATISTIC_INTERFACE;
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url ,null);

        GsonRequest<MJOtherStatisticModel> otherRequest = new GsonRequest<MJOtherStatisticModel>(
                Request.Method.GET,
                url,
                MJOtherStatisticModel.class,
                null,
                otherListener,
                this
        );
        //this.showProgressDialog("", "正在获取数据，请稍等...");
        //VolleyRequestManager.getRequestQueue().add(otherRequest);
        VolleyRequestManager.AddRequest(otherRequest);
    }

    protected void clearData(){
        moresta_ordercount.setText(  "0" );
        moresta_sp.setText( "0" );
        moresta_hytj.setText( "0" );
        moresta_fxs.setText( "0" );
    }

    Response.Listener<MJOtherStatisticModel> otherListener=new Response.Listener<MJOtherStatisticModel>() {
        @Override
        public void onResponse(MJOtherStatisticModel  mjOtherStatisticModel ) {
            MoreStatisticActivity.this.closeProgressDialog();
            morestat_refresh.onRefreshComplete();
            clearData();

            if( mjOtherStatisticModel==null){
                DialogUtils.showDialog(MoreStatisticActivity.this, MoreStatisticActivity.this.getSupportFragmentManager(),"错误信息","请求数据失败","关闭");
                return;
            }
            if( mjOtherStatisticModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(MoreStatisticActivity.this,MoreStatisticActivity.this.getSupportFragmentManager(),"错误信息", mjOtherStatisticModel.getSystemResultDescription(),"关闭");
                return;
            }
            if( mjOtherStatisticModel.getResultCode() == Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(MoreStatisticActivity.this, LoginActivity.class);
                return;
            }
            if( mjOtherStatisticModel.getResultCode() !=1){
                DialogUtils.showDialog(MoreStatisticActivity.this,MoreStatisticActivity.this.getSupportFragmentManager(),"错误信息",mjOtherStatisticModel.getResultDescription(),"关闭");
                return;
            }

            if( mjOtherStatisticModel.getResultData() ==null || mjOtherStatisticModel.getResultData().getOtherInfoList() ==null ){
                DialogUtils.showDialog( MoreStatisticActivity.this , MoreStatisticActivity.this.getSupportFragmentManager(),
                        "错误信息","服务端返回数据不完整","关闭");
                return;
            }

            String orderCount = String.valueOf( mjOtherStatisticModel.getResultData().getOtherInfoList().getBillAmount());
            String spCount = String.valueOf( mjOtherStatisticModel.getResultData().getOtherInfoList().getGoodsAmount() );
            String fxsCount = String.valueOf( mjOtherStatisticModel.getResultData().getOtherInfoList().getDiscributorAmount());
            String memberCount = String.valueOf( mjOtherStatisticModel.getResultData().getOtherInfoList().getMemberAmount());

            moresta_ordercount.setText(  orderCount );
            moresta_sp.setText( spCount );
            moresta_hytj.setText( memberCount );
            moresta_fxs.setText( fxsCount );
        }
    };

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        MoreStatisticActivity.this.closeProgressDialog();
        morestat_refresh.onRefreshComplete();
        clearData();

        super.onErrorResponse(volleyError);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.morestatistic_title2){//订单统计
            if( hasRole(RoleEnum.订单统计 ) == false){
                ToastUtils.showLong("您所属的账号无访问权限。");
                return;
            }
            Bundle bd = new Bundle();
            bd.putInt("tabType",Constant.TAB_ORDER);
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class,bd);
        }else if( v.getId() == R.id.morestatistic_menu_xftj){//消费统计
            if( hasRole(RoleEnum.消费统计 ) == false){
                ToastUtils.showLong("您所属的账号无访问权限。");
                return;
            }
            ActivityUtils.getInstance().showActivity(this, ConsumeStatisticsActivity.class );
        }else if( v.getId() == R.id.morestatistic_menu_xsmx){//销售明细
            if( hasRole(RoleEnum.销售明细 ) == false){
                ToastUtils.showLong("您所属的账号无访问权限。");
                return;
            }
            ActivityUtils.getInstance().showActivity(this, SalesDetailActivity.class );

        }else if( v.getId() == R.id.morestatistic_menu_hytj){//会员统计
            if( hasRole(RoleEnum.会员统计 ) == false){
                ToastUtils.showLong("您所属的账号无访问权限。");
                return;
            }
            Bundle bd = new Bundle();
            bd.putInt( "tabType" , Constant.TAB_MEMBER );
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class , bd );
        }else if( v.getId() == R.id.morestatistic_menu_fltj){//返利积分
            if( hasRole(RoleEnum.返利积分统计 ) == false){
                ToastUtils.showLong("您所属的账号无访问权限。");
                return;
            }
            ActivityUtils.getInstance().showActivity(this, RebateStatisticsActivity.class );
        }else if( v.getId() == R.id.morestatistic_menu_xsetj){//销售额统计
            if( hasRole(RoleEnum.销售额统计 ) == false){
                ToastUtils.showLong("您所属的账号无访问权限。");
                return;
            }

            Bundle bd = new Bundle();
            bd.putInt( "tabType" , Constant.TAB_SALE );
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class, bd );
        }else if( v.getId() == R.id.morestatistic_menu_fxs){//分销商
            if( hasRole(RoleEnum.会员统计 ) == false){
                ToastUtils.showLong("您所属的账号无访问权限。");
                return;
            }

            Bundle bd = new Bundle();
            bd.putInt( "tabType" , Constant.TAB_MEMBER );
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class , bd );
        }else if( v.getId() == R.id.morestatistic_menu_sp){//商品
           // ActivityUtils.getInstance().showActivity(this, TopSalesActivity.class );
        }
    }
}
