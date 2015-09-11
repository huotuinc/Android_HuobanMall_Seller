package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditSetActivity extends BaseFragmentActivity implements OnClickListener {
    @Bind(R.id.ET)
    public EditText ET;
    @Bind(R.id.backtext)
    public TextView backtext;
    @Bind(R.id.editBtn)
    public Button deitBtn;
    @Bind(R.id.title)
    public TextView title;
    @Bind(R.id.backImage)
    public Button backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_set);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("修改");

    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtext: {
                finish();
                break;

            }
            default:
                break;
        }
    }
}
