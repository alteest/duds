package com.prospero.duds.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.prospero.duds.MainActivity;
import com.prospero.duds.cache.FileCache;
import com.prospero.duds.fragment.SimilarFragment;
import com.prospero.duds.view.SimilarView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetSimilarImageTask extends AsyncTask<SimilarView, Integer, Integer> {

    protected SimilarView view = null;
    protected Bitmap bitmap = null;

    @Override
    protected Integer doInBackground(SimilarView... similarViews) {
        if (similarViews.length == 1) {
            URL obj = null;
            try {
                view = similarViews[0];

                //obj = new URL(urls[0].getUrl());
                String url = MainActivity.baseUrl + "getimage/?name=" + view.getFilename();

                FileCache fileCache = MainActivity.activity.getFileCache();
                //File f = fileCache.getFile(url);
                File f = fileCache.getFile(view.getStore(), view.getFilename());
                bitmap = fileCache.decodeFile(f);
                if (bitmap != null) {
                    return bitmap.getByteCount();
                }

                obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                //con.setUseCaches(false);
                //con.setDoOutput(true);
                con.setRequestProperty("User-Agent", "DUDS");
                con.setRequestProperty("Cache-Control", "no-cache");

                int responseCode = con.getResponseCode();
                //System.out.println("getimage response Code : " + responseCode);

                OutputStream os = new FileOutputStream(f);
                fileCache.CopyStream(con.getInputStream(), os);

                //bitmap = BitmapFactory.decodeStream(con.getInputStream());

                os.close();
                con.disconnect();
                bitmap = fileCache.decodeFile(f);
                return bitmap.getByteCount();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    protected void onPostExecute(Integer result) {
        view.hideProgressBar();
        if (result != null) {
            view.setImage(bitmap);
        }
    }
}
