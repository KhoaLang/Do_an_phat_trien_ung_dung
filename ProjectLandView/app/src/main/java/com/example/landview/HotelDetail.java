package com.example.landview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landview.Hotel.Hotel;
import com.example.landview.chung.SliderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelDetail extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Hotel hotel;

    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;
    private Handler sliderHandler = new Handler();

    private TextView tvImageCount;
    private TextView tvName;
    private RatingBar ratingBar;
    private TextView tvTotalRate;
    private TextView tvPrice;
    private TextView tvDescription;
    private TextView tvAddress;

    private boolean isExpand =false;

    private void initUI() {
        tvImageCount = findViewById(R.id.tv_hotel_image_count);
        tvName = findViewById(R.id.tv_hotel_name);
        ratingBar = findViewById(R.id.rb_hotel);
        tvTotalRate = findViewById(R.id.tv_hotel_total_rate);
        tvPrice =findViewById(R.id.tv_hotel_price);
        tvDescription = findViewById(R.id.tv_hotel_description);
        tvAddress = findViewById(R.id.tv_hotel_address);

        viewPager2 = findViewById(R.id.vp2_hotel);
    }

    private void getHotelData(){
        Intent intent = getIntent();
        hotel = (Hotel) intent.getSerializableExtra("hotel");
        if(hotel == null){
            Toast.makeText(HotelDetail.this, "Hotel is null", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        //ánh xạ view
        initUI();
        getHotelData();

        tvImageCount.setText(1 + "/" + hotel.getImages());
        sliderAdapter = new SliderAdapter(hotel.getImages());
        setUpViewPager2(sliderAdapter);

        tvName.setText(hotel.getName());
        tvAddress.setText(hotel.getAddress());
        tvPrice.setText("1.0000.000 VNĐ");
        tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isExpand){
                    isExpand = true;
                    tvDescription.setLines(tvDescription.getLineCount());
                } else {
                    isExpand = false;
                    tvDescription.setLines(4);
                }

            }
        });

        getRating();





    }

    private void setUpViewPager2(SliderAdapter sliderAdapter){
        viewPager2.setAdapter(sliderAdapter);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvImageCount.setText((position + 1) + "/" + hotel.getImages().size());
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };


    private void getRating() {
        db.collection("landscapes")
                .document(hotel.getId()).collection("review")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                            if(querySnapshot.size() ==0){
                                ratingBar.setRating(5f);
                                tvTotalRate.setText("0 đánh giá");
                            }
                            else {
                                float rate = 0;
                                int count =0;
                                for(DocumentSnapshot document : querySnapshot){
                                    double rating = document.getDouble("rating");
                                    rate += rating;
                                    count ++;
                                }
                                ratingBar.setRating(rate/count);
                                tvTotalRate.setText(String.valueOf(count) + " đánh giá");
                            }
                        }
                    }
                });

    }

  /*  private void gotoListHotel() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.booking.com/budget/city/vn/hanoi.vi.html"));
        startActivity(intent);
    }
*/


}