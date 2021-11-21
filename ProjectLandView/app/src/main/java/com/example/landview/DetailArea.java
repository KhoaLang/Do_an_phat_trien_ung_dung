package com.example.landview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.landview.Area.TopItem;

public class DetailArea extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView txtName,txtDesCription;
    private LinearLayout layout,btnSeemap;
    private Bundle bundle;
    private TopItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_area);
        //ánh xạ view
        initUI();
        //nhận dữ liệu và sét sự kiện từ bundel Top ItemAdapter
        bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }
        item = (TopItem) bundle.get("topItem");
        txtName.setText(item.getName());
        txtDesCription.setText(item.getTextDescription());
        //sử lí sự kiện readmore
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processReadmore();
            }
        });
        //sử lí sự kiện button xem bản đồ
        btnSeemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getmLink()));
                startActivity(intent);
            }
        });
    }

    private void processReadmore() {
        txtDesCription.setMaxLines(txtDesCription.getLineCount());
        txtDesCription.setEllipsize(null);
    }

    private void initUI() {
        viewPager = findViewById(R.id.viewpager);
        txtName = findViewById(R.id.name);
        txtDesCription = findViewById(R.id.description);
        layout = findViewById(R.id.readmore);
        btnSeemap = findViewById(R.id.btnSeeMap);
    }
}