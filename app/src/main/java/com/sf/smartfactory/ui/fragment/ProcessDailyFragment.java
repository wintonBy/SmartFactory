package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.ProcessDailyAdapter;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.ProcessNum;
import com.sf.smartfactory.network.response.ProcessNumResponse;
import com.sf.smartfactory.view.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/8/8 21:37
 * @package: com.sf.smartfactory.ui.fragment
 * @project: Factory
 * @mail:
 * @describe: 每日加工数量
 */
public class ProcessDailyFragment extends BaseFragment{

    @BindView(R.id.rv)
    RecyclerView mRV;

    private ProcessDailyAdapter adapter;
    private List<List<ProcessNum>> dataList;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static ProcessDailyFragment newInstance(Bundle params){
        ProcessDailyFragment instance = new ProcessDailyFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_process_num_daily,null);
        ButterKnife.bind(this,rootView);
        initView();
        initData();
        return rootView;
    }

    private void initView() {
        mRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mRV.addItemDecoration(new ListItemDecoration());
    }
    private void initData() {
        dataList = new ArrayList<>();
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        RetrofitClient.getInstance().getProcessNum(1533052800000L, 1533657600000L, new BaseSubscriber<ProcessNumResponse>() {
            @Override
            public void success(ProcessNumResponse processNumResponse) {
                dataList.addAll(processNumResponse.getData().getList());
                adapter = new ProcessDailyAdapter(getContext(),dataList);
                mRV.setAdapter(adapter);
            }

            @Override
            public void failed(Throwable e) {
            }
        });
    }


}
