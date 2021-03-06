package com.prospero.duds.async;

import android.os.Bundle;

import com.prospero.duds.MainActivity;
import com.prospero.duds.fragment.UploadFragment;
import com.prospero.duds.fragment.BoxFragment;

import org.json.JSONException;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchBoxesTask extends HttpTask { //} AsyncTask<UploadFragment, Integer, Integer> {

    private String filepath = null;
    private String attachmentFileName = null;
    private String attachmentName = null;
    private UploadFragment fragment;

    @Override
    protected Integer setVariables(Object... objects) {
        fragment = (UploadFragment) objects[0];
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
        return new URL(MainActivity.baseUrl + "search/");
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

        FileInputStream fileStream = new FileInputStream(filepath);
        int bytesAvailable = fileStream.available(); // create a buffer of

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = fileStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            wr.write(buffer, 0, bufferSize);
            bytesAvailable = fileStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileStream.read(buffer, 0, bufferSize);
        }

        wr.writeBytes(crlf);
        wr.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

        wr.flush();
        wr.close();
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Integer result) {
        fragment.hideProgressBar();
        if (result != null) {
            try {
                //MainActivity.activity.getPagerAdapter().setSearchResult(searchResult.getJSONArray(0));
                BoxFragment fragment = new BoxFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("array", jsonResponse.getJSONArray(0).toString());
                bundle.putString("filepath", filepath);
                fragment.setArguments(bundle);
                MainActivity.activity.setFragment(fragment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}