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
        list.add(new TopItem(R.drawable.hanoi,R.drawable.tym,"Hà Nội","Hồ Hoàn Kiếm là hồ nước ngọt nhân tạo nằm giữa thủ đô Hà Nội. Hồ rộng tới 12ha với chiều dài 700m, rộng 200m và sâu hơn 1m. Giữa hồ có tháp rùa, trên hồ có đền Ngọc Sơn,\n" +
                "xung quanh là cầu Thê Húc, tháp Bút và đền Bà Kiệu."));
        list.add(new TopItem(R.drawable.dalat_dulich,R.drawable.tym,"Đà Lạt","Đà Lạt hiện nay là thành phố nổi tiếng về du lịch bậc nhất của Việt Nam. Ai cũng đã từng nghe và biết về thành phố ngàn hoa mộng mơ."));
        list.add(new TopItem(R.drawable.vungtau,R.drawable.tym,"Vũng Tàu","Vũng Tàu là một thành phố thuộc tỉnh Bà Rịa – Vũng Tàu, ở vùng Đông Nam Bộ, Việt Nam. Đây là trung tâm kinh tế, tài chính, văn hóa, du lịch, giao thông - vận tải và giáo dục và là một trong những trung tâm kinh tế của vùng Đông Nam Bộ"));
        list.add(new TopItem(R.drawable.danang,R.drawable.tym,"Đà Nẵng","Đà Nẵng là một thành phố trực thuộc trung ương, nằm trong vùng Duyên hải Nam Trung Bộ Việt Nam, là thành phố trung tâm và lớn nhất khu vực miền Trung - Tây Nguyên."));
        return list;
    }

}