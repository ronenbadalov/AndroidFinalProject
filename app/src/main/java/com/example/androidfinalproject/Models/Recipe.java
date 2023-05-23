package com.example.androidfinalproject.Models;

public class Recipe {
    private String name = "";
    private int cookingTime = 0;
    private int servings = 0;
    private String ingredients = "";
    private String preparationSteps = "";
    private String imagePath = "";
    private String timestamp = "";
    private String userId = "";


    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(String preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name  + '\'' +
                ", cookingTime=" + cookingTime +
                ", servings=" + servings +
                ", ingredients='" + ingredients  + '\'' +
                ", preparationSteps='" + preparationSteps + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}