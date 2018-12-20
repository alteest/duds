package com.prospero.duds.button;

import android.os.Handler;
import android.view.View;

public class DoubleClick implements View.OnClickListener {

    private static final long DOUBLE_CLICK_INTERVAL = 400L;  // Time to wait the second click.

    private final Handler mHandler = new Handler();

    /*
     * Click callback.
     */
    private final DoubleClickListener doubleClickListener;

    /*
     * Number of clicks in @DOUBLE_CLICK_INTERVAL interval.
     */
    private int clicks;

    /*
     * Flag to check if click handler is busy.
     */
    private boolean isBusy = false;

    /**
     * Builds a DoubleClick.
     *
     * @param doubleClickListener the click listener to notify clicks.
     */
    public DoubleClick(final DoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    @Override
    public void onClick(final View view) {

        if (!isBusy) {
            //  Prevent multiple click in this short time
            isBusy = true;

            // Increase clicks count
            clicks++;

            mHandler.postDelayed(new Runnable() {
                public final void run() {

                    if (clicks >= 2) {  // Double tap.
                        doubleClickListener.onDoubleClick(view);
                    }

                    if (clicks == 1) {  // Single tap
                        doubleClickListener.onSingleClick(view);
                    }

                    // we need to  restore clicks count
                    clicks = 0;
                }
            }, DOUBLE_CLICK_INTERVAL);
            isBusy = false;
        }

    }
}