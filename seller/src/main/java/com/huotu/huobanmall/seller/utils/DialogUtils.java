package com.huotu.huobanmall.seller.utils;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.huotu.huobanmall.seller.activity.LoginActivity;

/**
 * Created by Administrator on 2015/9/10.
 */
public class DialogUtils {
    public static void showDialog( Context context , FragmentManager fragmentManager,
                                   String title ,String message ,
                                   String NegativeText ){
        SimpleDialogFragment.createBuilder( context , fragmentManager)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButtonText(NegativeText)
                .show();
    }
}
