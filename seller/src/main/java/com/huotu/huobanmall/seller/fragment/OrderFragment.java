package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.huotu.huobanmall.seller.adapter.PurchaseOfGoodsAdapter;
import com.huotu.huobanmall.seller.bean.PurchaseOfGoods;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

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
public class OrderFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    List<PurchaseOfGoods> _purchaseOfGoods;
    PurchaseOfGoodsAdapter _purchaseAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
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
        ButterKnife.bind( this ,rootView);

        initData();

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



        //_orderGoods.setHasFixedSize(true);

        _purchaseOfGoods =new ArrayList<PurchaseOfGoods>();
        PurchaseOfGoods item=new PurchaseOfGoods();
        item.setName("1、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("10件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("2、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("20件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("3、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("30件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("4、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("40件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("5、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("50件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("6、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("60件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("7、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("70件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("8、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("80件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("9、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("90件");
        _purchaseOfGoods.add(item);
        item=new PurchaseOfGoods();
        item.setName("10、萨芬阿是打发斯蒂芬啊发送发送阿斯撒飞撒 阿萨德阿斯");
        item.setCounts("100件");
        _purchaseOfGoods.add(item);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL , false);
        //_orderGoods.setLayoutManager(layoutManager);
        _purchaseAdapter=new PurchaseOfGoodsAdapter(getActivity(), _purchaseOfGoods);
        //_orderGoods.setAdapter(_purchaseAdapter);

        setLineChartData();
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


}
