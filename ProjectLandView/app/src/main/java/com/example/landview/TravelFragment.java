package com.example.landview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.landview.Area.Area;
import com.example.landview.Area.TopItem;
import com.example.landview.Area.TopItemAdapter;
import com.example.landview.Area.TravelFragmentAreaAdapter;
import com.example.landview.Hotel.Hotel;
import com.example.landview.LandScape.ItemSuggest;
import com.example.landview.LandScape.ItemSuggestAdapter;
import com.example.landview.Restaurant.Restaurant;
import com.example.landview.TravelHotel.TravelHotelAdapter;
import com.example.landview.TravelRestaurant.TravelRestaurantAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class TravelFragment extends Fragment {
    private static final String TAG = "TravelFragment";

    ImageView iconNotify,iconsetting,iconSearch;
    EditText edtSerch;
    RecyclerView recTopReview,recSuggest,recvHotel,recvRestaurant;
    TopItemAdapter topItemAdapter;
    ItemSuggestAdapter itemSuggestAdapter;
    private TravelHotelAdapter travelHotelAdapter;
    private TravelRestaurantAdapter travelRestaurantAdapter;

    private ArrayList<Area> areas;
    private TravelFragmentAreaAdapter areaAdapter;


    // Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
        recvHotel = view.findViewById(R.id.recvTophotel);
        recvRestaurant = view.findViewById(R.id.recvTopRestaurant);
        ///sét dữ liệu vào recycleview Top review
       // setDataRecyTopReview();
        //sét dữ liệu vào recycleview Suggest Place
        setDataRecySuggestPlace();
        //sét dữ liệu vào recycle view Top Hotel
        setDataRecyTopHotel();
        //sét dữ liệu vào recycle view Top Restaurant
        setDataRecyTopRestaurant();


        recTopReview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        areas = new ArrayList<>();

        // Lấy dữ liệu area từ database
        db.collection("areas").limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        // Tạo từng area object
                        Area area = document.toObject(Area.class);
                        GeoPoint geoPoint = document.getGeoPoint("geopoint");
                        area.setLatitude(geoPoint.getLatitude());
                        area.setLongitude(geoPoint.getLongitude());
                        Log.d(TAG, "Area: " + area.toString());
                        areas.add(area);
                    }
                    // bind ArrayList<Area> areas vào adapter
                    areaAdapter = new TravelFragmentAreaAdapter(getContext(), areas);
                    // Set item click listener cho adapter
                    areaAdapter.setRecyclerViewItemClickListener(new TravelFragmentAreaAdapter.AreaInterface() {
                        // click vào cả cái area
                        @Override
                        public void itemClick(int position) {
                            Intent intent = new Intent(getContext(), DetailArea.class);
                            intent.putExtra("area", areas.get(position));
                            startActivity(intent);
                        }
                        // click vào nút like
                        @Override
                        public void likeClick(int position, ImageView view) {
                                checkLike(position, view); //
                        }
                    });
                    recTopReview.setAdapter(areaAdapter);
                } else {

                }
            }
        });

        return view;
    }

    private void checkLike(int position, ImageView view){
        Area area = areas.get(position);
        String userId = mAuth.getCurrentUser().getUid();
        ArrayList<String> likes = area.getLikes();
        DocumentReference areaRef = db.collection("areas").document(area.getId());
        DocumentReference userRef = db.collection("users").document(userId);
        if(likes.contains(userId)){
            likes.remove(userId);
            db.collection("areas").document(area.getId()).update("likes", FieldValue.arrayRemove(userId));
            userRef.update("likes", FieldValue.arrayRemove(areaRef));
        } else if (likes.isEmpty() || !likes.contains(userId)){
            likes.add(userId);
            db.collection("areas").document(area.getId()).update("likes", FieldValue.arrayUnion(userId));
            userRef.update("likes", FieldValue.arrayUnion(areaRef));
        }
        areaAdapter.notifyDataSetChanged();
    }

    private void setDataRecyTopRestaurant() {
        travelRestaurantAdapter = new TravelRestaurantAdapter(this.getContext(),getListRestaurant());
        recvRestaurant.setAdapter(travelRestaurantAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recvRestaurant.setLayoutManager(linearLayoutManager);
    }

    private List<Restaurant> getListRestaurant() {
        List<Restaurant>list = new ArrayList<>();
        list.add(new Restaurant(R.drawable.nhahang1,R.drawable.tym,R.drawable.dot,"Pizza Full House","$$-$$$","185 đánh giá","Phù hợp với người ăn chay, Tùy chọn ăn chay","Số 27 trong số 2.417 Nhà hàng tại Hà Nội","https://www.tripadvisor.com.vn/Restaurant_Review-g293924-d17793352-Reviews-Pizza_Full_House-Hanoi.html"));
        list.add(new Restaurant(R.drawable.nhahang2,R.drawable.tym,R.drawable.dot,"Hemispheres Steak & Seafood Grill","$$$$","210 đánh giá","Tùy chọn đồ ăn không có gluten","Số 35 trong số 2.417 Nhà hàng tại Hà Nội","https://www.tripadvisor.com.vn/Restaurant_Review-g293924-d1537464-Reviews-Hemispheres_Steak_Seafood_Grill-Hanoi.html"));
        list.add(new Restaurant(R.drawable.nhahang3,R.drawable.tym,R.drawable.dot,"Dalcheeni HANOI","933 lượt đánh giá","Phù hợp với người ăn chay, Tùy chọn ăn chay, Thịt kiểu Hồi giáo, Tùy chọn đồ ăn không có gluten","Số 43 trong số 2.417 Nhà hàng tại Hà Nội","https://www.tripadvisor.com.vn/Restaurant_Review-g293924-d8116368-Reviews-Dalcheeni_HANOI-Hanoi.html"));
        list.add(new Restaurant(R.drawable.nhahng4,R.drawable.tym,R.drawable.dot,"Hidden Gem Coffee","$","380 lượt đánh giá","Tùy chọn ăn chay, Tùy chọn đồ ăn không có gluten","Số 4 trong số 493 Cà phê & Trà tại Hà Nội","https://www.tripadvisor.com.vn/Restaurant_Review-g293924-d15273821-Reviews-Hidden_Gem_Cafe_Pub-Hanoi.html"));
        return list;
    }

    private void setDataRecyTopHotel() {
        travelHotelAdapter = new TravelHotelAdapter(this.getContext(),getListHotel());
        recvHotel.setAdapter(travelHotelAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recvHotel.setLayoutManager(linearLayoutManager);
    }

    private List<Hotel> getListHotel() {
        List<Hotel>list = new ArrayList<>();
        list.add(new Hotel(R.drawable.khachsan1,R.drawable.tym,R.drawable.stars2,"Little Charm Hanoi Hostel - Homestay","300.000đ","3024 lượt đánh giá","Nằm trong bán kính 2 phút đi bộ từ Ô Quan Chưởng ở quận Hoàn Kiếm, Little Charm Hanoi Hostel - Homestay cung cấp chỗ nghỉ trang nhã và nhà hàng kiểu Ý ngay trong khuôn viên.","Quận Hoàng Kiếm,Hà Nội","https://www.booking.com/hotel/vn/little-charm-hanoi-hostel.vi.html?label=gen173nr-1FCAMYAij0ATisA0gqWARo9AGIAQGYASq4ARfIAQzYAQHoAQH4AQKIAgGoAgO4At_p5IwGwAIB0gIkY2U0ZTA2MTgtOWY5Ny00MTA3LWE3ZGMtMzYyMWUyY2JiNTA52AIF4AIB;sid=6c7fa7deee361680673c0e9f77011cba;sig=v1GjJjPAEv"));
        list.add(new Hotel(R.drawable.khachsan2,R.drawable.tym,R.drawable.stars2,"The Chi Novel Hostel ","320.000đ","3000 lượt đánh giá"," Tọa lạc tại một vị trí thuận tiện ở trung tâm thành phố Hà Nội, The Chi Novel Hostel có phòng nghỉ gắn máy điều hòa, xe đạp cho khách sử dụng miễn phí, WiFi miễn phí và quầy bar.","Quận Hoàng Kiếm,Hà Nội","https://www.booking.com/hotel/vn/the-chi-novel-hostel.vi.html?label=gen173nr-1FCAMYAij0ATisA0gqWARo9AGIAQGYASq4ARfIAQzYAQHoAQH4AQKIAgGoAgO4At_p5IwGwAIB0gIkY2U0ZTA2MTgtOWY5Ny00MTA3LWE3ZGMtMzYyMWUyY2JiNTA52AIF4AIB;sid=6c7fa7deee361680673c0e9f77011cba;sig=v1GjJjPAEv"));
        list.add(new Hotel(R.drawable.khachsan3,R.drawable.tym,R.drawable.stars2,"Blue Hanoi Inn Hotel  ","400.000đ","2400 lượt đánh giá","Sap Hotel By Connek provides chic and modern rooms with free WiFi in Hanoi. Featuring a 24-hour front desk, the hotel has its own restaurant, tour desk and spa for guests to relax.","https://www.booking.com/hotel/vn/essence-palace.vi.html?label=gen173nr-1FCAMYAij0ATisA0gqWARo9AGIAQGYASq4ARfIAQzYAQHoAQH4AQKIAgGoAgO4At_p5IwGwAIB0gIkY2U0ZTA2MTgtOWY5Ny00MTA3LWE3ZGMtMzYyMWUyY2JiNTA52AIF4AIB;sid=6c7fa7deee361680673c0e9f77011cba;sig=v1GjJjPAEv"));
        list.add(new Hotel(R.drawable.khachsan4,R.drawable.tym,R.drawable.stars2,"Little Charm Hanoi Hostel - Homestay","300.000đ","3024 lượt đánh giá","Tọa lạc tại Khu Phố Cổ của thành phố Hà Nội, Blue Hanoi Inn Hotel cung cấp các phòng rộng rãi với TV màn hình phẳng cùng Wi-Fi miễn phí.","Quận Hoàng Kiếm,Hà Nội","https://www.booking.com/hotel/vn/travelmate-hanoi-hanoi.vi.html?label=gen173nr-1FCAMYAij0ATisA0gqWARo9AGIAQGYASq4ARfIAQzYAQHoAQH4AQKIAgGoAgO4At_p5IwGwAIB0gIkY2U0ZTA2MTgtOWY5Ny00MTA3LWE3ZGMtMzYyMWUyY2JiNTA52AIF4AIB;sid=6c7fa7deee361680673c0e9f77011cba;sig=v1GjJjPAEv"));
        return list;
    }

    private void setDataRecySuggestPlace() {
        itemSuggestAdapter = new ItemSuggestAdapter(this.getContext(),getListSuggest());
        recSuggest.setAdapter(itemSuggestAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recSuggest.setLayoutManager(linearLayoutManager);
    }

    private List<ItemSuggest> getListSuggest() {
        List<ItemSuggest>listSuggest = new ArrayList<>();
        listSuggest.add(new ItemSuggest(R.drawable.halong,R.drawable.tym,"Hạ Long","Thành phố Hạ Long được thành lập ngày 27 tháng 12 năm 1993 trên cơ sở toàn bộ diện tích và dân số của thị xã Hồng Gai cũ[1]. Ngày 10 tháng 10 năm 2013, thành phố được công nhận là đô thị loại."));
        listSuggest.add(new ItemSuggest(R.drawable.phuquoc,R.drawable.tym,"Phú Quốc","Phú Quốc là hòn đảo lớn nhất Việt Nam, nằm trong vịnh Thái Lan. Đảo Phú Quốc cùng với các đảo nhỏ hơn ở lân cận và quần đảo Thổ Chu nằm cách đó 55 hải lý về phía tây nam hợp thành thành phố Phú Quốc thuộc tỉnh Kiên Giang. Đây cũng là thành phố đảo đầu tiên của Việt Nam."));
        listSuggest.add(new ItemSuggest(R.drawable.bienlonghai,R.drawable.tym,"Biển Long Hải","Long Hải là một trong hai thị trấn của huyện Long Điền tỉnh Bà Rịa Vũng Tàu. Long Hải cách thành phố Vũng Tàu khoảng 12km về phía đông bắc và cách thị xã Bà Rịa khoảng 7km về phía đông nam, cách thành phố Hồ Chí Minh khoảng 100Km."));
        listSuggest.add(new ItemSuggest(R.drawable.nuibaden,R.drawable.tym,"Núi Bà Đen","Núi Bà Đen là một ngọn núi nằm trong quần thể di tích văn hóa lịch sử núi Bà Đen, được biết đến bởi phong cảnh hữu tình và nhiều huyền thoại tại Tây Ninh. Theo Gia Định thành thông chí, tên gốc của núi Bà Đen là Bà Dinh."));
        listSuggest.add(new ItemSuggest(R.drawable.longan,R.drawable.tym,"Long An","Long An là một tỉnh thuộc vùng Đồng bằng sông Cửu Long, Việt Nam. Đây là địa phương nằm ở cửa ngõ của vùng Đồng bằng sông Cửu Long, liền kề với Thành phố Hồ Chí Minh."));
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
        list.add(new TopItem(R.drawable.hanoi,R.drawable.tym,"Hà Nội","Hà Nội là thủ đô, thành phố trực thuộc trung ương và cũng là một đô thị loại đặc biệt của Việt Nam. Hà Nội nằm về phía tây bắc của trung tâm vùng đồng bằng châu thổ sông Hồng, với địa hình bao gồm vùng đồng bằng trung tâm và vùng đồi núi ở phía bắc và phía tây thành phố. Với diện tích 3.358,6 km2 và dân số 8,05 triệu người (2019), Hà Nội là thành phố có diện tích lớn nhất Việt Nam, đồng thời cũng là thành phố đông dân thứ hai và có mật độ dân số cao thứ hai trong 63 đơn vị hành chính cấp tỉnh của Việt Nam, nhưng phân bố dân số không đồng đều. Hà Nội có 30 đơn vị hành chính cấp huyện, gồm 12 quận, 17 huyện và 1 thị xã.","https://www.google.com/maps/place/H%C3%A0+N%E1%BB%99i,+Ho%C3%A0n+Ki%E1%BA%BFm,+H%C3%A0+N%E1%BB%99i,+Vi%E1%BB%87t+Nam/@21.0227387,105.8194541,14z/data=!3m1!4b1!4m5!3m4!1s0x3135ab9bd9861ca1:0xe7887f7b72ca17a9!8m2!3d21.0277644!4d105.8341598?hl=vi-VN"));
        list.add(new TopItem(R.drawable.dalat_dulich,R.drawable.tym,"Đà Lạt","Đà Lạt là thành phố tỉnh lỵ của tỉnh Lâm Đồng, nằm trên cao nguyên Lâm Viên, thuộc vùng Tây Nguyên, Việt Nam. Từ xa xưa, vùng đất này vốn là địa bàn cư trú của những cư dân người Lạch, người Chil và người Srê thuộc dân tộc Cơ Ho. Cuối thế kỷ 19, khi tìm kiếm một địa điểm để xây dựng trạm nghỉ dưỡng dành cho người Pháp ở Đông Dương, Toàn quyền Paul Doumer đã quyết định chọn cao nguyên Lâm Viên theo đề nghị của bác sĩ Alexandre Yersin, người từng thám hiểm tới nơi đây vào năm 1893. Trong nửa đầu thế kỷ 20, từ một địa điểm hoang vu, người Pháp đã quy hoạch và xây dựng lên một thành phố xinh đẹp với những biệt thự, công sở, khách sạn và trường học, một trung tâm du lịch và giáo dục của Đông Dương khi đó. Trải qua những khoảng thời gian thăng trầm của hai cuộc chiến tranh cùng giai đoạn khó khăn những thập niên 1970–1980, Đà Lạt ngày nay là một thành phố khá đông dân, đô thị loại I trực thuộc tỉnh, giữ vai trò trung tâm chính trị, kinh tế và văn hóa quan trọng của tỉnh Lâm Đồng và vùng Tây Nguyên, hướng đến là thành phố trực thuộc Trung ương vào năm 2030.","https://www.google.com/maps/place/Tp.+%C4%90%C3%A0+L%E1%BA%A1t,+L%C3%A2m+%C4%90%E1%BB%93ng,+Vi%E1%BB%87t+Nam/@11.9039022,108.3806821,12z/data=!3m1!4b1!4m5!3m4!1s0x317112fef20988b1:0xad5f228b672bf930!8m2!3d11.9404192!4d108.4583132?hl=vi-VN"));
        list.add(new TopItem(R.drawable.vungtau,R.drawable.tym,"Vũng Tàu","Vũng Tàu là một thành phố thuộc tỉnh Bà Rịa – Vũng Tàu, ở vùng Đông Nam Bộ, Việt Nam. Đây là trung tâm kinh tế, tài chính, văn hóa, du lịch, giao thông - vận tải và giáo dục và là một trong những trung tâm kinh tế của vùng Đông Nam Bộ. Sở hữu nhiều bãi biển đẹp và cơ sở hạ tầng được đầu tư hoàn chỉnh, Vũng Tàu là một địa điểm du lịch nổi tiếng tại miền Nam. Ngoài ra, thành phố còn là khu vực hậu cần của ngành công nghiệp dầu khí Việt Nam. Từ ngày 2 tháng 5 năm 2012, tỉnh lỵ của tỉnh Bà Rịa – Vũng Tàu được chuyển đến thành phố Bà Rịa.[5] Thành phố Vũng Tàu được công nhận là đô thị loại I trực thuộc tỉnh đầu tiên của cả Nam Bộ.[2]","https://www.google.com/maps/place/V%C5%A9ng+T%C3%A0u,+B%C3%A0+R%E1%BB%8Ba+-+V%C5%A9ng+T%C3%A0u,+Vi%E1%BB%87t+Nam/@10.403331,107.0530101,12z/data=!3m1!4b1!4m5!3m4!1s0x31756fd4554f0cf5:0xb24fd23bf641fa40!8m2!3d10.4113797!4d107.136224?hl=vi-VN"));
        list.add(new TopItem(R.drawable.danang,R.drawable.tym,"Đà Nẵng","Đà Nẵng là một thành phố trực thuộc trung ương, nằm trong vùng Duyên hải Nam Trung Bộ Việt Nam, là thành phố trung tâm và lớn nhất khu vực miền Trung - Tây Nguyên Thành phố Đà Nẵng là thành phố tổng hợp đa ngành, đa lĩnh vực; trung tâm chính trị - kinh tế - xã hội với vai trò là trung tâm công nghiệp, tài chính, du lịch, dịch vụ, văn hóa, giáo dục - đào tạo, y tế chất lượng cao, khoa học - công nghệ, khởi nghiệp, đổi mới sáng tạo của khu vực Miền Trung - Tây Nguyên và cả nước; trung tâm tổ chức các sự kiện tầm khu vực và quốc tế. Thành phố Đà Nẵng đóng vai trò hạt nhân, quan trọng trong Vùng kinh tế trọng điểm miền Trung, đồng thời cũng là một trong 5 thành phố trực thuộc Trung ương ở Việt Nam, đô thị loại I, trung tâm cấp quốc gia, cùng với Hải Phòng và Cần Thơ.","https://www.google.com/maps/place/%C4%90%C3%A0+N%E1%BA%B5ng,+H%E1%BA%A3i+Ch%C3%A2u,+%C4%90%C3%A0+N%E1%BA%B5ng,+Vi%E1%BB%87t+Nam/@16.0471659,108.1716865,13z/data=!3m1!4b1!4m5!3m4!1s0x314219c792252a13:0xfc14e3a044436487!8m2!3d16.0544068!4d108.2021667?hl=vi-VN"));
        return list;
    }

}