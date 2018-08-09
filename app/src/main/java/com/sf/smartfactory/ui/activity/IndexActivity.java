package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.IndexViewPagerAdapter;
import com.sf.smartfactory.contract.IndexContract;
import com.sf.smartfactory.network.bean.UpdateInfo;
import com.sf.smartfactory.presenter.IndexPresenter;
import com.sf.smartfactory.service.UpdateDataService;
import com.sf.smartfactory.ui.fragment.FactoryFragment;
import com.sf.smartfactory.ui.fragment.HomeFragment;
import com.sf.smartfactory.ui.fragment.OrderFragment;
import com.sf.smartfactory.ui.fragment.ProcessDailyFragment;
import com.sf.smartfactory.ui.fragment.UpdateApkDialog;
import com.sf.smartfactory.ui.fragment.UserFragment;
import com.sf.smartfactory.view.DiyScrollViewPager;
import com.winton.bottomnavigationview.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/3/22 20:33
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 首页页面
 */
public class IndexActivity extends BaseActivity<IndexPresenter> implements IndexContract.View {

    @BindView(R.id.nv_bottom)
    NavigationView mNV;

    @BindView(R.id.vp_content)
    DiyScrollViewPager mVP;
    private List<Fragment> mFragments;
    private IndexViewPagerAdapter mAdapter;

    private UpdateApkDialog updateFragment;


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
        Intent intent = new Intent(context, IndexActivity.class);
        if (params != null) {
            intent.putExtras(params);
        }
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.act_index);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);
        initBottomNavigation();
        mVP.setCanScroll(false);
        mVP.setOffscreenPageLimit(4);
        startUpdateService();
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNavigation() {

        List<NavigationView.Model> nvItems = new ArrayList<>();
        nvItems.add(new NavigationView.Model.Builder(R.mipmap.ic_home,R.mipmap.ic_home_uncheck).title(getResources().getString(R.string.home)).build());
        nvItems.add(new NavigationView.Model.Builder(R.mipmap.ic_factory,R.mipmap.ic_factory_uncheck).title(getResources().getString(R.string.machine_info)).build());
        nvItems.add(new NavigationView.Model.Builder(R.mipmap.ic_order,R.mipmap.ic_order_uncheck).title(getResources().getString(R.string.order_manager)).build());
        nvItems.add(new NavigationView.Model.Builder(R.mipmap.ic_me,R.mipmap.ic_me_unchek).title(getResources().getString(R.string.me)).build());

        mNV.setItems(nvItems);
        mNV.build();
        mNV.check(0);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mNV.setOnTabSelectedListener(new NavigationView.OnTabSelectedListener() {
            @Override
            public void selected(int index, NavigationView.Model model) {
                String title = model.getTitle();
                mVP.setCurrentItem(index,false);
            }

            @Override
            public void unselected(int index, NavigationView.Model model) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initFragments();
        mAdapter = new IndexViewPagerAdapter(getSupportFragmentManager(),mFragments);
        mVP.setAdapter(mAdapter);
        mPresenter.checkVersion();

    }
    private void initFragments(){
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance(null));
        mFragments.add(FactoryFragment.newInstance(null));
        mFragments.add(OrderFragment.newInstance(null));
        mFragments.add(new ProcessDailyFragment());
    }

    @Override
    protected IndexPresenter loadPresenter(){
            return new IndexPresenter();
    }

    @Override
    protected void onDestroy() {
        releaseResource();
        super.onDestroy();
    }

    @Override
    public void showNewVersion(UpdateInfo info) {
        if(updateFragment == null){
            String title = "新版本";
            String url = info.getUrl();
            String sInfo = info.getInfo();
            int force = info.getForce();
            Bundle param = new Bundle();
            param.putString("title",title);
            param.putString("downloadUrl",url);
            param.putString("info",sInfo);
            param.putInt("force",force);
            updateFragment = UpdateApkDialog.createInstance(param);
        }
        updateFragment.show(getSupportFragmentManager(),"update");
    }

    /**
     * 释放资源
     */
    private void releaseResource(){
        Intent intent = new Intent(this, UpdateDataService.class);
        stopService(intent);
    }

    /**
     * 启动数据更新服务
     */
    private void startUpdateService(){
        Intent intent = new Intent(this, UpdateDataService.class);
        startService(intent);
    }
}
