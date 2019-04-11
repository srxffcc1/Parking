package com.nado.parking.net;

import com.nado.parking.manager.RequestManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by maqing on 2017/8/9.
 * Email:2856992713@qq.com
 * 网络请求描述接口
 */
public interface RetrofitRequestInterface {

    /*首页轮播图*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetHomeIcon")
    Call<String> getFlash(@FieldMap Map<String, String> params);

    /*首页数据*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetHome")
    Call<String> getHome(@FieldMap Map<String, String> params);

    /*停车记录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetCarParkList")
    Call<String> getCarParkList(@FieldMap Map<String, String> params);

    /*车主精选*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "getChoice")
    Call<String> getChoice(@FieldMap Map<String, String> params);

    /*车主精选动态测试*/
    @FormUrlEncoded
    @POST
    Call<String> getChoiceDy(@Url String url, @FieldMap Map<String, String> params);
    //RequestManager.mInterfacePrefix + "getChoice"


    /*获得订单*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetAllOrder")
    Call<String> getGoodOrder(@FieldMap Map<String, String> params);


    /*水费查询订单*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=life&a=query_arrearage")
    Call<String> sendSearchShuiOrder(@FieldMap Map<String, String> params);

    /*电费查询订单*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=power&a=finduserpower")
    Call<String> sendSearchDianOrder(@FieldMap Map<String, String> params);



    /*获得充值金额*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=telephone&a=telephone_note")
    Call<String> getCanPhonePay(@FieldMap Map<String, String> params);


    /*水费充值订单*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=life&a=create_order")
    Call<String> getShuiOrder(@FieldMap Map<String, String> params);

    /*电费充值订单*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=power&a=powercz")
    Call<String> getDianOrder(@FieldMap Map<String, String> params);


    /*判断水费是不是欠费*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=life&a=get_money")
    Call<String> chechNeedShuiPay(@FieldMap Map<String, String> params);


    /*获得用户绑定的电公司*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=power&a=getusersave")
    Call<String> getBindDianPayCompany(@FieldMap Map<String, String> params);


    /*获得用户绑定的水公司*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=life&a=get_product_user")
    Call<String> getBindShuiPayCompany(@FieldMap Map<String, String> params);

    /*生成订单*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=telephone&a=makeorder")
    Call<String> buildOrder(@FieldMap Map<String, String> params);


    /*更新订单*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=telephone&a=updateorder")
    Call<String> successOrder(@FieldMap Map<String, String> params);

    /*检查手机和充值金额是否正确*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=telephone&a=checkphone")
    Call<String> checkPhonePay(@FieldMap Map<String, String> params);

    /*水机构列表*/
    @FormUrlEncoded
    @POST("index.php?g=app&m=life&a=get_company")
    Call<String> getShuiCompany(@FieldMap Map<String, String> params);

    /*支付检查*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "CheckPay")
    Call<String> checkPay(@FieldMap Map<String, String> params);

    /*支付检查动态设置url*/
    @FormUrlEncoded
    @POST
    Call<String> checkPayDy(@Url String url,  @FieldMap Map<String, String> params);


    /*获取城市*/
    @FormUrlEncoded
    @POST
    Call<String> getAllCityDy(@Url String url,  @FieldMap Map<String, String> params);


    /*最新设备*/
    @FormUrlEncoded
    @POST("GetDateServices.asmx/GetDate")
    Call<String> getDeviceDate(@FieldMap Map<String, String> params);

    /*全部行程*/
    @FormUrlEncoded
    @POST("GetDateServices.asmx/GetDate")
    Call<String> getGuiJiDate(@FieldMap Map<String, String> params);

    /*我的停车订单详情*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetMyCarOrderDetail")
    Call<String> getCarOrderDetail(@FieldMap Map<String, String> params);

    /**
     * 获取微信支付签名数据
     */
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "getrepayid")
    Call<String> getWechatSign(@FieldMap Map<String, String> params);

    /**
     * 获取支付宝支付签名数据
     */
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetSign")
    Call<String> getAlipaySign(@FieldMap Map<String, String> params);


    /*获取验证码*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetCode")
    Call<String> getCode(@FieldMap Map<String, String> params);

    /*用户注册          页面还有问题需要修改*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "Register")
    Call<String> register(@FieldMap Map<String, String> params);

    /*获取可用分店列表*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "login/showBranchList.do")
    Call<String> showBranchList(@FieldMap Map<String, String> params);

    /*用户登录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "Login")
    Call<String> login(@FieldMap Map<String, String> params);

//    /*我的信息*/
//    @FormUrlEncoded
//    @POST(RequestManager.mInterfacePrefix + "GetUserinfo")
//    Call<String> mine(@FieldMap Map<String, String> params);

    /*发送修改密码验证码，并验证号码是否正确*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetCode2")
    Call<String> sendUpdatePasswordCode(@FieldMap Map<String, String> params);

    /*修改用户密码*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "ChangePwd")
    Call<String> updatePassword(@FieldMap Map<String, String> params);

    /*修改用户密码*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "OverLookPass")
    Call<String> forgetPassword(@FieldMap Map<String, String> params);

    /*上传用户头像*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "ChangeUserInfo")
    Call<String> updateHead(@FieldMap Map<String, String> params);

    /*问题列表*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetHelpList")
    Call<String> questionList(@FieldMap Map<String, String> params);

    /*我的吐槽*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "Guestbook")
    Call<String> mineGuest(@FieldMap Map<String, String> params);

    /*问题详情*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetHelpDetail")
    Call<String> questionDetail(@FieldMap Map<String, String> params);

    /*获取所有车辆*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetAllCar")
    Call<String> getAllCar(@FieldMap Map<String, String> params);

    /*添加车辆*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "EditCar")
    Call<String> addCar(@FieldMap Map<String, String> params);

    /*删除车辆*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "DeleteCar")
    Call<String> deleteCar(@FieldMap Map<String, String> params);

    /*切换省市区1*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "ChooseCity")
    Call<String> getChooseCity(@FieldMap Map<String, String> params);

    /*停车场列表信息*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetParkList")
    Call<String> getParkList(@FieldMap Map<String, String> params);

    /*关于我们bug*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + " ")
    Call<String> showAboutUs(@FieldMap Map<String, String> params);

    /*提交意见反馈*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + " ")
    Call<String> addOpinion(@FieldMap Map<String, String> params);

    /*补充车辆信息*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "UpdateCarInfo")
    Call<String> updateCarInfo(@FieldMap Map<String, String> params);

    /*违章行为记录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetWZAll")
    Call<String> getPeccancyData(@FieldMap Map<String, String> params);

    /*特定车辆违章行为记录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "GetWZ")
    Call<String> getPeccancyHistory(@FieldMap Map<String, String> params);

    /*用户支付订单申请*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + " ")
    Call<String> payOrder(@FieldMap Map<String, String> params);


    /*个人信息修改bug*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "usercapital/changePersonalDate.do")
    Call<String> changePersonalDate(@FieldMap Map<String, String> params);


    /*获取全部省市区信息*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "china/showAllChina.do")
    Call<String> showAllChina(@FieldMap Map<String, String> params);


    /*我们的信息*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + " ")
    Call<String> helpCenter(@FieldMap Map<String, String> params);


    /*判断系统版本*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix + "CheckVersionforios")
    Call<String> getVersion(@FieldMap Map<String, String> params);


}
