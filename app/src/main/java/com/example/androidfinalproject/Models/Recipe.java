package com.example.androidfinalproject.Models;

public class Recipe {
    private String id ="";
    private String name = "";
    private int cookingTime = 0;
    private int servings = 0;
    private String ingredients = "";
    private String preparationSteps = "";
    private String imagePath = "";
    private String imageName="";
    private String timestamp = "";
    private String userId = "";
    private String createdAt= "";

    public Recipe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
                ", imageName='" + imageName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public void toRecipe(String recipeString){
        String tempString = recipeString;
        tempString = tempString.replace("Recipe{", "");
        tempString = tempString.replace("}", "");
        tempString = tempString.replace("'", "");

        // Split the string into parts
        String[] parts = tempString.split(", ");

        // Set each field using the parts
        for (String part : parts) {
            String[] keyValue = part.split("=");

            switch (keyValue[0]) {
                case "name":
                    this.setName(keyValue[1]);
                    break;
                case "cookingTime":
                    this.setCookingTime(Integer.parseInt(keyValue[1]));
                    break;
                case "servings":
                    this.setServings(Integer.parseInt(keyValue[1]));
                    break;
                case "ingredients":
                    this.setIngredients(keyValue[1]);
                    break;
                case "preparationSteps":
                    this.setPreparationSteps(keyValue[1]);
                    break;
                case "imagePath":
                    this.setImagePath(keyValue[1]+"="+keyValue[2]+"="+keyValue[3]);
                    break;
                case "imageName":
                    this.setImageName(keyValue[1]);
                    break;
                case "timestamp":
                    this.setTimestamp(keyValue[1]);
                    break;
                case "createdAt":
                    this.setCreatedAt(keyValue[1]);
                    break;
            }
        }
    }
}