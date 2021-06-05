package com.example.nordboxapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import nordboxcad.Evento;

public class ListAdapterEvento extends RecyclerView.Adapter<ListAdapterEvento.ViewHolder>{
    public List<Evento> mData;
    private final LayoutInflater mInflater;
    private final Context context;
    static UsuarioStatico usuarioStatico = new UsuarioStatico();
//    UsuarioStatico usuarioStatico = new UsuarioStatico();

    public ListAdapterEvento(List<Evento> itemList, Context context)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @NotNull
    @Override
    public ListAdapterEvento.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.lista_elementos_evento, null);
        return new ListAdapterEvento.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterEvento.ViewHolder holder, final int position)
    {
        holder.bindData(mData.get(position));


        holder.btnEventoApuntarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnEventoApuntarse.getContentDescription().equals("1")){
                    //TODO desapuntarse
                } else {
                    //TODO apuntarse
                }
            }
        });
    }

    public void  setItems(List<Evento> items) { mData = items; }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivDeportista1, ivDeportista2, ivDeportista3, ivDeportista4, ivDeportista5,
                ivDeportista6, ivDeportista7, ivDeportista8, ivEntrenador;
        Button btnEventoApuntarse;
        TextView tvEventoNombre;
        ConstraintLayout constraintLayout;

        ViewHolder(View itemView)
        {
            super(itemView);
            tvEventoNombre = itemView.findViewById(R.id.tvEventoNombre);

            ivDeportista1 = itemView.findViewById(R.id.ivDeportista1);
            ivDeportista2 = itemView.findViewById(R.id.ivDeportista2);
            ivDeportista3 = itemView.findViewById(R.id.ivDeportista3);
            ivDeportista4 = itemView.findViewById(R.id.ivDeportista4);
            ivDeportista5 = itemView.findViewById(R.id.ivDeportista5);
            ivDeportista6 = itemView.findViewById(R.id.ivDeportista6);
            ivDeportista7 = itemView.findViewById(R.id.ivDeportista7);
            ivDeportista8 = itemView.findViewById(R.id.ivDeportista8);

            ivEntrenador = itemView.findViewById(R.id.ivEntrenador);

            constraintLayout = itemView.findViewById(R.id.clEvento);

            btnEventoApuntarse = itemView.findViewById(R.id.btnEventoApuntarse);
        }

        void bindData(final Evento item)
        {
            tvEventoNombre.setText(item.getNombre());
            ivEntrenador.setImageResource(R.drawable.nordbox);

            if(item.getApuntados().getDeportista1() != null){
                ivDeportista1.setImageResource(R.drawable.desconocido);
            }
            if(item.getApuntados().getDeportista2() != null){
                ivDeportista2.setImageResource(R.drawable.desconocido);
            }
            if(item.getApuntados().getDeportista3() != null){
                ivDeportista3.setImageResource(R.drawable.desconocido);
            }
            if(item.getApuntados().getDeportista4() != null){
                ivDeportista4.setImageResource(R.drawable.desconocido);
            }
            if(item.getApuntados().getDeportista5() != null){
                ivDeportista5.setImageResource(R.drawable.desconocido);
            }
            if(item.getApuntados().getDeportista6() != null){
                ivDeportista6.setImageResource(R.drawable.desconocido);
            }
            if(item.getApuntados().getDeportista7() != null){
                ivDeportista7.setImageResource(R.drawable.desconocido);
            }
            if(item.getApuntados().getDeportista8() != null){
                ivDeportista8.setImageResource(R.drawable.desconocido);
            }

            constraintLayout.setBackgroundColor(Integer.parseInt(item.getColor()));

            if(item.getApuntados().getDeportista1() == usuarioStatico.getUsuario().getId()) {
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setBackgroundColor(-65536);
                btnEventoApuntarse.setContentDescription("1");
            } else if(item.getApuntados().getDeportista2() == usuarioStatico.getUsuario().getId()){
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setBackgroundColor(-65536);
                btnEventoApuntarse.setContentDescription("1");
            } else if(item.getApuntados().getDeportista3() == usuarioStatico.getUsuario().getId()){
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setBackgroundColor(-65536);
                btnEventoApuntarse.setContentDescription("1");
            } else if(item.getApuntados().getDeportista4() == usuarioStatico.getUsuario().getId()){
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setBackgroundColor(-65536);
                btnEventoApuntarse.setContentDescription("1");
            } else if(item.getApuntados().getDeportista5() == usuarioStatico.getUsuario().getId()){
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setBackgroundColor(-65536);
                btnEventoApuntarse.setContentDescription("1");
            } else if(item.getApuntados().getDeportista6() == usuarioStatico.getUsuario().getId()){
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setContentDescription("1");
                btnEventoApuntarse.setBackgroundColor(-65536);
            } else if(item.getApuntados().getDeportista7() == usuarioStatico.getUsuario().getId()){
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setContentDescription("1");
                btnEventoApuntarse.setBackgroundColor(-65536);
            } else if(item.getApuntados().getDeportista8() == usuarioStatico.getUsuario().getId()){
                btnEventoApuntarse.setText(R.string.borrar);
                btnEventoApuntarse.setContentDescription("1");
                btnEventoApuntarse.setBackgroundColor(-65536);
            } else {
                btnEventoApuntarse.setText(R.string.apuntarse);
                btnEventoApuntarse.setContentDescription("2");
            }
        }
    }
}
