package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NutritionResponse {

    @SerializedName("common")
    private Food[] commonFoods;

    @SerializedName("branded")
    private Food[] brandedFoods;

    public Food[] getCommonFoods() {
        return commonFoods;
    }

    public Food[] getBrandedFoods() {
        return brandedFoods;
    }

    public static class Food implements Parcelable {

        @SerializedName("food_name")
        private String foodName;

        @SerializedName("serving_unit")
        private String servingUnit;

        @SerializedName("brand_name")
        private String brandName;

        @SerializedName("serving_qty")
        private String servingQty;

        @SerializedName("nf_calories")
        private int calories;

        @SerializedName("photo")
        private Photo photo;

        // Constructors, getters, and setters for other fields...

        // Parcelable implementation
        protected Food(Parcel in) {
            foodName = in.readString();
            servingUnit = in.readString();
            brandName = in.readString();
            servingQty = in.readString();
            calories = in.readInt();
            photo = in.readParcelable(Photo.class.getClassLoader());
        }

        public static final Creator<Food> CREATOR = new Creator<Food>() {
            @Override
            public Food createFromParcel(Parcel in) {
                return new Food(in);
            }

            @Override
            public Food[] newArray(int size) {
                return new Food[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(foodName);
            dest.writeString(servingUnit);
            dest.writeString(brandName);
            dest.writeString(servingQty);
            dest.writeInt(calories);
            dest.writeParcelable(photo, flags);
        }


        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getServingUnit() {
            return servingUnit;
        }

        public void setServingUnit(String servingUnit) {
            this.servingUnit = servingUnit;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getServingQty() {
            return servingQty;
        }

        public void setServingQty(String servingQty) {
            this.servingQty = servingQty;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }
    }

    public static class Photo implements Parcelable {

        @SerializedName("thumb")
        private String thumb;

        public String getThumb() {
            return thumb;
        }

        // Parcelable implementation for Photo
        protected Photo(Parcel in) {
            thumb = in.readString();
        }

        public static final Creator<Photo> CREATOR = new Creator<Photo>() {
            @Override
            public Photo createFromParcel(Parcel in) {
                return new Photo(in);
            }

            @Override
            public Photo[] newArray(int size) {
                return new Photo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(thumb);
        }
    }
}
