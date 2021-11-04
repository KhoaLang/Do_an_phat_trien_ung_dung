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

public class ItemReviewAdapter extends RecyclerView.Adapter<ItemReviewAdapter.ReviewViewholder>{
    Context mcontext;
    List<ItemReview>mlist;

    public ItemReviewAdapter(Context mcontext, List<ItemReview> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ReviewViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review_item,parent,false);
        return new ReviewViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewholder holder, int position) {
        ItemReview itemReview = mlist.get(position);
        if(itemReview==null)
        {
            return;
        }
        holder.imgName.setImageResource(itemReview.getImg());
        holder.name.setText(itemReview.getName());
        holder.imgRate.setImageResource(itemReview.getRateImg());
        holder.nameAddress.setText(itemReview.getAddress());
    }

    @Override
    public int getItemCount() {
        if(mlist!=null)
        {
            return mlist.size();
        }
        return 0;
    }

    public class ReviewViewholder extends RecyclerView.ViewHolder{
        ImageView imgName,imgRate;
        TextView name,nameAddress;
        public ReviewViewholder(@NonNull View itemView) {
            super(itemView);
            imgName = itemView.findViewById(R.id.imgItemReview);
            name = itemView.findViewById(R.id.txtName);
            imgRate = itemView.findViewById(R.id.imgrate);
            nameAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
