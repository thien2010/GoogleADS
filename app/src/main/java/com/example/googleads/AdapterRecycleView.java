package com.example.googleads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHoler>{
    Context context;
    List<ItemModel> itemModels;

    public AdapterRecycleView(Context context, List<ItemModel> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        ItemModel itemModel = itemModels.get(position);
        holder.item.setText(itemModel.getTitle());
    }

    @Override
    public int getItemCount() {
        if (itemModels!=null){
            return itemModels.size();
        }
        return 0;
    }

    public class ViewHoler extends RecyclerView.ViewHolder{

        TextView item ;
        RecyclerView recyclerView;
        public ViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.tv_item);
            recyclerView = itemView.findViewById(R.id.rcv_item);
        }
    }
}
