package com.example.landview.Topreview;

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

public class TopItemAdapter extends RecyclerView.Adapter<TopItemAdapter.TopItemViewholder>{
    Context mcontext;
    List<TopItem>topItemList;

    public TopItemAdapter(Context mcontext, List<TopItem> topItemList) {
        this.mcontext = mcontext;
        this.topItemList = topItemList;
    }

    @NonNull
    @Override
    public TopItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_topreview,parent,false);
        return new TopItemViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopItemViewholder holder, int position) {
        TopItem item = topItemList.get(position);
        holder.background.setImageResource(item.getBackground());
        holder.imgTym.setImageResource(item.getIcon());
        holder.txtName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        if(topItemList!=null)
        {
            return topItemList.size();
        }
        return 0;
    }

    public class TopItemViewholder extends RecyclerView.ViewHolder{
        ImageView background,imgTym;
        TextView txtName;
        public TopItemViewholder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.imgTop);
            imgTym = itemView.findViewById(R.id.iconToptym);
            txtName = itemView.findViewById(R.id.textTop);
        }
    }
}
