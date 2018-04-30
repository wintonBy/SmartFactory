package com.sf.smartfactory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.sf.smartfactory.R;
import com.sf.smartfactory.network.bean.UpdateInfo;
import com.sf.smartfactory.service.UpdateDataService;
import com.sf.smartfactory.adapter.IndexViewPagerAdapter;
import com.sf.smartfactory.contract.IndexContract;
import com.sf.smartfactory.presenter.IndexPresenter;
import com.sf.smartfactory.ui.fragment.FactoryFragment;
import com.sf.smartfactory.ui.fragment.HomeFragment;
import com.sf.smartfactory.ui.fragment.OrderFragment;
import com.sf.smartfactory.ui.fragment.UpdateFragment;
import com.sf.smartfactory.ui.fragment.UserFragment;
import com.sf.smartfactory.view.DiyScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

/**
 * @author: winton
 * @time: 2018/3/22 20:33
 * @package: com.sf.smartfactory.ui.activity
 * @project: SmartFactory
 * @mail:
 * @describe: 首页页面
 */
public class IndexActivity extends BaseActivity<IndexPresenter> implements IndexContract.View {


    @BindView(R.id.ntb)
    NavigationTabBar mNTB;
    @BindView(R.id.vp_content)
    DiyScrollViewPager mVP;
    @BindView(R.id.tv_title)
    TextView mTVTitle;
    private List<Fragment> mFragments;
    private IndexViewPagerAdapter mAdapter;

    private UpdateFragment updateFragment;


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
        ButterKnife.bind(this);
        initBottomNavigation();
        mVP.setCanScroll(false);
        startUpdateService();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNavigation() {
        final List<NavigationTabBar.Model> tabs = new ArrayList<>();
        tabs.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_home),
                        R.color.colorAccent)
                        .title(getResources().getString(R.string.home))
                        .build());
        tabs.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_factory),
                        R.color.colorAccent)
                        .title(getResources().getString(R.string.machine_info))
                        .build());

//        tabs.add(
//                new NavigationTabBar.Model.Builder(
//                        getResources().getDrawable(R.mipmap.ic_stuff),
//                        R.color.colorAccent)
//                        .title(getResources().getString(R.string.stuff_info))
//                        .build());

        tabs.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.mipmap.ic_order),
                R.color.colorAccent)
                .title(getResources().getString(R.string.order_manager))
                .build());

        tabs.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.mipmap.ic_me),
                R.color.colorAccent)
                .title(getResources().getString(R.string.me))
                .build());
        mNTB.setModels(tabs);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTVTitle.setText(R.string.home);
        mNTB.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                String title = model.getTitle();
                mTVTitle.setText(title);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initFragments();
        mAdapter = new IndexViewPagerAdapter(getSupportFragmentManager(),mFragments);
        mVP.setAdapter(mAdapter);
        mNTB.setViewPager(mVP);
        mPresenter.checkVersion();

    }
    private void initFragments(){
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance(null));
        mFragments.add(FactoryFragment.newInstance(null));
//        mFragments.add(StuffFragment.newInstance(null));
        mFragments.add(OrderFragment.newInstance(null));
        mFragments.add(UserFragment.newInstance(null));
    }

    @Override
    protected IndexPresenter loadPresenter() {
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
            updateFragment = UpdateFragment.createInstance(param);
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
