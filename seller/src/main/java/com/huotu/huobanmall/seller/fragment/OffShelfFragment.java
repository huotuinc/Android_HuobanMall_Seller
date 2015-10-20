package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.GoodsAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.GoodsOpeTypeEnum;
import com.huotu.huobanmall.seller.bean.MJGoodModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.StringUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class OffShelfFragment extends BaseFragment {

    @Bind(R.id.goods_offshelf_listView)
    PullToRefreshListView _goodsOffshelfListview;
    List<GoodsModel> _goodsList = null;
    GoodsAdapter _goodsAdapter = null;
    OperateTypeEnum _type = OperateTypeEnum.REFRESH;
    private OnFragmentInteractionListener mListener;

    boolean _isVisiable=false;
    boolean _isPrepared = false;
    boolean _isFirst =true;
    Handler _handler = new Handler();
    View emptyView=null;
    boolean isSetEmptyView = false;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OffShelfFragment.
     */
    public static OffShelfFragment newInstance() {
        OffShelfFragment fragment = new OffShelfFragment();
        return fragment;
    }

    public OffShelfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_off_shelf, container, false);
        ButterKnife.bind( this ,rootView);

        _goodsList = new ArrayList<>();
        _goodsAdapter=new GoodsAdapter(this.getActivity() , _goodsList);
        _goodsOffshelfListview.getRefreshableView().setAdapter(_goodsAdapter);
        _pulltoRefreshListView = _goodsOffshelfListview;

        emptyView = new View(this.getActivity());
        emptyView.setBackgroundResource(R.mipmap.tpzw);
        //_goodsOffshelfListview.setEmptyView(emptyView);

        _goodsOffshelfListview.setMode(PullToRefreshBase.Mode.BOTH);
        _goodsOffshelfListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _type = OperateTypeEnum.REFRESH;
                getData(_type);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _type = OperateTypeEnum.LOADMORE;
                getData(_type);
            }
        });

        _isPrepared=true;

        if( savedInstanceState !=null && savedInstanceState.containsKey("goodsList") &&  savedInstanceState.getSerializable("goodsList") !=null ){
            _isFirst=false;
            if( _goodsList==null ){_goodsList=new ArrayList<>();}
            _goodsList.clear();
            _goodsList.addAll((List<GoodsModel>) savedInstanceState.getSerializable("goodsList"));
            _goodsAdapter.notifyDataSetChanged();
        }else {
            firstGetData();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        _goodsOffshelfListview.onRefreshComplete();
        VolleyRequestManager.cancelAllRequest();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if( getUserVisibleHint()){
            _isVisiable=true;
            firstGetData();
        }else{
            _isVisiable=false;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    protected void firstGetData(){
        if(_isVisiable && _isPrepared && _isFirst ) {
            //OffShelfFragment.this.showProgressDialog("", "正在获取数据，请稍等...");
            //_type = OperateTypeEnum.REFRESH;
            //_isFirst = false;
            //getData(_type);
            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if( OffShelfFragment.this.getActivity()==null|| OffShelfFragment.this.isDetached()|| OffShelfFragment.this.isRemoving() ) return;
                    _type = OperateTypeEnum.REFRESH;
                    _isFirst = false;
                    _goodsOffshelfListview.setRefreshing(true);
                }
            },1000);

        }
    }

    protected void getData( OperateTypeEnum type ){
        Map<String,String> maps = new HashMap<>();
        if( type == OperateTypeEnum.REFRESH){
            //maps.put("lastProductId","");
        }else {
            if( _goodsList !=null && _goodsList.size() >0 ) {
                String lastId = String.valueOf(_goodsList.get(_goodsList.size() - 1).getGoodsId());
                maps.put("lastProductId", lastId);
            }
        }

        HttpParaUtils httpParaUtils = new HttpParaUtils();
        maps.put("type", String.valueOf( GoodsOpeTypeEnum.OFFSHELF.getIndex() ) );
        String url = Constant.GOODSLIST_INTERFACE;
        url = httpParaUtils.getHttpGetUrl(url,maps);

        GsonRequest<MJGoodModel> goodsListRequest=new GsonRequest<MJGoodModel>(Request.Method.GET,
                url,
                MJGoodModel.class,
                null,
                new MyListener(this),
                new MJErrorListener(this)
        );

        VolleyRequestManager.AddRequest(goodsListRequest);
    }

    //Response.Listener<MJGoodModel> goodslistListener =new Response.Listener<MJGoodModel>() {
    static class MyListener implements Response.Listener<MJGoodModel>{
        WeakReference<OffShelfFragment> ref;
        public MyListener(OffShelfFragment frag){
            ref=new WeakReference<OffShelfFragment>(frag);
        }

        @Override
        public void onResponse(MJGoodModel mjGoodModel) {
           //if( OffShelfFragment.this.getActivity()==null|| OffShelfFragment.this.isDetached() || OffShelfFragment.this.isRemoving() ) return;
            if( ref.get()==null || ref.get().getActivity() ==null ) return;

            if (  ref.get().isSetEmptyView == false) {
                ref.get(). _goodsOffshelfListview.setEmptyView(ref.get().emptyView);
                ref.get().isSetEmptyView=true;
            }

            ref.get().closeProgressDialog();
            ref.get()._goodsOffshelfListview.onRefreshComplete();

            if( mjGoodModel==null ){
                SimpleDialogFragment.createBuilder(ref.get().getActivity(), ref.get().getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( "获取数据失败" )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if( mjGoodModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder(ref.get().getActivity(), ref.get().getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjGoodModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( ref.get().getActivity() , ref.get().getActivity().getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            if( ref.get()._type == OperateTypeEnum.REFRESH){
                ref.get()._goodsList.clear();
                if( mjGoodModel.getResultData() !=null && mjGoodModel.getResultData().getList()!=null && mjGoodModel.getResultData().getList().size()>0 ) {
                    ref.get()._goodsList.addAll(mjGoodModel.getResultData().getList());
                }else{
                    ToastUtils.showLong(Constant.No_Data_Text,Gravity.BOTTOM);
                }
            }else{
                if( mjGoodModel.getResultData() !=null && mjGoodModel.getResultData().getList()!=null && mjGoodModel.getResultData().getList().size()>0 ) {
                    ref.get()._goodsList.addAll(mjGoodModel.getResultData().getList());
                }else{
                    ToastUtils.showLong(Constant.No_Data_Text, Gravity.BOTTOM);
                }
            }
            ref.get()._goodsAdapter.notifyDataSetChanged();
        }
    };

//    @Override
//    public void onErrorResponse(VolleyError volleyError) {
//        if( OffShelfFragment.this.getActivity()==null ||  OffShelfFragment.this.isDetached() || OffShelfFragment.this.isRemoving() ) return;
//
//        _goodsOffshelfListview.onRefreshComplete();
//        super.onErrorResponse(volleyError);
//    }

//    Response.ErrorListener errorListener =new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError volleyError) {
//            if( OffShelfFragment.this.getActivity() ==null ||  OffShelfFragment.this.isRemoving() || OffShelfFragment.this.isDetached() ) return;
//
//            _goodsOffshelfListview.onRefreshComplete();
//            OffShelfFragment.this.closeProgressDialog();
//            String message="";
//            if( null != volleyError.networkResponse){
//                message=new String( volleyError.networkResponse.data);
//            }else{
//                message = volleyError.getMessage();
//            }
//            if( message.length()<1){
//                message = "网络请求失败，请检查网络状态";
//            }
//            DialogUtils.showDialog(OffShelfFragment.this.getActivity(), OffShelfFragment.this.getFragmentManager(), "错误信息", message, "关闭");
//
//        }
//    };

}
