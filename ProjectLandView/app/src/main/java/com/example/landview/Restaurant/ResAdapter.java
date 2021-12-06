package com.example.landview.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.Hotel.Hotel;
import com.example.landview.Hotel.HotelAdapter;
import com.example.landview.R;

import java.util.List;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ViewHolder> {
    private Context mcontext;
    private List<Restaurant> reslist;

    public ResAdapter(Context mcontext, List<Restaurant> reslist) {
        this.mcontext = mcontext;
        this.reslist = reslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_restaurant_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant item = reslist.get(position);
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
        if(reslist!=null)
        {
            return reslist.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBackground,imgIcon,imgRate;
        private TextView txtName,txtPrice,txtNumberRate,txtIntro,txtAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.imgRes);
            imgIcon = itemView.findViewById(R.id.iconRestym);
            txtName = itemView.findViewById(R.id.txtNameRes);
            imgRate = itemView.findViewById(R.id.imgrateRes);
            txtPrice = itemView.findViewById(R.id.txtPriceRes);
            txtNumberRate = itemView.findViewById(R.id.txtNumberate);
            txtIntro = itemView.findViewById(R.id.txtIntro);
            txtAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
