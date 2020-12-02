package com.example.lib_network.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.lib_network.okhttp.exception.OkHttpException;
import com.example.lib_network.okhttp.listener.DisposeDataHandler;
import com.example.lib_network.okhttp.listener.DisposeDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    private long mProgress;

    public CommonFileCallback(DisposeDataHandler disposeDataHandler) {
        this.mListener = (DisposeDownloadListener) disposeDataHandler.getmListener();
        this.mFilePath = disposeDataHandler.getmSource();
        mDeliveryHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case PROGRESS_MESSAGE:
                    mListener.onProgress((int)msg.obj);
                    break;
                }
            }
        };
    }

    @Override
    public void onFailure(Call call, IOException e) {
        mDeliveryHandler.post(()->{
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final File file = handleResponse(response);
        mDeliveryHandler.post(() -> {
                if (file != null) {
                    mListener.onSuccess(file);
                } else {
                    mListener.onFailure(new OkHttpException(IO_ERROR, EMPTY_MSG));
                }
        });
    }

    /**
     * 处理文件类型的响应
     * @param response
     * @return
     */
    private File handleResponse(Response response) {
        File file = null;
        if(response!=null) {
            InputStream is = null;
            FileOutputStream fos = null;
            byte[] buffer = new byte[2048];
            int length;
            int currentLength = 0;
            long sumLength;
            checkLocalFilePath(mFilePath);
            file = new File(mFilePath);
            try {
                fos = new FileOutputStream(file);
                is = response.body().byteStream();
                sumLength = response.body().contentLength();
                while ((length=is.read(buffer))!=-1) {
                    currentLength+=length;
                    mProgress = currentLength/sumLength*100;
                    mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE,mProgress).sendToTarget();
                }
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                file = null;
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {

                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    file = null;
                }
            }
        }
        return file;
    }

    private void checkLocalFilePath(String mFilePath) {
        File path =new File(mFilePath.substring(0,mFilePath.lastIndexOf(File.separator)+1));
        File file = new File(mFilePath);
        if(path.exists()) {
            path.mkdirs();
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
