package com.vcom.lib_guide;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zgh
 * @Date: 2022/5/20 9:36
 * @Description: 多个引导组成集合
 */
public class GuideSet {

    private List<GuideView> guideViews = new ArrayList<>();

    private int position = 0;

    public void addGuide(GuideView guideView) {
        guideViews.add(guideView);
        guideView.setDismissCallBack(new DismissCallBack() {
            @Override
            public void onDismiss() {
                int nextPosition = position + 1;
                if (guideViews.size() > nextPosition) {
                    //不是最后一个
                    GuideView next = guideViews.get(nextPosition);
                    position++;
                    next.show();
                }
            }
        });
    }

    public void show() {
        guideViews.get(0).show();
    }
}
