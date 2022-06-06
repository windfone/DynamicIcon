package com.vcom.lib_guide;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * @Author: zgh
 * @Date: 2022/5/23 18:01
 * @Description: 功能引导遮罩
 */
public class GuideUtils {


    private static GuideUtils instance = null;

    private Context context;

    private GuideSet guideSet;

    private GuideUtils(Context context) {
        this.context = context;
        guideSet = new GuideSet();
    }

    public synchronized static GuideUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (GuideUtils.class) {
                instance = new GuideUtils(context);
            }
        }
        return instance;
    }

    private GuideView createGuideView(View view, @LayoutRes int layout, @IdRes int clickId) {
        return new GuideView.Builder(context)
                .focusView(view)
                .setPadding(5, 5, 5, 5)
                .setRadius(5)
                .setRelyActivity((Activity) context)
                .setLayout(layout, null)
                .setPassId(clickId)
                .build();
    }

    private GuideView createNoPassIdGuideView(View view, @LayoutRes int layout) {
        return new GuideView.Builder(context)
                .focusView(view)
                .setPadding(5, 5, 5, 5)
                .setRadius(5)
                .setRelyActivity((Activity) context)
                .setLayout(layout, new DecorateInflate() {
                    @Override
                    public void onInflate(final GuideView guideView, View inflaterView) {
                        inflaterView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                guideView.dismiss();
                            }
                        });
                    }
                })
                .build();
    }

    public GuideUtils addView(View view, @LayoutRes int layout, @IdRes int clickId) {
        guideSet.addGuide(createGuideView(view, layout, clickId));
        return this;
    }

    public GuideUtils addNoPassIdView(View view, @LayoutRes int layout) {
        guideSet.addGuide(createNoPassIdGuideView(view, layout));
        return this;
    }

    public void show() {
        guideSet.show();
    }

    /**
     * @param view
     * @param radius
     * @param layout
     * @param clickId 点击消失id
     */
    private void showSingleView(View view, int radius, @LayoutRes int layout, @IdRes int clickId) {
        new GuideView.Builder(context)
                .focusView(view)
                .setRadius(radius)
                .setRelyActivity((Activity) context)
                .setLayout(layout, null)
                .setPassId(clickId)
                .build()
                .show();
    }

    /**
     * 不设置clickId，点击空白处消失
     *
     * @param view
     * @param radius
     * @param layout
     */
    private void showNoPassIdSingleView(View view, int radius, @LayoutRes int layout) {
        new GuideView.Builder(context)
                .focusView(view)
                .setRadius(radius)
                .setRelyActivity((Activity) context)
                .setLayout(layout, new DecorateInflate() {
                    @Override
                    public void onInflate(final GuideView guideView, View inflaterView) {
                        inflaterView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                guideView.dismiss();
                            }
                        });
                    }
                })
                .build()
                .show();
    }

    /**
     * 默认配置
     *
     * @param view
     * @param layout
     */
    private void showDefaultView(View view, @LayoutRes int layout) {
        new GuideView.Builder(context)
                .focusView(view)
                .setPadding(5, 5, 5, 5)
                .setRadius(15)
                .setRelyActivity((Activity) context)
                .setLayout(layout, new DecorateInflate() {
                    @Override
                    public void onInflate(final GuideView guideView, View inflaterView) {
                        inflaterView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                guideView.dismiss();
                            }
                        });
                    }
                })
                .build()
                .show();
    }

}