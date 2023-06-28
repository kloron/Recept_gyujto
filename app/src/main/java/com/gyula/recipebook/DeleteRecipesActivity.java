package com.gyula.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteRecipesActivity extends AppCompatActivity {
    EditText editText;
    DatabaseHandler dbHandler;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_delete_recipes);

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ShowRecipesActivity.class);
        if(Integer.parseInt(String.valueOf(editText)) >0){
            dbHandler.removeRecipes(Integer.parseInt(String.valueOf(editText)));
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        else{
            Toast.makeText(this, "Nem megfelelő ID törléshez!", Toast.LENGTH_SHORT).show();

        }


    }

    public void goBack(View view) {
        Intent intent = new Intent(this, ShowRecipesActivity.class);
        startActivity(intent);
    }

}
