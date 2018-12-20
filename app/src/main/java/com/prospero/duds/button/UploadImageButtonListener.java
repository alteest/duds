package com.prospero.duds.button;

import android.content.Intent;
import android.view.View;

import com.prospero.duds.MainActivity;

public class UploadImageButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        // Create the Intent for Image Gallery.
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        MainActivity.activity.startActivityForResult(intent, 1);
    }
}
