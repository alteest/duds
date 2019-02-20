package com.prospero.duds.async;

import android.os.Bundle;

import com.prospero.duds.MainActivity;
import com.prospero.duds.fragment.BoxFragment;
import com.prospero.duds.fragment.SimilarFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchSimilaritiesTask extends HttpTask { //AsyncTask<BoxFragment, Integer, Integer> {

    private String filepath = null;
    private String attachmentFileName = null;
    private String attachmentName = null;
    private BoxFragment fragment;

    @Override
    protected Integer setVariables(Object... objects) {
        fragment = (BoxFragment) objects[0];
        filepath = fragment.getFilepath();
        if (filepath == null) {
            return null;
        }
        attachmentFileName = filepath.substring(filepath.lastIndexOf("/") + 1);
        attachmentName = attachmentFileName;
        if (attachmentFileName.indexOf(".") > 0)
            attachmentName = attachmentFileName.substring(0, attachmentFileName.lastIndexOf("."));
        return -1;
    }

    @Override
    protected URL getURL() throws MalformedURLException {
        return new URL(MainActivity.baseUrl + "similar/");
    }

    @Override
    protected HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "DUDS");
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Cache-Control", "no-cache");
        con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        return con;
    }

    @Override
    protected void sendRequest(HttpURLConnection con) throws IOException {
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(twoHyphens + boundary + crlf);
        wr.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" + attachmentFileName + "\"" + crlf);
        wr.writeBytes(crlf);
        //I want to send only 8 bit black & white bitmaps

        int maxBufferSize = 1 * 1024 * 1024;
        byte[] buffer = fragment.getBytes();
        int bufferSize = Math.min(buffer.length, maxBufferSize);

        wr.write(buffer, 0, bufferSize);

        wr.writeBytes(crlf);
        wr.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

        wr.flush();
        wr.close();
    }

    protected void onPostExecute(Integer result) {
        fragment.hideProgressBar();
        if (result != null) {
            try {
                SimilarFragment fragment = new SimilarFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("array", jsonResponse.getJSONArray(0).toString());
                fragment.setArguments(bundle);
                MainActivity.activity.setFragment(fragment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
