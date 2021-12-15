package com.example.landview.HomeFragmentSection.Reviews;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.Area.Area;
import com.example.landview.DetailArea;
import com.example.landview.Hotel.Hotel;
import com.example.landview.HotelDetail;
import com.example.landview.LandScape.Landscape;
import com.example.landview.LandScapeDetail;
import com.example.landview.R;
import com.example.landview.Restaurant.Restaurant;
import com.example.landview.RestaurantDetail;
import com.example.landview.Utils.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//class này là phần search nhưng chưa đổi tên
public class Review extends AppCompatActivity {
    EditText edtSearch;
    ImageView iconSearch;
    ItemReviewAdapter itemReviewAdapter;
    ItemReviewAdapter itemReviewAdapterAfterSearch;
    RecyclerView recyclerView;
    List<ItemReview> list;

    FirebaseFirestore db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_review);
        //ánh xạ view
        edtSearch = findViewById(R.id.edtSearch);
        iconSearch = findViewById(R.id.iconSearch);
        recyclerView = findViewById(R.id.recvSearch);
        list = new ArrayList<>();


        db = FirebaseFirestore.getInstance();

        //đổ dữ liệu vào recycleview
        list = getlistItemReviews();
        itemReviewAdapter = new ItemReviewAdapter(this, list);
        itemReviewAdapter.setItemReviewClick(itemReviewClickListener);
        recyclerView.setAdapter(itemReviewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Hàm bắt các sự kiện search
        searchActivity();

    }
    //Hàm này lấy vài landscape để show trưng
    private List<ItemReview> getlistItemReviews() {
        db.collection("landscapes").whereEqualTo("landscapeName", "vinh ha long").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot dc : task.getResult()){
                        List<String> imagesList = (List<String>) dc.get("images");
                        list.add(new ItemReview(imagesList.get(0), "Vịnh Hạ Long", R.drawable.stars,
                                dc.get("address").toString(), dc.get("type").toString(), dc.get("landscapeName").toString()));
                    }
                }
            }
        });

        db.collection("landscapes").whereEqualTo("landscapeName", "ghenh bang").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot dc : task.getResult()){
                        List<String> imagesList = (List<String>) dc.get("images");
                        list.add(new ItemReview(imagesList.get(0), "Ghềnh Bàng", R.drawable.stars,
                                dc.get("address").toString(), dc.get("type").toString(), dc.get("landscapeName").toString()));
                    }
                }
            }
        });
        db.collection("landscapes").whereEqualTo("landscapeName", "hoang thanh thang long").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot dc : task.getResult()){
                        List<String> imagesList = (List<String>) dc.get("images");
                        list.add(new ItemReview(imagesList.get(0), "Hoàng thành Thăng Long", R.drawable.stars,
                                dc.get("address").toString(), dc.get("type").toString(), dc.get("landscapeName").toString()));
                    }
                }
            }
        });

        db.collection("landscapes").whereEqualTo("landscapeName", "nha hat lon ha noi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot dc : task.getResult()){
                        List<String> imagesList = (List<String>) dc.get("images");
                        list.add(new ItemReview(imagesList.get(0), "Nhà hát lớn Hà Nội",
                                R.drawable.stars, dc.get("address").toString(), dc.get("type").toString(), dc.get("landscapeName").toString()));
                    }
                }
            }
        });
        return list;
    }


    //Hàm này query để show những chỗ liên quan đến phần từ được search
    private void searchActivity(){

        iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                list.addAll(filter(edtSearch.getText().toString()));
                itemReviewAdapter.notifyDataSetChanged();
            }
        });

        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN)){
                    list.clear();
                    list.addAll(filter(edtSearch.getText().toString()));
                    itemReviewAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
    }

    private List<ItemReview> filter(String s){
        String queryText = StringUtils.removeAccent(s.toLowerCase()); //removeAccent để chuyển chuỗi tiếng việt có dấu về không dấu

        //Query bằng condition EQUAL trước, add vào đầu danh sách
        db.collection("landscapes").whereEqualTo("landscapeName", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        if(document.exists()){
                            List<String> imagesList = (List<String>) document.get("images");
                            ItemReview ir = new ItemReview(imagesList.get(0), document.get("name").toString(),
                                    R.drawable.stars, document.get("address").toString(), document.get("type").toString(),
                                    document.get("landscapeName").toString());
                            list.add(ir);
                        }else{}
                    }
                }
            }
        });
        db.collection("areas").whereEqualTo("name", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        if(document.exists()) {
                            List<String> imagesList = (List<String>) document.get("images");
                            ItemReview ir = new ItemReview(imagesList.get(0), document.get("areaName").toString(),
                                    R.drawable.stars, document.get("address").toString(), document.get("type").toString(),
                                    document.get("name").toString());
                            list.add(ir);
                        }else{}
                    }
                }
            }
        });

        //sau đó thì mới dùng condition less than or equal to để các trường hợp không gõ hết tên địa danh
        // thì firebase có thể show ra những trường hợp có liên quan
        db.collection("landscapes").whereLessThanOrEqualTo("landscapeName", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        List<String> imagesList = (List<String>) document.get("images");
                        ItemReview ir = new ItemReview(imagesList.get(0), document.get("name").toString(),
                                R.drawable.stars, document.get("address").toString(), document.get("type").toString(),
                                document.get("landscapeName").toString());
                        if(listItemDuplicateCheck(ir)){ //check duplicate
                            list.add(ir);
                        }else{}
                    }
                }
            }
        });
        db.collection("areas").whereLessThanOrEqualTo("name", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        List<String> imagesList = (List<String>) document.get("images");
                        ItemReview ir = new ItemReview(imagesList.get(0), document.get("areaName").toString(),
                                R.drawable.stars, document.get("address").toString(), document.get("type").toString(),
                                document.get("name").toString());
                        if(listItemDuplicateCheck(ir)){ //check duplicate
                            list.add(ir);
                        }else{}
                    }
                }
            }
        });
        return list;
    }

    //Hàm này sẽ kiểm tra xem trong list có trùng phần tử hay không
    //do thực hiện 2 lần query bằng 2 condition khác nhau cho mỗi Collection
    //trường hợp condition equal to nếu đã thêm vào list thì kiểu gì condition less than or equal to cũng sẽ thêm lại phần tử đó vào list
    private boolean listItemDuplicateCheck(ItemReview itemReview){
        for(ItemReview temp: list){
            if(itemReview.getName().equals(temp.getName()) && itemReview.getImg().equals(temp.getImg())){
                return false;
            }
        }
       return true;
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
                                    Intent intent = new Intent(Review.this, DetailArea.class);
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
                                    Intent intent = new Intent(Review.this, LandScapeDetail.class);
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
                                    Intent intent = new Intent(Review.this, RestaurantDetail.class);
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
                                    Intent intent = new Intent(Review.this, HotelDetail.class);
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
}
