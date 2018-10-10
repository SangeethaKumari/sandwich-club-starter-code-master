package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = "DetailActivity";
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mainNameValue;
    private TextView alsoKnownAsValue;
    private TextView ingredientsValue;
    private TextView placeOfOriginValue;
    private TextView descriptionValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        Log.i(LOG_TAG, String.valueOf(position));

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.i(LOG_TAG, json.toString());

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method retrieves the values from the Sandwich model and
     * updates teh activity_detail view textView values.
     *
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {

        mainNameValue = findViewById(R.id.mainNameValue);
        mainNameValue.setText(sandwich.getMainName());

        alsoKnownAsValue = findViewById(R.id.alsoKnownAsValue);
        StringBuffer alsoKnownAsString = new StringBuffer("");
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();

        if (alsoKnownAsList.size() != 0) {
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                alsoKnownAsString.append(alsoKnownAsList.get(i));
                if (i < alsoKnownAsList.size() - 1) {
                    alsoKnownAsString.append(", ");
                }
            }
            alsoKnownAsValue.setText(alsoKnownAsString);
        } else {
            alsoKnownAsValue.setText("N/A");
        }

        ingredientsValue = findViewById(R.id.ingredientsValue);
        StringBuffer ingredientsString = new StringBuffer("");
        List<String> ingredientsList = sandwich.getIngredients();

        if (ingredientsList.size() != 0) {
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredientsString.append(ingredientsList.get(i));
                if (i < ingredientsList.size() - 1) {
                    ingredientsString.append(", ");
                }
            }
            ingredientsValue.setText(ingredientsString);
        } else {
            ingredientsValue.setText("N/A");
        }

        placeOfOriginValue = findViewById(R.id.placeOfOriginValue);
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginValue.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginValue.setText("N/A");
        }
        descriptionValue = findViewById(R.id.descriptionValue);
        if (!sandwich.getDescription().isEmpty()) {
            descriptionValue.setText(sandwich.getDescription());
        } else {
            descriptionValue.setText("N/A");
        }
    }
}
