package com.example.lib_network.okhttp.listener;

public class DisposeDataHandler {
    private DisposeDataListener mListener;
    private Class<?> mClass;
    private String mSource;//文件下载保存路径

    public DisposeDataHandler(DisposeDataListener mListener) {
        this.mListener = mListener;
    }

    public DisposeDataHandler(DisposeDataListener mListener, Class<?> mClass) {
        this.mListener = mListener;
        this.mClass = mClass;
    }

    public DisposeDataHandler(DisposeDataListener mListener, Class<?> mClass, String mSource) {
        this.mListener = mListener;
        this.mClass = mClass;
        this.mSource = mSource;
    }

    public DisposeDataListener getmListener() {
        return mListener;
    }

    public Class<?> getmClass() {
        return mClass;
    }

    public String getmSource() {
        return mSource;
    }
}
