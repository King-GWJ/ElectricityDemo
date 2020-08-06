package com.gwj.latte.core.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Author: GWJ
 * @Date: 2020/7/31
 * @Explain:
 */
public interface RestService {
    @GET
    Call<ResponseBody> get(@Url String url,@QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> post(@Url String url, @QueryMap Map<String, Object> params);

    @POST
    Call<ResponseBody> postRam(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @GET
    Call<ResponseBody> put(@Url String url, @QueryMap Map<String, Object> params);

    @POST
    Call<ResponseBody> putRam(@Url String url, @Body RequestBody body);


    @DELETE
    Call<ResponseBody> delete(@Url String url, @QueryMap Map<String, Object> params);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    @Multipart
    @POST
    Call<ResponseBody> upload(@Url String url, @Part MultipartBody.Part file);
}
