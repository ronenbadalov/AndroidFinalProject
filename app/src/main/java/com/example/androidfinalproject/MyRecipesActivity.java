package com.example.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidfinalproject.Fragments.ListFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyRecipesActivity extends AppCompatActivity {
    private ListFragment listFragment;
    private ShapeableImageView logout_img;
    private MaterialTextView greet;
    private FloatingActionButton add_recipe;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        findViews();
        initViews();

        initFragments();
        beginTransactions();
    }

    private void initViews() {
        greet.setText("Hello "+user.getDisplayName()+"!");
        logout_img.setOnClickListener(v -> {
            logout();
        });
        add_recipe.setOnClickListener(v -> {
            moveToAddRecipeActivity();
        });
    }

    private void findViews() {
        logout_img = findViewById(R.id.logout_img);
        greet = findViewById(R.id.greet);
        add_recipe = findViewById(R.id.add_recipe);
    }

    private void logout(){
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                // user is now signed out
                startActivity(new Intent(MyRecipesActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void moveToAddRecipeActivity(){
        Intent intent = new Intent(this, NewRecipeActivity.class);
        startActivity(intent);
        finish();
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_list_FRAME, listFragment).commit();
    }

    private void initFragments() {
        listFragment = new ListFragment();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
