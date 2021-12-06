package com.example.landview.Area;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TravelFragmentAreaAdapter extends RecyclerView.Adapter<TravelFragmentAreaAdapter.ViewHolder> {
    private static final String TAG = "FragmentAreaAdapter";
    private ArrayList<Area> areas;
    private Context context;
    private AreaInterface listener;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void setRecyclerViewItemClickListener(AreaInterface listener){
        this.listener = listener;
    }
    public TravelFragmentAreaAdapter(Context context, ArrayList<Area> areas) {
        this.context = context;
        this.areas = areas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_item_topreview, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Area area = areas.get(position);
        Picasso.get().load(area.getImages().get(0)).into(holder.areaIv);
        holder.areaText.setText(area.getAreaName());
        ArrayList<String> likeList = area.getLikes();
        holder.areaLike.setImageResource(R.drawable.tym);
        String userId = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "onBindViewHolder: " + userId);
        if(likeList.contains(userId)){
            holder.areaLike.setImageResource(R.drawable.ic_red_tym_24);
        } else {
            holder.areaLike.setImageResource(R.drawable.tym);
        }
    }

    @Override
    public int getItemCount() {
        if(areas == null) return 0;
        return areas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView areaIv;
        private TextView areaText;
        private ImageView areaLike;
        private AreaInterface listener;
        public ViewHolder(@NonNull View itemView, AreaInterface listener) {
            super(itemView);
            areaIv = itemView.findViewById(R.id.imgTop);
            areaText = itemView.findViewById(R.id.textTop);
            areaLike = itemView.findViewById(R.id.iconToptym);
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClick(getBindingAdapterPosition());
                }
            });

            areaLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.likeClick(getBindingAdapterPosition(), (ImageView) view);
                }
            });
        }
    }

    public interface AreaInterface{
        void itemClick(int position);
        void likeClick(int position, ImageView view);
    }
}
