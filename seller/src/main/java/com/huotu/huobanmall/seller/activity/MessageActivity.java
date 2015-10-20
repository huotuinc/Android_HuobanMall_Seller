package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.MessageAdapter;
import com.huotu.huobanmall.seller.bean.MJMessageModel;
import com.huotu.huobanmall.seller.bean.MessageModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.Paging;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
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
 *
 * @类名称：MessageActivity
 * @类描述：消息中心界面
 * @创建人：
 * @修改人：
 * @修改时间：
 * @修改备注：
 * @version:
 */
public class MessageActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.header_title)
    public TextView header_title;
    @Bind(R.id.header_back)
    public Button header_back;
    @Bind(R.id.msgList)
    PullToRefreshListView msgList;
    List<MessageModel> list;
    MessageAdapter adapter;
    OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    Handler handler = new Handler();
    View emptyView = null;
   boolean isSetEmptyView=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        header_title.setText("消息中心");
        header_back.setOnClickListener(this);

        emptyView = new View(this);
        emptyView.setBackgroundResource(R.mipmap.tpzw);
        //msgList.setEmptyView(emptyView);

        msgList.setMode(PullToRefreshBase.Mode.BOTH);
        msgList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                operateType = OperateTypeEnum.REFRESH;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                operateType = OperateTypeEnum.LOADMORE;
                getData();
            }
        });

        list=new ArrayList<>();
        adapter=new MessageAdapter(this,list);
        msgList.setAdapter(adapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        firstGetData();
    }

    protected void firstGetData(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( MessageActivity.this.isFinishing() ){return;}
                operateType= OperateTypeEnum.REFRESH;
                msgList.setRefreshing(true);
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyRequestManager.cancelAllRequest();
    }

    protected void getData(){
        if( false == this.canConnect() ){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    MessageActivity.this.closeProgressDialog();
                    msgList.onRefreshComplete();
                }
            });
            return;
        }

        String url =Constant.MESSAGE_INTERFACE;
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        Paging paging = new Paging();
        paging.setPagingSize(Constant.PAGES_COMMON);
        Map<String,String > maps = new HashMap<>();

        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            paging.setPagingTag("");
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( list != null && list.size() > 0)
            {
                MessageModel message = list.get(list.size() - 1);
                paging.setPagingTag(String.valueOf(message
                        .getMessageOrder()));
            } else if (list != null && list.size() == 0)
            {
                paging.setPagingTag("");
            }
        }

        maps.put("pagingTag", paging.getPagingTag());
        maps.put("pagingSize", paging.getPagingSize().toString());

        url = httpParaUtils.getHttpGetUrl( url , maps);

        GsonRequest<MJMessageModel> request =new GsonRequest<MJMessageModel>(
                Request.Method.GET,
                url ,
                MJMessageModel.class,
                null,
                new MyListener(this),
                new MJErrorListener(this)
        );

        VolleyRequestManager.AddRequest(request);
    }

    //Response.Listener<MJMessageModel> listener = new Response.Listener<MJMessageModel>() {
    static class MyListener implements Response.Listener<MJMessageModel>{
        WeakReference<MessageActivity> ref;
        public MyListener(MessageActivity act){
            ref = new WeakReference<MessageActivity>(act);
        }

        @Override
        public void onResponse(MJMessageModel mjMessageModel) {
            if( ref.get()==null) return;
            if(  ref.get().isFinishing() )return;

            ref.get().msgList.onRefreshComplete();
            ref.get().closeProgressDialog();

            if( ref.get().isSetEmptyView ==false ){
                ref.get().msgList.setEmptyView(ref.get().emptyView);
                ref.get().isSetEmptyView=true;
            }

            if(!ref.get().validateData(mjMessageModel)){
                return;
            }

            if( mjMessageModel.getResultData() == null ){
                DialogUtils.showDialog(ref.get() ,ref.get().getSupportFragmentManager(),"错误信息","服务端返回的数据有问题","关闭");
                return;
            }

            if( ref.get().operateType== OperateTypeEnum.REFRESH){
                ref.get().list.clear();
                if( mjMessageModel.getResultData().getMessages()!=null){
                    ref.get().list.addAll( mjMessageModel.getResultData().getMessages());
                }

                ref.get().adapter.notifyDataSetChanged();
            }else if( ref.get().operateType == OperateTypeEnum.LOADMORE){
                if( mjMessageModel.getResultData().getMessages()!=null){
                    ref.get().list.addAll( mjMessageModel.getResultData().getMessages());
                }
                ref.get().adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if( MessageActivity.this.isFinishing() ) return;

        msgList.onRefreshComplete();
        super.onErrorResponse(volleyError);
    }

    @Override
        public void onClick(View v)
        {
            switch (v.getId()) {
                case R.id.header_back: {
                    finish();
                }
                    break;
                default:
                    break;
            }
        }
}
