package com.demo.recyclerxulc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





public class FunctionBlockAdapter extends RecyclerView.Adapter<FunctionBlockAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private List<FunctionItem> data = new ArrayList<>();
    private LayoutInflater inflater;


    private Context context;

    public FunctionBlockAdapter(Context context, @NonNull List<FunctionItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (data != null) {
            this.data = data;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.layout_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int index = position;
        FunctionItem fi = data.get(position);
        setImage(fi.imageUrl, holder.iv);
        holder.text.setText(fi.name);
        holder.btn.setImageResource(R.drawable.ic_block_delete);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionItem fi = data.remove(index);
                if (listener != null) {
                    listener.remove(fi);
                }
                notifyDataSetChanged();
            }
        });
    }

    public void setImage(String url, ImageView iv) {
        try {
            int rid = context.getResources().getIdentifier(url,"drawable",context.getPackageName());
            iv.setImageResource(rid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder holder, int fromPosition, int targetPosition) {
        if (fromPosition < data.size() && targetPosition < data.size()) {
            Collections.swap(data, fromPosition, targetPosition);
            notifyItemMoved(fromPosition, targetPosition);
        }
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(0.8f);
        holder.itemView.setScaleY(0.8f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(1.0f);
        holder.itemView.setScaleY(1.0f);
    }

    @Override
    public void onItemDismiss(RecyclerView.ViewHolder holder) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv, btn;
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            text = (TextView) itemView.findViewById(R.id.text);
            btn = (ImageView) itemView.findViewById(R.id.btn);
        }
    }

    public interface OnItemRemoveListener {
        void remove(FunctionItem item);
    }

    private OnItemRemoveListener listener;

    public void setOnItemRemoveListener(OnItemRemoveListener listener) {
        this.listener = listener;
    }
}
