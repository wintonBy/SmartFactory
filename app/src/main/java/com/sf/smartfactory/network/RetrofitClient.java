package com.sf.smartfactory.network;

import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.sf.smartfactory.BuildConfig;
import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.network.response.DeviceListResponse;
import com.sf.smartfactory.network.response.DeviceRateResponse;
import com.sf.smartfactory.network.response.DeviceSummaryResponse;
import com.sf.smartfactory.network.response.LastStatusResponse;
import com.sf.smartfactory.network.response.LoginResponse;
import com.sf.smartfactory.network.response.MachineProcessListResponse;
import com.sf.smartfactory.network.response.OEEResponse;
import com.sf.smartfactory.network.response.OrderListResponse;
import com.sf.smartfactory.network.response.RunTimeSummaryResponse;
import com.sf.smartfactory.network.response.StatusListResponse;
import com.sf.smartfactory.network.response.StuffListResponse;
import com.sf.smartfactory.network.response.UpdateInfoResponse;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by winton on 2017/6/23.
 */

public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 15;
    private ServerApi mServer;

    public static String baseUrl = ServerApi.BASE_URL;


    private OkHttpClient mOkHttpClient;


    private static class InstanceHolder{
        private static RetrofitClient instance = new RetrofitClient();
    }

    public static RetrofitClient getInstance(){
        return InstanceHolder.instance;
    }


    private RetrofitClient(){
        this(null);
    }

    private RetrofitClient(String url){
        if(TextUtils.isEmpty(url)){
            url = baseUrl;
        }
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(MyApplication.INSTANCE));
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .cookieJar(cookieJar);
        if(BuildConfig.DEBUG){
            builder.addInterceptor(new LoggerInterceptor("OKHttp"));
        }
        mOkHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                                .client(mOkHttpClient)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .baseUrl(url)
                                .build();
        mServer = retrofit.create(ServerApi.class);
    }

    /**
     * 线程调度器
     * @return
     */
    Observable.Transformer schedulersTransForm(){
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)observable).subscribeOn(Schedulers.io())
                                                .unsubscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /****************************************接口实现*********************************************/

    public void post(String url , Map<String,String> params, Subscriber<ResponseBody> subscriber){
        mServer.executePost(url,params)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void get(String url,Map<String,String> params,Subscriber<ResponseBody> subscriber){
        mServer.executeGet(url,params)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void loginByUsername(String username, String pwd, Subscriber<LoginResponse> subscriber){
        mServer.loginByUsername(username,pwd)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void loginByPhone(String phone, String pwd, Subscriber<LoginResponse> subscriber){
        mServer.loginByPhone(phone,pwd)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void devicesList(boolean needStatus, Subscriber<DeviceListResponse> subscriber){
        mServer.deviceList(needStatus)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void monitorDeviceList(Subscriber<DeviceListResponse> subscriber){
        mServer.monitorDeviceList()
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void deviceSummary(Subscriber<DeviceSummaryResponse> subscriber){
        mServer.deviceSummary()
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void orderList(Subscriber<OrderListResponse> subscriber){
        mServer.orderList("0")
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void machineProcessList(Subscriber<MachineProcessListResponse> subscriber){
        mServer.machineProcessList(true,true,true)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void stuffList(Subscriber<StuffListResponse> subscriber){
        mServer.stuffList()
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void rate(String deviceId, long start, long end, Subscriber<DeviceRateResponse> subscriber){
        mServer.rate(deviceId,start,end,true)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void timeSummary(String deviceId, long start, long end, Subscriber<RunTimeSummaryResponse> subscriber){
        mServer.timeSummary(deviceId,start,end)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }
    public void statusList(String deviceId,long start, long end, Subscriber<StatusListResponse> subscriber ){
        mServer.timeStatus(deviceId,start,end)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void lastStatusOne(String deviceId, Subscriber<LastStatusResponse> subscriber){
        mServer.lastStatusOne(deviceId)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }
    public void oee(String deviceId, long start, long end, Subscriber<OEEResponse> subscriber){
        mServer.oee(deviceId,start,end)
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }

    public void updateInfo(Subscriber<UpdateInfoResponse> subscriber){
        mServer.updateInfo()
                .compose(schedulersTransForm())
                .subscribe(subscriber);
    }


}
