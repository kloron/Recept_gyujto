package com.gyula.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OpenRecipeActivity extends AppCompatActivity {
    TextView category, sampleType, title, shortStory, timeType, difficulty, ingredients, instructions, time;

    EditText editText;
    ArrayList<Recipe> list;
    DatabaseHandler dbHandler;
    WebView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_recipe);

//        int poz = Integer.parseInt(getIntent().getStringExtra(EXTRA_MESSAGE30));
        dbHandler = new DatabaseHandler(OpenRecipeActivity.this);
        list = dbHandler.readRecipes();
        int poz = list.size()-1;

        category = findViewById(R.id.showCategories);
        sampleType = findViewById(R.id.showSampleType);
        title = findViewById(R.id.showTitle);
        shortStory = findViewById(R.id.showShortStory);
        time = findViewById(R.id.showTime);
        timeType= findViewById(R.id.showTimeType);
        difficulty= findViewById(R.id.showDifficulty);
        ingredients = findViewById(R.id.showIngredients);
        instructions = findViewById(R.id.showInstructions);

        category.setText(list.get(poz).categories);
        sampleType.setText(list.get(poz).sampleType);
        title.setText(list.get(poz).title);
        shortStory.setText(list.get(poz).shortStory);
        time.setText(String.valueOf(list.get(poz).time));
        timeType.setText(list.get(poz).timeType);
        difficulty.setText(list.get(poz).difficulty);
        ingredients.setText(list.get(poz).ingredients);
        instructions.setText(list.get(poz).instructions);

        image = findViewById(R.id.showPicture);
        image.loadUrl("https://firebasestorage.googleapis.com/v0/b/recipebook-42d45.appspot.com/o/paprikas-krumpli.jpg?alt=media&token=603ab89a-5089-4b1f-979b-a855180f9811");

    }



    public void goBack(View view) {
        Intent intent = new Intent(this, ShowRecipesActivity.class);
        startActivity(intent);
    }

    public void update(View view) {
        Intent intent = new Intent(this, UpdateRecipesActivity.class);
        startActivity(intent);
    }
    public void delete(View view) {
        Intent intent = new Intent(this, DeleteRecipesActivity.class);
        startActivity(intent);
    }
}

