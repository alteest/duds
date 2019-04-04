package com.prospero.duds.async;

import android.os.AsyncTask;

import com.prospero.duds.MainActivity;
import com.prospero.duds.view.SimilarView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

abstract class HttpTask extends AsyncTask<Object, Integer, Integer> {

    static final String twoHyphens = "--";
    static final String boundary = "***********";
    static final String crlf = "\r\n";

    protected JSONArray jsonResponse = null;

    protected boolean doRequest(Object... objects) {
        return objects.length == 1;
    }

    protected abstract Integer setVariables(Object... objects);
    protected abstract URL getURL() throws MalformedURLException;
    protected abstract HttpURLConnection getHttpURLConnection(URL url) throws IOException;
    protected abstract void sendRequest(HttpURLConnection con) throws IOException;

    protected StringBuffer readResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
        return response;
    }

    protected Integer processResponse(StringBuffer buffer) throws JSONException {
        jsonResponse = new JSONArray(buffer.toString());
        return jsonResponse.length();
    }

    @Override
    protected Integer doInBackground(Object... objects) {
        if (doRequest(objects)) {
            try {
                Integer returnCode = setVariables(objects);
                if (returnCode == null) {
                    return returnCode;
                }

                URL url = getURL();
                HttpURLConnection con = getHttpURLConnection(url);

                sendRequest(con);
                int responseCode = con.getResponseCode();
                //System.out.println("Response Code : " + responseCode);
                StringBuffer response = readResponse(con);
                MainActivity.appendLog("Processed '" + url.getPath() + "' and returned " + response);
                return processResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
                MainActivity.appendLog(e.toString());
                return null;
            }
        }
        MainActivity.appendLog("doRequest doesn't match");
        return null;
    }
}
