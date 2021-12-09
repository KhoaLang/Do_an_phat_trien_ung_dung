package com.example.landview.HomeFragmentSection.Reviews;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.landview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        itemReviewAdapter = new ItemReviewAdapter(this,list);
        recyclerView.setAdapter(itemReviewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //adapter thứ 2 sau khi search
        searchActivity();
    }

    //Hàm này lấy vài landscape để show trưng
    private List<ItemReview> getlistItemReviews() {
//        List<ItemReview>list2 = new ArrayList<>();

        db.collection("landscapes").whereEqualTo("landscapeName", "vinh ha long").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot dc : task.getResult()){
                        List<String> imagesList = (List<String>) dc.get("images");
                        list.add(new ItemReview(imagesList.get(0), "Vịnh Hạ Long", R.drawable.stars, dc.get("address").toString()));
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
                        list.add(new ItemReview(imagesList.get(0), "Ghềnh Bàng", R.drawable.stars, dc.get("address").toString()));
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
                        list.add(new ItemReview(imagesList.get(0), "Hoàng thành Thăng Long", R.drawable.stars, dc.get("address").toString()));
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
                                R.drawable.stars, dc.get("address").toString()));
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
//                List<ItemReview> newList = new ArrayList<>();
//                newList = filter(edtSearch.getText().toString());
                list.clear();
                list.addAll(filter(edtSearch.getText().toString()));
                itemReviewAdapter.notifyDataSetChanged();
//                itemReviewAdapter.replaceNewList(newList);
//                itemReviewAdapterAfterSearch = new ItemReviewAdapter(Review.this, list);
//                recyclerView.setAdapter(itemReviewAdapterAfterSearch, false);
            }
        });
    }

    private List<ItemReview> filter(String s){
//        List<ItemReview> list2 = new ArrayList<>();
        String queryText = s.toLowerCase();
//        db.collection("areas").whereLessThanOrEqualTo("name", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document: task.getResult()){
//                        List<String> imagesList = (List<String>) document.get("images");
//                        ItemReview ir = new ItemReview(imagesList.get(0), document.get("areaName").toString(),
//                                R.drawable.stars, document.get("address").toString());
//                        list.add(ir);
//                    }
//                }
//            }
//        });
//        db.collection("hotels").whereLessThanOrEqualTo("name", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document: task.getResult()){
//                        List<String> imagesList = (List<String>) document.get("images");
//                        ItemReview ir = new ItemReview(imagesList.get(0), document.get("name").toString(),
//                                R.drawable.stars, document.get("address").toString());
//                        list.add(ir);
//                    }
//                }
//            }
//        });
        db.collection("landscapes").whereLessThanOrEqualTo("landscapeName", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        List<String> imagesList = (List<String>) document.get("images");
                        ItemReview ir = new ItemReview(imagesList.get(0), document.get("name").toString(),
                                R.drawable.stars, document.get("address").toString());
                        list.add(ir);
                    }
                }
            }
        });
//        db.collection("restaurants").whereLessThanOrEqualTo("restaurantName", queryText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document: task.getResult()){
//                        List<String> imagesList = (List<String>) document.get("images");
//                        ItemReview ir = new ItemReview(imagesList.get(0), document.get("name").toString(),
//                                R.drawable.stars, document.get("address").toString());
//                        list.add(ir);
//                    }
//                }
//            }
//        });
        return list;
    }
}
