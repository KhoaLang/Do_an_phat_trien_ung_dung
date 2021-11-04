package com.example.landview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailHoguom extends AppCompatActivity {
    private TabLayout mtablayout;
    private ViewPager2 mviewpager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.layout_review_detail_hoguom);
        //ánh xạ view
        mtablayout = findViewById(R.id.tablayout);
        mviewpager = findViewById(R.id.viewPager2);
        //sét dữ liệu
        viewPagerAdapter = new ViewPagerAdapter(this);
        mviewpager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(mtablayout,mviewpager,(tab, position) ->{
            switch (position)
            {
                case 0:
                    tab.setText("Overview");
                    break;
                case 1:
                    tab.setText("Review");
                    break;
                case 2:
                    tab.setText("Image");
                    break;
                default:tab.setText("Overview");
            }
        }).attach();
    }
}
