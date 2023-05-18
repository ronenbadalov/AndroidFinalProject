package com.example.androidfinalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfinalproject.Fragments.ListFragment;

public class MyRecipesActivity extends AppCompatActivity {
    private ListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        initFragments();
        beginTransactions();
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_list_FRAME, listFragment).commit();
    }

    private void initFragments() {
        listFragment = new ListFragment();
    }

}
