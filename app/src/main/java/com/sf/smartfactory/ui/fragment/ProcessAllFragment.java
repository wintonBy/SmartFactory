package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.MachineProcessAdapter;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.MachineProcess;
import com.sf.smartfactory.network.response.MachineProcessListResponse;
import com.sf.smartfactory.view.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/8/9 23:44
 * @package: com.sf.smartfactory.ui.fragment
 * @project: Factory
 * @mail:
 * @describe: 加工数量总计
 */
public class ProcessAllFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView mRVOrderNumList;
    @BindView(R.id.tv_current)
    TextView mTVCurrent;
    @BindView(R.id.tv_week)
    TextView mTVWeek;
    @BindView(R.id.tv_month)
    TextView mTVMonth;

    private String timeFlag;
    private View timeCheckView;

    private StateView mSVOrderNumber;
    private List<MachineProcess> mProcessList;
    private MachineProcessAdapter mProcessAdapter;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static ProcessAllFragment newInstance(Bundle params){
        ProcessAllFragment instance = new ProcessAllFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.frag_process_number,container,false);
        ButterKnife.bind(this,rootView);
        mSVOrderNumber = StateView.inject(mRVOrderNumList);
        initListener();
        initData();
        return rootView;
    }

    private void initData() {
        mProcessList = new ArrayList<>();
        mProcessAdapter = new MachineProcessAdapter(getActivity(),mProcessList);
        mRVOrderNumList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVOrderNumList.setAdapter(mProcessAdapter);
    }

    private void initListener() {
        mSVOrderNumber.setOnRetryClickListener(new StateView.OnRetryClickListener() {

            @Override
            public void onRetryClick() {
                loadProcessList();
            }
        });
        checkCurrent();
    }

    @OnClick(R.id.tv_current)
    public void clickCurrent(View v){
        if(timeCheckView != v){
            checkCurrent();
        }
    }

    @OnClick(R.id.tv_week)
    public void clickWeek(View v){
        if(timeCheckView != v){
            checkWeek();
        }
    }
    @OnClick(R.id.tv_month)
    public void clickMonth(View v){
        if(timeCheckView != v){
            checkMonth();
        }
    }

    /**
     * 加载加工信息
     */
    private void loadProcessList(){
        RetrofitClient.getInstance().machineProcessList(timeFlag,new BaseSubscriber<MachineProcessListResponse>(){

            @Override
            public void success(MachineProcessListResponse machineProcessListResponse) {
                if(!ObjectUtils.isEmpty(machineProcessListResponse.getData())){
                    setProcessList(machineProcessListResponse.getData().getList());
                    return;
                }
                ToastUtils.showLong("数据异常");
                mSVOrderNumber.showRetry();
                return;
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }

    /**
     * 选中当前班次
     */
    private void checkCurrent(){
        timeCheckView = mTVCurrent;
        mTVCurrent.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        mTVMonth.setTextColor(ContextCompat.getColor(getActivity(),R.color.black30));
        mTVWeek.setTextColor(ContextCompat.getColor(getActivity(),R.color.black30));
        timeFlag = "";
        loadProcessList();
    }
    /**
     * 选中本周
     */
    private void checkWeek(){
        timeCheckView = mTVWeek;
        mTVWeek.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        mTVMonth.setTextColor(ContextCompat.getColor(getActivity(),R.color.black30));
        mTVCurrent.setTextColor(ContextCompat.getColor(getActivity(),R.color.black30));
        timeFlag = "one_week";
        loadProcessList();
    }
    /**
     * 选中本月
     */
    private void checkMonth(){
        timeCheckView = mTVMonth;
        mTVMonth.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        mTVCurrent.setTextColor(ContextCompat.getColor(getActivity(),R.color.black30));
        mTVWeek.setTextColor(ContextCompat.getColor(getActivity(),R.color.black30));
        timeFlag = "this_month";
        loadProcessList();
    }

    /**
     * 设置数据的进度
     * @param list
     */
    private void setProcessList(List<MachineProcess> list){
        mProcessList.clear();
        if(list == null){
            mSVOrderNumber.showRetry();
            return;
        }
        if(list.isEmpty()){
            mSVOrderNumber.showEmpty();
            return;
        }
        mSVOrderNumber.showContent();
        mProcessList.addAll(list);
        mProcessAdapter.notifyDataSetChanged();
    }

}
