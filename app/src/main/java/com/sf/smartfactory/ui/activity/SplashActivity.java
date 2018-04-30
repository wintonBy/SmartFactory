package com.sf.smartfactory.ui.activity;

import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.R;
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

    @Override
    protected void initView() {
        setContentView(R.layout.act_splash);
        MyApplication.permissionInstance.request();
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected SplashPresenter loadPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                toNext();
            }
        },2000);
    }

    @Override
    public void toNext() {
        LoginActivity.start(this,null);
        finish();
    }


}
