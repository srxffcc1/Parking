package com.nado.parking.ui.pay;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nado.parking.R;
import com.nado.parking.adapter.recycler.RecyclerCommonAdapter;
import com.nado.parking.base.BaseActivity;
import com.nado.parking.bean.Company;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DianChoseJGActivity extends BaseActivity {
    private RelativeLayout rlLayoutTopBackBar;
    private LinearLayout llLayoutTopBackBarBack;
    private TextView tvLayoutTopBackBarTitle;
    private TextView tvLayoutTopBackBarEnd;
    private TextView tvLayoutBackTopBarOperate;
    private TwinklingRefreshLayout tflActivityParkLot;
    private EditText etActivitySearchSearch;
    private ImageView ivActivitySearchSearch;
    private RecyclerView list;
    private RecyclerCommonAdapter<Company> myAdapter;
    private List<Company> mBeanList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sdjgxz;
    }

    @Override
    public void initView() {

        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        tflActivityParkLot = (TwinklingRefreshLayout) findViewById(R.id.tfl_activity_park_lot);
        etActivitySearchSearch = (EditText) findViewById(R.id.et_activity_search_search);
        ivActivitySearchSearch = (ImageView) findViewById(R.id.iv_activity_search_search);
        list = (RecyclerView) findViewById(R.id.list);
        tvLayoutTopBackBarTitle.setText("机构选择");
    }

    @Override
    public void initData() {
        initCompanyList();

    }

    private void initCompanyList() {
        Map<String, String> map = new HashMap<>();

        RequestManager.mRetrofitManager3.createRequest(RetrofitRequestInterface.class).getChoice(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {

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

    }
}
