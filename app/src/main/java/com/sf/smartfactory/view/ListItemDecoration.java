package com.sf.smartfactory.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author: winton
 * @time: 2018/5/14 19:25
 * @package: com.sf.smartfactory.view
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 10;
        outRect.right = 16;
        outRect.left = 16;
        outRect.bottom = 10;
    }
}
