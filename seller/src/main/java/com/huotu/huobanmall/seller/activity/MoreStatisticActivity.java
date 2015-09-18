package com.huotu.huobanmall.seller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.BaseModel;
import com.huotu.huobanmall.seller.bean.MJOtherStatisticModel;
import com.huotu.huobanmall.seller.bean.OtherStatisticModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_statistic);
        ButterKnife.bind(this);

        btnBack.setOnClickListener(this);
        morestatistic_title2.setOnClickListener(this);
        moresta_fljftj.setOnClickListener(this);
        moresta_fxs.setOnClickListener(this);
        moresta_hytj.setOnClickListener(this);
        moresta_sp.setOnClickListener(this);
        moresta_xftj.setOnClickListener(this);
        moresta_xsetj.setOnClickListener(this);
        moresta_xsmx.setOnClickListener(this);

        getData();
    }

    protected void getData(){
        String url = Constant.OTHERSTATISTIC_INTERFACE;
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url ,null);

        GsonRequest<MJOtherStatisticModel> otherRequest = new GsonRequest<MJOtherStatisticModel>(
                Request.Method.GET,
                url,
                MJOtherStatisticModel.class,
                null,
                otherListener,
                errorListener
        );

        this.showProgressDialog("","正在获取数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(otherRequest);
    }

    Response.Listener<MJOtherStatisticModel> otherListener=new Response.Listener<MJOtherStatisticModel>() {
        @Override
        public void onResponse(MJOtherStatisticModel  mjOtherStatisticModel ) {
            MoreStatisticActivity.this.closeProgressDialog();

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

    Response.ErrorListener errorListener =new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            MoreStatisticActivity.this.closeProgressDialog();
            String message ="";
            if( volleyError.networkResponse !=null){
                message = new String( volleyError.networkResponse.data );
            }
            DialogUtils.showDialog(MoreStatisticActivity.this,MoreStatisticActivity.this.getSupportFragmentManager(),"错误信息",message,"关闭");

        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.morestatistic_title2){
            Bundle bd = new Bundle();
            bd.putInt("tabType",Constant.TAB_ORDER);
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class,bd);
        }else if( v.getId() == R.id.morestatistic_menu_xftj){
            ActivityUtils.getInstance().showActivity(this, ConsumeStatisticsActivity.class );
        }else if( v.getId() == R.id.morestatistic_menu_xsmx){
            ActivityUtils.getInstance().showActivity(this, SalesDetailActivity.class );

        }else if( v.getId() == R.id.morestatistic_menu_hytj){
            Bundle bd = new Bundle();
            bd.putInt( "tabType" , Constant.TAB_MEMBER );
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class , bd );
        }else if( v.getId() == R.id.morestatistic_menu_fltj){
            ActivityUtils.getInstance().showActivity(this, RebateStatisticsActivity.class );

        }else if( v.getId() == R.id.morestatistic_menu_xsetj){
            Bundle bd = new Bundle();
            bd.putInt( "tabType" , Constant.TAB_SALE );
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class, bd );
        }else if( v.getId() == R.id.morestatistic_menu_fxs){
            Bundle bd = new Bundle();
            bd.putInt( "tabType" , Constant.TAB_MEMBER );
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class , bd );
        }else if( v.getId() == R.id.morestatistic_menu_sp){
           // ActivityUtils.getInstance().showActivity(this, TopSalesActivity.class );
        }
    }
}
