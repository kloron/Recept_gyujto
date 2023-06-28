package com.gyula.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowRecipesActivity extends AppCompatActivity {

    String category, sampleType, title, shortStory, timeType, difficulty, ingredients, instructions;

    EditText editText;
    public static final String EXTRA_MESSAGE30 = "com.gyula.recipebook.MESSAGE30";
    int time, poz;
    ArrayList<Recipe> list;
    DatabaseHandler dbHandler;

    RecycleViewAdapter recipeAdapter;
    RecyclerView recipeRV;

    FirebaseDatabase firebaseDatabase;
    Recipe recipe;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        firebaseDatabase = FirebaseDatabase.getInstance();
        recipe = new Recipe();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Recipes");
        editText = findViewById(R.id.searchListview);

        dbHandler = new DatabaseHandler(ShowRecipesActivity.this);
        list = dbHandler.readRec();
        recipeAdapter = new RecycleViewAdapter(list, ShowRecipesActivity.this);
        recipeRV = findViewById(R.id.recyclerViewSQL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowRecipesActivity.this, RecyclerView.VERTICAL, false);
        recipeRV.setLayoutManager(linearLayoutManager);
        recipeRV.setAdapter(recipeAdapter);


                recipeRV.addOnItemTouchListener(new RecyclerTouchListener(ShowRecipesActivity.this, recipeRV, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        poz= position;
                        sendMessage(view);

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        poz= position;
                        sendMessage(view);
                    }
                }));

        filter(String.valueOf(editText));
            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // lenyíló menüsáv létrehozása
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.addnewrecipe) {
            goToAddNewRecipe(item);
        }


        return super.onOptionsItemSelected(item);
    }

    public void goToAddNewRecipe(MenuItem menuItem) {
        Intent intent = new Intent(this, AddRecipesActivity.class);
        addrecipeLauncher.launch(intent);
    }
    public void deleteRecipe(MenuItem menuItem) {
        Intent intent = new Intent(this, DeleteRecipesActivity.class);
        delrecipeLauncher.launch(intent);
    }
    public void updateRecipe(MenuItem menuItem) {
        Intent intent = new Intent(this, UpdateRecipesActivity.class);
        updaterecipeLauncher.launch(intent);
    }
    public void openOcr(MenuItem menuItem) {
        Intent intent = new Intent(this, OpenOCRActivity.class);
        ocrLauncher.launch(intent);
    }
    public void addOcr(MenuItem menuItem) {
        Intent intent = new Intent(this, OCRAddActivity.class);
        addOCRLauncher.launch(intent);
    }
    public void goCookbook(MenuItem menuItem) {
        Intent intent = new Intent(this, CookBookActivity.class);
        cookbookLauncher.launch(intent);
    }
    public void goShoplist(MenuItem menuItem) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        shoplistLauncher.launch(intent);
    }
    public void deleteAll(MenuItem menuItem) {
        Intent intent = new Intent(this, DeleteAllRecipeActivity.class);
        deleteALLrecipeLauncher.launch(intent);
    }


    ActivityResultLauncher<Intent> addrecipeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();


                        if (data != null) {
                            category = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE0);
                            sampleType = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE1);
                            title = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE2);
                            shortStory = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE3);
                            time = Integer.parseInt(data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE4));
                            timeType = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE5);
                            difficulty = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE6);
                            ingredients = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE7);
                            instructions = data.getStringExtra(AddRecipesActivity.EXTRA_MESSAGE8);
                        }

                        dbHandler.addRecepies(category, sampleType, title, shortStory, time, timeType, difficulty, ingredients, instructions);
                        recipe.setCategories(category);
                        recipe.setSampleType(sampleType);
                        recipe.setTitle(title);
                        recipe.setShortStory(shortStory);
                        recipe.setTime(time);
                        recipe.setTimeType(timeType);
                        recipe.setDifficulty(difficulty);
                        recipe.setIngredients(ingredients);
                        recipe.setInstructions(instructions);
                        databaseReference.setValue(recipe);

                        Toast.makeText(ShowRecipesActivity.this, "Sikeres recept hozzáadás!", Toast.LENGTH_LONG).show();

                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                        Toast.makeText(ShowRecipesActivity.this, "Sikertelen recept hozzáadás!", Toast.LENGTH_LONG).show();

                    }

                }

            });

    ActivityResultLauncher<Intent> showrecipeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Toast.makeText(ShowRecipesActivity.this, "Sikeres recept megnyitás!", Toast.LENGTH_LONG).show();

                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen recept megnyitás!", Toast.LENGTH_LONG).show();

                }

            });

    ActivityResultLauncher<Intent> delrecipeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    dbHandler.removeRecipes(list.size()-1);
                    Toast.makeText(ShowRecipesActivity.this, "Sikeres recept törlés!", Toast.LENGTH_LONG).show();

                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen recept törlés!", Toast.LENGTH_LONG).show();

                }

            });

    ActivityResultLauncher<Intent> updaterecipeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Toast.makeText(ShowRecipesActivity.this, "Sikeres recept módosítás!", Toast.LENGTH_LONG).show();

                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen recept módosítás!", Toast.LENGTH_LONG).show();

                }

            });

    ActivityResultLauncher<Intent> ocrLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Toast.makeText(ShowRecipesActivity.this, "Sikeres recept megnyitás!", Toast.LENGTH_LONG).show();

                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen recept megnyitás!", Toast.LENGTH_LONG).show();

                }

            });

    ActivityResultLauncher<Intent> addOCRLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Toast.makeText(ShowRecipesActivity.this, "Sikeres recept megnyitás!", Toast.LENGTH_LONG).show();

                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen recept megnyitás!", Toast.LENGTH_LONG).show();

                }

            });

    ActivityResultLauncher<Intent> cookbookLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Toast.makeText(ShowRecipesActivity.this, "Sikeres receptgyüjtő megnyitás!", Toast.LENGTH_LONG).show();

                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen receptgyüjtő megnyitás!", Toast.LENGTH_LONG).show();

                }

            });

    ActivityResultLauncher<Intent> shoplistLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Toast.makeText(ShowRecipesActivity.this, "Sikeres shoplist megnyitás!", Toast.LENGTH_LONG).show();

                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen shoplist megnyitás!", Toast.LENGTH_LONG).show();

                }

            });

    ActivityResultLauncher<Intent> deleteALLrecipeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Toast.makeText(ShowRecipesActivity.this, "Sikeres törlés!", Toast.LENGTH_LONG).show();
                    dbHandler.removeTable();
                }
                if (result.getResultCode() == Activity.RESULT_CANCELED) { // ha nem tér vissza rendesen az Activityből, akkor jelezzük
                    Toast.makeText(ShowRecipesActivity.this, "Sikertelen törlés!", Toast.LENGTH_LONG).show();

                }

            });

    public void sendMessage(View view) {
        Intent intent = new Intent(this, OpenRecipeActivity.class);

        intent.putExtra(EXTRA_MESSAGE30, poz);

        setResult(Activity.RESULT_OK, intent);
        finish();
        showrecipeLauncher.launch(intent);

    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Recipe> filteredlist = new ArrayList<Recipe>();

        // running a for loop to compare elements.
        for (Recipe item : list) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "Nincs találat..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recipeAdapter.filterList(filteredlist);
        }
    }

}

