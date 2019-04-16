package com.nado.parking.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nado.parking.global.LocalApplication;
import com.nado.parking.manager.AccountManager;
import com.nado.parking.manager.RequestManager;
import com.nado.parking.net.RetrofitCallBack;
import com.nado.parking.net.RetrofitRequestInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by licrynoob on 2016/guide_2/13 <br>
 * Copyright (C) 2016 <br>
 * Email:licrynoob@gmail.com <p>
 * BaseFragment
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    protected Fragment mFragment;
    protected LocalApplication mApp;
    protected Activity mActivity;
    protected LayoutInflater mInflater;
    protected ViewGroup mContainer;
    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    private void getTestList() {
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", AccountManager.sUserBean.getId());
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getCanPhonePay(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("info");
                    if (code == 0) {

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragment = this;
        mApp = LocalApplication.getInstance();
        mInflater = inflater;
        mContainer = container;
        initInstanceState(savedInstanceState);
        beforeInflateView();
        if (mRootView == null) {
            mRootView = inflater.inflate(getInflateViewId(), container, false);
            initView();
            initData();
            initEvent();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    /**
     * 初始化保存的数据
     */
    protected void initInstanceState(Bundle savedInstanceState) {

    }

    /**
     * inflateView之前调用
     */
    protected void beforeInflateView() {

    }

    /**
     * 获取布局Id
     *
     * @return InflateViewId
     */
    protected abstract int getInflateViewId();

    /**
     * 通过id初始化View
     *
     * @param viewId viewId
     * @param <T>    T
     * @return T
     */
    protected <T extends View> T byId(int viewId) {
        return (T) mRootView.findViewById(viewId);
    }
    protected <T extends View> T findViewById(int viewId) {
        return (T) mRootView.findViewById(viewId);
    }
    public BaseFragment setArgumentz(String key,String value){
        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        this.setArguments(bundle);
        return this;
    }

}
