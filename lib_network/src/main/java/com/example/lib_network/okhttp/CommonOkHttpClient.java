package com.example.lib_network.okhttp;

import com.example.lib_network.okhttp.cookie.SimpleCookieJar;
import com.example.lib_network.okhttp.https.HttpsUtils;
import com.example.lib_network.okhttp.listener.DisposeDataHandler;
import com.example.lib_network.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 用来发送GET、POST请求的工具类，包括设置一些请求的公共参数
 */
public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkhttpclient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClientBuilder.addInterceptor((chain)->{
            Request request = chain.request().newBuilder().addHeader("User-Agent","Hwork-mobile")//标明发送本次请求的客户端
                    .build();
            return chain.proceed(request);
        });

        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(),HttpsUtils.initTrustManangers());
        mOkhttpclient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getmOkhttpclient() {
        return mOkhttpclient;
    }

    public static Call get(Request request, DisposeDataHandler handler) {
        Call call =mOkhttpclient.newCall(request);
        call.enqueue(new CommonJsonCallback(handler));
        return call;
    }
}
