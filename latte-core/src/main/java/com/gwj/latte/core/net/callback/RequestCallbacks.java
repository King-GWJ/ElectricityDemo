package com.gwj.latte.core.net.callback;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: GWJ
 * @Date: 2020/8/3
 * @Explain:
 */
public class RequestCallbacks implements Callback<ResponseBody> {
    private final IRequest RESPONSE;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public RequestCallbacks(IRequest response, ISuccess success, IFailure failure, IError error) {
        this.RESPONSE = response;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {//成功
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    try {
                        SUCCESS.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {//失败
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }


    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (RESPONSE != null) {
            RESPONSE.onRequestEnd();
        }
    }


}
