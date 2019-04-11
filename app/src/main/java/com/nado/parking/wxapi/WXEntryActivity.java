package com.nado.parking.wxapi;

/**
 * Created by Home-Pc on 2016-10-12.
 */

import android.app.Activity;
import android.os.Bundle;

import com.nado.parking.global.LocalApplication;
import com.nado.parking.util.LogUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalApplication.mIWXApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.e(TAG, baseResp.errCode + "");
        if (baseResp instanceof SendAuth.Resp) {
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            LogUtil.e(TAG,resp.toString());
            finish();

        }
    }
}
