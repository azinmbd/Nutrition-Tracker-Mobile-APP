package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private SearchView searchView;
    private List<NutritionResponse.Food> foodList = new ArrayList<>();
    private double totalCalories = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodAdapter = new FoodAdapter(this, foodList);
        recyclerView.setAdapter(foodAdapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    makeApiRequest(newText);
                } else {
                    foodList.clear();
                    foodAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        makeApiRequest("hamburger");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    startActivity(new Intent(SearchActivity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.action_search) {
                    return true; // Already in the search activity, do nothing
                } else if (itemId == R.id.action_profile) {
                    startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_search);

        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onPlusIconClick(NutritionResponse.Food food) {
                showSnackbar("Food added: " + food.getFoodName());
                NutritionManager.getInstance().updateTotalCalories((int) food.getCalories());
                NutritionManager.getInstance().addFoodItem(new FoodItem(food.getFoodName(), (int) food.getCalories()));
            }

            @Override
            public void onItemClick(NutritionResponse.Food food) {
                Intent intent = new Intent(SearchActivity.this, FoodDetailActivity.class);
                intent.putExtra("food", food);
                startActivity(intent);
            }
        });


    }

    private void makeApiRequest(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NutritionixApi nutritionixApi = retrofit.create(NutritionixApi.class);
        Call<NutritionResponse> call = nutritionixApi.getNutritionInfo("40d04b6f", "e5aabbd585844bab97507f6a065ee0f7", query);

        call.enqueue(new Callback<NutritionResponse>() {
            @Override
            public void onResponse(Call<NutritionResponse> call, Response<NutritionResponse> response) {
                if (response.isSuccessful()) {
                    NutritionResponse nutritionResponse = response.body();
                    if (nutritionResponse != null) {
                        NutritionResponse.Food[] brandedFoods = nutritionResponse.getBrandedFoods();
                        if (brandedFoods != null && brandedFoods.length > 0) {
                            foodAdapter.updateData(Arrays.asList(brandedFoods));
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NutritionResponse> call, Throwable t) {
            }
        });
    }

    private void showSnackbar(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor)); // Set background color
        snackbar.setActionTextColor(getResources().getColor(R.color.accentColor)); // Set action text color

        TextView snackbarText = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarText.setTextColor(getResources().getColor(R.color.white));

        ViewCompat.setElevation(snackbarView, getResources().getDimension(R.dimen.design_snackbar_elevation));

        snackbar.show();
    }
}
