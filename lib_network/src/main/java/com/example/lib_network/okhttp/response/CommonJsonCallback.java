package com.example.lib_network.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import com.example.lib_network.okhttp.exception.OkHttpException;
import com.example.lib_network.okhttp.listener.DisposeDataHandler;
import com.example.lib_network.okhttp.listener.DisposeDataListener;
import com.google.gson.Gson;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 处理json类型的响应，一次响应的回调
 */
public class CommonJsonCallback implements Callback {

    protected final String EMPTY_MSG="";

    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private Class<?> mClass;

    private Handler mDeliveryHandler;

    private DisposeDataListener mListener;

    public CommonJsonCallback(DisposeDataHandler disposeDataHandler) {
        this.mClass = disposeDataHandler.getmClass();
        this.mListener = disposeDataHandler.getmListener();
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(()->{
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(()->handleResponse(result));
    }

    private void handleResponse(String result) {
        if(StringUtils.isBlank(result)) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
            return;
        }

        try {
            if(mClass==null) {//业务层希望接收到原始的响应数据
                mListener.onSuccess(result);
            } else {
                Object resovedObj=new Gson().fromJson(result,mClass);
                if(ObjectUtils.isNotEmpty(resovedObj)) {
                    mListener.onSuccess(resovedObj);
                } else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                }
            }
        } catch (Exception e) {
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }

}
