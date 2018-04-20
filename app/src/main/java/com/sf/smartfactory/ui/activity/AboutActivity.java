package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.smartfactory.BuildConfig;
import com.sf.smartfactory.R;
import com.sf.smartfactory.contract.AboutContract;
import com.sf.smartfactory.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/3/24 12:09
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 关于页面
 */
public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {

    /**
     * 启动首页的方法
     *
     * @param context
     * @param params
     */
    public static void start(Context context, Bundle params) {
        if (context == null) {
            throw new IllegalArgumentException("context should not null");
        }
        Intent intent = new Intent(context, AboutActivity.class);
        if (params != null) {
            intent.putExtras(params);
        }
        context.startActivity(intent);
    }

    @BindView(R.id.tv_app_version)
    TextView tvVersion;
    @BindView(R.id.tv_title)
    TextView mTVTitle;

    @Override
    protected void initView() {
        setContentView(R.layout.act_about);
        ButterKnife.bind(this);
        findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        mTVTitle.setText(R.string.about);
        tvVersion.setText(String.format(getResources().getString(R.string.version_p), BuildConfig.VERSION_NAME));
    }

    @OnClick(R.id.iv_back)
    public void clickBack(View view){
        this.finish();
    }

    @Override
    protected AboutPresenter loadPresenter() {
        return new AboutPresenter();
    }



}
