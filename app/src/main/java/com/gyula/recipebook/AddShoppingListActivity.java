package com.gyula.recipebook;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShoppingListActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();



    }

    public void writeNewShoppinglist(String Id, String list) {
        Shoplist shoplist = new Shoplist(list);

        mDatabase.child("shoppinglist").child(Id).setValue(shoplist);
    }
}
