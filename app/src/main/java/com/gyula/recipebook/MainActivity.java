package com.gyula.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity{
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;
    private static final int RC_SIGN_IN = 123;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    int tag = 1;
    EditText userNameET;
    EditText passwordET;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseFirestore fdb;
    Button d, e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fdb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userNameET = findViewById(R.id.editTextUserName);
        passwordET =findViewById(R.id.editTextPassword);

        preferences= getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("503648564263-gaoo707otj6b71hksdn4f47ufqguae35.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Log.i(LOG_TAG, "onCreate");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(LOG_TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(LOG_TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(LOG_TAG, "signInWithCredential:success");
                            Intent showRecipes = new Intent(MainActivity.this, ShowRecipesActivity.class);

                            showLauncher.launch(showRecipes);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(LOG_TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

//    public void login(View view) { ;
//
//        String userName = userNameET.getText().toString();
//        String password = passwordET.getText().toString();
//        d=(Button)findViewById(R.id.loginButton);
//
//        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, task -> {
//            if(task.isSuccessful()){
//                Log.d(LOG_TAG, "User loged in successfully");
//
//                Log.d(LOG_TAG, "signInWithCredential:success");
//                Intent showRecipes = new Intent(MainActivity.this, ShowRecipesActivity.class);
//
//                showLauncher.launch(showRecipes);
//            } else {
//                Log.d(LOG_TAG, "User log in fail");
//                Toast.makeText(MainActivity.this, "User log in fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public void vendeg(View view) {
        Intent showRecipes = new Intent(MainActivity.this, ShowRecipesActivity.class);

        showLauncher.launch(showRecipes);
    }

    public void login(View view) {
        Intent showRecipes = new Intent(MainActivity.this, ShowRecipesActivity.class);

        showLauncher.launch(showRecipes);
    }

    public void register(View view) {
        e=(Button)findViewById(R.id.registerButton);
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);

        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(LOG_TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName",userNameET.getText().toString());
        editor.putString("password", passwordET.getText().toString());
        editor.apply();


        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "onResume");
    }

    private void addDataToFirestore(String courseName, String courseDescription, String courseDuration) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbRecipes = fdb.collection("recipe");

        // adding our data to our courses object class.
//        Recipe recipes = new Recipe(e);

        // below method is use to add data to Firebase Firestore.
//        dbRecipes.add(recipes).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                // after the data addition is successful
//                // we are displaying a success toast message.
//                Toast.makeText(MainActivity.this, "Your Recipe has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // this method is called when the data addition process is failed.
//                // displaying a toast message when data addition is failed.
//                Toast.makeText(MainActivity.this, "Fail to add recipe \n" + e, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void loginWithGoogle(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

//        googleSingInLauncher.launch(signInIntent);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    ActivityResultLauncher<Intent> showLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                }
            });

    ActivityResultLauncher<Intent> googleSingInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
//                    Intent showRecipes = new Intent(MainActivity.this, ShowRecipesActivity.class);
//                    showLauncher.launch(showRecipes);

                }
            });
}