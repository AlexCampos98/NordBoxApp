package com.example.nordboxapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public List<ListBench> mData;
    private final LayoutInflater mInflater;
    private final Context context;

    public ListAdapter(List<ListBench> itemList, Context context)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @NotNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.lista_elementos_bench, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
    {
        holder.bindData(mData.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, mData.get(position).name, Toast.LENGTH_LONG).show();
            }
        });

        holder.relativeLayout.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, mData.get(position).getId(), 0, "Mostrar Ejemplo");
            }
        });
    }

    public void  setItems(List<ListBench> items) { mData = items; }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iconImage;
        TextView name, city, status;
        RelativeLayout relativeLayout;
        Integer id;

        ViewHolder(View itemView)
        {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImage);
            name = itemView.findViewById(R.id.nombreRc);
            city = itemView.findViewById(R.id.city);
            status = itemView.findViewById(R.id.status);
            relativeLayout = itemView.findViewById(R.id.rlEjercicio);
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

            id = item.getId();
        }
    }
}
