package com.sf.smartfactory.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.MachineProcessAdapter;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.MachineProcess;
import com.sf.smartfactory.network.response.MachineProcessListResponse;
import com.sf.smartfactory.view.stateview.StateView;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/3/27 17:55
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 加工信息页
 */
public class FactoryFragment extends BaseFragment{

    private View rootView;
    @BindView(R.id.rv)
    RecyclerView mRV;
    private StateView mStateView;

    private List<MachineProcess> mProcessList;
    private MachineProcessAdapter mAdapter;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static FactoryFragment newInstance(Bundle params){
        FactoryFragment instance = new FactoryFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_machine,null);
        ButterKnife.bind(this,rootView);
        mStateView = StateView.inject(mRV);
        initView();
        initListener();
        initData();
        return rootView;
    }

    private void initView(){
    }

    private void initData(){
        mProcessList = new ArrayList<>();
        mAdapter = new MachineProcessAdapter(getActivity(),mProcessList);
        mRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRV.setAdapter(mAdapter);
        loadList();
    }
    private void initListener(){
       mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
           @Override
           public void onRetryClick() {
               loadList();
           }
       });
    }

    /**
     * 获取加工信息
     */
    private void loadList(){
        RetrofitClient.getInstance().machineProcessList(new BaseSubscriber<MachineProcessListResponse>(){
            @Override
            public void onNext(MachineProcessListResponse machineProcessListResponse) {
                super.onNext(machineProcessListResponse);
                if( !ObjectUtils.isEmpty(machineProcessListResponse)
                        && machineProcessListResponse.isSuccess()
                        && !ObjectUtils.isEmpty(machineProcessListResponse.getData())){
                    setProcessList(machineProcessListResponse.getData().getList());
                    return;
                }
                ToastUtils.showLong("数据异常");
                mStateView.showRetry();
                return;

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
    private void setProcessList(List<MachineProcess> list){
        mProcessList.clear();
        if(list == null){
            mStateView.showRetry();
            return;
        }
        if(list.isEmpty()){
            mStateView.showEmpty();
            return;
        }
        mProcessList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UpdateDataEvent event){
        loadList();
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
