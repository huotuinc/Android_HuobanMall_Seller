package com.huotu.huobanmall.seller.utils;

import android.content.Context;
import android.net.Uri;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;


/**
 *
 * @类名称：BitmapLoader
 * @类描述：加载图片
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月2日 上午9:26:12
 * @修改备注：
 * @version:
 */
public class BitmapLoader
{
    private static BitmapLoader instance;

    public synchronized static BitmapLoader create()
    {
        if (instance == null)
        {
            instance = new BitmapLoader();
        }
        return instance;
    }

    private BitmapLoader()
    {

    }

    /**
     *
     * @方法描述：采用volly加载网络图片
     * @方法名：displayUrl
     * @参数：@param context 上下文环境
     * @参数：@param imageView 图片空间
     * @参数：@param imageUrl url地址
     * @参数：@param initImg 初始化图片
     * @参数：@param errorImg 错误图片
     * @返回：void
     * @exception
     * @since
     */
    public void displayUrl(Context context, NetworkImageView imageView,
            String imageUrl)
    {
        displayUrl(context, imageView, imageUrl , R.mipmap.icon ,R.mipmap.icon );
    }

    /**
     *
     * @方法描述：采用volly加载网络图片
     * @方法名：displayUrl
     * @参数：@param context 上下文环境
     * @参数：@param imageView 图片空间
     * @参数：@param imageUrl url地址
     * @参数：@param initImg 初始化图片
     * @参数：@param errorImg 错误图片
     * @返回：void
     * @exception
     * @since
     */
    public void displayUrl(Context context, NetworkImageView imageView,
            String imageUrl, int initImg, int errorImg)
    {
        if( null == imageUrl || imageUrl.length()<1 ||  Uri.parse(imageUrl).getHost()==null){
            imageView.setDefaultImageResId(initImg);
            imageView.setBackgroundResource(initImg);
            imageView.setErrorImageResId(errorImg);
            //ImageLoader imageLoader = VolleyRequestManager.getImageLoader(context);
            imageView.setImageUrl("", null );
            return;
        }

        imageView.setBackgroundResource(0);
        imageView.setDefaultImageResId(initImg);
        ImageLoader imageLoader = VolleyRequestManager.getImageLoader(context);
        imageView.setErrorImageResId(errorImg);
        imageView.setImageUrl(imageUrl, imageLoader);
    }
}
