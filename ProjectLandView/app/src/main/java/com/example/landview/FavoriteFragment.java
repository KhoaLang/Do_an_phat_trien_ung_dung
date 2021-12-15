package com.example.landview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.landview.Area.Area;
import com.example.landview.HomeFragmentSection.Reviews.ItemReview;
import com.example.landview.HomeFragmentSection.Reviews.ItemReviewAdapter;
import com.example.landview.HomeFragmentSection.Reviews.Review;
import com.example.landview.Hotel.Hotel;
import com.example.landview.LandScape.Landscape;
import com.example.landview.Restaurant.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteFragment extends Fragment {
    RecyclerView favoriteRecyclerView;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    LinearLayout emptyList;
    EditText edt;
    Button goBtn;
    ItemReviewAdapter favAdapter;
    List<ItemReview> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        goBtn = view.findViewById(R.id.goBtn);
        emptyList = view.findViewById(R.id.emptyList);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        favoriteRecyclerView = view.findViewById(R.id.recvFav);
        edt = view.findViewById(R.id.edtSearch);
        list = new ArrayList<>();
        //Chuyển ->
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Review.class);
                getContext().startActivity(intent);
            }
        });
        //kiểm tra user có thích chỗ nào chưa
        db.collection("users").document(getCurrentUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<DocumentReference> group = (List<DocumentReference>) document.get("likes");
                        if(group.size() > 0){ //nếu đã thích bất cứ một chỗ nào rồi thì xóa hình crying_girl đi
                            emptyList.setVisibility(View.INVISIBLE);
                            for (DocumentReference image: group){//truy xuất đến document có trong likes
                                image.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot ds = task.getResult();
                                            List<String> imagesList = (List<String>) ds.get("images");//lấy danh sách hình ảnh ra
                                            ItemReview ir;
                                            switch (ds.get("type").toString()){//chỗ này xài switch case do cấu trúc trong database nó khác
                                                case "area":
                                                    ir = new ItemReview(imagesList.get(0), ds.get("areaName").toString(), R.drawable.stars,
                                                            ds.get("address").toString(), ds.get("type").toString(),
                                                            ds.get("name").toString());
                                                    break;
                                                case "restaurant":
                                                    ir = new ItemReview(imagesList.get(0), ds.get("name").toString(), R.drawable.stars,
                                                            ds.get("address").toString(), ds.get("type").toString(),
                                                            ds.get("restaurantName").toString());
                                                    break;
                                                case "landscape":
                                                    ir = new ItemReview(imagesList.get(0), ds.get("name").toString(), R.drawable.stars,
                                                            ds.get("address").toString(), ds.get("type").toString(),
                                                            ds.get("landscapeName").toString());
                                                    break;
                                                default:
                                                    ir = new ItemReview(imagesList.get(0), ds.get("name").toString(), R.drawable.stars,
                                                            ds.get("address").toString(), ds.get("type").toString(),
                                                            ds.get("name").toString());
                                                    break;
                                            }
                                            list.add(ir);
                                        }
                                    }
                                });
                            }
                        }else{
                            emptyList.setVisibility(View.VISIBLE);
                        }
                    } else {}
                }else{}
            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TravelFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.favoriteFrgmt, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        //setup 1 recycler view cho favorite fragment
        favAdapter = new ItemReviewAdapter(getContext(), list);
        favoriteRecyclerView.setAdapter(favAdapter);
        favAdapter.setItemReviewClick(itemReviewClickListener);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    private ItemReviewAdapter.ItemReviewClick itemReviewClickListener = new ItemReviewAdapter.ItemReviewClick() {
        @Override
        public void itemClick(int position) {
            switch (list.get(position).getType()){
                case "area":
                    List<Area> areaList = new ArrayList<>();
                    db.collection("areas").whereEqualTo("name", list.get(position).getQueryName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot: task.getResult()){
                                    Area area = documentSnapshot.toObject(Area.class);
                                    GeoPoint geoPoint = documentSnapshot.getGeoPoint("geopoint");

                                    area.setLatitude(geoPoint.getLatitude());
                                    area.setLongitude(geoPoint.getLongitude());
                                    areaList.add(area);
                                    Intent intent = new Intent(getContext(), DetailArea.class);
                                    intent.putExtra("area", areaList.get(0));
                                    startActivity(intent);
                                    break;
                                }

                            }
                        }
                    });

                    break;
                case "landscape":
                    List<Landscape> landList = new ArrayList<>();
                    db.collection("landscapes").whereEqualTo("landscapeName", list.get(position).getQueryName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot: task.getResult()){
                                    Landscape landscape = documentSnapshot.toObject(Landscape.class);

                                    GeoPoint geoPoint = documentSnapshot.getGeoPoint("geopoint");
                                    landscape.setLatitude(geoPoint.getLatitude());
                                    landscape.setLongitude(geoPoint.getLongitude());

                                    landList.add(landscape);
                                    Intent intent = new Intent(getContext(), LandScapeDetail.class);
                                    intent.putExtra("landscape", landList.get(0));
                                    startActivity(intent);
                                    break;
                                }

                            }
                        }
                    });
                    break;
                case "restaurant":
                    List<Restaurant> restList = new ArrayList<>();
                    db.collection("restaurants").whereEqualTo("restaurantName", list.get(position).getQueryName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot: task.getResult()){
                                    Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);

                                    GeoPoint geoPoint = documentSnapshot.getGeoPoint("geopoint");
                                    restaurant.setLatitude(geoPoint.getLatitude());
                                    restaurant.setLongitude(geoPoint.getLongitude());

                                    restList.add(restaurant);
                                    Intent intent = new Intent(getContext(), RestaurantDetail.class);
                                    intent.putExtra("restaurant", restList.get(0));
                                    startActivity(intent);
                                    break;
                                }

                            }
                        }
                    });
                    break;
                case "hotel":
                    List<Hotel> hotelList = new ArrayList<>();
                    db.collection("hotels").whereEqualTo("name", list.get(position).getQueryName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot: task.getResult()){
                                    Hotel hotel = documentSnapshot.toObject(Hotel.class);

                                    GeoPoint geoPoint = documentSnapshot.getGeoPoint("geopoint");
                                    hotel.setLatitude(geoPoint.getLatitude());
                                    hotel.setLongitude(geoPoint.getLongitude());

                                    hotelList.add(hotel);
                                    Intent intent = new Intent(getContext(), HotelDetail.class);
                                    intent.putExtra("hotel", hotelList.get(0));
                                    startActivity(intent);
                                    break;
                                }

                            }
                        }
                    });

                    break;
            }

        }
    };

    private String getCurrentUserId(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        return id;
    }
}