package com.prospero.duds.ao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Box {

    public Integer label = null;
    public Double score = null;
    public Integer x1 = null;
    public Integer y1 = null;
    public Integer x2 = null;
    public Integer y2 = null;

    public Box(JSONObject jobj) {
        try {
            label = jobj.getInt("label");
            score = jobj.getDouble("score");
            JSONArray box = jobj.getJSONArray("box");
            x1 = box.getInt(0);
            y1 = box.getInt(1);
            x2 = box.getInt(2);
            y2 = box.getInt(3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
