package com.nado.parking.ui.pay;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nado.parking.R;
import com.nado.parking.base.BaseActivity;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DianBindActivity extends BaseActivity {
    private android.widget.RelativeLayout rlLayoutTopBackBar;
    private android.widget.LinearLayout llLayoutTopBackBarBack;
    private android.widget.TextView tvLayoutTopBackBarTitle;
    private android.widget.TextView tvLayoutTopBackBarEnd;
    private android.widget.TextView tvLayoutBackTopBarOperate;
    private com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout tflActivityParkLot;
    private android.widget.LinearLayout passjigou;
    private android.widget.EditText et;
    private android.widget.CheckBox check;
    private android.widget.TextView tip;
    private android.widget.TextView save;
    private TextView pleasechose;
    private String product_id;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sdbind;
    }

    @Override
    public void initView() {

        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        tflActivityParkLot = (TwinklingRefreshLayout) findViewById(R.id.tfl_activity_park_lot);
        passjigou = (LinearLayout) findViewById(R.id.passjigou);
        et = (EditText) findViewById(R.id.et);
        check = (CheckBox) findViewById(R.id.check);
        tip = (TextView) findViewById(R.id.tip);
        save = (TextView) findViewById(R.id.save);
        tvLayoutTopBackBarTitle.setText("绑定缴费账户");
        pleasechose = (TextView) findViewById(R.id.pleasechose);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

        passjigou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivityForResult(new Intent(v.getContext(), ShuiChoseJGActivity.class).putExtra("type",getIntent().getStringExtra("type")),100);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind();
            }
        });

    }

    private void bind() {
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", AccountManager.sUserBean.getId());
        map.put("product_id",product_id);
        map.put("wecaccount",et.getText().toString());
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getCanPhonePay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    if (code == 0) {
                        Toast.makeText(getBaseContext(),"绑定成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode== Activity.RESULT_OK){
                pleasechose.setText(data.getStringExtra("company"));
                product_id=data.getStringExtra("id");
            }
        }
    }
}
