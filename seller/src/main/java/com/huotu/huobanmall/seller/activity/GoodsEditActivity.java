package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.GoodseditAdapter;
import com.huotu.huobanmall.seller.bean.BaseModel;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.GoodsOpeTypeEnum;
import com.huotu.huobanmall.seller.bean.MJGoodModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.fragment.SaleGoodsFragment;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品编辑页面
 */
public class GoodsEditActivity extends BaseFragmentActivity implements
        RadioGroup.OnCheckedChangeListener ,
        CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemClickListener{
    @Bind(R.id.saleing_btn)
    RadioButton saleing_btn;
    @Bind(R.id.offshelf_btn)
    RadioButton offshelf_btn;
    @Bind(R.id.goodsedit_title)
    RadioGroup goodsedit_title;
    @Bind(R.id.header_back)
    TextView _header_back;
    //@Bind(R.id.goodsedit_groupAll)
    //RadioGroup _goodsedit_groupall;
    @Bind(R.id.goodsedit_all)
    Button _goodsedit_all;
    @Bind(R.id.goodsedit_delete)
    Button _goodsedit_delete;
    @Bind(R.id.goodsedit_onshelf)
    Button _goodsedit_onshelf;
    @Bind(R.id.goodsedit_offshelf)
    Button _goodsedit_offshelf;
    @Bind(R.id.goodsedit_listview)
    PullToRefreshListView _goodsedit_listview;
    GoodseditAdapter _saleGoodsAdapter;
    GoodseditAdapter _offShelfAdapter;
    List<GoodsModel> _saleGoodsList = null;
    List<GoodsModel> _offShelfList=null;
    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;
    int _type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_edit);
        ButterKnife.bind(this);

        goodsedit_title.setOnCheckedChangeListener(this);
        _saleGoodsList = new ArrayList<>();
        _offShelfList = new ArrayList<>();
        _saleGoodsAdapter = new GoodseditAdapter(this, _saleGoodsList );
        _offShelfAdapter = new GoodseditAdapter(this,_offShelfList);
        _goodsedit_listview.getRefreshableView().setAdapter(_saleGoodsAdapter);
        _header_back.setOnClickListener(this);

        _goodsedit_delete.setOnClickListener(this);
        _goodsedit_onshelf.setOnClickListener(this);
        _goodsedit_offshelf.setOnClickListener(this);

        //_goodsedit_all.setOnCheckedChangeListener(this);
        _goodsedit_all.setOnClickListener(this);

        //_goodsedit_groupall.setOnCheckedChangeListener(this);

        _goodsedit_listview.setMode(PullToRefreshBase.Mode.BOTH);

        _goodsedit_listview.getRefreshableView().setOnItemClickListener(this);

        _goodsedit_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _operateType= OperateTypeEnum.REFRESH;
                if(_type == 1){
                    getSaleGoodsData(_operateType);
                }else {
                    getOffShelfGoodsData(_operateType);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _operateType= OperateTypeEnum.LOADMORE;
                if(_type == 1){
                    getSaleGoodsData(_operateType);
                }else {
                    getOffShelfGoodsData(_operateType);
                }
            }
        });

        firstSaleGoodData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void getOffShelfGoodsData(OperateTypeEnum operateType){
        Map<String,String> paras = new HashMap<>();
        if( operateType == OperateTypeEnum.REFRESH){
        }else {
            if( _offShelfList !=null && _offShelfList.size()>0 ) {
                String lastId = String.valueOf(_offShelfList.get(_offShelfList.size() - 1).getGoodsId());
                paras.put("lastProductId", lastId);
            }
        }
        String url = Constant.GOODSLIST_INTERFACE;
        paras.put("type", "2" );
        HttpParaUtils utils = new HttpParaUtils();
        String urlString = utils.getHttpGetUrl(url, paras);

        GsonRequest<MJGoodModel > goodsListRequest =
                new GsonRequest<>(
                        Request.Method.GET,
                        urlString,
                        MJGoodModel.class,
                        null,
                        goodsListListener,
                        this);

        VolleyRequestManager.getRequestQueue().add(goodsListRequest);
    }

    protected void firstSaleGoodData(){
        this.showProgressDialog("","正在获取数据，请稍等...");
        _type=1;
        _operateType= OperateTypeEnum.REFRESH;
        getSaleGoodsData(_operateType);
    }

    private void getSaleGoodsData( OperateTypeEnum operateType ) {
        Map<String,String> paras = new HashMap<>();
        if( operateType == OperateTypeEnum.REFRESH){
        }else {
            if( _saleGoodsList !=null && _saleGoodsList.size()>0) {
                String lastId = String.valueOf(_saleGoodsList.get(_saleGoodsList.size() - 1).getGoodsId());
                paras.put("lastProductId", lastId);
            }
        }

        String url = Constant.GOODSLIST_INTERFACE;
        paras.put("type", "1" );

        HttpParaUtils utils = new HttpParaUtils();
        String urlString = utils.getHttpGetUrl(url, paras);

        GsonRequest<MJGoodModel > goodsListRequest =
                new GsonRequest<MJGoodModel>(
                        Request.Method.GET,
                        urlString,
                        MJGoodModel.class,
                        null,
                        goodsListListener,
                        this);

        VolleyRequestManager.getRequestQueue().add(goodsListRequest);
    }

    private Response.Listener< MJGoodModel > goodsListListener = new Response.Listener<MJGoodModel>() {
        @Override
        public void onResponse( MJGoodModel mjGoodModel ) {
            GoodsEditActivity.this.closeProgressDialog();
            _goodsedit_listview.onRefreshComplete();
            if(_type==1) {
                _goodsedit_offshelf.setVisibility(View.VISIBLE);
                _goodsedit_onshelf.setVisibility(View.GONE);
            }else{
                _goodsedit_offshelf.setVisibility(View.GONE);
                _goodsedit_onshelf.setVisibility(View.VISIBLE);
            }

            if( mjGoodModel==null){
                SimpleDialogFragment.createBuilder(GoodsEditActivity.this, GoodsEditActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( "获取数据失败" )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if( mjGoodModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder( GoodsEditActivity.this , GoodsEditActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjGoodModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity( GoodsEditActivity.this, LoginActivity.class);
                return;
            }
            else if( mjGoodModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( GoodsEditActivity.this , GoodsEditActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjGoodModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            _goodsedit_all.setBackgroundResource(R.mipmap.wsz);
            if( _type == 1 && _operateType == OperateTypeEnum.REFRESH){
                _saleGoodsList.clear();
                if( mjGoodModel.getResultData()!=null && mjGoodModel.getResultData().getList()!=null) {
                    _saleGoodsList.addAll(mjGoodModel.getResultData().getList());
                }
                //_saleGoodsAdapter.notifyDataSetChanged();
                _goodsedit_listview.getRefreshableView().setAdapter(_saleGoodsAdapter);
            }else if( _type==1 && _operateType == OperateTypeEnum.LOADMORE){
                if( mjGoodModel.getResultData() !=null && mjGoodModel.getResultData().getList() !=null ) {
                    _saleGoodsList.addAll(mjGoodModel.getResultData().getList());
                }
                _saleGoodsAdapter.notifyDataSetChanged();
            }else if( _type==2 && _operateType==OperateTypeEnum.REFRESH){
                _offShelfList.clear();
                if( mjGoodModel.getResultData()!=null && mjGoodModel.getResultData().getList() !=null ) {
                    _offShelfList.addAll(mjGoodModel.getResultData().getList());
                }
                _goodsedit_listview.getRefreshableView().setAdapter(_offShelfAdapter);
            }else if( _type==2 && _operateType==OperateTypeEnum.LOADMORE){
                if( mjGoodModel.getResultData()!=null && mjGoodModel.getResultData().getList()!=null ) {
                    _offShelfList.addAll(mjGoodModel.getResultData().getList());
                }
                //_goodsedit_listview.setAdapter(_offShelfAdapter);
                _offShelfAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        _goodsedit_listview.onRefreshComplete();
        super.onErrorResponse(volleyError);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.header_back) {
            finish();
        }else if( v.getId()==R.id.goodsedit_delete){
            operGoods( GoodsOpeTypeEnum.DELETEGOODS );
        }else if(v.getId()==R.id.goodsedit_onshelf){
            operGoods( GoodsOpeTypeEnum.ONSHELF );
        }else if(v.getId()==R.id.goodsedit_offshelf){
            operGoods( GoodsOpeTypeEnum.OFFSHELF );
        }else if(v.getId()==R.id.goodsedit_all){
            selectGoods();
        }
    }

    protected void selectGoods(){
        //_goodsedit_all.setChecked( !_goodsedit_all.isChecked() );

        boolean isChecked =false;
        if( _goodsedit_all.getTag()==null){
            isChecked=false;
        }else {
            isChecked = (Boolean) _goodsedit_all.getTag();
        }
        isChecked=!isChecked;
        if( isChecked ) {
            _goodsedit_all.setBackgroundResource(R.mipmap.qx);
        }else{
            _goodsedit_all.setBackgroundResource(R.mipmap.wsz);
        }

        if(_type ==1){
            if(_saleGoodsList!=null){
                for(GoodsModel item :_saleGoodsList){
                    item.setSelected(isChecked);
                }
                _saleGoodsAdapter.notifyDataSetChanged();
            }
        }else if( _type==2){
            if(_offShelfList!=null){
                for (GoodsModel item: _offShelfList){
                    item.setSelected(isChecked);
                }
                _offShelfAdapter.notifyDataSetChanged();
            }
        }

        _goodsedit_all.setTag( isChecked);
    }


    protected void operGoods( GoodsOpeTypeEnum type ){
        String url = Constant.OPERGOODS_INTERFACE;

        int count =0;
        String ids="";

        if( _type==1){
            for(GoodsModel item : _saleGoodsList){
               if(item.isSelected()){
                   count++;
                   if( ids.length()>0){
                       ids+=",";
                   }
                   ids+= String.valueOf( item.getGoodsId());
               }
            }
        }else if(_type==2){
            for(GoodsModel item : _offShelfList){
                if(item.isSelected()){
                    count++;
                    if( ids.length()>0 ){
                        ids+=",";
                    }
                    ids+= String.valueOf( item.getGoodsId());
                }
            }
        }

        if(count<1){
            SimpleDialogFragment.createBuilder(this, this.getSupportFragmentManager())
                    .setTitle("提示信息")
                    .setMessage("请选择需要操作的商品")
                    .setNegativeButtonText("确定")
                    .show();
            return;
        }

        Map<String,String> paras = new HashMap<>();
        paras.put("type", String.valueOf( type.getIndex() ));
        paras.put("goods",ids);

        HttpParaUtils httpParaUtils =new HttpParaUtils();
        Map<String,String> maps = httpParaUtils.getHttpPost(paras);
        GsonRequest<BaseModel> operGoodsRequest=new GsonRequest<BaseModel>(
                Request.Method.POST,
                url,
                BaseModel.class,
                null,
                maps,
                operGoodsListener,
                this
        );

//        if( _progressDialog!=null){
//            _progressDialog.dismiss();
//            _progressDialog=null;
//        }
//        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder( this, this.getSupportFragmentManager())
//                .setMessage("正在获取数据，请稍等...")
//                .setCancelable(false)
//                .setCancelableOnTouchOutside(false);
//        _progressDialog = (ProgressDialogFragment) builder.show();
        GoodsEditActivity.this.showProgressDialog("","请稍等...");

        VolleyRequestManager.getRequestQueue().add(operGoodsRequest);
    }

    protected Response.Listener<BaseModel> operGoodsListener=new Response.Listener<BaseModel>() {
        @Override
        public void onResponse(BaseModel baseModel) {
           GoodsEditActivity.this.closeProgressDialog();

            if( null == baseModel){
                SimpleDialogFragment.createBuilder( GoodsEditActivity.this , GoodsEditActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage("请求失败")
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if( baseModel.getSystemResultCode() !=1 ){
                SimpleDialogFragment.createBuilder( GoodsEditActivity.this , GoodsEditActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage(baseModel.getSystemResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if(baseModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity( GoodsEditActivity.this ,LoginActivity.class);
                return;
            }
            if(baseModel.getResultCode() !=1){
                SimpleDialogFragment.createBuilder( GoodsEditActivity.this , GoodsEditActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage(baseModel.getResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            if(_type==1){
                _operateType= OperateTypeEnum.REFRESH;
                getSaleGoodsData(_operateType);
            }else if(_type==2){
                _operateType=OperateTypeEnum.REFRESH;
                getOffShelfGoodsData(_operateType);
            }
        }
    };


    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            // finish自身
            GoodsEditActivity.this.finish();
            return true;
        }
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.saleing_btn) {
            _type=1;
            _operateType = OperateTypeEnum.REFRESH;
            _goodsedit_offshelf.setVisibility(View.VISIBLE);
            _goodsedit_onshelf.setVisibility(View.GONE);

            getSaleGoodsData(_operateType);
        } else if (checkedId == R.id.offshelf_btn) {
            _type=2;
            _operateType = OperateTypeEnum.REFRESH;
            _goodsedit_offshelf.setVisibility(View.GONE);
            _goodsedit_onshelf.setVisibility(View.VISIBLE);

            getOffShelfGoodsData( _operateType );
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       if( position<1)return;

        if( _type==1){
            GoodsModel model = _saleGoodsList.get(position-1);
            model.setSelected( !model.isSelected());
            _saleGoodsAdapter.notifyDataSetChanged();
        }else if(_type==2){
            GoodsModel model = _offShelfList.get(position-1);
            model.setSelected( !model.isSelected());
            _offShelfAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(_type ==1){
            if(_saleGoodsList!=null){
                for(GoodsModel item :_saleGoodsList){
                    item.setSelected(isChecked);
                }
                _saleGoodsAdapter.notifyDataSetChanged();
            }
        }else if( _type==2){
            if(_offShelfList!=null){
                for (GoodsModel item: _offShelfList){
                    item.setSelected(isChecked);
                }
                _offShelfAdapter.notifyDataSetChanged();
            }
        }
    }
}
