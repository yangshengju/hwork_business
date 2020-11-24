package com.example.lib_network.okhttp.response;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 处理json类型的响应
 */
public class CommonJsonCallback implements Callback {

    protected final String EMPTY_MSG="";

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
