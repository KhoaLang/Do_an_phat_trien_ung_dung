package com.example.landview.TravelRestaurant;

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

import com.example.landview.HotelResDetail;
import com.example.landview.R;
import com.example.landview.Restaurant.Restaurant;

import java.util.List;

public class TravelRestaurantAdapter extends RecyclerView.Adapter<TravelRestaurantAdapter.ViewHolder>{
    private Context mcontext;
    private List<Restaurant>restaurants;

    public TravelRestaurantAdapter(Context mcontext, List<Restaurant> restaurants) {
        this.mcontext = mcontext;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_restaurant_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant item = restaurants.get(position);
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
                bundle.putSerializable("Restaurant",item);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(restaurants != null)
        {
            return restaurants.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView background,icon,rate;
        private TextView name,price,numberRate,intro,address,link;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.imgRes);
            icon = itemView.findViewById(R.id.iconRestym);
            rate = itemView.findViewById(R.id.imgrateRes);
            name = itemView.findViewById(R.id.txtNameRes);
            price = itemView.findViewById(R.id.txtPriceRes);
            numberRate = itemView.findViewById(R.id.txtNumberate);
            intro = itemView.findViewById(R.id.txtIntro);
            address = itemView.findViewById(R.id.txtAddress);
            link = itemView.findViewById(R.id.txtLink);
            linearLayout = itemView.findViewById(R.id.layout);
        }
    }
}
