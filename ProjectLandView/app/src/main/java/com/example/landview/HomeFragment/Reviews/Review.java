package com.example.landview.HomeFragment.Reviews;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.R;

import java.util.ArrayList;
import java.util.List;

public class Review extends AppCompatActivity {
    EditText edtSearchReview;
    ImageView iconSearch;
    ItemReviewAdapter itemReviewAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.layout_review);
        //ánh xạ view
        edtSearchReview = findViewById(R.id.edtSearch);
        iconSearch = findViewById(R.id.iconSearch);
        recyclerView = findViewById(R.id.recvSearch);
        //đổ dữ liệu vào recycleview
        itemReviewAdapter = new ItemReviewAdapter(this,getlistItemReviews());
        recyclerView.setAdapter(itemReviewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private List<ItemReview> getlistItemReviews() {
        List<ItemReview>list = new ArrayList<>();
        list.add(new ItemReview(R.drawable.hoguom,"Hồ Gươm - Hà Nội",R.drawable.stars,"Phường Hoàn Kiếm,Quận Hà Đông"));
        list.add(new ItemReview(R.drawable.bientrac,"Biển Bãi Trước",R.drawable.stars,"Hạ Long,Tp. Vũng Tàu,Bà Rịa - Vũng Tàu"));
        list.add(new ItemReview(R.drawable.mangden,"Núi LangBiAng",R.drawable.stars,"Lạc Dương,Đà Lạt"));
        list.add(new ItemReview(R.drawable.badinh_langbac,"Lăng Bác Hồ",R.drawable.stars,"Điện Bàn,Ba Đình,Hà Nội"));
        return list;

    }
}
