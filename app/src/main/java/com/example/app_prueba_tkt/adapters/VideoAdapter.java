package com.example.app_prueba_tkt.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.R;
import com.example.app_prueba_tkt.entities.Usuario;
import com.example.app_prueba_tkt.entities.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>
{
    public int contLike=0;
    private List<Video> data;

    public VideoAdapter(List<Video>data){ this.data=data;}

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

        String videoPath = video.archivoVideo;
        if (videoPath != null && !videoPath.isEmpty()) {
            vVideo.setVideoPath(videoPath);
        } else {
            // Maneja el caso en que el videoPath es nulo o vacío
            Log.e("VideoAdapter", "El archivo de video es nulo o vacío para el video: " + video.nombreVideo);
        }

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
    public void updateData(List<Video> newVideos) {
        this.data.clear();
        this.data.addAll(newVideos);
        notifyDataSetChanged();
    }
    public List<Video> getCurrentData() {
        return new ArrayList<>(this.data); // Asegúrate de que `data` sea el nombre de tu lista interna
    }
//    public void playVideoAtPosition(int position) {
//        notifyDataSetChanged(); // Detiene los anteriores
//        playVideoAtPosition(position);
//    }
}
