package com.gyula.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteAllRecipeActivity extends AppCompatActivity {
    EditText editText;
    DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_all_recipes);

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ShowRecipesActivity.class);

            dbHandler.removeTable();
        setResult(Activity.RESULT_OK, intent);
            finish();

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, ShowRecipesActivity.class);
        startActivity(intent);
    }

}