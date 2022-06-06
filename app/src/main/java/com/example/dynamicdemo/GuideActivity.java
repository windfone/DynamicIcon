package com.example.dynamicdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


public class GuideActivity extends AppCompatActivity {

    /**
     * 用于放置背景图片
     */
    private BGABanner mBackgroundBanner;
    /**
     * 用于放置浮动文字
     */
    private BGABanner mForegroundBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setListener();
        processLogic();
    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        mForegroundBanner = findViewById(R.id.banner_guide_foreground);
        mBackgroundBanner = findViewById(R.id.banner_guide_background);

    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
            }
        });
    }

    private void processLogic() {
        List<String> caches = getIntent().getStringArrayListExtra("urls");
        if (caches != null && caches.size() > 0) {
            mForegroundBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                    Glide.with(GuideActivity.this)
                            .load(model)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .apply(new RequestOptions().fitCenter())
                            .into(itemView);
                }
            });
            mForegroundBanner.setData(caches, null);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}
