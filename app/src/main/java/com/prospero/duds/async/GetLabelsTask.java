package com.prospero.duds.async;

import android.os.Bundle;

import com.prospero.duds.MainActivity;
import com.prospero.duds.ao.Label;
import com.prospero.duds.fragment.BoxFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class GetLabelsTask extends HttpGetTask {

    @Override
    protected Integer setVariables(Object... objects) {
        return 0;
    }

    @Override
    protected URL getURL() throws MalformedURLException {
        return new URL(MainActivity.baseUrl + "getlabels/");
    }

    protected void onPostExecute(Integer result) {
        if (result != null) {
            try {
                Map<Integer, Label> labels = MainActivity.activity.getLabels();
                labels.clear();
                for (int i = 0; i < jsonResponse.length(); i++) {
                    Label label = new Label(jsonResponse.getJSONObject(i));
                    labels.put(label.id, label);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
