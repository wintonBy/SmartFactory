package com.sf.smartfactory.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sf.smartfactory.BuildConfig;
import com.sf.smartfactory.R;
import com.sf.smartfactory.view.DialogEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: winton
 * @time: 2018/5/2 23:54
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class VersionFragment extends BaseFragment {

    @BindView(R.id.tv_app_version)
    TextView tvVersion;

    private DialogEx mDialog = null;

    /**
     * 获取该类的实例
     * @param params
     * @return
     */
    public static VersionFragment newInstance(Bundle params){
        VersionFragment instance = new VersionFragment();
        if(params != null){
            instance.setArguments(params);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_version_info,null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }
    protected void initData() {
        tvVersion.setText(String.format(getResources().getString(R.string.version_p), BuildConfig.VERSION_NAME));
    }

    @OnClick(R.id.tv_call)
    public void clickCall(View view){
        if (mDialog != null) {
            mDialog.show();
            return;
        }
        mDialog = new DialogEx(getActivity(), getString(R.string.call_service), R.string.confirm,
                R.string.cancel) {
            @Override
            public void confirm() {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+getString(R.string.call_num)));
                startActivity(intent);
                cancel();
            }
        };

    }
}
