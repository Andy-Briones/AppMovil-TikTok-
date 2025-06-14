package com.example.app_prueba_tkt.adapters;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Usuario;

import java.util.List;

public class VerSegAdapter extends RecyclerView.Adapter<VerSegAdapter.VerSegAdapterViewHolder>
{
    private List<Usuario> datauser;

    public VerSegAdapter(List<Usuario>datauser){this.datauser=datauser;}
    @NonNull
    @Override
    public VerSegAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seg, parent, false);

        return new VerSegAdapter.VerSegAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerSegAdapterViewHolder holder, int position) {
        Usuario usuario = datauser.get(position);

        TextView titulo = holder.itemView.findViewById(R.id.tvTitulo);
        TextView nombre = holder.itemView.findViewById(R.id.tvArrobaUsuario);

        nombre.setText(usuario.nombreUsuario);
    }

    @Override
    public int getItemCount() {
        return datauser.size();
    }

    public class VerSegAdapterViewHolder extends RecyclerView.ViewHolder {
        public VerSegAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
