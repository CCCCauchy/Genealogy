package com.oraro.genealogy.mvp.listener;

/**
 * Created by XY on 2016/9/17.
 */
public interface OnLoadDataListener<T> {
    void onSuccess(T data);
    void onFailure(Throwable e);
}
