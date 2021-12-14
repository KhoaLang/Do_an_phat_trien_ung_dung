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
import android.widget.LinearLayout;

import com.example.landview.HomeFragmentSection.Reviews.ItemReview;
import com.example.landview.HomeFragmentSection.Reviews.ItemReviewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteFragment extends Fragment {
    RecyclerView favoriteRecyclerView;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    LinearLayout emptyList;
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
        list = new ArrayList<>();

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
                                                case "areas":
                                                    ir = new ItemReview(imagesList.get(0), ds.get("areaName").toString(), R.drawable.stars, ds.get("address").toString());
                                                    break;
                                                default:
                                                    ir = new ItemReview(imagesList.get(0), ds.get("name").toString(), R.drawable.stars, ds.get("address").toString());
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
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    private String getCurrentUserId(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        return id;
    }
}