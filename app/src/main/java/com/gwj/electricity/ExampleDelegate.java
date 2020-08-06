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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
                .url("banners")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("ggg", "json:"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

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