package com.example.lib_network.okhttp.cookie;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SimpleCookieJar implements CookieJar {
    private final List<Cookie> allCookies = new ArrayList<>();
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        allCookies.addAll(cookies);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> result = new ArrayList<>();
        allCookies.forEach((Cookie cookie) ->{
            if (cookie.matches(url)) {
                result.add(cookie);
            }
        });
        return result;
    }
}
