package com.prospero.duds.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.activity.SettingsActivity;
import com.prospero.duds.fragment.AboutFragment;
import com.prospero.duds.fragment.FeedbackFragment;
import com.prospero.duds.fragment.UploadImageFragment;
import com.prospero.duds.fragment.UploadPhotoFragment;

import java.util.ArrayList;
import java.util.List;

public class StartupView extends LinearLayout {

    private final Drawable drawableButtons;

    @SuppressLint("ClickableViewAccessibility")
    public StartupView(Context context) {
        super(context);
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(getLayoutResource(), this);

        drawableButtons = getResources().getDrawable(R.drawable.startup_buttons);

        final LinearLayout buttonsLayout = findViewById(R.id.startup_buttons_linear_layout);
        final LinearLayout layout = findViewById(R.id.startup_linear_layout);
        layout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
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
                        //System.out.println("Action was DOWN");
                        return true;
                    /*case (MotionEvent.ACTION_MOVE):
                        System.out.println("Action was MOVE");
                        return true;
                        */
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
                        //System.out.println("Action was UP");
                        return true;
                    /* case (MotionEvent.ACTION_CANCEL):
                        System.out.println("Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        System.out.println("Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                        */
                    default:
                        return false;
                }
            }
        });


        Button buttonPicture = findViewById(R.id.startup_button_picture);
        buttonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String permission: new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}) {
                    if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                MainActivity.activity.setFragment(new UploadImageFragment());
            }
        });

        Button buttonPhoto = findViewById(R.id.startup_button_photo);
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String permission: new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}) {
                    if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                MainActivity.activity.setFragment(new UploadPhotoFragment());
            }
        });

        Button buttonFeedback = findViewById(R.id.startup_button_feedback);
        buttonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.setFragment(new FeedbackFragment());
            }
        });

        Button buttonSettings = findViewById(R.id.startup_button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.startActivity(new Intent(MainActivity.activity, SettingsActivity.class));
            }
        });

        Button shareSettings = findViewById(R.id.startup_button_share);
        shareSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String shareMessage = getResources().getString(R.string.share_application_text) + "\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + "TODO" +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    MainActivity.activity.startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_application_header)));
                } catch(Exception e) {
                    //e.toString();
                }               }
        });

        Button buttonAbout = findViewById(R.id.startup_button_about);
        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.setFragment(new AboutFragment());
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

    private void changeButtons() {
        int w = getWidth();
        int h = getHeight();
        //int w = drawable.getIntrinsicWidth();
        //int h = drawable.getIntrinsicHeight();
        int border = getPercentPixels(h, 1);
        int buttonHeight = getPercentPixels(h, 11);
        int pos = 0;

        List<Integer> buttons = new ArrayList<>();
        buttons.add(R.id.startup_button_about);
        buttons.add(R.id.startup_button_share);
        buttons.add(R.id.startup_button_feedback);
        buttons.add(R.id.startup_button_settings);
        buttons.add(R.id.startup_button_photo);
        buttons.add(R.id.startup_button_picture);
        Button button;
        for (Integer res: buttons) {
            button = findViewById(res);
            if (button.getVisibility() == VISIBLE) {
                changeButton(button, buttonHeight, border, w, h, pos);
                pos += 1;
            }
        }

        ImageView startupHeaderImage = findViewById(R.id.startup_header_image);
        LayoutParams params = (LayoutParams) startupHeaderImage.getLayoutParams();
        params.setMargins(border, border, border, border);
        startupHeaderImage.setImageDrawable(getButtonBackground(border, border, w - border,
                h - border - pos * border - pos * (buttonHeight + border)));

        /*
        Button buttonAbout = (Button) findViewById(R.id.startup_button_about);
        changeButton(buttonAbout, buttonHeight, border, w, h, 0);

        Button buttonShare = (Button) findViewById(R.id.startup_button_share);
        changeButton(buttonShare, buttonHeight, border, w, h, 1);

        Button buttonSettings = (Button) findViewById(R.id.startup_button_settings);
        changeButton(buttonSettings, buttonHeight, border, w, h, 2);

        Button buttonPhoto = (Button) findViewById(R.id.startup_button_photo);
        changeButton(buttonPhoto, buttonHeight, border, w, h, 3);

        Button buttonPicture = (Button) findViewById(R.id.startup_button_picture);
        changeButton(buttonPicture, buttonHeight, border, w, h, 4);
        */
    }

    private void changeButton(Button button, int buttonHeight, int margin, int width, int height, int pos) {
        button.setTransformationMethod(null);
        button.setHeight(buttonHeight);
        LayoutParams params = (LayoutParams) button.getLayoutParams();
        params.setMargins(margin, margin, margin, margin);
        button.setBackground(getButtonBackground(margin, height - buttonHeight - pos * (buttonHeight + margin),
                width - margin, height - margin - pos * (buttonHeight + margin)));
    }

    private int getLayoutResource() {
        return R.layout.startup_view;
    }

    @SuppressLint("WrongThread")
    private Drawable getButtonBackground(int x0, int y0, int x1, int y1) {

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
        return new BitmapDrawable(getResources(), boxBitmap);

        /*
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
        */
    }

    private static int getPercentPixels(int size, float percent) {
        return Math.round(size * percent / 100);
    }

}
