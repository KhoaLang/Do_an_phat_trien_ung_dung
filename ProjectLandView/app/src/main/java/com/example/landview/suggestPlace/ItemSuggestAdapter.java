package com.example.landview.suggestPlace;

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

import com.example.landview.R;
import com.example.landview.TopReviewDetail;

import java.util.List;

public class ItemSuggestAdapter extends RecyclerView.Adapter<ItemSuggestAdapter.ItemSuggestHolder> {
    private Context mcontext;
    private List<ItemSuggest>mlist;

    public ItemSuggestAdapter(Context mcontext, List<ItemSuggest> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ItemSuggestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_suggest_place,parent,false);

        return new ItemSuggestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSuggestHolder holder, int position) {
        ItemSuggest itemSuggest = mlist.get(position);
        holder.background.setImageResource(itemSuggest.getBackground());
        holder.imgTym.setImageResource(itemSuggest.getIcon());
        holder.txtName.setText(itemSuggest.getName());
        holder.txtDes.setText((itemSuggest.getDescription()));
        //sử lí sự kiện
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, TopReviewDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemSuggest",itemSuggest);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlist!=null)
        {
            return mlist.size();
        }
        return 0;
    }

    public class ItemSuggestHolder extends RecyclerView.ViewHolder{
         ImageView background,imgTym;
        TextView txtName,txtDes;
        LinearLayout linearLayout;
        public ItemSuggestHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.imgSuggest);
            imgTym = itemView.findViewById(R.id.iconSuggesttym);
            txtName = itemView.findViewById(R.id.textSuggets);
            txtDes = itemView.findViewById(R.id.desSuggets);
            linearLayout = itemView.findViewById(R.id.layout);
        }
    }
}
