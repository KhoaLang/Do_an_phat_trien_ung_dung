package com.example.landview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home,container,false);
       //ánh xạ view
        viewPager2 = view.findViewById(R.id.viewPager2);
        circleIndicator3 = view.findViewById(R.id.indicator3);
        list = getList();
        sliderAdapter = new SliderAdapter(list);
        viewPager2.setAdapter(sliderAdapter);
        circleIndicator3.setViewPager(viewPager2);
        return view;
    }

    private List<Slider> getList() {
        List<Slider>list= new ArrayList<>();
        list.add(new Slider(R.drawable.badinh_langbac));
        list.add(new Slider(R.drawable.dalat));
        list.add(new Slider(R.drawable.picture_slider));
        return list;
    }
}