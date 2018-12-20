package com.prospero.duds.async;

import android.os.AsyncTask;
import android.os.Bundle;

import com.prospero.duds.MainActivity;
import com.prospero.duds.fragment.UploadFragment;
import com.prospero.duds.fragment.BoxFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchBoxesTask extends AsyncTask<UploadFragment, Integer, Integer> {

    private JSONArray searchResult = null;
    private String filepath = null;
    private UploadFragment fragment;

    @Override
    protected Integer doInBackground(UploadFragment... urls) {
        if (urls.length == 1) {
            URL obj = null;
            try {
                fragment = urls[0];
                filepath = fragment.getFilepath();
                if (filepath == null) {
                    return null;
                }
                String attachmentFileName = filepath.substring(filepath.lastIndexOf("/") + 1);
                String attachmentName = attachmentFileName;
                if (attachmentFileName.indexOf(".") > 0)
                    attachmentName = attachmentFileName.substring(0, attachmentFileName.lastIndexOf("."));
                String crlf = "\r\n";
                String twoHyphens = "--";
                String boundary = "***********";

                //obj = new URL(urls[0].getUrl());
                obj = new URL(MainActivity.baseUrl + "search/");
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setUseCaches(false);
                con.setDoOutput(true);

                //add request header
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "DUDS");
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                // Send post request
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(twoHyphens + boundary + crlf);
                wr.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" + attachmentFileName + "\"" + crlf);
                wr.writeBytes(crlf);
                //I want to send only 8 bit black & white bitmaps

                FileInputStream fileStream = new FileInputStream(filepath);
                int bytesAvailable = fileStream.available(); // create a buffer of

                int maxBufferSize = 1 * 1024 * 1024;
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

                int responseCode = con.getResponseCode();
                //System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                con.disconnect();
                //System.out.println(response.toString());

                //JSONObject jobj = new JSONObject(response.toString());
                searchResult = new JSONArray(response.toString());
                return searchResult.length();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
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
                bundle.putSerializable("array", searchResult.getJSONArray(0).toString());
                bundle.putString("filepath", filepath);
                fragment.setArguments(bundle);
                MainActivity.activity.setFragment(fragment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}