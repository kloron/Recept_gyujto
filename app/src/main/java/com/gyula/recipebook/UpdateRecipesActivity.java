package com.gyula.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateRecipesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MESSAGE0 = "com.gyula.recipebook.MESSAGE0";
    public static final String EXTRA_MESSAGE1 = "com.gyula.recipebook.MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "com.gyula.recipebook.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.gyula.recipebook.MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "com.gyula.recipebook.MESSAGE4";
    public static final String EXTRA_MESSAGE5 = "com.gyula.recipebook.MESSAGE5";
    public static final String EXTRA_MESSAGE6 = "com.gyula.recipebook.MESSAGE6";
    public static final String EXTRA_MESSAGE7 = "com.gyula.recipebook.MESSAGE7";
    public static final String EXTRA_MESSAGE8 = "com.gyula.recipebook.MESSAGE8";

    EditText editText0, editText2, editText3, editText4, editText7, editText8;
    Spinner spinner1, spinner2, spinner3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipes);
        editText0 = findViewById(R.id.updCategories);
        spinner1 = findViewById(R.id.updSampleType);
        editText2 = findViewById(R.id.updTitle);
        editText3 = findViewById(R.id.updShortStory);
        editText4 = findViewById(R.id.updTime);
        spinner2 = findViewById(R.id.updTimeType);
        spinner3 = findViewById(R.id.updDifficulty);
        editText7 = findViewById(R.id.updIngredients);
        editText8 = findViewById(R.id.updInstructions);

        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sampleTypes, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.timeTypes, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);

        spinner3.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.difficulties, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter2);
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, ShowRecipesActivity.class);


        String categories = editText0.getText().toString();
        String sampleType = spinner1.getSelectedItem().toString();
        String title = editText2.getText().toString();
        String shortStory = editText3.getText().toString();
        String time = editText4.getText().toString();
        String timeType = spinner2.getSelectedItem().toString();
        String difficulty = spinner3.getSelectedItem().toString();
        String ingredients = editText7.getText().toString();
        String instructions = editText8.getText().toString();

        intent.putExtra(EXTRA_MESSAGE0, categories);
        intent.putExtra(EXTRA_MESSAGE1, sampleType);
        intent.putExtra(EXTRA_MESSAGE2, title);
        intent.putExtra(EXTRA_MESSAGE3, shortStory);
        intent.putExtra(EXTRA_MESSAGE4, time);
        intent.putExtra(EXTRA_MESSAGE5, timeType);
        intent.putExtra(EXTRA_MESSAGE6, difficulty);
        intent.putExtra(EXTRA_MESSAGE7,ingredients);
        intent.putExtra(EXTRA_MESSAGE8, instructions);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, ShowRecipesActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
