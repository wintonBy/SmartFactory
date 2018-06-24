package com.sf.smartfactory.network;

import com.sf.smartfactory.BuildConfig;
import com.sf.smartfactory.network.response.BaseResponse;
import com.sf.smartfactory.network.response.DeviceClockResponse;
import com.sf.smartfactory.network.response.DeviceListResponse;
import com.sf.smartfactory.network.response.DeviceRateResponse;
import com.sf.smartfactory.network.response.DeviceSummaryResponse;
import com.sf.smartfactory.network.response.FactoryInfoResponse;
import com.sf.smartfactory.network.response.LastStatusResponse;
import com.sf.smartfactory.network.response.LoginResponse;
import com.sf.smartfactory.network.response.MachineProcessListResponse;
import com.sf.smartfactory.network.response.OEEResponse;
import com.sf.smartfactory.network.response.OrderListResponse;
import com.sf.smartfactory.network.response.QuickTimeResponse;
import com.sf.smartfactory.network.response.RunTimeSummaryResponse;
import com.sf.smartfactory.network.response.StatusListResponse;
import com.sf.smartfactory.network.response.StuffListResponse;
import com.sf.smartfactory.network.response.TimeResponse;
import com.sf.smartfactory.network.response.UpdateInfoResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author: winton
 * @time: 2018/4/7 10:48
 * @package: com.sf.smartfactory.network.subscriber
 * @project: SmartFactory
 * @mail:
 * @describe: 服务端API
 */
public interface ServerApi {

    public static final String BASE_URL = BuildConfig.SERVER_HOST;

    @GET("{url}")
    Observable<ResponseBody> executeGet(@Path("url") String url, @QueryMap Map<String, String> params);
    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @QueryMap Map<String, String> params);

    /**
     * 用户名登录接口
     * @param username
     * @param pwd
     * @return
     */
    @POST("rest/user/loginByUsername")
    Observable<LoginResponse> loginByUsername(@Query("username")String username, @Query("pwd")String pwd);

    /**
     * 手机号登录
     * @param phone
     * @param pwd
     * @return
     */
    @POST("rest/user/loginByPhone")
    Observable<LoginResponse> loginByPhone(@Query("phone")String phone,@Query("pwd")String pwd);

    /**
     * 设备列表
     * @param needStatus 是否需要返回状态信息
     * @return
     */
    @POST("rest/device/list")
    Observable<DeviceListResponse> deviceList(@Query("needStatus") boolean needStatus);

    /**
     * 监控设备列表
     * @return
     */
    @POST("rest/monitor/device/list")
    Observable<DeviceListResponse> monitorDeviceList();

    /**
     * 获取设备运行状态简况
     * @return
     */
    @POST("rest/monitor/device/summary")
    Observable<DeviceSummaryResponse> deviceSummary();

    /**
     * 订单监控列表
     * @param status
     * @return
     */
    @POST("rest/product/order/list")
    Observable<OrderListResponse> orderList( @Query("status")Integer status);

    /**
     * 加工信息监控
     * @param needDevice
     * @param needEmp
     * @param needProduct
     * @return
     */
    @POST("rest/monitor/product/part")
    Observable<MachineProcessListResponse> machineProcessList(
                                                              @Query("needDevice")boolean needDevice,
                                                              @Query("needEmp")boolean needEmp,
                                                              @Query("needProduct")boolean needProduct,
                                                              @Query("flag")String flag
    );

    /**
     * 员工列表
     * @return
     */
    @POST("rest/emp/listAll")
    Observable<StuffListResponse> stuffList();

    /**
     * time接口
     * @param deviceId
     * @param start
     * @param end
     * @return
     */
    @POST("rest/analysis/device/time")
    Observable<DeviceRateResponse> rate(@Query("deviceId")String deviceId, @Query("start")long start, @Query("end")long end , @Query("needDeviceValues") boolean needDeviceValues);

    /**
     * time接口
     * @param deviceId
     * @param start
     * @param end
     * @return
     */
    @POST("rest/analysis/device/time")
    Observable<TimeResponse> time(@Query("deviceId")String deviceId, @Query("start")long start, @Query("end")long end , @Query("needDeviceValues") boolean needDeviceValues);

    /**
     *  quickTime接口
     * @param deviceId
     * @param start
     * @param end
     * @param needDeviceValues
     * @return
     */
    @POST("rest/analysis/device/time")
    Observable<QuickTimeResponse> quickTime(@Query("deviceId")String deviceId, @Query("start")long start, @Query("end")long end , @Query("needDeviceValues") boolean needDeviceValues);

    /**
     * time接口
     * @param deviceId
     * @param start
     * @param end
     * @return
     */
    @POST("rest/analysis/device/time")
    Observable<RunTimeSummaryResponse> timeSummary(@Query("deviceId")String deviceId, @Query("start")long start, @Query("end")long end);

    /**
     * time接口
     * @param deviceId
     * @param start
     * @param end
     * @return
     */
    @POST("rest/analysis/device/time")
    Observable<StatusListResponse> timeStatus(@Query("deviceId")String deviceId, @Query("start")long start, @Query("end")long end );






    /**
     * 获取设备状态
     * @param deviceId
     * @return
     */
    @POST("rest/monitor/device/latestStatusOne")
    Observable<LastStatusResponse> lastStatusOne(@Query("deviceId")String deviceId);

    /**
     * oee分析接口
     * @param deviceId
     * @return
     */
    @POST("rest/analysis/device/oee")
    Observable<OEEResponse> oee(@Query("deviceId")String deviceId);

    /**
     * 版本信息
     * @return
     */
    @POST("rest/common/app/version")
    Observable<UpdateInfoResponse> updateInfo();

    /**
     * 退出登录
     * @return
     */
    @POST("rest/user/logout")
    Observable<BaseResponse> logout();

    @POST("rest/analysis/device/clocks")
    Observable<DeviceClockResponse> deviceClock();

    /**
     * 获取工厂信息
     * @return
     */
    @POST("rest/setting/factory/query")
    Observable<FactoryInfoResponse> getFactory();


}
