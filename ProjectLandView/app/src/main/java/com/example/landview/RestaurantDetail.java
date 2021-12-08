package com.example.landview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.landview.Restaurant.Restaurant;

public class RestaurantDetail extends AppCompatActivity {

    private Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        getRestaurantData();
    }

    private void getRestaurantData(){
        Intent intent = getIntent();
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        if(restaurant == null){
            Toast.makeText(RestaurantDetail.this, "Restaurant object is null", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}