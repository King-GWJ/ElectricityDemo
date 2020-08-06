package com.gwj.latte.core.net;

import com.gwj.latte.core.net.callback.IError;
import com.gwj.latte.core.net.callback.IFailure;
import com.gwj.latte.core.net.callback.IResponse;
import com.gwj.latte.core.net.callback.ISuccess;
import com.gwj.latte.core.net.callback.RequestCallbacks;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final File FILE;

    public RestClient(String url,
                      Map<String, Object> params,
                      IResponse response,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.RESPONSE = response;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
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
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAM:
                call = service.postRam(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAM:
                call = service.putRam(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part file = MultipartBody.Part.createFormData("file", FILE.getName(),requestBody);
                call=RestCreatro.getRestService().upload(URL, file);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<ResponseBody> getRequestCallback() {
        return new RequestCallbacks(RESPONSE, SUCCESS, FAILURE, ERROR);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAM);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAM);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }


}
