package com.gwj.latte.core.net.download;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.gwj.latte.core.net.RestCreatro;
import com.gwj.latte.core.net.callback.IError;
import com.gwj.latte.core.net.callback.IFailure;
import com.gwj.latte.core.net.callback.IRequest;
import com.gwj.latte.core.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: GWJ
 * @Date: 2020/8/6
 * @Explain:
 */
public class DownloadHandler {

    private final String url;
    private static final Map<String, Object> params = RestCreatro.getParams();
    private final IRequest iRequest;
    private final ISuccess iSuccess;
    private final IFailure iFailure;
    private final IError iError;
    private final String download_dir;
    private final String extension;
    private final String name;

    public DownloadHandler(String url, IRequest iRequest, ISuccess iSuccess, IFailure iFailure, IError iError, String download_dir, String extension, String name) {
        this.url = url;
        this.iRequest = iRequest;
        this.iSuccess = iSuccess;
        this.iFailure = iFailure;
        this.iError = iError;
        this.download_dir = download_dir;
        this.extension = extension;
        this.name = name;
    }

    public final void handlerDownload(){
        if(iRequest!=null){
            iRequest.onRequestStart();
        }
        RestCreatro.getRestService().download(url,params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    final ResponseBody responseBody = response.body();
                    final SaveFileTask task = new SaveFileTask(iRequest, iSuccess);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                            download_dir, extension, responseBody, name);

                    //这里一定要注意判断，否则文件下载不全
                    if (task.isCancelled()) {
                        if (iRequest != null) {
                            iRequest.onRequestEnd();
                        }
                    }
                } else {
                    if (iError != null) {
                        iError.onError(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if (iFailure != null) {
                    iFailure.onFailure();
                }
            }
        });

    }

}
