package com.example.lib_network.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.example.lib_network.okhttp.listener.DisposeDataHandler;
import com.example.lib_network.okhttp.listener.DisposeDownloadListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonFileCallback implements Callback {

    private final int NETWORK_ERROR = -1;
    private final int IO_ERROR = -2;
    private final String EMPTY_MSG = "";

    /**
     * 将其它线程的数据转发到UI线程
     */
    private static final int PROGRESS_MESSAGE = 0x01;
    private Handler mDeliveryHandler;
    private DisposeDownloadListener mListener;

    private String mFilePath;
    private String mProgress;

    public CommonFileCallback(DisposeDataHandler disposeDataHandler) {
        this.mListener = (DisposeDownloadListener) disposeDataHandler.getmListener();
        this.mFilePath = disposeDataHandler.getmSource();
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
