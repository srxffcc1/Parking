package com.nado.parking.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nado.parking.R;
import com.nado.parking.adapter.recycler.RecyclerCommonAdapter;
import com.nado.parking.adapter.recycler.base.ViewHolder;
import com.nado.parking.base.BaseFragment;
import com.nado.parking.bean.BannerBean;
import com.nado.parking.bean.CarChoiceBean;
import com.nado.parking.bean.LocEvent;
import com.nado.parking.bean.OrderEvent;
import com.nado.parking.constant.HomepageConstant;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;
import com.nado.parking.ui.pay.PhonePayActivity;
import com.nado.parking.ui.pay.ShuiDianPayMenuActivity;
import com.nado.parking.ui.pay.YouKaPayActivity;
import com.nado.parking.ui.user.PeccancyQueryActivity;
import com.nado.parking.ui.user.account.LoginActivity;
import com.nado.parking.util.DisplayUtil;
import com.nado.parking.util.LogUtil;
import com.nado.parking.util.NetworkUtil;
import com.nado.parking.util.ToastUtil;
import com.nado.parking.widget.BannerLayout;
import com.nado.parking.widget.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout trlFragmentHome;
    private com.nado.parking.widget.BannerLayout blFragmentHome;
    private android.widget.LinearLayout liYkcz;
    private android.widget.LinearLayout liHfcz;
    private android.widget.LinearLayout liSdcz;
    private android.widget.LinearLayout liWzcx;
    private android.widget.LinearLayout liWbjl;
    private android.widget.LinearLayout liWzdj;
    private android.widget.LinearLayout liGzcx;
    private android.widget.LinearLayout liCljy;
    private android.widget.RelativeLayout rlMore;
    private android.support.v7.widget.RecyclerView rvFragmentHomeAll;
    private List<String> mBannerUrlList = new ArrayList<>();
    private List<BannerBean> mBannerList = new ArrayList<>();
    private List<CarChoiceBean> mCarChoiceList = new ArrayList<>();
    private int mDataStatus = STATUS_REFRESH;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_LOAD = 2;
    private int mPage = 1;
    private RecyclerCommonAdapter<CarChoiceBean> mCarBeanAdapter;
    private android.widget.TextView changeCity;


    @Override
    protected int getInflateViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {

    }





    @Override
    public void initView() {
        trlFragmentHome = (TwinklingRefreshLayout) byId(R.id.trl_fragment_home);
        blFragmentHome = (BannerLayout) byId(R.id.bl_fragment_home);
        liYkcz = (LinearLayout) byId(R.id.li_ykcz);
        liHfcz = (LinearLayout) byId(R.id.li_hfcz);
        liSdcz = (LinearLayout) byId(R.id.li_sdcz);
        liWzcx = (LinearLayout) byId(R.id.li_wzcx);
        liWbjl = (LinearLayout) byId(R.id.li_wbjl);
        liWzdj = (LinearLayout) byId(R.id.li_wzdj);
        liGzcx = (LinearLayout) byId(R.id.li_gzcx);
        liCljy = (LinearLayout) byId(R.id.li_cljy);
        rlMore = (RelativeLayout) byId(R.id.rl_more);
        rvFragmentHomeAll = (RecyclerView) byId(R.id.rv_fragment_home_all);

        changeCity = (TextView) findViewById(R.id.changeCity);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(LocEvent wechatPayEvent) {
        changeCity.setText(HomepageConstant.mLocationCity.replace("市",""));
    }
    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        getHomeBanner();
        getChoice();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode== Activity.RESULT_OK){
                changeCity.setText(data.getStringExtra("city"));
                HomepageConstant.mLocationCity=data.getStringExtra("city");
                HomepageConstant.mLocationProvince=data.getStringExtra("province");
            }
        }
    }

    @Override
    public void initEvent() {
        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), ChooseCityActivityNoCopy.class).putExtra("now",changeCity.getText().toString()),100);
            }
        });
        liWzcx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, PeccancyQueryActivity.class));
            }
        });
        trlFragmentHome.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                mDataStatus = STATUS_REFRESH;
                mPage=1;
                trlFragmentHome.finishLoadmore();
                trlFragmentHome.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPage++;
                trlFragmentHome.finishLoadmore();
                trlFragmentHome.finishRefreshing();

            }
        });
        liHfcz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), PhonePayActivity.class));
            }
        });
        liSdcz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(v.getContext(),ChooseCityActivity.class).putExtra("url","index.php?g=app&m=life&a=get_city"));



                startActivity(new Intent(v.getContext(), ShuiDianPayMenuActivity.class));

