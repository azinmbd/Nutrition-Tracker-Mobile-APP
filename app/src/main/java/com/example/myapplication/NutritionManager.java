package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class NutritionManager {
    private static NutritionManager instance;
    private int totalConsumedCalories = 0;
    private int userGoal = 0; // Default goal
    private List<FoodItem> addedFoodItems = new ArrayList<>();
    private NutritionManager() {
    }

    public static synchronized NutritionManager getInstance() {
        if (instance == null) {
            instance = new NutritionManager();
        }
        return instance;
    }

    public int getTotalConsumedCalories() {
        return totalConsumedCalories;
    }

    public void updateTotalCalories(int addedCalories) {
        totalConsumedCalories += addedCalories;
    }

    public int getUserGoal() {
        return userGoal;
    }

    public void setUserGoal(int goal) {
        userGoal = goal;
    }

    public void addFoodItem(FoodItem foodItem) {
        addedFoodItems.add(foodItem);
    }

    public List<FoodItem> getAddedFoodItems() {
        return addedFoodItems;
    }
}

