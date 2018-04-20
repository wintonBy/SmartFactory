package com.sf.smartfactory.presenter;

import com.sf.smartfactory.mvp.IPresenter;
import com.sf.smartfactory.mvp.IView;

import java.lang.ref.WeakReference;

/**
 * Created by winton on 2017/6/25.
 */

public class BasePresenter<V extends IView> implements IPresenter {


    private WeakReference actReference;  //activity 弱引言


    @Override
    public void attachView(IView view) {
        actReference = new WeakReference(view);
    }

    @Override
    public void detachView() {
    }

    @Override
    public V getView() {
        return (V)actReference.get();
    }

}
