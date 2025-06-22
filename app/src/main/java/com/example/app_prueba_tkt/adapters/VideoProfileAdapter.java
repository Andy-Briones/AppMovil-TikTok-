package com.example.app_prueba_tkt.adapters;

import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Video;
import com.example.app_prueba_tkt.entities.Video_Profile;

import java.util.List;

public class VideoProfileAdapter extends RecyclerView.Adapter<VideoProfileAdapter.VideoProfileViewHolder>
{
    private List<Video_Profile> data;

    public VideoProfileAdapter(List<Video_Profile>data){ this.data=data;}

    @NonNull
    @Override
    public VideoProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_profile, parent, false);

        return new VideoProfileAdapter.VideoProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoProfileViewHolder holder, int position) {
        Video_Profile videop = data.get(position);

        VideoView vvideo = holder.itemView.findViewById(R.id.videoViewP);
        ProgressBar cargando = holder.itemView.findViewById(R.id.loadingIndicator);
        vvideo.setVideoURI(Uri.parse(videop.videoURL));
        vvideo.seekTo(1);

        String videoPath = videop.videoURL;
        if (videoPath != null && !videoPath.isEmpty()) {
            vvideo.setVideoPath(videoPath);
        } else {
            // Maneja el caso en que el videoPath es nulo o vacío
            Log.e("VideoAdapter", "El archivo de video es nulo o vacío para el video: ");
        }

        vvideo.stopPlayback();
        vvideo.setOnPreparedListener(mp ->
        {
            mp.setLooping(true);
            cargando.setVisibility(View.GONE);
            mp.start();
        });
        vvideo.setOnErrorListener((mp, what, extra)->
        {
            cargando.setVisibility(View.GONE);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VideoProfileViewHolder extends RecyclerView.ViewHolder {
        PlayerView playerView;
        public VideoProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);
        }
    }

}
