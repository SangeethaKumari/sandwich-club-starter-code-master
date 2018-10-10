package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String LOG_TAG = "JsonUtils";


    public static Sandwich parseSandwichJson(String sandwichJson) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(sandwichJson)) {
            Log.i(LOG_TAG, "Sandwich JSON data is empty ...");
            return null;
        }
        // Create a new {@link Sandwich} object with the magnitude, location, time,
        // and url from the JSON response.
        Sandwich sandwich = new Sandwich();

        // Create an empty ArrayList that we can start adding news to the newsList
        List<Sandwich> newsList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(sandwichJson);

            // Extract the JSONArray associated with the key called "name",
            // which represents a list of response (or sandwich).
            JSONObject currentSandwich = baseJsonResponse.getJSONObject("name");


            // Extract the value for the key called "mainName"
            String mainName = currentSandwich.getString("mainName");
            sandwich.setMainName(mainName);

            // Extract the value for the key called "alsoKnownAs"
            List<String> alsoKnownAs = new ArrayList<String>();
            JSONArray arrObj = currentSandwich.getJSONArray("alsoKnownAs");
            for (int j = 0; j < arrObj.length(); j++) {
                alsoKnownAs.add(arrObj.get(j).toString());

            }
            sandwich.setAlsoKnownAs(alsoKnownAs);

            // Extract the value for the key called "placeOfOrigin"
            String placeOfOrigin = baseJsonResponse.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            // Extract the value for the key called "description"
            String description = baseJsonResponse.getString("description");
            sandwich.setDescription(description);
            // Extract the value for the key called "image"
            String image = baseJsonResponse.getString("image");
            sandwich.setImage(image);

            // Extract the value for the key called "ingredients"
            List<String> ingredients = new ArrayList<String>();
            JSONArray ingredientsArrObj = baseJsonResponse.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsArrObj.length(); j++) {
                ingredients.add(ingredientsArrObj.get(j).toString());
            }
            sandwich.setIngredients(ingredients);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the sandwich JSON results", e);
        }
        // Return the list of news
        return sandwich;
    }


}
