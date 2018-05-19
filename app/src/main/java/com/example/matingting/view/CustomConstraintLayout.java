package com.example.matingting.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class CustomConstraintLayout extends ConstraintLayout {

    private onPerformDrawListener listener;

    public CustomConstraintLayout(Context context) {
        super(context);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(onPerformDrawListener listener) {
        this.listener = listener;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (listener != null){
            listener.onPerformDraw();
        }
    }


    public interface onPerformDrawListener{
        void onPerformDraw();
    }
}
