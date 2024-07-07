
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private final Context context;
    private final List<NutritionResponse.Food> foodList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onPlusIconClick(NutritionResponse.Food food);
        void onItemClick(NutritionResponse.Food food);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FoodAdapter(Context context, List<NutritionResponse.Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NutritionResponse.Food food = foodList.get(position);

        holder.foodNameTextView.setText(food.getFoodName());
        holder.caloriesTextView.setText("Calories: " + food.getCalories());
        holder.servingUnitTextView.setText("Serving Unit: " + food.getServingUnit());

        Glide.with(context)
                .load(food.getPhoto().getThumb())
                .into(holder.foodImageView);

        holder.plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onPlusIconClick(food);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(food);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public void updateData(List<NutritionResponse.Food> newData) {
        foodList.clear();
        foodList.addAll(newData);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImageView;
        TextView foodNameTextView;
        TextView caloriesTextView;
        TextView servingUnitTextView;
        Button plusIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
            foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
            caloriesTextView = itemView.findViewById(R.id.caloriesTextView);
            servingUnitTextView = itemView.findViewById(R.id.servingUnitTextView);
            plusIcon = itemView.findViewById(R.id.addItemButton);
        }
    }
}
