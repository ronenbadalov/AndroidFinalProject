package com.example.androidfinalproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidfinalproject.Models.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


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
    private Recipe recipe;
    private String recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        Intent intent = getIntent();
        String recipe_string = intent.getStringExtra("RECIPE_DATA");
        this.recipeId = intent.getStringExtra("RECIPE_ID");

        this.recipe = new Recipe();
        this.recipe.toRecipe(recipe_string);
        findViews();
        initViews();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    private void initViews() {
        nameEditText.setText(this.recipe.getName());
        cookingTimeEditText.setText(String.valueOf(this.recipe.getCookingTime()));
        servingsEditText.setText(String.valueOf(this.recipe.getServings()));
        ingredientsEditText.setText(this.recipe.getIngredients());
        stepsEditText.setText(this.recipe.getPreparationSteps());
        Glide.with(this).load(this.recipe.getImagePath()).placeholder(R.drawable.camera_placeholder).dontAnimate().into(imageView);
        deleteButton.setOnClickListener(v -> {
            deleteRecipe();
            moveBackToMyRecipes();
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
        imageView = findViewById(R.id.view_recipe_image);
    }

    private void deleteRecipe(){
        db.collection("recipes").document(recipeId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Tag", "DocumentSnapshot successfully deleted!");

                // If the document delete is successful, delete the image from Firebase Storage
                StorageReference imageRef = storage.getReference().child("images/"+recipe.getImageName());

                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Tag", "Image successfully deleted!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Tag", "Error deleting image", e);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "Error deleting document", e);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}