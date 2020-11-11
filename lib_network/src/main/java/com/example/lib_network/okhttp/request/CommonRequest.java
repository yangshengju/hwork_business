package com.example.lib_network.okhttp.request;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 对外提供get/post/文件上传请求
 */
public class CommonRequest {
    public static Request createPostRequest(String url,RequestParams params,RequestParams headers) {
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if(params!=null) {
//            params.urlParams.e
        }

        return new Request.Builder().build();
    }

}
