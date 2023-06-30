package com.example.androidfinalproject.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalproject.Adapters.RecipeAdapter;
import com.example.androidfinalproject.Models.Recipe;
import com.example.androidfinalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
import java.util.HashMap;

public class ListFragment extends Fragment {
    private RecyclerView list_LST_recipes;
    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private RecipeAdapter recipeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_recipes.setLayoutManager(linearLayoutManager);
        db.collection("recipes")
                .whereEqualTo("userId", firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<HashMap<String, Object>> documents = new ArrayList<>();
                            Collections.sort(documents, new Comparator<HashMap<String, Object>>() {
                                @Override
                                public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                                    return o1.get("createdAt").toString().compareTo(o2.get("createdAt").toString());
                                }
                            });
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Recipe recipe = new Recipe();
                                recipe.setUserId(document.get("userId").toString());
                                recipe.setName(document.get("name").toString());
                                recipe.setImagePath(document.get("imagePath").toString());
                                recipe.setTimestamp(document.get("timestamp").toString());
                                recipe.setIngredients(document.get("ingredients").toString());
                                recipe.setPreparationSteps(document.get("preparationSteps").toString());
                                recipe.setServings(Integer.parseInt(document.get("servings").toString()));
                                recipe.setCookingTime(Integer.parseInt(document.get("cookingTime").toString()));
                                recipeArrayList.add(recipe);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            recipeAdapter = new RecipeAdapter(getActivity(),recipeArrayList);
                            list_LST_recipes.setAdapter(recipeAdapter);

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });


    }


    private void findViews(View view) {
        list_LST_recipes = view.findViewById(R.id.list_LST_recipes);
    }
}