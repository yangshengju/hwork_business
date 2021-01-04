package com.example.lib_network.okhttp.https;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class HttpsUtils {
    public static SSLSocketFactory initSSLSocketFactory() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            X509TrustManager[] x509TrustManagers = new X509TrustManager[] {
                    initTrustManangers()
            };

            sslContext.init(null,x509TrustManagers,new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }

    public static X509TrustManager initTrustManangers() {
        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return  x509TrustManager;
    }
}
