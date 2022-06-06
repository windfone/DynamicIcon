package com.vcom.lib_guide;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * @Author: zgh
 * @Date: 2022/5/19 15:21
 * @Description: 功能引导遮罩
 */
public class GuideView extends FrameLayout {

    private final String TAG = "GuideView";

    /**
     * 高亮View
     */
    private View focusView;
    private Activity relyActivity;
    private int layout;
    private int clickId;
    private float radius;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int bgColor = Color.parseColor("#B3000000");

    /**
     * 引导区域
     */
    private Rect viewRect;
    /**
     * activity根rect
     */
    private Rect contentRect;
    private Paint paint;
    /**
     * 要绘制的高亮矩形
     */
    private RectF rectF;
    private PorterDuffXfermode porterDuffXfermode;
    /**
     * activity根布局
     */
    private FrameLayout contentParent;

    private View inflaterView;

    private DecorateInflate decorateInflate;

    public void setRelyActivity(Activity relyActivity) {
        this.relyActivity = relyActivity;
        contentParent = relyActivity.findViewById(android.R.id.content);
    }

    private DismissCallBack dismissCallBack;
    public void setDismissCallBack(DismissCallBack dismissCallBack) {
        this.dismissCallBack = dismissCallBack;
    }

    private GuideView(Context context) {
        super(context);
        setWillNotDraw(false);
        viewRect = new Rect();
        contentRect = new Rect();
        rectF = new RectF();

        initPint();
    }


    private void initPint() {
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        PorterDuff.Mode mode = PorterDuff.Mode.CLEAR;
        porterDuffXfermode = new PorterDuffXfermode(mode);
        paint.setXfermode(porterDuffXfermode);
        //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "onDraw");

        canvas.drawColor(bgColor);

        contentParent.getGlobalVisibleRect(contentRect);
        int topMargin = contentRect.top;

        Log.d(TAG, "topMargin:" + topMargin);

        //画出高亮区域
        focusView.getGlobalVisibleRect(viewRect);

        int left = viewRect.left - paddingLeft;
        rectF.left = left;
        Log.d(TAG, "left:" + left);
        int right = viewRect.right + paddingRight;
        rectF.right = right;
        Log.d(TAG, "right:" + right);
        int top = viewRect.top - paddingTop - topMargin;
        rectF.top = top;
        Log.d(TAG, "top:" + top);
        int bottom = viewRect.bottom + paddingBottom - topMargin;
        rectF.bottom = bottom;
        Log.d(TAG, "bottom:" + bottom);

        canvas.drawRoundRect(rectF, radius, radius, paint);

    }

    public void show() {
        inflaterView = LayoutInflater.from(relyActivity).inflate(layout, this, true);
        if (clickId != 0) {
            inflaterView.findViewById(clickId).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (dismissCallBack != null) {
                        dismissCallBack.onDismiss();
                    }
                }
            });
        }
        if (decorateInflate != null) {
            decorateInflate.onInflate(this,inflaterView);
        }
        this.setClickable(true);
        contentParent.post(new Runnable() {
            @Override
            public void run() {
                contentParent.addView(GuideView.this,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });

    }

    public void dismiss() {
        contentParent.post(new Runnable() {
            @Override
            public void run() {
                contentParent.removeView(GuideView.this);
            }
        });
    }

    public static class Builder {

        private GuideView guideView;

        public Builder(Context context) {
            guideView = new GuideView(context);
        }

        public Builder focusView(View view) {
            guideView.focusView = view;
            return this;
        }

        public Builder setRelyActivity(Activity activity) {
            guideView.setRelyActivity(activity);
            return this;
        }

        public Builder setLayout(@LayoutRes int layout, @Nullable DecorateInflate decorateInflate) {
            guideView.layout = layout;
            guideView.decorateInflate = decorateInflate;
            return this;
        }

        public Builder setPassId(@IdRes int clickId) {
            guideView.clickId = clickId;
            return this;
        }

        public Builder setRadius(float radius) {
            guideView.radius = radius;
            return this;
        }

        public Builder setPadding(int paddingLeft, int paddingRight, int paddingTop, int paddingBottom) {
            guideView.paddingTop = paddingTop;
            guideView.paddingBottom = paddingBottom;
            guideView.paddingLeft = paddingLeft;
            guideView.paddingRight = paddingRight;
            return this;
        }

        public Builder setBgColor(int bgColor) {
            guideView.bgColor = bgColor;
            return this;
        }

        public GuideView build() {
            return guideView;
        }
    }
}
