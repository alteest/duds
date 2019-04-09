package com.prospero.duds.async;

import com.prospero.duds.MainActivity;
import com.prospero.duds.ao.Label;

import org.json.JSONException;

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
