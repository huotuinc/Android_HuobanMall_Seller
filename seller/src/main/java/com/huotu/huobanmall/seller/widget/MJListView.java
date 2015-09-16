package com.huotu.huobanmall.seller.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/9/15.
 */
public class MJListView extends ListView {
    public MJListView(Context context) {
        super(context);
    }

    public MJListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MJListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
