package com.example.nordboxapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import nordboxcad.Evento;
import nordboxcad.NordBoxCADCliente;

public class ListAdapterEvento extends RecyclerView.Adapter<ListAdapterEvento.ViewHolder>{
    public List<Evento> mData;
    private final LayoutInflater mInflater;
    private final Context context;
    static UsuarioStatico usuarioStatico = new UsuarioStatico();
    private static boolean excluido;
    boolean esperaHilo = true;

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
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            //Conectamos con el servidor.
                            NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                            //Guardo los diferentes tipos de ejercicios.
                            nordBoxCAD.desapuntarseEvento(usuarioStatico.getUsuario().getId(),mData.get(position).getIdEvento());
                            esperaHilo = false;
                        }
                    });
                    thread.start();

                    //Bucle de espera de hilo.
                    boolean bucleEspFinHilo = true;
                    while (bucleEspFinHilo) {
                        if (!esperaHilo) {
                            bucleEspFinHilo = false;
                        }
                    }
                } else {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            //Conectamos con el servidor.
                            NordBoxCADCliente nordBoxCAD = new NordBoxCADCliente("10.0.2.2", 30501);

                            //Guardo los diferentes tipos de ejercicios.
                            nordBoxCAD.apuntarseEvento(usuarioStatico.getUsuario().getId(),mData.get(position).getIdEvento());
                            esperaHilo = false;
                        }
                    });
                    thread.start();

                    //Bucle de espera de hilo.
                    boolean bucleEspFinHilo = true;
                    while (bucleEspFinHilo) {
                        if (!esperaHilo) {
                            bucleEspFinHilo = false;
                        }
                    }
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
            boolean apuntarse = true;

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

            if(item.getApuntados().getDeportista1() != null) {
                if (item.getApuntados().getDeportista1().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if(item.getApuntados().getDeportista2() != null){
                if (item.getApuntados().getDeportista2().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if(item.getApuntados().getDeportista3() != null){
                if (item.getApuntados().getDeportista3().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if(item.getApuntados().getDeportista4() != null){
                if (item.getApuntados().getDeportista4().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if(item.getApuntados().getDeportista5() != null){
                if (item.getApuntados().getDeportista5().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if(item.getApuntados().getDeportista6() != null){
                if (item.getApuntados().getDeportista6().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if(item.getApuntados().getDeportista7() != null){
                if (item.getApuntados().getDeportista7().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if(item.getApuntados().getDeportista8() != null){
                if (item.getApuntados().getDeportista8().intValue() == usuarioStatico.getUsuario().getId().intValue()) {
                    btnEventoApuntarse.setText(R.string.borrar);
                    btnEventoApuntarse.setBackgroundColor(-65536);
                    btnEventoApuntarse.setContentDescription("1");
                    apuntarse = false;
                    excluido = true;
                }
            }
            if (apuntarse) {
                if (excluido) {
                    btnEventoApuntarse.setText(R.string.excluido);
                    btnEventoApuntarse.setBackgroundColor(-7761753);
                    btnEventoApuntarse.setContentDescription("3");
                    btnEventoApuntarse.setEnabled(false);
                } else {
                    btnEventoApuntarse.setText(R.string.apuntarse);
                    btnEventoApuntarse.setContentDescription("2");
                }
            }
        }
    }
}
