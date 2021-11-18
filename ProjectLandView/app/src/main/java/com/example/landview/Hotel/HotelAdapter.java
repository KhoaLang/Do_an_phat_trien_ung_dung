package com.example.landview.Hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.R;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder>{

    private Context mcontext;
    private List<Hotel>hotelList;

    public HotelAdapter(Context mcontext, List<Hotel> hotelList) {
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
        holder.imgBackground.setImageResource(item.getmBackground());
        holder.imgIcon.setImageResource(item.getmIcon());
        holder.txtName.setText(item.getmName());
        holder.imgRate.setImageResource(item.getmRate());
        holder.txtPrice.setText(item.getmPrice());
        holder.txtNumberRate.setText(item.getNumberRate());
        holder.txtIntro.setText(item.getmIntro());
        holder.txtAddress.setText(item.getmAddress());

    }

    @Override
    public int getItemCount() {
        if(hotelList!=null)
        {
            return hotelList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBackground,imgIcon,imgRate;
        private TextView txtName,txtPrice,txtNumberRate,txtIntro,txtAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.imgHotel);
            imgIcon = itemView.findViewById(R.id.iconHoteltym);
            txtName = itemView.findViewById(R.id.txtNamehotel);
            imgRate = itemView.findViewById(R.id.imgrateHotel);
            txtPrice = itemView.findViewById(R.id.txtPriceHotel);
            txtNumberRate = itemView.findViewById(R.id.txtNumberate);
            txtIntro = itemView.findViewById(R.id.txtIntro);
            txtAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
