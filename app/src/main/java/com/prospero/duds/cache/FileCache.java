package com.prospero.duds.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileCache {

    final int REQUIRED_SIZE=1024;

    private File mCacheDir;

    public FileCache(Context context) {

        //Find the dir at SDCARD to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            //if SDCARD is mounted (SDCARD is present on device and mounted)
            mCacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "DudsFileCache");
        } else {
            // if checking on simulator the create cache dir in your application context
            mCacheDir = context.getCacheDir();
        }

        if (!mCacheDir.exists()) {
            mCacheDir.mkdirs();
        }
    }

    public File getFile(String store, String filename){
        File storeDir = new File(mCacheDir, store);
        if (!storeDir.exists()) {
            storeDir.mkdirs();
        }
        return new File(storeDir, filename);
    }

    public File getFile(String url){
        String filename = String.valueOf(url.hashCode());
        return new File(mCacheDir, filename);
    }

    public void clear(){
        File[] files = mCacheDir.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
    }

    public void list(){
        File[] files = mCacheDir.listFiles();
        if (files != null) {
            for (File f : files) {
                System.out.println("Cache file : " + f.getAbsolutePath());
            }
        }
    }


    public Bitmap decodeFile(File f){

        try {
            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null, options1);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.

            // Set width/height of recreated image
            int width_tmp = options1.outWidth, height_tmp= options1.outHeight;
            int scale = 1;
            while (true){
                if(width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with current scale values
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, options2);
            stream2.close();
            return bitmap;

        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try {
            byte[] bytes=new byte[buffer_size];
            for(;;) {
                int count = is.read(bytes, 0, buffer_size);
                if(count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}
