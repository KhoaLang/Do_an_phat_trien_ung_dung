package com.example.landview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class UserFragment extends Fragment {
    private ImageView imguser;
    private TextView txtName,txtInformation,txtChange,txtLogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);
        imguser = view.findViewById(R.id.imgUser);
        txtName = view.findViewById(R.id.userName);
        txtInformation = view.findViewById(R.id.inforUser);
        txtChange = view.findViewById(R.id.txtChange);
        txtLogout = view.findViewById(R.id.txtLogout);
        //sét sự kiện ảnh
        imguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });
        return view;
    }

    private void displayDialog() {
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.layout_custom_dialog_user);
        dialog.show();
    }
}