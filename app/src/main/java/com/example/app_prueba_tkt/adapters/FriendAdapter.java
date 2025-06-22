package com.example.app_prueba_tkt.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.FriendsFragment;
import com.example.app_prueba_tkt.ProfileFragment;
import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Usuario;
import com.example.app_prueba_tkt.entities.Video_Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendAdapterViewHolder>
{
    private final Context context;
    private final List<Video_Profile> lista;
    public FriendAdapter(Context context, List<Video_Profile>lista)
    {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public FriendAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_friend, parent, false);
        return new FriendAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapterViewHolder holder, int position) {
        Video_Profile video = lista.get(position);
        PlayerView view = holder.itemView.findViewById(R.id.playerView);

        ExoPlayer exoPlayer = new ExoPlayer.Builder(context).build();
        view.setPlayer(exoPlayer);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(video.videoURL));
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);

        //loop
        exoPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);

        //exoPlayer.setPlayWhenReady(true);

        exoPlayer.addListener(new androidx.media3.common.Player.Listener() {
            @Override
            public void onRenderedFirstFrame() {
                holder.cargando.setVisibility(View.GONE);
            }
        });
        DatabaseReference myref = FirebaseDatabase.getInstance()
                .getReference("usuarios").child(video.userId);

        myref.get().addOnSuccessListener(dataSnapshot ->
        {
           if (dataSnapshot.exists())
           {
               String name = dataSnapshot.child("nombreUsuario").getValue(String.class);
               holder.nameU.setText("@"+name);
           }
           else
           {
               holder.nameU.setText("@Error");
           }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class FriendAdapterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar cargando;
        TextView nameU;
        public FriendAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            cargando = itemView.findViewById(R.id.loadingIndicator);
            nameU = itemView.findViewById(R.id.tvNameVideo);
        }
    }
}
