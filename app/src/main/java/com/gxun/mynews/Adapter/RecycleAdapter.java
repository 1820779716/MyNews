package com.gxun.mynews.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gxun.mynews.R;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private List<String> classItems;

    private int lastPosition = -1;

    public RecycleAdapter(List<String> classItems) {
        this.classItems = classItems;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View recycleView;
        TextView className;

        public ViewHolder(View view) {
            super(view);
            recycleView = view;
            className = view.findViewById(R.id.tv_item);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_bar, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.recycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (lastPosition != -1){
                    TextView textView = parent.getChildAt(lastPosition).findViewById(R.id.tv_item);
                    textView.setTextColor(Color.parseColor("#000000"));
                }
                lastPosition = position;
                holder.itemView.setTag(position);
                holder.className.setTextColor(Color.parseColor("#F44336"));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = classItems.get(position);
        holder.className.setText(item);
    }

    @Override
    public int getItemCount() {
        return classItems.size();
    }
}