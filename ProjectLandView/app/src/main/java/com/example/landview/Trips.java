package com.example.landview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Trips extends AppCompatActivity  {
    EditText edDes,edDaygo,edDayend;
    Button btnCreatePlan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trips);
        edDes = findViewById(R.id.edtDes);
        edDaygo = findViewById(R.id.edtDateGO);
        edDayend = findViewById(R.id.edtDateEnd);
        btnCreatePlan = findViewById(R.id.buttonCreate);

        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textDes = edDes.getText().toString();
                String textdaygo = edDaygo.getText().toString();
                String textdayend = edDayend.getText().toString();
                
            }
        });
    }
}
