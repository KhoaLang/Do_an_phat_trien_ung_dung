package com.example.landview.suggestPlace;

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
        TextView txtName;
        public ItemSuggestHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.imgSuggest);
            imgTym = itemView.findViewById(R.id.iconSuggesttym);
            txtName = itemView.findViewById(R.id.textSuggets);
        }
    }
}
