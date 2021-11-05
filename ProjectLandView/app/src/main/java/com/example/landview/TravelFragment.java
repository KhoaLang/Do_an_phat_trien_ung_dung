package com.example.landview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.landview.Topreview.TopItem;
import com.example.landview.Topreview.TopItemAdapter;
import com.example.landview.suggestPlace.ItemSuggest;
import com.example.landview.suggestPlace.ItemSuggestAdapter;

import java.util.ArrayList;
import java.util.List;

public class TravelFragment extends Fragment {
    ImageView iconNotify,iconsetting,iconSearch;
    EditText edtSerch;
    RecyclerView recTopReview,recSuggest;
    TopItemAdapter topItemAdapter;
    ItemSuggestAdapter itemSuggestAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travel,container,false);
        //ánh xạ view
        iconNotify = view.findViewById(R.id.imgNotifycations);
        iconsetting = view.findViewById(R.id.imgSetting);
        iconSearch = view.findViewById(R.id.inconSearch);
        edtSerch = view.findViewById(R.id.edtSearch);
        recTopReview = view.findViewById(R.id.recvTopreview);
        recSuggest = view.findViewById(R.id.recvSuggets);
        ///sét dữ liệu vào recycleview Top review
        setDataRecyTopReview();
        //sét dữ liệu vào recycleview Suggest Place
        setDataRecySuggestPlace();
        return view;
    }

    private void setDataRecySuggestPlace() {
        itemSuggestAdapter = new ItemSuggestAdapter(this.getContext(),getListSuggest());
        recSuggest.setAdapter(itemSuggestAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recSuggest.setLayoutManager(linearLayoutManager);
    }

    private List<ItemSuggest> getListSuggest() {
        List<ItemSuggest>listSuggest = new ArrayList<>();
        listSuggest.add(new ItemSuggest(R.drawable.halong,R.drawable.tym,"Hạ Long"));
        listSuggest.add(new ItemSuggest(R.drawable.phuquoc,R.drawable.tym,"Phú Quốc"));
        listSuggest.add(new ItemSuggest(R.drawable.bienlonghai,R.drawable.tym,"Biển Long Hải"));
        listSuggest.add(new ItemSuggest(R.drawable.nuibaden,R.drawable.tym,"Núi Bà Đen"));
        listSuggest.add(new ItemSuggest(R.drawable.longan,R.drawable.tym,"Long An"));
        return listSuggest;
    }

    private void setDataRecyTopReview() {
        topItemAdapter = new TopItemAdapter(this.getContext(),getListTop());
        recTopReview.setAdapter(topItemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recTopReview.setLayoutManager(linearLayoutManager);
    }

    private List<TopItem> getListTop() {
        List<TopItem>list = new ArrayList<>();
        list.add(new TopItem(R.drawable.hanoi,R.drawable.tym,"Hà Nội"));
        list.add(new TopItem(R.drawable.dalat_dulich,R.drawable.tym,"Đà Lạt"));
        list.add(new TopItem(R.drawable.vungtau,R.drawable.tym,"Vũng Tàu"));
        list.add(new TopItem(R.drawable.danang,R.drawable.tym,"Đà Nẵng"));
        return list;
    }

}