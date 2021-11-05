package com.example.landview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class NewItemAdapter extends RecyclerView.Adapter<NewItemAdapter.NewViewholder> {
    Context context;
    List<NewItem>mlist;

    public NewItemAdapter(Context context, List<NewItem> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public NewViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item,parent,false);
        return new NewViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewholder holder, int position) {
        NewItem item = mlist.get(position);
        if(item==null)
        {
            return;
        }
        holder.image.setImageResource(item.getImageNews());
        holder.text.setText(item.getTextNews());
    }

    @Override
    public int getItemCount() {
        if(mlist!=null)
        {
            return mlist.size();
        }
        return 0;
    }

    public class NewViewholder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        LinearLayout layout;
        public NewViewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgNews);
            text = itemView.findViewById(R.id.textNews);
            layout = itemView.findViewById(R.id.layoutNews);
        }
    }
}