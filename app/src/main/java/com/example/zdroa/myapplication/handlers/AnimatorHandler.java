package com.example.zdroa.myapplication.handlers;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;

public class AnimatorHandler {

    public static ViewPropertyAnimator getMoveYAxisViewAnimation(View view, float destinationYPos, int duration) {
        return getMoveViewAnimation(view, destinationYPos, -1f, duration);
    }

    public static ViewPropertyAnimator getMoveXAxisViewAnimation(View view, float destinationXPos, int duration) {
        return getMoveViewAnimation(view, -1f, destinationXPos, duration);
    }

    public static ViewPropertyAnimator getMoveViewAnimation(View view, float destinationYPos, float destinationXPos, int duration) {
        ViewPropertyAnimator animator = view.animate();
        animator.setDuration(duration);
        if (destinationYPos > 0f) {
            animator.y(destinationYPos);
        }
        if (destinationXPos > 0f) {
            animator.x(destinationXPos);
        }
        return animator;
    }

    public static AnimatorSet getShrinkAnimation(View view, float scale, int duration) {
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", scale);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", scale);
        scaleDownX.setDuration(duration);
        scaleDownY.setDuration(duration);
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);
        return scaleDown;
    }
}
