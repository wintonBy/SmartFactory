package com.sf.smartfactory.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.R;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.contract.SplashContract;
import com.sf.smartfactory.presenter.SplashPresenter;

/**
 * @author: winton
 * @time: 2018/3/22 17:04
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 启动页
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    private final int REQ_STORAGE = 1;

    @Override
    protected void initView() {
        setContentView(R.layout.act_splash);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadFactoryInfo();
    }

    @Override
    protected SplashPresenter loadPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStoragePermission();
    }

    @Override
    public void toNext() {
        if(SPUtils.getInstance().getBoolean(Constant.SP_IS_FIRST_OPEN,true)){
            LoginActivity.start(this,null);
            SPUtils.getInstance().put(Constant.SP_IS_FIRST_OPEN,false);
        }else {
            IndexActivity.start(this,null);
        }
        finish();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtils.with(getWindow().getDecorView()).setMessage(msg).showError();
    }

    /**
     * 程序必须保证存储权限 否则无法升级
     */
    public void checkStoragePermission(){
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(storagePermission == PackageManager.PERMISSION_GRANTED){
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toNext();
                }
            },2000);
            return;
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            showError("拒绝存储权限将导致不可预知的错误！");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQ_STORAGE);
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQ_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    toNext();
                }else {
                    checkStoragePermission();
                }
                break;
        }
    }
}
