package com.prospero.duds.async;

import android.graphics.Bitmap;

import com.prospero.duds.MainActivity;
import com.prospero.duds.cache.FileCache;
import com.prospero.duds.view.SimilarView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetSimilarImageTask extends HttpGetTask { //} AsyncTask<SimilarView, Integer, Integer> {

    private SimilarView view = null;
    private Bitmap bitmap = null;
    private FileCache fileCache = null;
    private File f = null;

    protected Integer setVariables(Object... objects) {
        view = (SimilarView) objects[0];

        fileCache = MainActivity.activity.getFileCache();
        f = fileCache.getFile(view.getStore(), view.getFilename());
        bitmap = fileCache.decodeFile(f);
        if (bitmap != null) {
            return bitmap.getByteCount();
        }
        return -1;
    }

    protected URL getURL() throws MalformedURLException {
        return new URL(MainActivity.baseUrl + "getimage/?name=" + view.getFilename());
    }

    protected StringBuffer readResponse(HttpURLConnection con) throws IOException {
        OutputStream os = new FileOutputStream(f);
        FileCache.CopyStream(con.getInputStream(), os);
        //bitmap = BitmapFactory.decodeStream(con.getInputStream());
        os.close();
        con.disconnect();
        return null;
    }

    protected Integer processResponse(StringBuffer buffer) {
        bitmap = fileCache.decodeFile(f);
        return bitmap.getByteCount();
    }

    protected void onPostExecute(Integer result) {
        view.hideProgressBar();
        if (result != null) {
            view.setImage(bitmap);
        }
    }
}
