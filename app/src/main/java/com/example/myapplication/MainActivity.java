package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFoodAddedListener {

    private TextView totalCaloriesTextView;
    private TextView goalCaloriesTextView;
    private ProgressBar caloriesProgressBar;
    private Button editGoalButton;

    private int totalConsumedCalories = 0;
    private static final int REQUEST_SET_GOAL = 1;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalCaloriesTextView = findViewById(R.id.totalCaloriesTextView);
        goalCaloriesTextView = findViewById(R.id.goalCaloriesTextView);
        caloriesProgressBar = findViewById(R.id.caloriesProgressBar);
        editGoalButton = findViewById(R.id.editGoalButton);

        ListView addedItemsListView = findViewById(R.id.addedItemsListView);
        List<FoodItem> addedFoodItems = NutritionManager.getInstance().getAddedFoodItems();
        ArrayAdapter<FoodItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addedFoodItems);
        addedItemsListView.setAdapter(adapter);

        totalConsumedCalories = NutritionManager.getInstance().getTotalConsumedCalories();
        int userGoal = NutritionManager.getInstance().getUserGoal();
        updateGoalProgress(userGoal);
        updateTotalCaloriesView();

        showSetGoalDialogIfNeeded();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    return true;
                } else if (itemId == R.id.action_search) {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    return true;
                } else if (itemId == R.id.action_profile) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });

        editGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetGoalDialog(); // Show the set goal dialog
            }
        });
    }

    private void updateGoalProgress(int newGoalCalories) {
        goalCaloriesTextView.setText(String.valueOf(newGoalCalories));
        caloriesProgressBar.setMax(newGoalCalories);
        caloriesProgressBar.setProgress(0);
    }

    private void updateTotalCaloriesView() {
        totalCaloriesTextView.setText(String.valueOf(totalConsumedCalories));
        caloriesProgressBar.setProgress(totalConsumedCalories);
    }

    private void showSetGoalActivity() {
        Intent intent = new Intent(MainActivity.this, SetGoalActivity.class);
        startActivityForResult(intent, REQUEST_SET_GOAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SET_GOAL && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("goalCalories")) {
                int newGoalCalories = data.getIntExtra("goalCalories", 0);
                updateGoalProgress(newGoalCalories);
                NutritionManager.getInstance().setUserGoal(newGoalCalories);
            }
        }
    }

    private void showSetGoalDialogIfNeeded() {
        int userGoal = NutritionManager.getInstance().getUserGoal();
        if (userGoal == 0) {
            showSetGoalActivity();
        }
    }

    @Override
    public void onFoodAdded(double addedCalories) {
        totalConsumedCalories += addedCalories;
        updateTotalCaloriesView();
        saveTotalConsumedCalories();
    }

    private void saveTotalConsumedCalories() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("totalConsumedCalories", totalConsumedCalories);
        editor.apply();
    }

    private void showSetGoalDialog() {
        if (dialog == null) {
            dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_set_goal);
            dialog.setCancelable(true);

            EditText goalCaloriesEditText = dialog.findViewById(R.id.goalCaloriesEditText);
            Button setGoalButton = dialog.findViewById(R.id.setGoalButton);

            setGoalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText goalEditText = dialog.findViewById(R.id.goalCaloriesEditText);
                    String goalString = goalEditText.getText().toString();
                    if (!goalString.isEmpty()) {
                        int newGoalCalories = Integer.parseInt(goalString);
                        updateGoalProgress(newGoalCalories);
                        NutritionManager.getInstance().setUserGoal(newGoalCalories);
                        updateTotalCaloriesView(); // Update the total calories view
                    }
                    dialog.dismiss();
                }
            });
        }

        // Show the dialog
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

}
