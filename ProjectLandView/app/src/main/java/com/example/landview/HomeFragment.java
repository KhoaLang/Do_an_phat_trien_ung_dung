package com.example.landview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {
    private ImageView imgNotify,imgSetting;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    SliderAdapter sliderAdapter;
    List<Slider>list;
    ItemServiceAdapter itemServiceAdapter;
    GridView gridView;
    RecyclerView rcv;
    NewItemAdapter newItemAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home,container,false);
       //ánh xạ view
        viewPager2 = view.findViewById(R.id.viewPager2);
        circleIndicator3 = view.findViewById(R.id.indicator3);
        gridView = view.findViewById(R.id.gridView);
        rcv = view.findViewById(R.id.recycleview);
        //đổ dữ liệu slider
        addSlider();
        //đổ dữ liệu vào gridView
        addListitems();
        //gán sự kiện cho gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        Intent intent = new Intent(getActivity(),Trips.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(),Review.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(),Weather.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getActivity(),Promotion.class);
                        startActivity(intent3);
                        break;
                }
            }
        });

        //đổ dữ liệu vao recycleview
        newItemAdapter = new NewItemAdapter(this.getContext(),getListNews());
        rcv.setAdapter(newItemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        rcv.setLayoutManager(linearLayoutManager);



        return view;
    }

    private List<NewItem> getListNews() {
        List<NewItem>list = new ArrayList<>();
        list.add(new NewItem(R.drawable.hoian,"Thành phố nào ở nước ta duy nhất giáp biển?"));
        list.add(new NewItem(R.drawable.covid,"Covid 19 đã hủy hoại như thế nào?"));
        list.add(new NewItem(R.drawable.danhlam,"Top những địa điểm du lịch hấp dẫn"));
        return list;
    }

    private void addListitems() {
        itemServiceAdapter = new ItemServiceAdapter(this.getContext(),R.layout.layout_service_item_home_fragment);
        itemServiceAdapter.add(new ItemService(R.color.tripColor,R.drawable.icon_trips,"Trips"));
        itemServiceAdapter.add(new ItemService(R.color.reviewColor,R.drawable.icon_rate,"Review"));
        itemServiceAdapter.add(new ItemService(R.color.weatherColor,R.drawable.icon_weather,"Weather"));
        itemServiceAdapter.add(new ItemService(R.color.promotionColor,R.drawable.icon_pro,"Promotions"));
        gridView.setAdapter(itemServiceAdapter);
    }


    private void addSlider() {
        list = getList();
        sliderAdapter = new SliderAdapter(list);
        viewPager2.setAdapter(sliderAdapter);
        circleIndicator3.setViewPager(viewPager2);
    }

    private List<Slider> getList() {
        List<Slider>list= new ArrayList<>();
        list.add(new Slider(R.drawable.badinh_langbac));
        list.add(new Slider(R.drawable.dalat));
        list.add(new Slider(R.drawable.picture_slider));
        return list;
    }
}