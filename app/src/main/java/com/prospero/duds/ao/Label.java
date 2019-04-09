package com.prospero.duds.ao;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Label {
    public Integer id;
    private String name;

    public Label(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Label(@NonNull JSONObject jsonObj) {
        try {
            id = jsonObj.getInt("id");
            name = jsonObj.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
