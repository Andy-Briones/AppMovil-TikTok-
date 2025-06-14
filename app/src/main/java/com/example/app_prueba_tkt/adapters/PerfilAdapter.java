package com.example.app_prueba_tkt.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.HomeFragment;
import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Usuario;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilAdapterViewHolder>
{
    FragmentManager fragmentManager;
    List<Usuario> Listaamigos;
    public PerfilAdapter(List<Usuario>listaamigos, FragmentManager fragmentManager)
    {
        this.Listaamigos = listaamigos;
        this.fragmentManager = fragmentManager;
    }
    @NonNull
    @Override
    public PerfilAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new PerfilAdapter.PerfilAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilAdapterViewHolder holder, int position) {
        Usuario user = Listaamigos.get(position);

        holder.btnAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment fragment = new HomeFragment();
                Bundle ar = new Bundle();
                ar.putString("idUsuario", user.idUsuario);
                fragment.setArguments(ar);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragments, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Listaamigos.size();
    }

    public class PerfilAdapterViewHolder extends RecyclerView.ViewHolder {
        Button btnAmigos;
        public PerfilAdapterViewHolder(@NonNull View itemView) {

            super(itemView);
            btnAmigos = itemView.findViewById(R.id.encontrarAmigos);
        }
    }
}
