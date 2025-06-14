package com.example.app_prueba_tkt.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.ProfileFragment;
import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder>
{
    Context context;
    FragmentManager fragmentManager;
    List<Usuario> Listaamigos;
    public HomeAdapter(List<Usuario>listaamigos, FragmentManager fragmentManager, Context context)
    {
        this.Listaamigos = listaamigos;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new HomeAdapter.HomeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        Usuario user = Listaamigos.get(position);

        holder.nombre.setText(user.nombreUsuario);

        String userActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userDestino = user.idUsuario;

        holder.btnseguir.setEnabled(false);
        holder.btnseguir.setText("Cargando...");

        DatabaseReference follow = FirebaseDatabase.getInstance().getReference("Follows")
                        .child(userActual)
                                .child(userDestino);

        follow.get().addOnSuccessListener(data ->{
           if (data.exists())
           {
               holder.btnseguir.setEnabled(false);
               holder.btnseguir.setText("Siguiendo");
           }
           else {
               holder.btnseguir.setEnabled(true);
               holder.btnseguir.setText("Seguir");
           }
           holder.btnseguir.setOnClickListener(v -> {
               FirebaseDatabase.getInstance().getReference().setValue(true)
                       .addOnSuccessListener(vv ->{
                           Toast.makeText(context, "Siguiendo a "+ user.nombreUsuario, Toast.LENGTH_SHORT).show();
                           holder.btnseguir.setEnabled(false);
                           holder.btnseguir.setText("Siguiendo");
                       }).addOnFailureListener(ff ->{
                           Toast.makeText(context, "Error al seguir", Toast.LENGTH_SHORT).show();
                       });
           });
        });
        //Ver perfil de usuario
        holder.itemView.setOnClickListener(v ->  {
            if (v != holder.btnseguir) {
                ProfileFragment fragmento = new ProfileFragment();
                Bundle ar = new Bundle();
                ar.putString("idUsuario", user.idUsuario);
                fragmento.setArguments(ar);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragments, fragmento)
                        .addToBackStack(null)
                        .commit();
            }
        });
//                setValue(true)
//                .addOnSuccessListener(vv ->{
//                    Toast.makeText(context, "Siguiendo a "+ user.nombreUsuario, Toast.LENGTH_SHORT).show();
//                    holder.btnseguir.setEnabled(false);
//                }).addOnFailureListener(ff ->
//                {
//                    Toast.makeText(context, "Error al seguir", Toast.LENGTH_SHORT).show();
//                });

    }

    @Override
    public int getItemCount() {
        return Listaamigos.size();
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        Button btnseguir;
        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombreUsuario);
            btnseguir = itemView.findViewById(R.id.btnseguimiento);
        }
    }
    public void updateList(List<Usuario> nuevo)
    {
        Listaamigos.clear();
        Listaamigos.addAll(nuevo);
        notifyDataSetChanged();
    }
}
