package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sf.smartfactory.contract.IndexContract;
import com.sf.smartfactory.presenter.IndexPresenter;

/**
 * @author: winton
 * @time: 2018/3/22 20:33
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 首页页面
 */
public class IndexActivity extends BaseActivity<IndexPresenter> implements IndexContract.View {
    /**
     * 启动首页的方法
     * @param context
     * @param params
     */
    public static void start(Context context, Bundle params){
        if(context == null){
            throw new IllegalArgumentException("context should not null");
        }
        Intent intent = new Intent(context,IndexActivity.class);
        if(params != null){
            intent.putExtras(params);
        }
        context.startActivity(intent);
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
                    super.initData();
    }

    @Override
    protected IndexPresenter loadPresenter() {
        return null;
    }
}
