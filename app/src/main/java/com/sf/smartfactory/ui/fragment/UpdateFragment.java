package com.sf.smartfactory.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.R;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.utils.DeviceUtils;
import com.sf.smartfactory.utils.DrawableUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CloseUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: winton
 * @time: 2018/4/25 11:17
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 升级提示
 */
public class UpdateFragment extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_info)
    TextView mInfo;
    @BindView(R.id.pb_download)
    ProgressBar mDownload;
    @BindView(R.id.ll_bt)
    LinearLayout mBtLayout;
    @BindView(R.id.bt_cancel)
    Button mBtCancel;

    private Bundle startParams;
    private String info;
    private String downloadUrl;
    private String title;
    private boolean force;
    private String filePath;


    public static UpdateFragment createInstance(@NonNull Bundle param){
        if(param == null){
            throw new IllegalArgumentException("param should not be null");
        }
        UpdateFragment instance = new UpdateFragment();
        instance.setArguments(param);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme =android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style,theme);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_update_dialog,null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        startParams = getArguments();
        title = startParams.getString("title");
        downloadUrl = startParams.getString("downloadUrl");
        info = startParams.getString("info");
        force = startParams.getInt("force",-1) == 0? true : false;
        if(force){
            mBtCancel.setVisibility(View.GONE);
        }
        mBtCancel.setBackground(DrawableUtils.INSTANCE.changeDrawableColor(getActivity(),R.drawable.shape_button_login, android.R.color.darker_gray));
        setTitle(title);
        setInfo(info);
    }
    private void setTitle(String title){
        if(mTitle != null && title != null){
            mTitle.setText(title);
        }
    }
    private void setInfo(String info){
        if(mInfo != null && info != null){
            mInfo.setText(info);
        }
    }

    @OnClick(R.id.bt_cancel)
    public void clickCancel(View view){
        this.dismiss();
    }

    @OnClick(R.id.bt_update)
    public void clickUpdate(View view){
        downloadApk(downloadUrl);
    }

    /**
     * 下载APK
     * @param url
     */
    private void downloadApk(String url){
        if(ObjectUtils.isEmpty(url)){
            ToastUtils.showLong("下载地址为空！！！");
            return;
        }
        MyDownloadTask downloadTask = new MyDownloadTask();
        downloadTask.execute(url);

    }

    private class MyDownloadTask extends AsyncTask<String,Integer,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBtLayout.setVisibility(View.GONE);
            mDownload.setVisibility(View.VISIBLE);
            filePath = MyApplication.INSTANCE.getExternalCacheDir().getPath()+File.separator+ Constant.APK_DOWNLOAD_NAME;
            LogUtils.dTag("filePtah",filePath);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mDownload.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(String... strings) {
            download(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            UpdateFragment.this.dismiss();
            AppUtils.installApp(filePath);
        }
        private int computerPercent(float all,float over){
            int percent = 0;
            if(all != 0){
                percent = (int)(over / all * 100);
            }
            return percent;
        }

        /**
         * 下载
         * @param downloadUrl
         */
        private void download(String downloadUrl){
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(30 * 1000);
                con.setReadTimeout(30 * 1000);
                long length = con.getContentLength();
                is = con.getInputStream();
                FileUtils.createOrExistsFile(filePath);
                fos = new FileOutputStream(filePath);
                byte[] buffer = new byte[2048];
                int readLen = 0;
                int overLen = 0;
                while ((readLen =is.read(buffer)) > 0){
                    fos.write(buffer,0,readLen);
                    overLen += readLen;
                    LogUtils.dTag("UpdateFragment","文件写入长度："+overLen);
                    publishProgress(computerPercent(length,overLen));
                }
                fos.flush();

            }catch (Exception e){
                LogUtils.dTag("UpdateFragment",e);
            }finally {
                CloseUtils.closeIO(is,fos);
            }
        }


    }

}
