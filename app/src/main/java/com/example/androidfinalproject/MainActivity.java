package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private MaterialTextView main_LBL_title;
    private AppCompatEditText main_ET_text;
    private MaterialButton main_BTN_update;


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
        main_BTN_update.setOnClickListener(v -> {
            changeTitle("title");
        });
    }

    private void changeTitle(String title) {
    }

    private void findViews() {
        main_LBL_title = findViewById(R.id.main_LBL_title);
        main_ET_text = findViewById(R.id.main_ET_text);
        main_BTN_update = findViewById(R.id.main_BTN_update);
    }
}