package com.example.zdroa.myapplication.handlers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.zdroa.myapplication.utils.AppSettings;

public class LoadingSpinner extends ProgressBar {

    private Window window;
    private ViewGroup parentView;

    public LoadingSpinner(Context context) {
        super(context);
    }

    public LoadingSpinner(Context context, Window window, ViewGroup parentView, ViewGroup rootView, boolean hideOnInit) {
        super(context, null, android.R.attr.progressBarStyleLarge);
        this.window = window;
        this.parentView = parentView;
        super.getIndeterminateDrawable().setColorFilter(new PorterDuffColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AppSettings.DEFAULT_PROGRESS_BAR_WIDTH, AppSettings.DEFAULT_PROGRESS_BAR_HEIGHT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        super.setLayoutParams(params);
        this.addToLayout(rootView);
        if (hideOnInit) {
            this.hide();
            this.enableUserInput();
        }
    }

    public void addToLayout(ViewGroup rootView) {
        rootView.addView(this);
    }


    public void hideAndShowParentViewAndEnableUserInput() {
        hide();
        showParentView();
        enableUserInput();
    }

    public void showAndHideParentViewAndDisableUserInput() {
        show();
        hideParentView();
        disableUserInput();
    }

    public void showParentView() {
        parentView.setVisibility(View.VISIBLE);
    }

    public void hideParentView() {
        parentView.setVisibility(View.GONE);
    }

    public void hide() {
        super.setVisibility(View.GONE);
    }

    public void show() {
        super.setVisibility(View.VISIBLE);
    }

    private void disableUserInput() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void enableUserInput() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
