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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.WebViewActivity;
import com.huotu.huobanmall.seller.adapter.MembersFragmentPageAdapter;
import com.huotu.huobanmall.seller.adapter.OrderFragmentPageAdapter;
import com.huotu.huobanmall.seller.common.Constants;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
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
            intent.putExtra(Constants.Extra_Url, url);
            ActivityUtils.getInstance().showActivity(getActivity(), intent);
        }else if( v.getId()==R.id.members_statis2){
            String url = "http://www.baidu.com";
            Intent intent = new Intent();
            intent.setClass(getActivity(), WebViewActivity.class);
            intent.putExtra(Constants.Extra_Url, url);
            ActivityUtils.getInstance().showActivity(getActivity(), intent);
        }
    }


    class MemberLineChartFragment extends BaseFragment{

        @Bind(R.id.members_lineChart)
        LineChart _memberLineChart;


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
    }

}
