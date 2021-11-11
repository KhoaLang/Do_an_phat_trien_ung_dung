package com.example.landview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemHotelAdapter extends RecyclerView.Adapter<ItemHotelAdapter.HotelViewholder> {
    Context mcontext;
    List<ItemHotel>mlist;

    public ItemHotelAdapter(Context mcontext, List<ItemHotel> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public HotelViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hotel_item,parent,false);
        return new HotelViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewholder holder, int position) {
        ItemHotel itemHotel = mlist.get(position);
        if(itemHotel==null)
        {
            return;
        }
        holder.imgBackground.setImageResource(itemHotel.getBackground());
        holder.imgIcon.setImageResource(itemHotel.getIcontym());
        holder.txtName.setText(itemHotel.getNamehotel());
        holder.imgRate.setImageResource(itemHotel.getImgrate());
        holder.txtprice.setText(itemHotel.getPrice());
    }

    @Override
    public int getItemCount() {
        if(mlist!=null)
        {
            return mlist.size();
        }
        return 0;
    }

    public class HotelViewholder extends RecyclerView.ViewHolder{
        ImageView imgBackground,imgIcon,imgRate;
        TextView txtName,txtprice;
        public HotelViewholder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.imgHotel);
            imgIcon = itemView.findViewById(R.id.imgrateHotel);
            txtName = itemView.findViewById(R.id.txtNamehotel);
            imgRate = itemView.findViewById(R.id.imgrateHotel);
            txtprice = itemView.findViewById(R.id.txtPriceHotel);
        }
    }
}
