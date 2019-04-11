package com.nado.parking.ui.pay;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nado.parking.R;
import com.nado.parking.adapter.recycler.RecyclerCommonAdapter;
import com.nado.parking.adapter.recycler.base.ViewHolder;
import com.nado.parking.base.BaseActivity;
import com.nado.parking.bean.CarChoiceBean;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;
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

public class YouKaPayActivity extends BaseActivity {
    private static final String TAG = "YouKaPayActivity";
    private android.widget.RelativeLayout rlLayoutTopBackBar;
    private android.widget.LinearLayout llLayoutTopBackBarBack;
    private android.widget.TextView tvLayoutTopBackBarTitle;
    private android.widget.TextView tvLayoutTopBackBarEnd;
    private android.widget.TextView tvLayoutBackTopBarOperate;
    private com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout tflActivityParkLot;
    private android.widget.LinearLayout needchange;
    private android.widget.TextView youkaadd;
    private android.support.v7.widget.RecyclerView rvPayAll;
    private RecyclerCommonAdapter<CarChoiceBean> mCarBeanAdapter;
    private List<CarChoiceBean> mCarChoiceList = new ArrayList<>();
    private int mDataStatus = STATUS_REFRESH;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_LOAD = 2;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ykpay;
    }

    @Override
    public void initView() {

        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        tflActivityParkLot = (TwinklingRefreshLayout) findViewById(R.id.tfl_activity_park_lot);
        needchange = (LinearLayout) findViewById(R.id.needchange);
        youkaadd = (TextView) findViewById(R.id.youkaadd);
        rvPayAll = (RecyclerView) findViewById(R.id.rv_pay_all);
        tvLayoutTopBackBarTitle.setText("油卡充值");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

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
    private void checkPay() {
        Map<String, String> map = new HashMap<>();
        map.put("limit", "2");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).checkPhonePay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    /**
     * 生成订单
     */
    private void buildOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("limit", "2");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).buildOrder(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
    private void getCanPayList() {
        Map<String, String> map = new HashMap<>();
        map.put("limit", "2");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getCanPhonePay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                LogUtil.e(TAG, response);
                switch (mDataStatus) {
                    case STATUS_REFRESH:
                        tflActivityParkLot.finishRefreshing();
                        break;
//                    case STATUS_LOAD:
//                        tflActivityParkLot.finishLoadmore();
//                        break;
                }
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    mCarChoiceList.clear();
                    if (code == 0) {
                        JSONArray data = res.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject dataItem = data.getJSONObject(i);
                            CarChoiceBean beanCarChoice = new CarChoiceBean();
                            beanCarChoice.setId(dataItem.getString("id"));
                            beanCarChoice.setPicture(dataItem.getString("picture"));
                            beanCarChoice.setShow_price(dataItem.getString("show_price"));
                            beanCarChoice.setTitle(dataItem.getString("title"));
                            mCarChoiceList.add(beanCarChoice);
                        }
                        showCarChoiceRecycleView();

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
    private void showCarChoiceRecycleView() {
        if (mCarBeanAdapter == null) {


            mCarBeanAdapter = new RecyclerCommonAdapter<CarChoiceBean>(mActivity, R.layout.item_ykpay, mCarChoiceList) {

                @Override
                protected void convert(ViewHolder holder, CarChoiceBean carChoiceBean, int position) {
                    holder.setText(R.id.show_price, "￥"+carChoiceBean.show_price);
                    holder.setText(R.id.title, carChoiceBean.title);
//                    new GlideImageLoader().displayImage(mActivity,carChoiceBean.picture, (ImageView) holder.getView(R.id.picture));
//                    new GlideImageLoader().displayImage(mActivity,carChoiceBean.picture, (ImageView) holder.getView(R.id.picture));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });


                }

            };




            rvPayAll.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST, (int) DisplayUtil.dpToPx(mActivity, 1), ContextCompat.getColor(mActivity, R.color.colorLine), false, 2));
            rvPayAll.setAdapter(mCarBeanAdapter);
            rvPayAll.setLayoutManager(new GridLayoutManager(mActivity,2));
        }else{

            mCarBeanAdapter.notifyDataSetChanged();
        }

    }
}
