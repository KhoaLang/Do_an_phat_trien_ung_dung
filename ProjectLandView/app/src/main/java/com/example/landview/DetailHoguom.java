package com.example.landview;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailHoguom extends AppCompatActivity {
    private TabLayout mtablayout;
    private ViewPager2 mviewpager;
    String nameAddress = "";
    ItemReview itemReview;
    private ImageView img;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.layout_review_detail_hoguom);
        //ánh xạ view
        mtablayout = findViewById(R.id.tablayout);
        mviewpager = findViewById(R.id.viewPager2);
        img = findViewById(R.id.imgView);

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

        //sét dữ liệu
        setDataDetail();
        setdatatoFragment();
    }

    private void setDataDetail() {
        viewPagerAdapter = new ViewPagerAdapter(this);
        mviewpager.setAdapter(viewPagerAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle==null)
        {
            return;
        }
        itemReview = (ItemReview) bundle.get("object_ItemReview");
        img.setImageResource(itemReview.getImg());
    }
    private void setdatatoFragment()
    {
        String text = itemReview.getAddress();
        nameAddress = text;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewPager2,new Overview());
        fragmentTransaction.commit();
    }

    public String getNameAddress() {
        return nameAddress;
    }
}
