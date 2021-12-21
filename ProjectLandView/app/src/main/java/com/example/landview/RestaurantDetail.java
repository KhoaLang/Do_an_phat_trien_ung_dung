package com.example.landview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landview.Comment.CommentFragment;
import com.example.landview.Hotel.HotelUtilitiesAdapter;
import com.example.landview.Map.NearbyAndMapFragment;
import com.example.landview.Rating.RatingFragment;
import com.example.landview.Restaurant.Restaurant;
import com.example.landview.chung.SliderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantDetail extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Restaurant restaurant;

    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;
    private Handler sliderHandler = new Handler();

    private TextView tvImageCount;
    private TextView tvName;
    private RatingBar ratingBar;
    private TextView tvTotalRate;
    //    private TextView tvPrice;
//    private TextView tvDescription;
    private TextView tvAddress;

    private RecyclerView rcvResMenu;
    private HotelUtilitiesAdapter resMenuAdapter;
    private ArrayList<String> menuFood = new ArrayList<>();;


    private ArrayList<String> createDefaultMenu(){
        ArrayList<String> foods = new ArrayList<>();
        foods.add("Gà chiên nước mắm");
        foods.add("Cơm chiên");
        foods.add("Rượu vang");
        return foods;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        Toolbar myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Restaurant");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_white_back_24));

        // Lấy dữ liệu từ intent
        getRestaurantData();
        // Khởi tạo view
        initUi();

        menuFood = new ArrayList<>();
        if(!restaurant.getMenu().isEmpty()){
            menuFood = restaurant.getMenu();
        } else {
            menuFood = createDefaultMenu();
        }
        getMenuFood();


        tvImageCount.setText(1 + "/" + restaurant.getImages().size());
        tvName.setText(restaurant.getName());
        tvAddress.setText(restaurant.getAddress());

        sliderAdapter = new SliderAdapter(restaurant.getImages());
        setUpViewPager2(sliderAdapter);

        // getRating();

        getRating();


        createCommentFragment();

        createRatingFragment();

        createMapFragment();
    }

    Menu menu;

    private void checkLike(){
        ArrayList<String> likesList = restaurant.getLikesList();
        String userId = mAuth.getUid();
        if(likesList.contains(userId)){
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.red_heart));
        } else {
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.heart_shadow));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.action_bar_item, menu);
        checkLike();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite:
                likeClick();
                Toast.makeText(RestaurantDetail.this, "Favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(RestaurantDetail.this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case  android.R.id.home:
                this.finish();
                Toast.makeText(RestaurantDetail.this, "Home", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return  true;

    }

    private void likeClick(){
        ArrayList<String> likesList = restaurant.getLikesList();
        String userId = mAuth.getUid();
        DocumentReference hotelRef = db.collection("restaurants").document(restaurant.getId());
        DocumentReference userRef = db.collection("users").document(userId);

        if(likesList.contains(userId)){
            hotelRef.update("likesList", FieldValue.arrayRemove(userId));
            userRef.update("likes", FieldValue.arrayRemove(hotelRef));
            likesList.remove(userId);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.heart_shadow));
        } else {
            hotelRef.update("likesList", FieldValue.arrayUnion(userId));
            userRef.update("likes", FieldValue.arrayUnion(hotelRef));
            likesList.add(userId);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.red_heart));
        }
    }


    private void getMenuFood(){
        resMenuAdapter = new HotelUtilitiesAdapter(RestaurantDetail.this, menuFood);
        rcvResMenu.setAdapter(resMenuAdapter);
        rcvResMenu.setLayoutManager(new LinearLayoutManager(RestaurantDetail.this, LinearLayoutManager.HORIZONTAL,
                false));
        resMenuAdapter.notifyDataSetChanged();
    }

    private void createMapFragment(){
        NearbyAndMapFragment nearbyAndMapFragment = NearbyAndMapFragment
                .newInstance(restaurant.getLatitude(), restaurant.getLongitude());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fcv_map, nearbyAndMapFragment)
                .commit();
    }



    // Khởi tạo các Ui
    private void initUi() {
        viewPager2 = findViewById(R.id.vp2_res);
        tvImageCount = findViewById(R.id.tv_res_image_count);
        tvName = findViewById(R.id.tv_res_name);
        ratingBar = findViewById(R.id.rb_res);
        tvTotalRate = findViewById(R.id.tv_res_total_rate);
        tvAddress = findViewById(R.id.tv_res_address);

        rcvResMenu = findViewById(R.id.rcv_res_menu);

    }

    // Lấy restaurant object từ intent
    private void getRestaurantData() {
        Intent intent = getIntent();
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        if (restaurant == null) {
            Toast.makeText(RestaurantDetail.this, "Restaurant object is null", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /***************** Xử lý ảnh tự động trượt*****************/
    private void setUpViewPager2(SliderAdapter sliderAdapter) {
        viewPager2.setAdapter(sliderAdapter);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvImageCount.setText((position + 1) + "/" + restaurant.getImages().size());
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

    /**********************************************************************************************/


    /********************************* Xử lý lấy rating ****************************************************/
    private void getRating() {
        db.collection("restaurants")
                .document(restaurant.getId()).collection("review")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                        if (error == null) {
                            if (querySnapshot.size() == 0) {
                                ratingBar.setRating(5f);
                                tvTotalRate.setText("0 đánh giá");
                            } else {
                                float rate = 0;
                                int count = 0;
                                for (DocumentSnapshot document : querySnapshot) {
                                    double rating = document.getDouble("rating");
                                    rate += rating;
                                    count++;
                                }
                                ratingBar.setRating(rate / count);
                                tvTotalRate.setText(String.valueOf(count) + " đánh giá");
                            }
                        } else {
                            ratingBar.setRating(5f);
                            tvTotalRate.setText("0 đánh giá");
                        }
                    }
                });
    }

    /**********************************************************************************************/

    private void createCommentFragment() {
        // Tạo fragment object và truyền vào 2 tham số
        CommentFragment commentFragment = CommentFragment.newInstance(restaurant.getType(), restaurant.getId());
        // Gọi fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fcv_comment, commentFragment)
                .commit();
    }

    private void createRatingFragment() {
        RatingFragment ratingFragment = RatingFragment.newInstance(restaurant.getType(), restaurant.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fcv_rating, ratingFragment)
                .commit();
    }

/***** lEGACY CODE: maybe we can reuse it
    private void getRating() {
        db.collection("restaurants")
                .document(restaurant.getId()).collection("review")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot.size() == 0) {
                                ratingBar.setRating(5f);
                                tvTotalRate.setText("0 đánh giá");
                            } else {
                                float rate = 0;
                                int count = 0;
                                for (DocumentSnapshot document : querySnapshot) {
                                    double rating = document.getDouble("rating");
                                    rate += rating;
                                    count++;
                                }
                                ratingBar.setRating(rate / count);
                                tvTotalRate.setText(String.valueOf(count) + " đánh giá");
                            }

                        }
                    }
                });
    }
*/

}