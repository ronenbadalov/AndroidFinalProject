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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View;
import android.widget.Toast;

public class NewRecipeActivity extends AppCompatActivity {
    private AppCompatEditText nameEditText;
    private AppCompatEditText cookingTimeEditText;
    private AppCompatEditText servingsEditText;
    private AppCompatEditText ingredientsEditText;
    private AppCompatEditText stepsEditText;
    private MaterialButton saveButton;
    private FirebaseFirestore db;
    SimpleDateFormat dateFormat;
    SimpleDateFormat createdAtFormat;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private ImageView imageView;
    private Bitmap imageBitmap;
    private FirebaseStorage storage;
    ActivityResultLauncher<Intent> takePictureResultLauncher;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        findViews();
        initViews();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yy");
        createdAtFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        firebaseAuth  = FirebaseAuth.getInstance();
    }

    private void initViews() {
        saveButton.setOnClickListener(v -> {
            if(nameEditText.getText().toString().equals("") || stepsEditText.getText().toString().equals("") ||    ingredientsEditText.getText().toString().equals("") ||  servingsEditText.getText().toString().equals("") ||  cookingTimeEditText.getText().toString().equals("")){
                Toast.makeText(NewRecipeActivity.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            uploadImageToFirebase();
        });

        takePictureResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            imageBitmap = (Bitmap) extras.get("data");
                            imageView.setImageBitmap(imageBitmap);
                        }
                    }
                }
        );

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    takePictureResultLauncher.launch(takePictureIntent);
                }
            } else {
                Toast.makeText(this, "Camera Permission is Required to Take Photo", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                takePictureResultLauncher.launch(takePictureIntent);
            }
        }
    }
    private void saveRecipe(String imageUrl){

        Recipe recipe = new Recipe();
        recipe.setName(nameEditText.getText().toString());
        recipe.setCookingTime(Integer.parseInt(cookingTimeEditText.getText().toString()));
        recipe.setServings(Integer.parseInt(servingsEditText.getText().toString()));
        recipe.setIngredients(ingredientsEditText.getText().toString());
        recipe.setPreparationSteps(stepsEditText.getText().toString());
        recipe.setTimestamp(dateFormat.format(new Date()));
        recipe.setUserId(firebaseAuth.getUid());
        recipe.setImagePath(imageUrl);
        recipe.setCreatedAt(createdAtFormat.format(new Date()));

        // Add a new document with a generated ID
        db.collection("recipes")
                .add(recipe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("ADDED", "DocumentSnapshot added with ID: " + documentReference.getId());
                        moveBackToMyRecipes();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FAILED", "Error adding document", e);
                    }
                });
    }

    private void uploadImageToFirebase() {
        if (imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            StorageReference storageRef = storage.getReference();
            StorageReference imagesRef = storageRef.child("images/"+firebaseAuth.getUid()+"-" +createdAtFormat.format(new Date())+".jpg");

            UploadTask uploadTask = imagesRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(NewRecipeActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            saveRecipe(imageURL);
                        }
                    });
                }
            });
        } else {
            Toast.makeText(NewRecipeActivity.this, "No image to upload!", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveBackToMyRecipes(){
        Intent intent = new Intent(this,MyRecipesActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        nameEditText = findViewById(R.id.nameEditText);
        cookingTimeEditText = findViewById(R.id.cookingTimeEditText);
        servingsEditText = findViewById(R.id.servingsEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        stepsEditText = findViewById(R.id.stepsEditText);
        saveButton = findViewById(R.id.saveButton);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveBackToMyRecipes();
    }
}