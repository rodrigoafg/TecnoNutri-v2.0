package com.example.rodri.testetecnonutri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by rodri on 30/10/2016.
 */

public class ItemJSONParser {

    public List<HashMap<String, Object>> parse(JSONObject jObject) {

        JSONArray jItems = null;
        try {
            jItems = jObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getItems(jItems);
    }

    private List<HashMap<String, Object>> getItems(JSONArray jItems) {
        int itemCount = jItems.length();
        List<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> item = null;

        for (int i = 0; i < itemCount; i++) {
            try {
                item = getItem((JSONObject) jItems.get(i));
                itemList.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return itemList;
    }

    private HashMap<String, Object> getItem(JSONObject jItem) {
        HashMap<String, Object> profile = new HashMap<String, Object>();
        String profileName = "";
        String profilePicture = "";
        String postImage = "";
        String goal = "";
        String date = "";
        String kcal = "";

        try {
            profileName = jItem.getJSONObject("profile").getString("name");
            profilePicture = jItem.getJSONObject("profile").getString("image");
            postImage = jItem.getString("image");
            goal = jItem.getJSONObject("profile").getString("general_goal");
            date = jItem.getString("date");
            kcal = jItem.getString("energy");


            profile.put("name", profileName);
            profile.put("postPicture", postImage);
            profile.put("profilePicture", profilePicture);
            profile.put("date", date);
            profile.put("objetivo", goal);
            profile.put("energy", kcal);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profile;
    }

}
