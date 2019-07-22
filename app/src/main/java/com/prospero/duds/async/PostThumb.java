package com.prospero.duds.async;

import android.support.annotation.NonNull;

import com.prospero.duds.MainActivity;

import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PostThumb extends HttpTask { //AsyncTask<BoxFragment, Integer, Integer> {

    private ThumbData data;

    @Override
    protected Integer setVariables(Object... objects) {
        data = (ThumbData) objects[0];
        return -1;
    }

    @Override
    protected URL getURL() throws MalformedURLException {
        return new URL(MainActivity.baseUrl + "like/");
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
        return con;
    }

    @Override
    protected void sendRequest(HttpURLConnection con) throws IOException {

        OutputStream out = new BufferedOutputStream(con.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        HashMap<String, String> data = new HashMap<>();
        data.put("request", this.data.getSimilarView().getUUID());
        data.put("picture", Integer.toString(this.data.getSimilarView().getPicture()));
        data.put("mode", Boolean.toString(this.data.getMode()));

        writer.write(getPostDataString(data));
        writer.flush();
        writer.close();
        out.close();
    }

    @NonNull
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    @Override
    Integer processResponse(StringBuffer buffer) throws JSONException {
        return 0;
    }
//    protected void onPostExecute(Integer result) {
//    }
}
