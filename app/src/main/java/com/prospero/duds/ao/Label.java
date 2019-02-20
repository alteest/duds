package com.prospero.duds.ao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Label {
    public Integer id;
    public String name;

    public Label(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Label(JSONObject jobj) {
        try {
            id = jobj.getInt("id");
            name = jobj.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
