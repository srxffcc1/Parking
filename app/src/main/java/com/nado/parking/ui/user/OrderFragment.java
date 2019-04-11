package com.nado.parking.ui.user;

import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nado.parking.R;
import com.nado.parking.base.BaseFragment;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;

import java.util.HashMap;
import java.util.Map;

public class OrderFragment extends BaseFragment {
    private com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout trlFragmentHome;
    private android.support.v7.widget.RecyclerView rvFragmentHomeAll;

    @Override
    protected int getInflateViewId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView() {

        trlFragmentHome = (TwinklingRefreshLayout) findViewById(R.id.trl_fragment_home);
        rvFragmentHomeAll = (RecyclerView) findViewById(R.id.rv_fragment_home_all);
    }

    @Override
    public void initData() {
        getOrderList(1);
    }

    private void getTestList(int page) {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());
            map.put("page", page+"");
            map.put("order_status", getArguments().getString("order_status"));
            map.put("limit", "10");
            RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getGoodOrder(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
                @Override
                public void onSuccess(String response) {

                }

                @Override
                public void onError(Throwable t) {

                }
            });
        }else{

        }

    }
    private void getOrderList(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", AccountManager.sUserBean.getId());
        map.put("page", page+"");
        map.put("order_status", getArguments().getString("order_status"));
        map.put("limit", "10");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getGoodOrder(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    @Override
    public void initEvent() {
        trlFragmentHome.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

                trlFragmentHome.finishLoadmore();
                trlFragmentHome.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);

                trlFragmentHome.finishLoadmore();
                trlFragmentHome.finishRefreshing();

            }
        });
    }
}
