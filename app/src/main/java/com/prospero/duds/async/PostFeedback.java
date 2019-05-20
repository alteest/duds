package com.prospero.duds.async;

import android.app.AlertDialog;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.fragment.FeedbackFragment;

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

public class PostFeedback extends HttpTask { //AsyncTask<BoxFragment, Integer, Integer> {

    private FeedbackFragment fragment;
    private String message;

    @Override
    protected Integer setVariables(Object... objects) {
        fragment = (FeedbackFragment) objects[0];
        message = fragment.getMessage();
        if (message.equals("")) {
            return null;
        }
        return -1;
    }

    @Override
    protected URL getURL() throws MalformedURLException {
        return new URL(MainActivity.baseUrl + "message/");
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
        //con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        return con;
    }

    @Override
    protected void sendRequest(HttpURLConnection con) throws IOException {

        OutputStream out = new BufferedOutputStream(con.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        HashMap<String, String> data = new HashMap<>();
        data.put("message", message);

        writer.write(getPostDataString(data));
        writer.flush();
        writer.close();
        out.close();
    }

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
    protected void onPostExecute(Integer result) {
        fragment.hideProgressBar();
        if (result != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.activity);
            builder.setMessage(R.string.feedback_send_success)
                    .setTitle("")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
            MainActivity.activity.getSupportFragmentManager().popBackStack();
        }
    }
}
