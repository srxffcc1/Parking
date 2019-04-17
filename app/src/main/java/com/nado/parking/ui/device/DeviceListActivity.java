package com.nado.parking.ui.device;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nado.parking.R;
import com.nado.parking.base.BaseActivity;

public class DeviceListActivity extends BaseActivity {
    private android.widget.RelativeLayout rlLayoutTopBackBar;
    private android.widget.LinearLayout llLayoutTopBackBarBack;
    private android.widget.TextView tvLayoutTopBackBarStart;
    private android.widget.TextView tvLayoutTopBackBarTitle;
    private android.widget.TextView tvLayoutTopBackBarEnd;
    private android.widget.TextView tvLayoutBackTopBarOperate;
    private android.widget.RelativeLayout hasobd;
    private android.widget.TextView obdid;
    private android.widget.TextView obdmacid;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_device_list;
    }

    @Override
    public void initView() {

        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarStart = (TextView) findViewById(R.id.tv_layout_top_back_bar_start);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        hasobd = (RelativeLayout) findViewById(R.id.hasobd);
        obdid = (TextView) findViewById(R.id.obdid);
        obdmacid = (TextView) findViewById(R.id.obdmacid);
        tvLayoutTopBackBarTitle.setText("我的设备");
        tvLayoutTopBackBarEnd.setText("添加");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
