package com.nado.parking.ui.main;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nado.parking.R;
import com.nado.parking.base.BaseActivity;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainGoodDetailActivity extends BaseActivity {
    private android.webkit.WebView webView;
    private android.widget.TextView goodSubmit;
    private android.widget.RelativeLayout rlLayoutTopBackBar;
    private android.widget.LinearLayout llLayoutTopBackBarBack;
    private TextView tvLayoutTopBackBarStart;
    private TextView tvLayoutTopBackBarTitle;
    private TextView tvLayoutTopBackBarEnd;
    private TextView tvLayoutBackTopBarOperate;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main_gooddetail;
    }

    @Override
    public void initView() {

        webView = (WebView) findViewById(R.id.webView);
        goodSubmit = (TextView) findViewById(R.id.good_submit);

        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarStart = (TextView) findViewById(R.id.tv_layout_top_back_bar_start);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        tvLayoutTopBackBarTitle.setText("商品详情");

        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
//设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
// 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(false);

//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setDatabaseEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setSavePassword(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }

    @Override
    public void initData() {
        getMainGood();
    }
    private void getMainGood() {
        Map<String, String> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getGoodDetail(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    if (code == 0) {
                        JSONObject jsonObject=res.getJSONObject("data");
                        try {
                            String html=jsonObject.optString("details");
                            System.out.println(html);
                            webView.loadData(html, "text/html",  "utf-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
    public void initEvent() {

    }
}
