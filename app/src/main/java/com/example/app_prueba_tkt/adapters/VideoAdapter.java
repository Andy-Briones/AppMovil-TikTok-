package com.example.app_prueba_tkt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>
{
    public int contLike=0;
    private List<Video> data;

    public VideoAdapter(List<Video>data){ this.data=data; }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = data.get(position);

        TextView tvNomVideo =holder.itemView.findViewById(R.id.textView5);
        VideoView vVideo= holder.itemView.findViewById(R.id.vVideo);
        TextView tvContadorLike= holder.itemView.findViewById(R.id.contadorLike);
        ImageButton imgbtnLike = holder.itemView.findViewById(R.id.meGusta);

        tvNomVideo.setText(video.nombreVideo);
        vVideo.setVideoPath(video.archivoVideo);

        vVideo.stopPlayback();
        vVideo.setOnPreparedListener(mp ->
        {
            mp.setLooping(true);
            mp.start();
        });
        contLike = 0;
        imgbtnLike.setOnClickListener(v -> {
            contLike++;
            tvContadorLike.setText(String.valueOf(contLike));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder
    {
        public VideoViewHolder(@NonNull View itemView){super(itemView);}
    }
}
