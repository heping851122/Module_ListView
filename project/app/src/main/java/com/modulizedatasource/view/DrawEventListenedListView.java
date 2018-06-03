package com.modulizedatasource.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;
import com.modulizedatasource.view.DrawEventListener;

/**
 * Created by tony on 2018/5/14.
 */

public class DrawEventListenedListView extends ListView {

    private DrawEventListener mOnDrawListener;

    public DrawEventListenedListView(Context context) {
        super(context);
    }

    public DrawEventListenedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawEventListenedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnDrawListener(DrawEventListener listener) {
        this.mOnDrawListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mOnDrawListener != null) {
            mOnDrawListener.onDraw();
        }
    }
}