//                CityPicker.from(HomeFragment.this) //activity或者fragment
//                        .enableAnimation(true)	//启用动画效果，默认无
//                        .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，传null会自动定位（默认）
//                        .setOnPickListener(new OnPickListener() {
//                            @Override
//                            public void onPick(int position, City data) {
////                                Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onCancel(){
////                                Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
////                                finish();
//                            }
//
//                            @Override
//                            public void onLocate() {
//
//                            }
//                        })
//                        .show();
            }
        });
        liYkcz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountManager.sUserBean == null) {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                    return;
                }
                startActivity(new Intent(v.getContext(), YouKaPayActivity.class));
            }
        });
    }
    private void getChoice() {
        Map<String, String> map = new HashMap<>();
        map.put("page",mPage+"");
        map.put("limit", "2");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getChoice(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                LogUtil.e(TAG, response);
                switch (mDataStatus) {
                    case STATUS_REFRESH:
                        trlFragmentHome.finishRefreshing();
                        break;
//                    case STATUS_LOAD:
//                        trlFragmentHome.finishLoadmore();
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


            mCarBeanAdapter = new RecyclerCommonAdapter<CarChoiceBean>(mActivity, R.layout.item_carchoice, mCarChoiceList) {

                @Override
                protected void convert(ViewHolder holder, final CarChoiceBean carChoiceBean, int position) {
                    holder.setText(R.id.show_price, "￥"+carChoiceBean.show_price);
                    holder.setText(R.id.title, carChoiceBean.title);

                    RequestOptions options = new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE);//缓存全尺寸
                    AccountManager.setBestGood(carChoiceBean.id);
                    Glide.with(mActivity).load(carChoiceBean.picture).apply(options).into((ImageView) holder.getView(R.id.picture));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(mActivity,MainGoodDetailActivity.class).putExtra("id",carChoiceBean.id));
                        }
                    });

//                    new GlideImageLoader().displayImage(mActivity,carChoiceBean.picture, (ImageView) holder.getView(R.id.picture));
//                    new GlideImageLoader().displayImage(mActivity,carChoiceBean.picture, (ImageView) holder.getView(R.id.picture));
                }

            };




            rvFragmentHomeAll.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST, (int) DisplayUtil.dpToPx(mActivity, 1), ContextCompat.getColor(mActivity, R.color.colorLine), false, 2));
            rvFragmentHomeAll.setAdapter(mCarBeanAdapter);
            rvFragmentHomeAll.setLayoutManager(new GridLayoutManager(mActivity,2));
        }else{

            mCarBeanAdapter.notifyDataSetChanged();
        }

    }
    private void getHomeBanner() {
        Map<String, String> map = new HashMap<>();
        if (AccountManager.sUserBean != null) {
            map.put("customer_id", AccountManager.sUserBean.getId());
        }
        map.put("pid", "2");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getFlash(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                LogUtil.e(TAG, response);
                switch (mDataStatus) {
                    case STATUS_REFRESH:
                        trlFragmentHome.finishRefreshing();
                        break;
//                    case STATUS_LOAD:
//                        trlFragmentHome.finishLoadmore();
//                        break;
                }
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    JSONArray data = res.getJSONObject("data").getJSONArray("banner");
                    mBannerList.clear();
                    if (code == 0) {
                        if (mDataStatus == STATUS_REFRESH) {
                            mBannerList.clear();
                        }

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject bannerItem = data.getJSONObject(i);
                            BannerBean bannerBean = new BannerBean();
                            bannerBean.setId(bannerItem.getString("id"));//轮播ID
                            bannerBean.setImage(bannerItem.getString("image"));//轮播图片
                            bannerBean.setRemark(bannerItem.getString("remark"));//轮播信息
                            bannerBean.setUrl(bannerItem.getString("linkurl"));//轮播链接

                            mBannerList.add(bannerBean);
                        }
                        showBanner();

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
    private void showBanner() {
        mBannerUrlList.clear();
        for (int i = 0; i < mBannerList.size(); i++) {
            mBannerUrlList.add(mBannerList.get(i).getImage());
        }
        if (mBannerUrlList.size() > 0) {
            blFragmentHome.setViewUrls(mBannerUrlList);
            blFragmentHome.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    final BannerBean bannerBean = mBannerList.get(position);
//                    switch (bannerBean.getType()) {
//                        case BannerBean.TYPE_EXTERNAL:
//                            if (!TextUtils.isEmpty(bannerBean.getContent())) {
//                                CommonUtil.jumpToBrowser(mActivity, bannerBean.getContent());
//                            } else {
//                                ToastUtil.showShort(mActivity, getString(R.string.prompt_no_link_url));
//                            }
//                            break;
//                        case BannerBean.TYPE_INSIDE:
//                            ToastUtil.showShort(mActivity,getString(R.string.jum_other_loading));
//                            break;
//                        case BannerBean.TYPE_PRODUCT:
//                            ToastUtil.showShort(mActivity,getString(R.string.jum_other_loading));
//                            break;
//                    }
                }
            });
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
