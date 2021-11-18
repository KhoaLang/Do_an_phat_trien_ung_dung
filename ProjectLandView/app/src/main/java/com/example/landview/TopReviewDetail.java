package com.example.landview;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.Hotel.Hotel;
import com.example.landview.Hotel.HotelAdapter;
import com.example.landview.Restaurant.ResAdapter;
import com.example.landview.Restaurant.Restaurant;
import com.example.landview.Topreview.TopItem;
import com.example.landview.suggestPlace.ItemSuggest;

import java.util.ArrayList;
import java.util.List;

public class TopReviewDetail extends AppCompatActivity {
    private ImageView imgDes;
    private TextView name,description;
    private RecyclerView rcvHotel,rcvRestaurant;
    private HotelAdapter hotelAdapter;
    private ResAdapter resAdapter;
    private List<Hotel>hotelList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_top_review_detail);
        //ánh xạ view
        initUI();
        //Nhận và truyền dữ liệu vào
        getDataFromBundel();
        //add data rcv Hotel
        getDataHotel();
        //add data rcv Restaurant
        getDataRestaurant();
    }

    private void getDataRestaurant() {
        resAdapter = new ResAdapter(this,getListRes());
        rcvRestaurant.setAdapter(resAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcvRestaurant.setLayoutManager(linearLayoutManager);
    }

    private List<Restaurant> getListRes() {
        List<Restaurant>list = new ArrayList<>();
        list.add(new Restaurant(R.drawable.nhahang1,R.drawable.tym,R.drawable.dot,"Pizza Full House","$$-$$$","185 lượt đánh giá","Kiểu Ý, Kiểu Mỹ, Pizza, Tốt cho sức khỏe","06 Đào Duy Từ,Hoàn Kiếm,Hà Nội"));
        list.add(new Restaurant(R.drawable.nhahang2,R.drawable.tym,R.drawable.dot,"Hemispheres Steak & Seafood Grill","$$$$","210 lượt đánh giá","Nhà hàng bít tết, Hải sản, Kiểu Âu","Tây Hồ,Hà Nội"));
        list.add(new Restaurant(R.drawable.nhahang3,R.drawable.tym,R.drawable.dot,"Dalcheeni HANOI","$$-$$$$","933 lượt đánh giá","Phù hợp với người ăn chay, Tùy chọn ăn chay, Thịt kiểu Hồi giáo, Tùy chọn đồ ăn không có gluten","Xuân Diệu Tứ Liên,Tây Hồ,Hà Nội"));
        list.add(new Restaurant(R.drawable.nhahang4jpg,R.drawable.tym,R.drawable.dot,"Hidden Gem Coffee","$","380 lượt đánh giá","Tùy chọn ăn chay, Tùy chọn đồ ăn không có gluten","Xuân Diệu Tứ Liên,3B Hàng Tre In The Alley, Hà Nội 100000 Việt Nam"));


        return list;
    }

    private void getDataHotel() {
        hotelAdapter = new HotelAdapter(this,getListHotel());
        rcvHotel.setAdapter(hotelAdapter);
        hotelAdapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcvHotel.setLayoutManager(linearLayoutManager);
    }

    private List<Hotel> getListHotel() {
        List<Hotel>list = new ArrayList<>();
        list.add(new Hotel(R.drawable.khachsan1,R.drawable.tym,R.drawable.stars2,"Little Charm Hanoi Hostel","300.000đ","3024 lượt đánh giá","Nằm trong bán kính 2 phút đi bộ từ Ô Quan Chưởng ở quận Hoàn Kiếm, Little Charm Hanoi Hostel - Homestay cung cấp chỗ nghỉ trang nhã và nhà hàng kiểu Ý ngay trong khuôn viên.","Quận Hoàng Kiếm,Hà Nội"));
        list.add(new Hotel(R.drawable.khachsan2,R.drawable.tym,R.drawable.stars2,"The Chi Novel Hostel ","350.000đ","3000 lượt đánh giá","Tọa lạc tại một vị trí thuận tiện ở trung tâm thành phố Hà Nội, The Chi Novel Hostel có phòng nghỉ gắn máy điều hòa, xe đạp cho khách sử dụng miễn phí, WiFi miễn phí và quầy bar","Quận Hoàng Kiếm,Hà Nội"));
        list.add(new Hotel(R.drawable.khachsan3,R.drawable.tym,R.drawable.stars2,"sạp Hotel by Connek","400.000đ","2024 lượt đánh giá"," Sap Hotel By Connek provides chic and modern rooms with free WiFi in Hanoi. Featuring a 24-hour front desk, the hotel has its own restaurant, tour desk and spa for guests to relax","Quận Hoàng Kiếm,Hà Nội"));
        list.add(new Hotel(R.drawable.khachsan4,R.drawable.tym,R.drawable.stars2,"Blue Hanoi Inn Hotel","320.000đ","3024 lượt đánh giá"," Tọa lạc tại Khu Phố Cổ của thành phố Hà Nội, Blue Hanoi Inn Hotel cung cấp các phòng rộng rãi với TV màn hình phẳng cùng Wi-Fi miễn phí.","Quận Hoàng Kiếm,Hà Nội"));
        return  list;
    }

    private void getDataFromBundel() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }

        else if (bundle.containsKey("topItem"))
        {
            TopItem item = (TopItem) bundle.get("topItem");
            imgDes.setImageResource(item.getBackground());
            name.setText(item.getName());
            description.setText(item.getTextDescription());
        }
        else if(bundle.containsKey("itemSuggest"))
        {
            ItemSuggest item = (ItemSuggest) bundle.get("itemSuggest");
            imgDes.setImageResource(item.getBackground());
            name.setText(item.getName());
            description.setText(item.getDescription());
        }
    }

    private void initUI() {
        imgDes = findViewById(R.id.imgDes);
        name = findViewById(R.id.nameDes);
        description = findViewById(R.id.desDes);
        rcvHotel = findViewById(R.id.rcvhotel);
        rcvRestaurant = findViewById(R.id.rcvRestaurant);
    }
}
