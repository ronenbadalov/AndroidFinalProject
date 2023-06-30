package com.example.androidfinalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidfinalproject.Models.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View;
import android.widget.Toast;

public class ViewRecipeActivity extends AppCompatActivity {
    private AppCompatEditText nameEditText;
    private AppCompatEditText cookingTimeEditText;
    private AppCompatEditText servingsEditText;
    private AppCompatEditText ingredientsEditText;
    private AppCompatEditText stepsEditText;
    private MaterialButton deleteButton;
    private FirebaseFirestore db;
    private ImageView imageView;
    private FirebaseStorage storage;
    private FirebaseAuth firebaseAuth;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        Intent intent = getIntent();
        String recipe_string = intent.getStringExtra("RECIPE_DATA");
        this.recipe = new Recipe();
        this.recipe.toRecipe(recipe_string);
        findViews();
        initViews();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        firebaseAuth  = FirebaseAuth.getInstance();
    }

    private void initViews() {
        nameEditText.setText(this.recipe.getName());
        cookingTimeEditText.setText(String.valueOf(this.recipe.getCookingTime()));
        servingsEditText.setText(String.valueOf(this.recipe.getServings()));
        ingredientsEditText.setText(this.recipe.getIngredients());
        stepsEditText.setText(this.recipe.getPreparationSteps());
        Glide.with(this).load(recipe.getImagePath()).into(imageView);
        deleteButton.setOnClickListener(v -> {

        });
    }

    private void moveBackToMyRecipes(){
        Intent intent = new Intent(this,MyRecipesActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        nameEditText = findViewById(R.id.viewRecipe_nameEditText);
        cookingTimeEditText = findViewById(R.id.viewRecipe_cookingTimeEditText);
        servingsEditText = findViewById(R.id.viewRecipe_servingsEditText);
        ingredientsEditText = findViewById(R.id.viewRecipe_ingredientsEditText);
        stepsEditText = findViewById(R.id.viewRecipe_stepsEditText);
        deleteButton = findViewById(R.id.deleteButton);
        imageView = findViewById(R.id.viewRecipe_imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveBackToMyRecipes();
    }
}