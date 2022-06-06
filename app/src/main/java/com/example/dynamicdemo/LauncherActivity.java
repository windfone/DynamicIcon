package com.example.dynamicdemo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class LauncherActivity extends AppCompatActivity {

    private ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        new File(getExternalCacheDir().getAbsolutePath());

        urls.add("https://zw.czbanbantong.com/national_appnass/v1/appshow/1645772611506");
        urls.add("https://zw.czbanbantong.com/national_appnass/v1/appshow/1652945167879");
        urls.add("https://zw.czbanbantong.com/national_appnass/v1/appshow/1653015210411");
        urls.add("https://ncp.czbanbantong.com/appnass/v1/appshow/1630989431331");

        for (int i = 0; i < urls.size(); i++) {
            preloadBanner(urls.get(i));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LauncherActivity.this, GuideActivity.class).putStringArrayListExtra("urls", urls));
            }
        },2000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在当前界面进入后台时，才释放资源，防止跳转白屏问题
        finish();
    }

    /**
     * 预加载图片Url
     *
     * @param url 动态url
     */
    private void preloadBanner(String url) {
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.DATA)
                .apply(new RequestOptions().fitCenter())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.i("","预加载失败：" + url);
                        Iterator<String> it = urls.iterator();
                        while (it.hasNext()) {
                            if (TextUtils.equals(url, it.next())) {
                                it.remove();
                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.i("","预加载完成：" + url);
                        return true;
                    }
                })
                .preload();
    }

}
