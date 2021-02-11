package com.example.nordboxapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public List<ListBench> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListBench> itemList, Context context)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.lista_elementos_bench, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
    {
        holder.bindData(mData.get(position));
    }

    public void  setItems(List<ListBench> items) { mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iconImage;
        TextView name, city, status;

        ViewHolder(View itemView)
        {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImage);
            name = itemView.findViewById(R.id.nombreRc);
            city = itemView.findViewById(R.id.city);
            status = itemView.findViewById(R.id.status);
        }

        void bindData(final ListBench item)
        {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);

            switch (item.getIcoImagen())
            {
                case "1":
                    iconImage.setImageResource(R.drawable.pecho);
                    break;
                case "2":
                    iconImage.setImageResource(R.drawable.brazo);
                    break;
                case "3":
                    iconImage.setImageResource(R.drawable.pierna);
                    break;
            }

            name.setText(item.getName());
            city.setText(item.getUltimaModificacion());
            status.setText(item.getnEjerciciosCreados());
        }
    }

}
