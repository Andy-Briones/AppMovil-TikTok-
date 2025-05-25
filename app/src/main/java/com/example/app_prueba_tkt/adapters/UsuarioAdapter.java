package com.example.app_prueba_tkt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Usuario;
import com.example.app_prueba_tkt.entities.UsuarioResponse;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>
{
    private List<Usuario> datauser;

    public UsuarioAdapter(List<Usuario>datauser){this.datauser=datauser;}

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_crear_cuenta, parent, false);

        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = datauser.get(position);

        TextView tvnombre = holder.itemView.findViewById(R.id.editTextName);
        TextView tvemail = holder.itemView.findViewById(R.id.editTextEmail);
        TextView tvpassword = holder.itemView.findViewById(R.id.editTextPassword);
        TextView tvbio = holder.itemView.findViewById(R.id.editTextBio);
        TextView tvpais = holder.itemView.findViewById(R.id.editTextCountry);

        tvnombre.setText(usuario.nombreUsuario);
        tvemail.setText(usuario.email);
        tvpassword.setText(usuario.contrasena);
        tvpais.setText(usuario.pais);
        tvbio.setText(usuario.bio);
    }

    @Override
    public int getItemCount() {
        return datauser.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder
    {
        public UsuarioViewHolder(@NonNull View itemView){super(itemView);}
    }
}
