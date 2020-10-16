package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String FIELD_VALUES_SEPARATOR = ", ";

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
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

    private void populateUI(final Sandwich sandwich) {
        populateUIElementWithString((TextView) findViewById(R.id.description_tv), sandwich.getDescription());
        populateUIElementWithString((TextView) findViewById(R.id.origin_tv), sandwich.getPlaceOfOrigin());
        populateUIElementWithList((TextView) findViewById(R.id.also_known_tv), sandwich.getAlsoKnownAs());
        populateUIElementWithList((TextView) findViewById(R.id.ingredients_tv), sandwich.getIngredients());
    }

    private void populateUIElementWithString(final TextView textView, final String value) {
        if(value == null || value.isEmpty()) {
            textView.setText(R.string.field_data_not_available);
            return;
        }

        textView.setText(value);
    }

    private void populateUIElementWithList(final TextView textView, final List<String> values) {
        if(values == null || values.isEmpty()) {
            textView.setText(R.string.field_data_not_available);
            return;
        }

        String result = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            result +=  FIELD_VALUES_SEPARATOR + values.get(i);
        }

        textView.setText(result);
    }
}
