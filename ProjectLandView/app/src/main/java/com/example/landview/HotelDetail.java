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
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landview.Comment.CommentFragment;
import com.example.landview.ExpandableTextView.ExpandableTextView;
import com.example.landview.Hotel.Hotel;
import com.example.landview.Hotel.HotelUtilitiesAdapter;
import com.example.landview.Map.NearbyAndMapFragment;
import com.example.landview.Rating.RatingFragment;
import com.example.landview.chung.SliderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HotelDetail extends AppCompatActivity {

    private static final String TAG = "HotelDetail";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
    private ExpandableTextView etvDescription;
    private TextView tvAddress;
    private RecyclerView utilRecyclerView;

    //Adapter for utilities
    private HotelUtilitiesAdapter utilAdapter;
    private ArrayList<String> utilList;


    private ArrayList<String> createDefaultUtilities(){
        ArrayList<String> defaultUtilities = new ArrayList<>();
        defaultUtilities.add("Máy lạnh");
        defaultUtilities.add("Khung cảnh đẹp");
        defaultUtilities.add("Bữa sáng miễn phí");
        return defaultUtilities;
    }

    private boolean isExpand =false;

    private void initUI() {
        tvImageCount = findViewById(R.id.tv_hotel_image_count);
        tvName = findViewById(R.id.tv_hotel_name);
        ratingBar = findViewById(R.id.rb_hotel);
        tvTotalRate = findViewById(R.id.tv_hotel_total_rate);
        tvPrice =findViewById(R.id.tv_hotel_price);
        etvDescription = findViewById(R.id.etv_hotel_description);
        tvAddress = findViewById(R.id.tv_hotel_address);
        utilRecyclerView = findViewById(R.id.utilitiesRV);

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

        Toolbar myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Hotel");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_white_back_24));

        //ánh xạ view
        initUI();
        getHotelData();

        tvImageCount.setText(1 + "/" + hotel.getImages());
        sliderAdapter = new SliderAdapter(hotel.getImages());
        setUpViewPager2(sliderAdapter);

        etvDescription.setText(getResources().getText(R.string.lorem_ipsum));
        tvName.setText(hotel.getName());
        tvAddress.setText(hotel.getAddress());
        tvPrice.setText(String.valueOf(hotel.getPrice()) + " VNĐ");

        //utilities list
        utilList = new ArrayList<>();
        if(!hotel.getUtilities().isEmpty()){
            utilList = hotel.getUtilities();
        } else {
            utilList = createDefaultUtilities();
        }

        getUtilities();

        getRating();

        // Review

        createCommentFragment();

        createRatingFragment();
        createMapFragment();


    }

    /******   MENU ITEM ***/
    Menu menu;

    private void checkLike(){
        ArrayList<String> likesList = hotel.getLikesList();
        String userId = mAuth.getUid();
        if(likesList.contains(userId)){
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_red_tym_24));
        } else {
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.tym));
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
                Toast.makeText(HotelDetail.this, "Favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(HotelDetail.this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case  android.R.id.home:
                this.finish();
                Toast.makeText(HotelDetail.this, "Home", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return  true;

    }

    private void likeClick(){
        ArrayList<String> likesList = hotel.getLikesList();
        String userId = mAuth.getUid();
        DocumentReference hotelRef = db.collection("hotels").document(hotel.getId());
        DocumentReference userRef = db.collection("users").document(userId);

        if(likesList.contains(userId)){
            hotelRef.update("likesList", FieldValue.arrayRemove(userId));
            userRef.update("likes", FieldValue.arrayRemove(hotelRef));
            likesList.remove(userId);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.tym));
        } else {
            hotelRef.update("likesList", FieldValue.arrayUnion(userId));
            userRef.update("likes", FieldValue.arrayUnion(hotelRef));
            likesList.add(userId);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_red_tym_24));
        }
    }

    private void getUtilities() {
        utilAdapter = new HotelUtilitiesAdapter(HotelDetail.this, utilList);
        utilRecyclerView.setAdapter(utilAdapter);
        utilRecyclerView.setLayoutManager(new LinearLayoutManager(HotelDetail.this, LinearLayoutManager.HORIZONTAL,
                false));
        utilAdapter.notifyDataSetChanged();

    }

    private void createMapFragment(){
        NearbyAndMapFragment nearbyAndMapFragment = NearbyAndMapFragment
                .newInstance(hotel.getLatitude(), hotel.getLongitude());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fcv_map, nearbyAndMapFragment)
                .commit();
    }

    /***************** Xử lý ảnh tự động trượt **********************************************/

    // Setup Viewpager2
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
    /*******************************************************************************************************/


    /********************************* Xử lý lấy rating ****************************************************/

    private void getRating(){
        db.collection("hotels")
                .document(hotel.getId()).collection("review")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                        if(error == null){ // không có lỗi
                            if(querySnapshot.size() == 0){ // nếu ko có review nào thì cho 5 sao
                                ratingBar.setRating(5f);
                                tvTotalRate.setText("0 đánh giá");
                            } else { // Nếu có review
                                float rate = 0; // tổng rating
                                int count =0; // số lượng rating
                                for(DocumentSnapshot document : querySnapshot){
                                    double rating = document.getDouble("rating");
                                    rate += rating;
                                    count ++;
                                }
                                ratingBar.setRating(rate/count); // Tính toán trung bình rating = rate/count
                                tvTotalRate.setText(String.valueOf(count) + " đánh giá");
                            }
                        } else { // Trường hợp có lỗi
                            ratingBar.setRating(5f);
                            tvTotalRate.setText("0 đánh giá");
                        }
                    }
                });
    }
    /**********************************************************************************************/



    private void createCommentFragment(){
        // Tạo fragment object và truyền vào 2 tham số
        CommentFragment commentFragment = CommentFragment.newInstance(hotel.getType(), hotel.getId());
        // Gọi fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fcv_comment, commentFragment)
                .commit();
    }

    private void createRatingFragment() {
        RatingFragment ratingFragment = RatingFragment.newInstance(hotel.getType(), hotel.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fcv_rating, ratingFragment)
                .commit();
    }

  /*  private void gotoListHotel() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.booking.com/budget/city/vn/hanoi.vi.html"));
        startActivity(intent);
    }
*/


}