package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SetGoalActivity extends AppCompatActivity {

    private EditText getNutrition;
    private Button setGoalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        getNutrition = findViewById(R.id.goalCaloriesEditText);
        setGoalButton = findViewById(R.id.setGoalButton);

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalCaloriesString = getNutrition.getText().toString();
                if (!goalCaloriesString.isEmpty()) {
                    int newGoalCalories = Integer.parseInt(goalCaloriesString);
                    Intent intent = new Intent();
                    intent.putExtra("goalCalories", newGoalCalories);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}

