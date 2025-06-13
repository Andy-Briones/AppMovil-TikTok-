package com.example.app_prueba_tkt.adapters;

import android.content.Context;
import android.content.Intent;
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

import com.example.app_prueba_tkt.FriendsFragment;
import com.example.app_prueba_tkt.ProfileFragment;
import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendAdapterViewHolder>
{
    Context context;
    FragmentManager fragmentManager;
    List<Usuario>Listaamigos;

    public FriendAdapter(List<Usuario>listaamigos, FragmentManager fragmentManager, Context context)
    {
        this.Listaamigos = listaamigos;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new FriendAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapterViewHolder holder, int position) {
        Usuario user = Listaamigos.get(position);

        holder.nombre.setText(user.nombreUsuario);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = new ProfileFragment();
                Bundle ar = new Bundle();
                ar.putString("idUsuario", user.idUsuario);
                fragment.setArguments(ar);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragments, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        holder.btnseguir.setOnClickListener(v -> {
            String userActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String userDestino = user.idUsuario;

            DatabaseReference follow = FirebaseDatabase.getInstance().getReference("Follows");
            follow.child(userActual).child(userDestino).setValue(true)
                    .addOnSuccessListener(vv ->{
                        Toast.makeText(context, "Siguiendo a"+ user.nombreUsuario, Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(ff ->
                    {
                        Toast.makeText(context, "Error al seguir", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return Listaamigos.size();
    }

    public class FriendAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        Button btnseguir;
        public FriendAdapterViewHolder(@NonNull View itemView) {
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
