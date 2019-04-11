package com.nado.parking.ui.pay;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nado.parking.R;
import com.nado.parking.adapter.recycler.RecyclerCommonAdapter;
import com.nado.parking.adapter.recycler.base.ViewHolder;
import com.nado.parking.base.BaseActivity;
import com.nado.parking.bean.SupportMoney;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;
import com.nado.parking.ui.main.PayAllActivity;
import com.nado.parking.util.DisplayUtil;
import com.nado.parking.util.LogUtil;
import com.nado.parking.util.NetworkUtil;
import com.nado.parking.util.ToastUtil;
import com.nado.parking.widget.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhonePayActivity extends BaseActivity {
    private static final String TAG = "PhonePayActivity";

    private RecyclerCommonAdapter<SupportMoney> mCarBeanAdapter;
    private List<SupportMoney> mCarChoiceList = new ArrayList<>();
    private int mDataStatus = STATUS_REFRESH;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_LOAD = 2;
    private RelativeLayout rlLayoutTopBackBar;
    private LinearLayout llLayoutTopBackBarBack;
    private TextView tvLayoutTopBackBarTitle;
    private TextView tvLayoutTopBackBarEnd;
    private TextView tvLayoutBackTopBarOperate;
    private RecyclerView rvPayAll;
    private android.support.design.widget.TextInputLayout layoutAccount;
    private android.widget.EditText et;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_hfpay;
    }

    @Override
    public void initView() {


        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        rvPayAll = (RecyclerView) findViewById(R.id.rv_pay_all);
        tvLayoutTopBackBarTitle.setText("手机充值");
        layoutAccount = (TextInputLayout) findViewById(R.id.layout_account);
        et = (EditText) findViewById(R.id.et);
    }

    @Override
    public void initData() {
        getCanPayList();
    }

    @Override
    public void initEvent() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isMobileNO(s.toString())) {
                    //显示错误提示
                    layoutAccount.setError("手机号输入错误");
                    layoutAccount.setErrorEnabled(true);
                } else {
                    layoutAccount.setError("手机号正确");
                    layoutAccount.setErrorEnabled(false);
                }
            }
        });

    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    private void getTestList() {
        Map<String, String> map = new HashMap<>();
        map.put("limit", "2");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getCanPhonePay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    /**
     * 检查手机和充值金额是否正确
     */
    private void checkPay(final String money) {
        if (!isMobileNO(et.getText().toString())) {
            //显示错误提示
            Toast.makeText(getBaseContext(), "检查手机号", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("telephone", et.getText().toString());
            map.put("pervalue", money);
            RequestManager.mRetrofitManager3.createRequest(RetrofitRequestInterface.class).checkPhonePay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
                @Override
                public void onSuccess(String response) {

                    try {
                        JSONObject res = new JSONObject(response);
                        int code = res.getInt("code");
                        String info = res.getString("info");
                        if (code == 0) {
                            buildOrder(money, et.getText().toString());
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

    }

    /**
     * 生成订单
     */
    private void buildOrder(final String money, final String tel) {
        Map<String, String> map = new HashMap<>();
        map.put("telephone", tel);
        map.put("pervalue", money);
        map.put("userid", AccountManager.sUserBean.getId());
        RequestManager.mRetrofitManager3.createRequest(RetrofitRequestInterface.class).buildOrder(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    if (code == 0) {
                        String orderid = res.get("data").toString();//获得的本司订单号
                        String paytypekey = "pay_type";
                        String pervalue = money;//充值金额
                        String url = "index.php?g=app&m=telephone&a=goodspay";
                        Map<String, String> postmap = new HashMap<>();
                        postmap.put("paymm",money);
                        postmap.put("url",url);
                        postmap.put("pervalue",pervalue);
                        postmap.put("paytypekey",paytypekey);
                        postmap.put("orderid",orderid);
                        PayAllActivity.open(PhonePayActivity.this, postmap);//打开充值界面 选择支付类型 然后会访问url交换对应的sign或appid来完成充值
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
        //充值成功的返回
        if(requestCode==PayAllActivity.START_PAY){
            if(resultCode==Activity.RESULT_OK){
                successPay(data);
            }
        }
    }

    private void successPay(Intent resultdata) {
        Map<String, String> map = new HashMap<>();
        map.put("orderid",resultdata.getStringExtra("orderid"));
        RequestManager.mRetrofitManager3.createRequest(RetrofitRequestInterface.class).successOrder(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    if (code == 0) {
                        finish();
                    }else{
                        Toast.makeText(getBaseContext(),"扣款成功但平台数据存在延迟稍后查看",Toast.LENGTH_SHORT).show();
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

    /**
     * 获得可以充值的数量
     */
    private void getCanPayList() {
        Map<String, String> map = new HashMap<>();
        RequestManager.mRetrofitManager3.createRequest(RetrofitRequestInterface.class).getCanPhonePay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                LogUtil.e(TAG, response);
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    mCarChoiceList.clear();
                    if (code == 0) {
                        JSONArray data = res.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            String dataItem = data.get(i) + "";
                            SupportMoney bean = new SupportMoney();
                            bean.price = dataItem;
                            mCarChoiceList.add(bean);
                        }
                        showRecycleView();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                if (!NetworkUtil.isConnected()) {
                    ToastUtil.showShort(mActivity, R.string.net_error);
                } else {
                    ToastUtil.showShort(mActivity, getString(R.string.net_error));
                }
            }
        });
    }

    /**
     * 车主精选
     */
    private void showRecycleView() {
        if (mCarBeanAdapter == null) {


            mCarBeanAdapter = new RecyclerCommonAdapter<SupportMoney>(mActivity, R.layout.item_hfpay, mCarChoiceList) {

                @Override
                protected void convert(ViewHolder holder, final SupportMoney carChoiceBean, int position) {
                    holder.setText(R.id.price, carChoiceBean.price + "元");
//                    new GlideImageLoader().displayImage(mActivity,carChoiceBean.picture, (ImageView) holder.getView(R.id.picture));
//                    new GlideImageLoader().displayImage(mActivity,carChoiceBean.picture, (ImageView) holder.getView(R.id.picture));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkPay(carChoiceBean.price);
                        }
                    });


                }

            };


            rvPayAll.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST, (int) DisplayUtil.dpToPx(mActivity, 1), ContextCompat.getColor(mActivity, R.color.colorLine), false, 2));
            rvPayAll.setAdapter(mCarBeanAdapter);
            rvPayAll.setLayoutManager(new GridLayoutManager(mActivity, 3));
        } else {

            mCarBeanAdapter.notifyDataSetChanged();
        }

    }
}
