package com.huotu.huobanmall.seller.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2015/9/18.
 */
public class MJExpandableListView extends ExpandableListView {

    public MJExpandableListView(Context context) {
        super(context);
    }

    public MJExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MJExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec( Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
