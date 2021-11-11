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

public class ItemResAdapter extends RecyclerView.Adapter<ItemResAdapter.ResViewholder> {
    Context mcontex;
    List<ItemRes>resList;

    public ItemResAdapter(Context mcontex, List<ItemRes> resList) {
        this.mcontex = mcontex;
        this.resList = resList;
    }

    @NonNull
    @Override
    public ResViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resraurant_item,parent,false);
        return new ResViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResViewholder holder, int position) {
        ItemRes itemRes = resList.get(position);
        if(itemRes==null)
        {
            return;
        }
        holder.imgBackground.setImageResource(itemRes.getBackground());
        holder.imgIcon.setImageResource(itemRes.getIcontym());
        holder.txtName.setText(itemRes.getNameRes());
        holder.imgRate.setImageResource(itemRes.getImgrate());
        holder.txtprice.setText(itemRes.getPrice());
    }

    @Override
    public int getItemCount() {
        if(resList!=null)
        {
            return resList.size();
        }
        return 0;
    }

    public class ResViewholder extends RecyclerView.ViewHolder{
        ImageView imgBackground,imgIcon,imgRate;
        TextView txtName,txtprice;
        public ResViewholder(@NonNull View itemView) {
            super(itemView);
            imgBackground = itemView.findViewById(R.id.imgRes);
            imgIcon = itemView.findViewById(R.id.iconRestym);
            txtName = itemView.findViewById(R.id.txtNameRes);
            imgRate = itemView.findViewById(R.id.imgrateRes);
            txtprice = itemView.findViewById(R.id.txtPriceRes);
        }
    }
}
