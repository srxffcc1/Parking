package com.nado.parking.ui.main;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nado.parking.R;
import com.nado.parking.base.BaseFragment;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.ui.device.DeviceActivity;

/**
 * 主页
 */
public class DeviceFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "DeviceFragment";
    private com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout trlFragmentHome;
    private android.widget.LinearLayout passdevice;
    private android.widget.TextView device;
    private android.widget.RelativeLayout rlMore;
    private android.widget.LinearLayout liClgz;
    private android.widget.LinearLayout liCltj;
    private android.widget.LinearLayout liQcfd;
    private android.widget.LinearLayout liDyjk;
    private android.widget.LinearLayout liXcgj;
    private android.widget.LinearLayout liJscp;
    private android.widget.LinearLayout liWmjs;
    private android.widget.LinearLayout liDh;
    private android.widget.LinearLayout dzg;
    private android.widget.LinearLayout jyzj;


    @Override
    protected int getInflateViewId() {
        return R.layout.fragment_device;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {

        trlFragmentHome = (TwinklingRefreshLayout) byId(R.id.trl_fragment_home);
        passdevice = (LinearLayout) byId(R.id.passdevice);
        device = (TextView) byId(R.id.device);
        rlMore = (RelativeLayout) byId(R.id.rl_more);
        liClgz = (LinearLayout) byId(R.id.li_clgz);
        liCltj = (LinearLayout) byId(R.id.li_cltj);
        liQcfd = (LinearLayout) byId(R.id.li_qcfd);
        liDyjk = (LinearLayout) byId(R.id.li_dyjk);
        liXcgj = (LinearLayout) byId(R.id.li_xcgj);
        liJscp = (LinearLayout) byId(R.id.li_jscp);
        liWmjs = (LinearLayout) byId(R.id.li_wmjs);
        liDh = (LinearLayout) byId(R.id.li_dh);
        dzg = (LinearLayout) byId(R.id.dzg);
        jyzj = (LinearLayout) byId(R.id.jyzj);
    }

    @Override
    public void initData() {
        initDevice();

    }
    public void initDevice(){
        if (AccountManager.sUserBean != null) {
            if(AccountManager.sUserBean.obd_id!=null){
                device.setText("跳转吧");
            }
        }
    }

    @Override
    public void initEvent() {
        passdevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), DeviceActivity.class));
            }
        });
    }
}
