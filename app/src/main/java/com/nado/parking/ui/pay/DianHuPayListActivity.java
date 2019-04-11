package com.nado.parking.ui.pay;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nado.parking.R;
import com.nado.parking.adapter.recycler.RecyclerCommonAdapter;
import com.nado.parking.adapter.recycler.base.ViewHolder;
import com.nado.parking.base.BaseActivity;
import com.nado.parking.bean.DianHuHao;
import com.nado.parking.bean.DianQianFei;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;
import com.nado.parking.util.DisplayUtil;
import com.nado.parking.widget.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DianHuPayListActivity extends BaseActivity {


    private RelativeLayout rlLayoutTopBackBar;
    private LinearLayout llLayoutTopBackBarBack;
    private TextView tvLayoutTopBackBarTitle;
    private TextView tvLayoutTopBackBarEnd;
    private TextView tvLayoutBackTopBarOperate;
    private RecyclerView list;
    private RecyclerCommonAdapter<DianHuHao> myAdapter;
    private List<DianHuHao> mBeanList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sdlist;
    }

    @Override
    public void initView() {


        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        list = (RecyclerView) findViewById(R.id.list);
        tvLayoutTopBackBarTitle.setText("选择户号缴费");
    }

    @Override
    public void initData() {
        getList();
    }

    private void getList() {
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", AccountManager.sUserBean.getId());
        map.put("type", "水".equals(getIntent().getStringExtra("type")) ? "1" : "2");
        RequestManager.mRetrofitManager3.createRequest(RetrofitRequestInterface.class).getBindDianPayCompany(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    mBeanList.clear();
                    if (code == 0) {
                        JSONArray jsonArray = res.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            DianHuHao bean = new DianHuHao();
                            bean.id = jsonObject.getString("id");
                            bean.user_id = jsonObject.getString("user_id");
                            bean.company = jsonObject.getString("company");
                            bean.wecacount = jsonObject.getString("wecacount");
                            bean.productid = jsonObject.getString("productid");
                            bean.status = jsonObject.getString("status");
                            mBeanList.add(bean);
                        }

                    } else {
                    }

                    showRecycleView();
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

    private void showRecycleView() {
        if (myAdapter == null) {


            myAdapter = new RecyclerCommonAdapter<DianHuHao>(mActivity, R.layout.item_shuihuhao, mBeanList) {

                @Override
                protected void convert(ViewHolder holder, final DianHuHao carChoiceBean, int position) {
                    holder.setText(R.id.huhao, carChoiceBean.wecacount);
                    holder.setText(R.id.jgname, carChoiceBean.company);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sendSearchOrder(carChoiceBean);
                        }
                    });
                }

            };

            list.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST, (int) DisplayUtil.dpToPx(mActivity, 1), ContextCompat.getColor(mActivity, R.color.colorLine), false, 2));
            list.setAdapter(myAdapter);
            list.setLayoutManager(new LinearLayoutManager(mActivity));
        } else {

            myAdapter.notifyDataSetChanged();
        }
    }

    public long starttime = 0;

    public void sendSearchOrder(final DianHuHao bean) {
        Map<String, String> map = new HashMap<>();
        map.put("company", bean.company);
        map.put("wecaccount", bean.wecacount);
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).sendSearchDianOrder(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    if (code == 0) {
                        String stockordernumber=res.getJSONObject("data").getString("stockordernumber");
                        chechNeedPay(bean,stockordernumber);
                    }else{
                        Toast.makeText(getBaseContext(), "查询不成功", Toast.LENGTH_SHORT).show();
                    }
//                    if (Status) {
//                        starttime = System.currentTimeMillis();
//                        chechNeedPay(bean);
//                    } else {
//                        Toast.makeText(getBaseContext(), "查询不成功", Toast.LENGTH_SHORT).show();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });

    }

    //需要先用一个下单id
    public void chechNeedPay(final DianHuHao hubean,String stockordernumber) {
        Map<String, String> map = new HashMap<>();
        map.put("order", stockordernumber);
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).chechNeedShuiPay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    if (code == 0) {

                        JSONObject jsonObject = res.getJSONObject("data");
                        String totalamount = jsonObject.getString("totalamount");

                        DianQianFei bean = new DianQianFei();

                        bean.esupordernumber = jsonObject.getString("esupordernumber");
                        bean.failmessage = jsonObject.getString("failmessage");
                        bean.finishedtime = jsonObject.getString("finishedtime");
                        bean.searchstatus = jsonObject.getString("searchstatus");
                        bean.stockordernumber = jsonObject.getString("stockordernumber");
                        bean.totalamount = jsonObject.getString("totalamount");
                        bean.useraddress = jsonObject.getString("useraddress");
                        bean.wecbalance = jsonObject.getString("wecbalance");

                        Intent intent = new Intent(getBaseContext(), DianPayActivity.class);
                        buildIntent(intent, bean);
                        intent.putExtra("delayfee",jsonObject.getJSONObject("wecbilldata").getString("delayfee"));
                        intent.putExtra("wecbillmoney",jsonObject.getJSONObject("wecbilldata").getString("wecbillmoney"));
                        intent.putExtra("productid",hubean.productid);
                        intent.putExtra("company",hubean.company);
                        intent.putExtra("wecaccount",hubean.wecacount);
                        intent.putExtra("totalamount",bean.totalamount);
                        intent.putExtra("company",hubean.company);
                        if ("0".equals(totalamount)) {
                            intent.putExtra("needpayflag", false);
                        } else {
                            intent.putExtra("needpayflag", true);
                        }
                        startActivity(intent);
                    } else {

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
