package com.example.hwork_business;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkHttpClientTest {
    public static void main(String[] args) {
        System.out.println("----------======----");
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        OkHttpClient eagerClient = okHttpClient.newBuilder().readTimeout(500, TimeUnit.MILLISECONDS).build();
//        Response response = eagerClient.newCall()
    }
}
