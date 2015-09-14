package com.huotu.huobanmall.seller.fragment;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.DefaultValueFormatter;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class TodayFragment extends  BaseFragment {
    public TodayFragment(){}


    protected void setLineChartData( LineChart lineChart , List<Integer> xData , List<Integer> yData ){
        //if(_data==null) return;
        if( xData==null || yData==null )return;

        int bg=0xFF2686DA;
        int gridBg=0x56A5EA;
        lineChart.setDrawGridBackground(false);
        lineChart.setGridBackgroundColor(gridBg);
        lineChart.setBackgroundColor(bg);
        lineChart.setDescription("");
        lineChart.setNoDataText("暂无数据");
        lineChart.getAxisRight().setEnabled(false);

        List<String> xValues= new ArrayList<String>();
        List<Entry> yValues=new ArrayList<>();
        int count = xData.size();// _data.getResultData().getMemberHour().size();
        for(int i=0;i< count ;i++){
            int x = xData.get(i);// _data.getResultData().getOrderHour().get(i);
            xValues.add( String.valueOf( x));
            int y = yData.get(i); //_data.getResultData().getOrderAmount().get(i);
            Entry item=new Entry( y , i );
            yValues.add(item);
        }
        final LineDataSet dataSet =new LineDataSet( yValues ,"");
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setCircleSize(5);
        //dataSet.setDrawCircleHole(true);
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(2);
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextSize(14);
        dataSet.setValueTextColor(Color.GREEN);
        dataSet.setDrawCubic(true);
        dataSet.setCircleColorHole(Color.RED);
        dataSet.setDrawCircleHole(true);


        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String formatString = String.valueOf( value ) +"单";
                return  formatString;
            }
        });


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(0xFFFFFFFF);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(0xFFFFFFFF);



        lineChart.getLegend().setEnabled(false);

        LineData data =new LineData(xValues ,dataSet );
        lineChart.setData(data);
        lineChart.animateX(3000, Easing.EasingOption.EaseInOutQuart);
    }
}
