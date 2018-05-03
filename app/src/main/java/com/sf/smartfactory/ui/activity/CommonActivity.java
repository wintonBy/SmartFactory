package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.sf.smartfactory.R;
import com.sf.smartfactory.presenter.BasePresenter;
import com.sf.smartfactory.ui.fragment.DeveloperInfoFragment;
import com.sf.smartfactory.ui.fragment.VersionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/5/2 23:15
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 一些公共页面的Activity，布局只有title加fragment
 */
public class CommonActivity extends BaseActivity {

    public final static int FT_DEVELOP_INFO = 0x01;
    public final static int FT_VERSION_INFO = 0x02;

    @BindView(R.id.tv_title)
    TextView mTVTitle;

    private String title;
    private int fragmentType;

    /**
     *
     * @param context
     * @param title
     * @param type
     */
    public static void start(Context context, @NonNull String title, int type) {
        if (context == null) {
            throw new IllegalArgumentException("context should not null");
        }
        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.act_common);
        ButterKnife.bind(this);
        findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        title = getIntent().getStringExtra("title");
        fragmentType = getIntent().getIntExtra("type",-1);
        mTVTitle.setText(title);
        Fragment fragment = getFragment(fragmentType);
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
        }
    }


    private Fragment getFragment(int type){
        switch (type){
            case FT_DEVELOP_INFO:
                return DeveloperInfoFragment.newInstance(null);
            case FT_VERSION_INFO:
                return VersionFragment.newInstance(null);
        }
        return null;
    }

    @OnClick(R.id.iv_back)
    public void clickBack(View view){
        this.finish();
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }
}
