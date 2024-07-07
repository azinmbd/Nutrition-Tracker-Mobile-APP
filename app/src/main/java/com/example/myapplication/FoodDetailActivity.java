package com.example.myapplication;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        NutritionResponse.Food food = getIntent().getParcelableExtra("food");

        if (food != null) {
            TextView foodNameTextView = findViewById(R.id.detailFoodNameTextView);
            TextView servingUnitTextView = findViewById(R.id.detailServingUnitTextView);
            TextView brandNameTextView = findViewById(R.id.detailBrandNameTextView);
            TextView servingQtyTextView = findViewById(R.id.detailServingQtyTextView);
            TextView caloriesTextView = findViewById(R.id.detailCaloriesTextView);
            ImageView foodImageView = findViewById(R.id.detailFoodImageView);
            Button addItemButton = findViewById(R.id.addItemButton);

            foodNameTextView.setText("Food Name: " + food.getFoodName());
            servingUnitTextView.setText("Serving Unit: " + food.getServingUnit());
            brandNameTextView.setText("Brand Name: " + food.getServingQty());
            servingQtyTextView.setText("Serving Quantity: " + food.getServingQty());
            caloriesTextView.setText("Calories: " + food.getCalories());

            Glide.with(this)
                    .load(food.getPhoto().getThumb())
                    .into(foodImageView);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NutritionManager.getInstance().updateTotalCalories((int) food.getCalories());
                    Toast.makeText(FoodDetailActivity.this, "Item added to total calories", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed(); // This will simulate the back button press
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

