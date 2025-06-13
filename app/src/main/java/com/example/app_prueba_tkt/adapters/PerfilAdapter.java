package com.example.app_prueba_tkt.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilAdapterViewHolder>
{

    @NonNull
    @Override
    public PerfilAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PerfilAdapterViewHolder extends RecyclerView.ViewHolder {
        public PerfilAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
