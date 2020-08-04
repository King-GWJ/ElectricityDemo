package com.gwj.latte.core.net;

import com.gwj.latte.core.net.callback.IError;
import com.gwj.latte.core.net.callback.IFailure;
import com.gwj.latte.core.net.callback.IResponse;
import com.gwj.latte.core.net.callback.ISuccess;
import com.gwj.latte.core.net.callback.RequestCallbacks;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Url;

/**
 * @Author: GWJ
 * @Date: 2020/7/31
 * @Explain:
 */
public class RestClient {

    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreatro.getParams();
    private final IResponse RESPONSE;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    public RestClient(String url,
                      Map<String, Object> params,
                      IResponse response,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.RESPONSE = response;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreatro.getRestService();
        Call<ResponseBody> call = null;
        if (RESPONSE != null) {
            RESPONSE.onResponseStart();
        }

        switch (method) {
            case GET:
                call=service.get(URL,PARAMS);
                break;
            case POST:
                call=service.post(URL,PARAMS);
                break;
            case PUT:
                call=service.put(URL,PARAMS);
                break;
            case DELETE:
                call=service.delete(URL,PARAMS);
                break;
            default:
                break;
        }

        if(call!=null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<ResponseBody> getRequestCallback(){
        return new RequestCallbacks(RESPONSE, SUCCESS, FAILURE, ERROR);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        request(HttpMethod.POST);
    }

    public final void put(){
        request(HttpMethod.PUT);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }


}
