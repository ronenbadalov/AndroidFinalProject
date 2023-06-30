package com.example.androidfinalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidfinalproject.Interfaces.RecipeCallback;
import com.example.androidfinalproject.Models.Recipe;
import com.example.androidfinalproject.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipes;
    private RecipeCallback recipeCallback;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes, RecipeCallback recipeCallback) {
        this.context = context;
        this.recipes = recipes;
        this.recipeCallback = recipeCallback;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = getItem(position);
        holder.recipe_name.setText((recipe.getName()));
        holder.preperation_time.setText(recipe.getCookingTime() + " mins");
        holder.date.setText(recipe.getTimestamp());
        Glide.with(context).load(recipe.getImagePath()).circleCrop().into(holder.image);
        holder.recipe_item_layout.setOnClickListener(v -> {
            recipeCallback.onRecipeClick(recipe);
        });
    }

    @Override
    public int getItemCount() {
        return this.recipes == null ? 0 : recipes .size();
    }

    private Recipe getItem(int position) {
        return this.recipes.get(position);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView recipe_name;
        private MaterialTextView preperation_time;
        private MaterialTextView date;
        private RelativeLayout recipe_item_layout;
        private ImageView image;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_name = itemView.findViewById(R.id.recipe_name);
            preperation_time = itemView.findViewById(R.id.preperation_time);
            date = itemView.findViewById(R.id.date);
            recipe_item_layout = itemView.findViewById(R.id.recipe_item_layout);
            image = itemView.findViewById(R.id.recipe_image);
        }
    }
}