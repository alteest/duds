package com.prospero.duds.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.fragment.BaseFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class UploadPhotoView extends UploadView {

    ShowcaseView view;

    @SuppressLint("RestrictedApi")
    public UploadPhotoView(BaseFragment fragment) {
        super(fragment);

        mImageButton = (ImageButton) findViewById(R.id.upload_photo_button);
        /*
        mImageButton.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                MainActivity.activity.validatePermission(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE});
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (intent.resolveActivity(MainActivity.activity.getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(view.getContext(),
                                "com.prospero.duds.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        MainActivity.activity.getCurrentFragment().startActivityForResult(intent, 1);
                    }
                }
            }

            @Override
            public void onDoubleClick(View view) {
                doSearch();
            }
        }));
        */

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        /*mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.activity.validatePermission(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE});
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (intent.resolveActivity(MainActivity.activity.getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(view.getContext(),
                                "com.prospero.duds.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        MainActivity.activity.getCurrentFragment().startActivityForResult(intent, 1);
                    }
                }
            }
        });*/

    }

    public void showShowcaseViews2() {
        //view.setTarget(new ViewTarget(mImageButton));
        //view.show();
        Button button = new Button(getContext());
        button.setText("");
        button.setEnabled(false);
        button.setVisibility(View.GONE);

        view = new ShowcaseView.Builder(MainActivity.activity)
                .setTarget(new ViewTarget(mImageButton))
                .setContentText(R.string.helper_upload_image_button_text)
                .replaceEndButton(button)
                .setStyle(R.style.MyCustomShowcaseTheme)
                .hideOnTouchOutside()
                .build();
    }

    @Override
    protected void selectImage() {
        MainActivity.activity.validatePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(MainActivity.activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(view.getContext(),
                        "com.prospero.duds.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                MainActivity.activity.getCurrentFragment().startActivityForResult(intent, 1);
            }
        }
    }

        @Override
    protected int getLayoutResource() {
        return R.layout.upload_photo_view;
    }

    @Override
    public void onActivityResult(Intent data) {
        galleryAddPic();
        setImage(mFilepath);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = fmt.format(date);
        String imageFileName = "JPEG_duds_" + timeStamp + "_";
        File storageDir = MainActivity.activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mFilepath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        MediaScannerConnection.scanFile(
                MainActivity.activity.getApplicationContext(),
                new String[]{mFilepath},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.v("duds",
                                "file " + path + " was scanned successfully: " + uri);
                    }
                });
        /*Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        intent.setData(Uri.fromFile(f));
        MainActivity.activity.sendBroadcast(intent);*/
    }

}
