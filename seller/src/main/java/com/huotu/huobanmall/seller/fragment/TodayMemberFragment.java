package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huotu.huobanmall.seller.Interface.IIndexFragmentInteractionListener;
import com.huotu.huobanmall.seller.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayMemberFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayMemberFragment extends BaseFragment implements View.OnClickListener{
    private IIndexFragmentInteractionListener mListener;
    @Bind(R.id.ll_todayOrder_order)
    LinearLayout _ll_order;
    @Bind(R.id.ll_todayOrder_fxs)
    LinearLayout _ll_fxs;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TodayMemberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayMemberFragment newInstance() {
        TodayMemberFragment fragment = new TodayMemberFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public TodayMemberFragment() {
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today_member, container, false);

        ButterKnife.bind(this, rootView);

        _ll_order.setOnClickListener(this);
        _ll_fxs.setOnClickListener(this);

        return  rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSwitchFragment(int position ) {
        if (mListener != null) {
            mListener.switchFragment(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IIndexFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IIndexFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.ll_todayOrder_order){
            onSwitchFragment(0);
        }else if( v.getId()==R.id.ll_todayOrder_fxs){
            onSwitchFragment(2);
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
