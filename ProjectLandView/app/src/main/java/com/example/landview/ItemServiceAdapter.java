package com.example.landview;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.List;

public class ItemServiceAdapter extends ArrayAdapter<ItemService> {

    public ItemServiceAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_service_item_home_fragment,parent,false);
        //ánh xạ
        LinearLayout layout = convertView.findViewById(R.id.lineaLayout);
        ImageView image = convertView.findViewById(R.id.imgIconFunction);
        TextView textView = convertView.findViewById(R.id.textFunction);

        ItemService item = getItem(position);

        layout.setBackgroundResource(item.getBg_Resource());
        image.setImageResource(item.getRsID());
        textView.setText(item.getName());

        return convertView;
    }
}
