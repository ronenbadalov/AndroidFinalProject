package com.example.androidfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.androidfinalproject.Models.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewRecipeActivity extends AppCompatActivity {
    private AppCompatEditText nameEditText;
    private AppCompatEditText cookingTimeEditText;
    private AppCompatEditText servingsEditText;
    private AppCompatEditText ingredientsEditText;
    private AppCompatEditText stepsEditText;
    private MaterialButton saveButton;
    private FirebaseFirestore db;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        findViews();
        initViews();
        db = FirebaseFirestore.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yy");
    }

    private void initViews() {
        saveButton.setOnClickListener(v -> {
            saveRecipe();
        });
    }

    private void saveRecipe(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Recipe recipe = new Recipe();
        recipe.setName(nameEditText.getText().toString());
        recipe.setCookingTime(Integer.parseInt(cookingTimeEditText.getText().toString()));
        recipe.setServings(Integer.parseInt(servingsEditText.getText().toString()));
        recipe.setIngredients(ingredientsEditText.getText().toString());
        recipe.setPreparationSteps(stepsEditText.getText().toString());
        recipe.setTimestamp(dateFormat.format(new Date()));
        recipe.setUserId(firebaseAuth.getUid());
        // Add a new document with a generated ID
        db.collection("recipes")
                .add(recipe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("ADDED", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FAILED", "Error adding document", e);
                    }
                });
    }

    private void findViews() {
        nameEditText = findViewById(R.id.nameEditText);
        cookingTimeEditText = findViewById(R.id.cookingTimeEditText);
        servingsEditText = findViewById(R.id.servingsEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        stepsEditText = findViewById(R.id.stepsEditText);
        saveButton = findViewById(R.id.saveButton);
    }
}