package com.example.landview.TravelHotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.Hotel.Hotel;
import com.example.landview.HotelResDetail;
import com.example.landview.R;

import java.util.List;

public class TravelHotelAdapter extends RecyclerView.Adapter<TravelHotelAdapter.ViewHolder>{
    private Context mcontext;
    private List<Hotel>hotelList;

    public TravelHotelAdapter(Context mcontext, List<Hotel> hotelList) {
        this.mcontext = mcontext;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hotel,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel item = hotelList.get(position);
        if(item == null)
        {
            return;
        }
        holder.background.setImageResource(item.getmBackground());
        holder.rate.setImageResource(item.getmRate());
        holder.icon.setImageResource(item.getmIcon());
        holder.name.setText(item.getmName());
        holder.price.setText(item.getmPrice());
        holder.numberRate.setText(item.getNumberRate());
        holder.intro.setText(item.getmIntro());
        holder.address.setText(item.getmAddress());
        holder.link.setText(item.getmLink());
        //sét sự kiện
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, HotelResDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Hotel",item);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(hotelList != null)
        {
            return hotelList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView background,icon,rate;
        private TextView name,price,numberRate,intro,address,link;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.imgHotel);
            icon = itemView.findViewById(R.id.iconHoteltym);
            rate = itemView.findViewById(R.id.imgrateHotel);
            name = itemView.findViewById(R.id.txtNamehotel);
            price = itemView.findViewById(R.id.txtPriceHotel);
            numberRate = itemView.findViewById(R.id.txtNumberate);
            intro = itemView.findViewById(R.id.txtIntro);
            address = itemView.findViewById(R.id.txtAddress);
            link = itemView.findViewById(R.id.txtLink);
            linearLayout = itemView.findViewById(R.id.layout);
        }
    }
}
