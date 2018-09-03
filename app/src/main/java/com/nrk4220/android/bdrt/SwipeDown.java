package com.nrk4220.android.bdrt;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeDown implements View.OnTouchListener {

    private GestureDetector gestureDetector;

    public SwipeDown (Context context){
        gestureDetector = new GestureDetector(context, new gestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class gestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                        if (diffY > 0) {
                            Execute();
                    }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
    public void Execute(){
    }
}
