package com.huotu.huobanmall.seller.widget;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/8/27.
 */
public class CountUpTimerView extends CountDownTimer {
    TextView view;
    float start;
    float end;
    long interval;
    long total;
    float current;
    String formatText;

    public CountUpTimerView( TextView view , String formatText , float start , float end , long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.view= view;
        this.start=start;
        this.formatText=formatText;
        this.end=end;
        this.interval=countDownInterval;
        this.total=millisInFuture;
        this.current=start;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //String content = String.format( formatTxt , millisUntilFinished / 1000 );
        float value = end-start;
        current += value* (1.0*interval/total);
        String content = String.format(formatText, current);
        view.setText( content );
    }

    @Override
    public void onFinish() {
        view.setText( String.format( formatText , end));
    }
}
