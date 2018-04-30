package com.sf.smartfactory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.smartfactory.R;
import com.sf.smartfactory.adapter.StuffListAdapter;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.Stuff;
import com.sf.smartfactory.network.response.StuffListResponse;
import com.sf.smartfactory.view.stateview.StateView;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/3/27 17:57
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 员工信息页
 */
public class StuffFragment extends BaseFragment {

    @BindView(R.id.rv_stuff)
    RecyclerView mRV;
    private StateView mStateView;

    private List<Stuff> mStuffs;
    private StuffListAdapter mAdapter;


    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static StuffFragment newInstance(Bundle params){
        StuffFragment instance = new StuffFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_stuff,null);
        ButterKnife.bind(this,rootView);
        mStateView = StateView.inject(mRV);
        initListener();
        initData();
        return rootView;
    }

    private void initListener(){
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                loadStuffList();
            }
        });
    }

    private void initData(){
        mStuffs = new ArrayList<>();
        mAdapter = new StuffListAdapter(getActivity(),mStuffs);
        mRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRV.setAdapter(mAdapter);
        loadStuffList();
    }

    private void loadStuffList(){
        RetrofitClient.getInstance().stuffList(new BaseSubscriber<StuffListResponse>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(StuffListResponse stuffListResponse) {
                super.onNext(stuffListResponse);
                if(!ObjectUtils.isEmpty(stuffListResponse)
                        && stuffListResponse.isSuccess()
                        && stuffListResponse.getData() !=null){
                    setStuffList(stuffListResponse.getData().getList());
                    return;
                }
                ToastUtils.showLong("数据异常");
                mStateView.showRetry();
                return;
            }
        });
    }
    private void setStuffList(List<Stuff> list){
        mStuffs.clear();
        if(list == null){
            mStateView.showRetry();
            return;
        }
        if(list == null){
            mStateView.showEmpty();
            return;
        }
        mStuffs.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
