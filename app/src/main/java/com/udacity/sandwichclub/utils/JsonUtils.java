package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        final Sandwich sandwich = new Sandwich();

        try {
            final JSONObject jsonObject = new JSONObject(json);
            sandwich.setAlsoKnownAs((parseListOfStringField(
                    jsonObject.getJSONObject("name"), "alsoKnownAs")));
            sandwich.setDescription(jsonObject.getString("description"));
            sandwich.setIngredients(parseListOfStringField(jsonObject, "ingredients"));
            sandwich.setMainName(jsonObject.getJSONObject("name").getString("mainName"));
            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            sandwich.setImage(jsonObject.getString("image"));
        } catch (JSONException e) {
            System.out.println(e.toString());
            return null;
        }

        return sandwich;
    }

    private static List<String> parseListOfStringField(final JSONObject jsonObject, String fieldName)
            throws JSONException {
        final JSONArray alsoKnownAsJsonArray = jsonObject.getJSONArray(fieldName);
        final List<String> alsoKnownAsList = new ArrayList<>();
        for(int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
            alsoKnownAsList.add(alsoKnownAsJsonArray.getString(i));
        }

        return alsoKnownAsList;
    }
}
