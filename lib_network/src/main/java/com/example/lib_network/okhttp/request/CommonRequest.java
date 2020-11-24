package com.example.lib_network.okhttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 对外提供get/post/文件上传请求
 */
public class CommonRequest {

    public static Request createPostRequest(String url,RequestParams params) {
        return createPostRequest(url,params,null);
    }

    /**
     * 对外创建post请求对象
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static Request createPostRequest(String url,RequestParams params,RequestParams headers) {
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if(params!=null) {
            for(Map.Entry<String,String> entry:params.urlParams.entrySet()) {
                mFormBodyBuilder.add(entry.getKey(),entry.getValue());

            }
        }
        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if(headers!=null) {
            for(Map.Entry<String,String> entry : headers.urlParams.entrySet()) {
                mHeadersBuilder.add(entry.getKey(),entry.getValue());
            }
        }

        Request request = new Request.Builder().
                url(url).
                headers(mHeadersBuilder.build()).
                post(mFormBodyBuilder.build())
                .build();
        return request;
    }

    /**
     * ressemble the params to the url
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {

        return createGetRequest(url, params, null);
    }

    /**
     * 对外创建get请求对象
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params, RequestParams headers) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        Headers mHeader = mHeaderBuild.build();
        return new Request.Builder().
                url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get()
                .headers(mHeader)
                .build();
    }

    public static  final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    public static Request createMultiPostRequest(String url, RequestParams params) {
        MultipartBody.Builder mMultipartBodyBuilder = new MultipartBody.Builder();
        mMultipartBodyBuilder.setType(MultipartBody.FORM);
        for(Map.Entry<String,Object> entry : params.fileParams.entrySet()) {
            if (entry.getValue() instanceof File) {
                mMultipartBodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                        RequestBody.create(FILE_TYPE, (File) entry.getValue()));
            } else if (entry.getValue() instanceof String) {

                mMultipartBodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                        RequestBody.create(null, (String) entry.getValue()));
            }
        }
        return new Request.Builder().url(url).post(mMultipartBodyBuilder.build()).build();
    }

}
