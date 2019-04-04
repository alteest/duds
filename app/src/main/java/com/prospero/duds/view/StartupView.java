package com.prospero.duds.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.activity.SettingsActivity;
import com.prospero.duds.fragment.UploadImageFragment;
import com.prospero.duds.fragment.UploadPhotoFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StartupView extends LinearLayout {

    Drawable drawableButtons = null;

    public StartupView(Context context) {
        super(context);
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(getLayoutResource(), this);

        drawableButtons = getResources().getDrawable(R.drawable.startup_buttons);

        final LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.startup_buttons_linear_layout);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.startup_linear_layout);
        layout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = MotionEventCompat.getActionMasked(motionEvent);
                TranslateAnimation animate = null;
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                0,                 // fromYDelta
                                buttonsLayout.getHeight()); // toYDelta
                        animate.setDuration(500);
                        animate.setFillAfter(true);
                        buttonsLayout.startAnimation(animate);
                        System.out.println("Action was DOWN");
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        System.out.println("Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        buttonsLayout.setVisibility(View.VISIBLE);
                        animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                buttonsLayout.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate.setDuration(500);
                        animate.setFillAfter(true);
                        buttonsLayout.startAnimation(animate);
                        System.out.println("Action was UP");
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        System.out.println("Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        System.out.println("Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });


        Button buttonPicture = (Button) findViewById(R.id.startup_button_picture);
        buttonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.setFragment(new UploadImageFragment());
            }
        });

        Button buttonPhoto = (Button) findViewById(R.id.startup_button_photo);
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.setFragment(new UploadPhotoFragment());
            }
        });

        Button buttonSettings = (Button) findViewById(R.id.startup_button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.startActivity(new Intent(MainActivity.activity, SettingsActivity.class));
            }
        });

    }

    @Override
    protected void onLayout (boolean changed,
                             int left,
                             int top,
                             int right,
                             int bottom) {
        if (changed) {
            changeButtons();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

/*    @Override
    protected void onSizeChanged (int w,
                                  int h,
                                  int oldw,
                                  int oldh) {
        int w1 = getWidth();
        super.onSizeChanged(w, h, oldw, oldh);
    }
*/

/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(false) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.startup_linear_layout);

            int w = getWidth();
            int h = getHeight();
            //int w = drawable.getIntrinsicWidth();
            //int h = drawable.getIntrinsicHeight();
            int h1 = getPercentPixels(h, 1);
            int h11 = getPercentPixels(h, 11);

            Button buttonAbout = (Button) findViewById(R.id.startup_button_about);
            changeButton(buttonAbout, h11, h1, w, h, 0);

            Button buttonShare = (Button) findViewById(R.id.startup_button_share);
            changeButton(buttonShare, h11, h1, w, h, 1);

            Button buttonSettings = (Button) findViewById(R.id.startup_button_settings);
            changeButton(buttonSettings, h11, h1, w, h, 2);

            Button buttonPhoto = (Button) findViewById(R.id.startup_button_photo);
            changeButton(buttonPhoto, h11, h1, w, h, 3);

            Button buttonPicture = (Button) findViewById(R.id.startup_button_picture);
            changeButton(buttonPicture, h11, h1, w, h, 4);
        }
    }
*/

    protected void changeButtons() {
        int w = getWidth();
        int h = getHeight();
        //int w = drawable.getIntrinsicWidth();
        //int h = drawable.getIntrinsicHeight();
        int h1 = getPercentPixels(h, 1);
        int h11 = getPercentPixels(h, 11);

        Button buttonAbout = (Button) findViewById(R.id.startup_button_about);
        changeButton(buttonAbout, h11, h1, w, h, 0);

        Button buttonShare = (Button) findViewById(R.id.startup_button_share);
        changeButton(buttonShare, h11, h1, w, h, 1);

        Button buttonSettings = (Button) findViewById(R.id.startup_button_settings);
        changeButton(buttonSettings, h11, h1, w, h, 2);

        Button buttonPhoto = (Button) findViewById(R.id.startup_button_photo);
        changeButton(buttonPhoto, h11, h1, w, h, 3);

        Button buttonPicture = (Button) findViewById(R.id.startup_button_picture);
        changeButton(buttonPicture, h11, h1, w, h, 4);
    }

    protected void changeButton(Button button, int buttonHeight, int margin, int width, int height, int pos) {
        button.setTransformationMethod(null);
        button.setHeight(buttonHeight);
        LayoutParams params = (LayoutParams) button.getLayoutParams();
        params.setMargins(margin, margin, margin, margin);
        button.setBackground(getButtonBackground(margin, height - buttonHeight - pos * (buttonHeight + margin),
                width - margin, height - margin - pos * (buttonHeight + margin)));
    }

    protected int getLayoutResource() {
        return R.layout.startup_view;
    };

    @SuppressLint("WrongThread")
    protected Drawable getButtonBackground(int x0, int y0, int x1, int y1) {

        int w = getWidth();
        int h = getHeight();

        Bitmap b = ((BitmapDrawable)drawableButtons).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, w, h, false);
        Drawable drawable = new BitmapDrawable(getResources(), bitmapResized);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        //drawable.setBounds(x0, y0, x1, y1);
        drawable.draw(canvas);

        Bitmap boxBitmap = Bitmap.createBitmap(bitmap, x0, y0, x1 - x0, y1 - y0);
        BitmapDrawable bmd = new BitmapDrawable(getResources(), boxBitmap);

        try {
            File path = Environment.getExternalStoragePublicDirectory("DudsFileCache");
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, "/test1111.png");
            FileOutputStream out = new FileOutputStream(file);
            boxBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmd;
    }

    protected static int getPercentPixels(int size, float percent) {
        return Math.round(size * percent / 100);
    }

}
