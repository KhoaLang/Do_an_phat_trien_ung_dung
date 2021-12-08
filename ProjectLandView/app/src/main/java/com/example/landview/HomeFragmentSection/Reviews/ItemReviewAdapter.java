package com.example.landview.HomeFragmentSection.Reviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.HomeFragmentSection.DetailReview.DetailHoguom;
import com.example.landview.R;
import com.squareup.picasso.Picasso;

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
        final ItemReview itemReview = mlist.get(position);
        if(itemReview==null)
        {
            return;
        }
//        holder.imgName.setImageResource(itemReview.getImg());
        Picasso.get().load(itemReview.getImg()).placeholder(R.drawable.destination).into(holder.imgName);
        holder.name.setText(itemReview.getName());
        holder.imgRate.setImageResource(itemReview.getRateImg());
        holder.nameAddress.setText(itemReview.getAddress());
        //sự kiện chuyển sang màn hình điện thoại
        holder.relativeLayout.setOnClickListener(view -> onGotoDetail(itemReview));
    }

    private void onGotoDetail(ItemReview itemReview) {
        Intent intent = new Intent(mcontext,DetailHoguom.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_ItemReview",itemReview);
        intent.putExtras(bundle);
        mcontext.startActivity(intent);
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
        RelativeLayout relativeLayout;
        public ReviewViewholder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            imgName = itemView.findViewById(R.id.imgItemReview);
            name = itemView.findViewById(R.id.txtName);
            imgRate = itemView.findViewById(R.id.imgrate);
            nameAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
