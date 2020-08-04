package com.gwj.electricity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.gwj.latte.core.delegates.LatteDelegate;
import com.gwj.latte.core.net.RestClient;
import com.gwj.latte.core.net.callback.IError;
import com.gwj.latte.core.net.callback.IFailure;
import com.gwj.latte.core.net.callback.ISuccess;


public class ExampleDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_exalple;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        textRestClient();
    }

    private void textRestClient() {
        RestClient.builder()
                .url("article/list/0/json")
                .params("cid", "60")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("ggg", "json:"+response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure(String msg) {
                        Log.d("ggg", "failure:"+msg);

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.d("ggg", "error:"+msg+","+code);

                    }
                })
                .build()
                .get();
    }

}