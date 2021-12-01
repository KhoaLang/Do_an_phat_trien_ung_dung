package com.example.landview.HomeFragmentSection.Reviews;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class Review extends AppCompatActivity {
    EditText edtSearchReview;
    ImageView iconSearch;
    ItemReviewAdapter itemReviewAdapter;
    RecyclerView recyclerView;

    FirebaseFirestore db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_review);
        //ánh xạ view
        edtSearchReview = findViewById(R.id.edtSearch);
        iconSearch = findViewById(R.id.iconSearch);
        recyclerView = findViewById(R.id.recvSearch);

        db = FirebaseFirestore.getInstance();

        //đổ dữ liệu vào recycleview
        itemReviewAdapter = new ItemReviewAdapter(this,getlistItemReviews());
        recyclerView.setAdapter(itemReviewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private List<ItemReview> getlistItemReviews() {
        List<ItemReview>list = new ArrayList<>();

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
                        list.add(new ItemReview(imagesList.get(0), "Nhà hát lớn Hà Nội", R.drawable.stars, dc.get("address").toString()));
                    }
                }
            }
        });
//        list.add(new ItemReview(R.drawable.hoguom,"Hồ Gươm - Hà Nội",R.drawable.stars,"Phường Hoàn Kiếm,Quận Hà Đông"));
//        list.add(new ItemReview(R.drawable.hoguom,"Hồ Gươm - Hà Nội",R.drawable.stars,"Phường Hoàn Kiếm,Quận Hà Đông"));
//        list.add(new ItemReview(R.drawable.bientrac,"Biển Bãi Trước",R.drawable.stars,"Hạ Long,Tp. Vũng Tàu,Bà Rịa - Vũng Tàu"));
//        list.add(new ItemReview(R.drawable.mangden,"Núi LangBiAng",R.drawable.stars,"Lạc Dương,Đà Lạt"));
//        list.add(new ItemReview(R.drawable.badinh_langbac,"Lăng Bác Hồ",R.drawable.stars,"Điện Bàn,Ba Đình,Hà Nội"));
        return list;

    }
}
