package com.example.landview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HotelDetail extends AppCompatActivity {
    private ImageView imgDes;
    private TextView txtName,txtNamedescription,seemore1,seemore2;
    private Button btnfind;
    private RecyclerView rcvhotel,rcvrestaurant;
    ItemHotelAdapter itemHotelAdapter;
    ItemResAdapter itemResAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        //ánh xạ view
        initUI();
        //đổ dữ liệu vào recycleHotel
        addDataRcvHotel();
        //đổ dữ liệu vào recycleRestaurant
        addDataRcvRes();
        //sét sự kiện cho xem thêm(hotel) và xem thêm (res)
        seemore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoListHotel();
            }
        });
    }

    private void gotoListHotel() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.booking.com/budget/city/vn/hanoi.vi.html"));
        startActivity(intent);
    }

    private void initUI() {
        imgDes = findViewById(R.id.imgDestination);
        txtName = findViewById(R.id.nameDestination);
        txtNamedescription = findViewById(R.id.desHanoi);
        rcvhotel = findViewById(R.id.rcvhotel);
        rcvrestaurant = findViewById(R.id.rcvRestaurant);
        seemore1 = findViewById(R.id.seemore1);
        seemore2 = findViewById(R.id.seemore2);
    }
    private void addDataRcvRes() {
        itemResAdapter = new ItemResAdapter(this,getListRestaurant());
        rcvrestaurant.setAdapter(itemResAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcvrestaurant.setLayoutManager(linearLayoutManager);
    }

    private List<ItemRes> getListRestaurant() {
        List<ItemRes>itemResList = new ArrayList<>();
        itemResList.add(new ItemRes(R.drawable.nhahang1,R.drawable.tym,"Dalcheeni Hanoi",R.drawable.dot_rate,"Kiểu Ấn Độ $$-$$$"));
        itemResList.add(new ItemRes(R.drawable.nhahang2,R.drawable.tym,"Hidden Gem Coffee",R.drawable.dot_rate,"Kiểu Việt $"));
        itemResList.add(new ItemRes(R.drawable.nhahang3,R.drawable.tym,"NYZ Pizza HaNoi",R.drawable.dot_rate,"Kiểu Ý $"));
        itemResList.add(new ItemRes(R.drawable.nhahng4,R.drawable.tym,"Nhà hàng Jackbo",R.drawable.dot_rate,"Quốc tế,Kiểu Á $"));
        return itemResList;
    }

    private void addDataRcvHotel() {
        itemHotelAdapter = new ItemHotelAdapter(this,getListhotel());
        rcvhotel.setAdapter(itemHotelAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcvhotel.setLayoutManager(linearLayoutManager);
    }

    private List<ItemHotel> getListhotel() {
        List<ItemHotel> list = new ArrayList<>();
        list.add(new ItemHotel(R.drawable.khachsan1,R.drawable.tym,"Litte Charm",R.drawable.stars2,"240000đ"));
        list.add(new ItemHotel(R.drawable.khachsan2,R.drawable.tym,"The Chi Novel",R.drawable.stars2,"400000đ"));
        list.add(new ItemHotel(R.drawable.khachsan3,R.drawable.tym,"Sạp Hotel by Connek",R.drawable.stars2,"430000đ"));
        list.add(new ItemHotel(R.drawable.khachsan4,R.drawable.tym,"Blue Hanoi Inn Hotel",R.drawable.stars2,"150000đ"));
        return  list;
    }


}