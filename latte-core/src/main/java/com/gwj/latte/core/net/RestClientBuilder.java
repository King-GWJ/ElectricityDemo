package com.gwj.latte.core.net;

import com.gwj.latte.core.net.callback.IError;
import com.gwj.latte.core.net.callback.IFailure;
import com.gwj.latte.core.net.callback.IResponse;
import com.gwj.latte.core.net.callback.ISuccess;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Author: GWJ
 * @Date: 2020/8/3
 * @Explain:
 */
public class RestClientBuilder {

    private String mUrl;
    private static final Map<String, Object> mParams = RestCreatro.getParams();
    private IResponse mIResponse;
    private ISuccess mISuccess;
    private IFailure mIFailure;
    private IError mIError;
    private RequestBody mBody;
    private File mFile;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile=file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile=new File(file);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onResponse(IResponse response) {
        this.mIResponse = response;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mISuccess = success;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        this.mIFailure = failure;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mIError = error;
        return this;
    }

    private Map<String, Object> checkParams() {
        if (mParams == null) {
            return new WeakHashMap<>();
        }
        return mParams;
    }

    public final RestClient build() {
        return new RestClient(mUrl, mParams, mIResponse, mISuccess, mIFailure, mIError, mBody,mFile);
    }


}
