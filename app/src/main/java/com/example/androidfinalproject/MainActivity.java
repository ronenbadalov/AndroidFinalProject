package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity {
    private MaterialTextView main_LBL_title;
    private AppCompatEditText main_ET_text;
    private MaterialButton main_BTN_add;
    private MaterialButton main_BTN_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();

    }

    private void initViews() {
        Intent intent = getIntent();
        main_LBL_title.setText(intent.getStringExtra("username"));
        main_BTN_add.setOnClickListener(v -> {
            moveToAddRecipeActivity();
        });
        main_BTN_logout.setOnClickListener(v -> {
            logout();
        });
    }

    private void logout(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void moveToAddRecipeActivity(){
        Intent intent = new Intent(this, NewRecipeActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        main_LBL_title = findViewById(R.id.main_LBL_title);
        main_ET_text = findViewById(R.id.main_ET_text);
        main_BTN_add = findViewById(R.id.main_BTN_add);
        main_BTN_logout = findViewById(R.id.main_BTN_logout);
    }
}