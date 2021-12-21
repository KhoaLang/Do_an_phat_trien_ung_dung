package com.example.landview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landview.Area.Area;
import com.example.landview.ExpandableTextView.ExpandableTextView;
import com.example.landview.Hotel.Hotel;
import com.example.landview.Hotel.HotelAdapter;
import com.example.landview.LandScape.Landscape;
import com.example.landview.LandScape.LandscapeAdapter;
import com.example.landview.Map.MapActivity;
import com.example.landview.Place.Place;
import com.example.landview.Restaurant.ResAdapter;
import com.example.landview.Restaurant.Restaurant;
import com.example.landview.chung.SliderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailArea extends AppCompatActivity {
    private static final String TAG = "DetailArea";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ViewPager2 viewPager2;
    private TextView txtName,txtDesCription;
    private ExpandableTextView etvDescription;

    private Handler sliderHandler = new Handler();
    private TextView imagesCountTv;
    private LinearLayout layout,btnSeemap;
    private Bundle bundle;
    private Area area;

    private ArrayList<Place> places;

    private ArrayList<Landscape> landscapes;
    private LandscapeAdapter landscapeAdapter;
    private RecyclerView rcvLandscape;

    private ArrayList<Hotel> hotels;
    private HotelAdapter hotelAdapter;
    private RecyclerView rcvHotel;


    private ArrayList<Restaurant> restaurants;
    private ResAdapter resAdapter;
    private RecyclerView rcvRestaurant;

    private String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_area);

        Toolbar myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Area");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_white_back_24));

        //ánh xạ view
        initUI();


        Intent intent = getIntent();
        area = (Area) intent.getSerializableExtra("area");

        txtName.setText(area.getAreaName());
        etvDescription.setText(area.getDescription());
        imagesCountTv.setText((1 + "/" + area.getImages().size()));
        setUpViewPager2(area.getImages());

        //sử lí sự kiện button xem bản đồ
        btnSeemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(places.size() > 0) {
                    Intent intent = new Intent(DetailArea.this, MapActivity.class);
                    intent.putExtra("places", places);
                    intent.putExtra("isArea", true);
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailArea.this, "Nơi này ko có chỗ để tham quan", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getNearBy();
    }

    private void processReadmore() {

        txtDesCription.setMaxLines(txtDesCription.getLineCount());
        txtDesCription.setEllipsize(null);
    }

    private void initUI() {
        viewPager2 = findViewById(R.id.viewpager2);
        imagesCountTv = findViewById(R.id.images_count_tv);
        txtName = findViewById(R.id.name);
        etvDescription = findViewById(R.id.etv_description);
        btnSeemap = findViewById(R.id.btnSeeMap);
    }

    private void setUpViewPager2(ArrayList<String> images){
        SliderAdapter adapter = new SliderAdapter(images);
        viewPager2.setAdapter(adapter);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                imagesCountTv.setText((position + 1) + "/" + area.getImages().size());
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

    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.action_bar_item, menu);
        checkLike();
        return true;
    }

    private void checkLike(){
        ArrayList<String> likes = area.getLikes();
        String userId = mAuth.getUid();
        if(likes.contains(userId)){
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.red_heart));
        } else {
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.heart_shadow));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite:
                likeClick();
                Toast.makeText(DetailArea.this, "Favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(DetailArea.this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case  android.R.id.home:
                Toast.makeText(DetailArea.this, "Home", Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return  true;

    }

    private void likeClick(){
        ArrayList<String> likesList = area.getLikes();
        String userId = mAuth.getUid();
        DocumentReference hotelRef = db.collection("areas").document(area.getId());
        DocumentReference userRef = db.collection("users").document(userId);

        if(likesList.contains(userId)){
            hotelRef.update("likes", FieldValue.arrayRemove(userId));
            userRef.update("likes", FieldValue.arrayRemove(hotelRef));
            likesList.remove(userId);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.heart_shadow));
        } else {
            hotelRef.update("likes", FieldValue.arrayUnion(userId));
            userRef.update("likes", FieldValue.arrayUnion(hotelRef));
            likesList.add(userId);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.red_heart));
        }
    }


    private void getNearBy(){

        Query query1 = db.collection("landscapes")
                .whereEqualTo("areaId", area.getId());

        Query query2 = db.collection("hotels")
                .whereEqualTo("areaId", area.getId());

        Query query3 = db.collection("restaurants")
                .whereEqualTo("areaId", area.getId());

        List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        tasks.add(query1.get());
        tasks.add(query2.get());
        tasks.add(query3.get());

        restaurants = new ArrayList<>();
        hotels = new ArrayList<>();
        landscapes = new ArrayList<>();
        places = new ArrayList<>();

        // whenAllComo

        Tasks.whenAllComplete(tasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {

            @Override
            public void onComplete(@NonNull Task<List<Task<?>>> t) {

                if(t.isSuccessful()){
                    for(Task<QuerySnapshot> task : tasks){

                        QuerySnapshot querySnapshot = task.getResult();

                        for(DocumentSnapshot document : querySnapshot){
                            String type = document.getString("type");

                            switch (type){
                                case "landscape":
                                    addLandscape(document);
                                    addPlace(document);
                                    break;
                                case "hotel":
                                    addHotel(document);
                                    addPlace(document);
                                    break;
                                case "restaurant":
                                    addRestaurant(document);
                                    addPlace(document);

                            }
                        }
                    }
                    Log.d(TAG, "LandSize: " + landscapes.size());
                    Log.d(TAG, "HotelSize: " + hotels.size());
                    Log.d(TAG, "ResSize: " + restaurants.size());


                }




            }
        });
    }

    private void addRestaurant(DocumentSnapshot document) {
        Restaurant restaurant = document.toObject(Restaurant.class);
        GeoPoint geoPoint = document.getGeoPoint("geopoint");

        restaurant.setLatitude(geoPoint.getLatitude());
        restaurant.setLongitude(geoPoint.getLongitude());

        restaurants.add(restaurant);
        Log.d(TAG, "addRestaurant: " + restaurant.toString());
    }

    private void addHotel(DocumentSnapshot document) {
        Hotel hotel = document.toObject(Hotel.class);
        GeoPoint geoPoint = document.getGeoPoint("geopoint");

        hotel.setLatitude(geoPoint.getLatitude());
        hotel.setLongitude(geoPoint.getLongitude());

        hotels.add(hotel);
        Log.d(TAG, "addHotel: " + hotel.toString());

    }

    private void addLandscape(DocumentSnapshot document){
        Landscape landscape = document.toObject(Landscape.class);
        GeoPoint geoPoint = document.getGeoPoint("geopoint");

        landscape.setLatitude(geoPoint.getLatitude());
        landscape.setLongitude(geoPoint.getLongitude());

        landscapes.add(landscape);
        Log.d(TAG, "addLandscape: " + landscape.toString());
    }

    private void addPlace(DocumentSnapshot document){
        Place place = document.toObject(Place.class);
        GeoPoint geoPoint = document.getGeoPoint("geopoint");

        place.setLatitude(geoPoint.getLatitude());
        place.setLongitude(geoPoint.getLongitude());

        place.setPath(document.getReference().getPath());
        places.add(place);
        Log.d(TAG, "SelectedPlace: " + place.toString());
    }

    private void setUpHotelRCV(){

    }

}