package com.gwj.latte.core.net.callback;

/**
 * @Author: GWJ
 * @Date: 2020/8/3
 * @Explain:
 */
public interface IError {
    void onError(int code, String msg);
}
