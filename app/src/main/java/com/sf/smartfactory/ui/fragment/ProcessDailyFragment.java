package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.ProcessDailyAdapter;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.ProcessNum;
import com.sf.smartfactory.network.response.ProcessNumResponse;
import com.sf.smartfactory.utils.DateUtils;
import com.sf.smartfactory.view.DatePickDialog;
import com.sf.smartfactory.view.ListItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwl;
    @BindView(R.id.tv_space_time)
    TextView mTVSpaceTime;

    private ProcessDailyAdapter adapter;
    private List<List<ProcessNum>> dataList;
    private DatePickDialog mDatePickDialog;

    private long start;
    private long end;


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
        View rootView = inflater.inflate(R.layout.frag_process_num_daily,null);
        ButterKnife.bind(this,rootView);
        initView();
        initListener();
        initData();
        return rootView;
    }

    private void initListener() {
        mSwl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initView() {
        mRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mRV.addItemDecoration(new ListItemDecoration());
    }
    private void initData() {
        dataList = new ArrayList<>();
        start = DateUtils.INSTANCE.getTodayStart();
        end = System.currentTimeMillis();
        mTVSpaceTime.setText(DateUtils.INSTANCE.getDate(start)+"——"+DateUtils.INSTANCE.getDate(end));
        loadData();
    }

    @OnClick(R.id.tv_space_time)
    public void clickStart(View v){
        initDialog();
        mDatePickDialog.updateTime(start,end);
        mDatePickDialog.show(getActivity().getSupportFragmentManager(),"datePicker_daily");
    }
    /**
     *
     */
    private void initDialog(){
        if(mDatePickDialog == null){
            mDatePickDialog = new DatePickDialog();
            mDatePickDialog.setListener(new DatePickDialog.Listener() {
                @Override
                public void onDismiss(long start, long end) {
                    mTVSpaceTime.setText(DateUtils.INSTANCE.getDate(start)+"——"+DateUtils.INSTANCE.getDate(end));
                    ProcessDailyFragment.this.start = start;
                    ProcessDailyFragment.this.end = end;
                    loadData();
                }
            });
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        RetrofitClient.getInstance().getProcessNum(start, end, new BaseSubscriber<ProcessNumResponse>() {

            @Override
            public void onStart() {
                super.onStart();
                mSwl.setRefreshing(true);
            }

            @Override
            public void success(ProcessNumResponse processNumResponse) {
                mSwl.setRefreshing(false);
                dataList.clear();
                dataList.addAll(processNumResponse.getData().getList());
                adapter = new ProcessDailyAdapter(getContext(),dataList);
                mRV.setAdapter(adapter);
            }

            @Override
            public void failed(Throwable e) {
                mSwl.setRefreshing(true);
            }
        });
    }

}
