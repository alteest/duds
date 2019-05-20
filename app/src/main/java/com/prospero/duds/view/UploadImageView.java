package com.prospero.duds.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.fragment.BaseFragment;


public class UploadImageView extends UploadView {

    @SuppressLint("RestrictedApi")
    public UploadImageView(BaseFragment fragment) {
        super(fragment);

        mImageButton = findViewById(R.id.upload_image_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    @Override
    protected void selectImage() {
        for (String permission: new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        MainActivity.activity.getCurrentFragment().startActivityForResult(intent, 1);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.upload_image_view;
    }

    @Override
    public void onActivityResult(Intent data) {
        Uri selectedImage = data.getData();
        if (selectedImage != null) {
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = MainActivity.activity.getContentResolver().query(selectedImage, filePath,
                    null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            setImage(picturePath);
        }
    }
}
