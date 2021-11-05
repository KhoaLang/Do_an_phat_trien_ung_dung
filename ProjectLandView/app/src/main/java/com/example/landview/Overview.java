package com.example.landview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Overview extends Fragment {
    private TextView textViewAddress,textViewDescriptions;
    DetailHoguom mdetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview,container,false);

        textViewAddress = view.findViewById(R.id.txtDestination);
        textViewDescriptions = view.findViewById(R.id.textDescription);
        mdetail = (DetailHoguom) getActivity();
//        //
//        Bundle bundle1 = getArguments();
//        textViewAddress.setText(bundle1.getString("NameAddress"));
        textViewAddress.setText(mdetail.getNameAddress());
        return view;
    }}