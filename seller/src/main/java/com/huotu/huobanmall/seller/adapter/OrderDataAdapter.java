package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.OrderTestModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.SystemTools;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public class OrderDataAdapter extends BaseAdapter{ //RecyclerView.Adapter<OrderDataAdapter.ViewHolder> {
    private Context _context;
    private List<OrderTestModel> _data;
    private LayoutInflater _inflater =null;
    private ILogisticListener _seeLogisticsListener = null;
    private ISeeOrderDetailListener _seeOrderDetailListener=null;

    public OrderDataAdapter ( Context context , List<OrderTestModel> data ) {
        super();
        _context= context;
        _data =data;
        _inflater = LayoutInflater.from(_context);
    }

    public void setLogisticsListener(ILogisticListener listener ){
        _seeLogisticsListener = listener;
    }

    public void set_seeOrderDetailListener(ISeeOrderDetailListener listener){
        _seeOrderDetailListener = listener;
    }

    protected void onSeeOrderDetailListener(View view , OrderTestModel model){
        if(_seeOrderDetailListener!=null){
            _seeOrderDetailListener.onSeeOrderDetail(view, model);
        }
    }
    protected void onLogisticListener( View view , OrderTestModel model ){
        if( _seeLogisticsListener!=null){
            _seeLogisticsListener.onClick(view, model);
        }
    }

    @Override
    public int getViewTypeCount() {
        //return super.getViewTypeCount();
        return 4;
    }



    @Override
    public int getCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data==null?null:_data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         OrderTestModel model = _data.get(position);
        if( 0== model.getViewType()){
            convertView= get_Main_View( convertView , model);
        }else if( 1 == model.getViewType()){
            convertView = get_Child_View(convertView, model);
        }else if(2==model.getViewType()){
            convertView = get_goods_View(convertView,model);
        }else if(3 ==model.getViewType()){
            convertView = get_Logistics_View(convertView,model);
        }

        return convertView;
    }

    protected View get_Logistics_View(View convertView , final OrderTestModel model){
        ViewHolder holder= null;
        if( convertView==null ) {
            convertView = _inflater.inflate(R.layout.layout_order_logistic_item,null);
            holder=new ViewHolder();
            holder.btnLogistics=(Button)convertView.findViewById(R.id.test_order_logistic_item_btn);
            holder.btnLogistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLogisticListener(v,  model );
                }
            });

            holder.tvTotalPrice = (TextView)convertView.findViewById(R.id.test_order_logistic_item_pay);
            holder.tvCount = (TextView)convertView.findViewById(R.id.test_order_logistic_item_amount);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvCount.setText ( "共" + String.valueOf( model.getCount()) + "件商品 实付:￥");
        holder.tvTotalPrice.setText(String.valueOf( model.getTotalPrice()));

        return convertView;
    }

    protected View get_goods_View(View convertView , final OrderTestModel model){
        ViewHolder holder= null;
        if( convertView==null ) {
            convertView = _inflater.inflate(R.layout.layout_order_goods_item,null);
            holder= new ViewHolder();
            holder.tvGoodsName = (TextView)convertView.findViewById(R.id.test_order_goods_item_goodsName);
            holder.tvPrice = (TextView)convertView.findViewById(R.id.test_order_goods_item_price);
            holder.tvCount = (TextView)convertView.findViewById(R.id.test_order_goods_item_count);
            holder.tvSpec = (TextView)convertView.findViewById(R.id.test_order_goods_item_spec);
            holder.ivPicture =(NetworkImageView)convertView.findViewById(R.id.test_order_goods_item_picture);
            holder.rlGoods=(RelativeLayout)convertView.findViewById(R.id.test_order_child_item_rl_goods);
            holder.rlGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSeeOrderDetailListener(v, model );
                }
            });
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvGoodsName.setText( model.getGoodsName() );
        holder.tvPrice.setText( "￥"+ String.valueOf( model.getPrice() ));
        holder.tvCount.setText( "X " + String .valueOf( model.getCount() ) );
        holder.tvSpec.setText(model.getSpec());
        BitmapLoader.create().displayUrl(_context, holder.ivPicture, model.getPictureUrl() , R.mipmap.goods , R.mipmap.goods );
        return convertView;
    }

    protected View get_Child_View(View convertView , OrderTestModel model){
        ViewHolder holder= null;
        if( convertView==null ) {
            convertView = _inflater.inflate(R.layout.layout_order_child_item,null);
            holder= new ViewHolder();
            holder.tvOrdernNo = (TextView)convertView.findViewById(R.id.test_order_child_item_childno);
            holder.tvStatus = (TextView)convertView.findViewById(R.id.test_order_child_item_status);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvOrdernNo.setText( model.getChildOrderNO() );
        holder.tvStatus.setText( model.getStatus() );
        return convertView;
    }

    protected View  get_Main_View( View convertView ,OrderTestModel model  ){
        ViewHolder holder= null;
        if( convertView==null ) {
            convertView = _inflater.inflate(R.layout.layout_order_main_item, null);
            holder=new ViewHolder();
            holder.tvOrdernNo = (TextView)convertView.findViewById(R.id.test_order_main_item_orderNo);
            holder.tvOrderTime = (TextView)convertView.findViewById(R.id.test_order_main_item_orderTime);
            convertView.setTag( holder );
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvOrdernNo.setText( model.getMainOrderNO()==null ? "": model.getMainOrderNO());

        String dateStr = SystemTools.getDateTime( model.getTime() , "yyyy-MM-dd HH:mm:ss");

        holder.tvOrderTime.setText( dateStr );

        return convertView;
    }



    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return _data.get(position).getViewType();
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        return position;
    }

//      @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                if( viewType==0 ){
//                    View view = _inflater.inflate(R.layout.layout_order_main_item, null);
//                    ViewHolder holder=new ViewHolder( view , viewType );
//                    return holder;
//                }else if( viewType==1){
//                    View view = _inflater.inflate(R.layout.layout_order_child_item,null);
//                    ViewHolder holder= new ViewHolder(view , viewType );
//                    return holder;
//                }else if( viewType==2){
//                    View view = _inflater.inflate(R.layout.layout_order_goods_item,null);
//                    ViewHolder holder= new ViewHolder(view , viewType );
//                    return holder;
//        }else if( viewType==3){
//            View view = _inflater.inflate(R.layout.layout_order_logistic_item,null);
//            ViewHolder holder =new ViewHolder(view , viewType );
//            return holder;
//        }else if( viewType==4){
//
//        }
//        return null;
//    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        OrderTestModel model=_data.get(position);
//        int viewType= model.getViewType();
//        if( viewType== 0){
//            holder.tvOrdernNo.setText( model.getMainOrderNO() );
//            holder.tvOrderTime.setText( String.valueOf(model.getOrderTime()));
//        }else if ( viewType==1){
//            holder.tvOrdernNo.setText( model.getChildOrderNO() );
//            holder.tvStatus.setText( model.getStatus() );
//        }else if( viewType==2){
//            holder.tvGoodsName.setText( model.getGoodsName() );
//            holder.tvPrice.setText( model.getPrice());
//            holder.tvCount.setText(model.getCount());
//            holder.tvSpec.setText( model.getSpec());
//            BitmapLoader.create().displayUrl(_context,holder.ivPicture, model.getPictureUrl());
//        }else if( viewType==3){
//            //holder.btnLogistics
//        }
//    }

//    @Override
//    public int getItemCount() {
//        return _data==null?0:_data.size();
//    }

    public class ViewHolder { //extends  RecyclerView.ViewHolder {

        public TextView tvOrdernNo;
        public TextView tvGoodsName;
        public TextView tvCount;
        public TextView tvSpec;
        public TextView tvStatus;
        public TextView tvTotalPrice;
        public TextView tvOrderTime;
        public TextView tvScore;
        public TextView tvPrice;
        public NetworkImageView ivPicture;
        public Button btnLogistics;
        public RelativeLayout rlGoods;
        public int position;
    }

    public interface ILogisticListener {
        void onClick(View view , OrderTestModel model );
    }
    public interface ISeeOrderDetailListener{
        void onSeeOrderDetail(View view, OrderTestModel model);
    }
}
