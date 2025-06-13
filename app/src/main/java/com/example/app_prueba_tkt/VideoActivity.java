package com.example.app_prueba_tkt;

import static com.example.app_prueba_tkt.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.adapters.VideoAdapter;
import com.example.app_prueba_tkt.entities.Usuario;
import com.example.app_prueba_tkt.entities.Video;
import com.example.app_prueba_tkt.entities.VideoPexels;
import com.example.app_prueba_tkt.entities.VideoResponsePexels;
import com.example.app_prueba_tkt.entities.Video_Profile;
import com.example.app_prueba_tkt.services.VideosService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoActivity extends AppCompatActivity {

    ImageButton imgbtnLike;
    List<Video> data;
    List<String> videosurl;
    VideoAdapter videoAdapter;
    TextView tvNombre;
    TextView tvdescripcion;
    DatabaseReference databaseReference;
    int contadorLike = 0;
    String usuarioid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_VIDEO}, 1);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        tvNombre = findViewById(R.id.editTextName);
        tvdescripcion = findViewById(id.editTextBio);

        //Storage Firebase
        FirebaseApp.initializeApp(this);
        setContentView(layout.activity_video);


        //Videos dentro del emulador

        data = new ArrayList<>();
        data.add(new Video("Video 1", "/storage/emulated/0/Download/13509997_2160_3840_30fps.mp4"));
        data.add(new Video("Video 2", "/storage/emulated/0/Download/13500906_1080_1920_30fps.mp4"));
        data.add(new Video("Video 3", "/storage/emulated/0/Download/71350-538489808_small.mp4"));
        data.add(new Video("Video 4", "/storage/emulated/0/Download/71563-538962838_small.mp4"));
        data.add(new Video("Video 5", "/storage/emulated/0/Download/242729_small.mp4"));

        // Mezclar el orden de los videos
        Collections.shuffle(data);

        videoAdapter = new VideoAdapter(data);
        RecyclerView rvVideos = findViewById(R.id.rvVideo);
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        rvVideos.setAdapter(videoAdapter);

//        rvVideos.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE)
//                {
//                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                    int PosicionVisible = layoutManager.findFirstCompletelyVisibleItemPosition();
//
//                    if(PosicionVisible != RecyclerView.NO_POSITION)
//                    {
//                        videoAdapter.playVideoAtPosition(PosicionVisible);
//                    }
//                }
//            }
//        });

        videosurl = new ArrayList<>();

        //Aqui esta la api
        SubirArchivo();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pexels.com/v1/").addConverterFactory(GsonConverterFactory.create())
                .build();
        //https://67ff052558f18d7209efd0c8.mockapi.io/Colores

        VideosService service = retrofit.create(VideosService.class);

        Call<VideoResponsePexels> call = service.getPopularVideos("Bearer Csxediy1WLPvsZr06bVVRt64XujcpntZubAEl9UnTKgAgwRJqKy1540A");
        call.enqueue(new Callback<VideoResponsePexels>() {
            @Override
            public void onResponse(Call<VideoResponsePexels> call, Response<VideoResponsePexels> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    List<VideoPexels> videosAPI = response.body().videos;
                    List<Video> nuevosvideos = new ArrayList<>();
                    for (VideoPexels video : videosAPI)
                    {
                        if (video.video_files != null && !video.video_files.isEmpty())
                        {
                            String titVideo ="Video1"+video.id;
                            String urlVideo = video.video_files.get(0).link;
                            nuevosvideos.add(new Video(titVideo, urlVideo));

                        }
                    }
                    videoAdapter.updateData(nuevosvideos);
                    Log.d("API_VIDEOS", "Cantidad de videos recibidos: " + nuevosvideos.size());
                }
            }

            @Override
            public void onFailure(Call<VideoResponsePexels> call, Throwable throwable) {
                Log.d("PixabayAPI", "Error al obtener videos: ");
            }
        });

//        Bundle bundle = new Bundle();
//        bundle.putString("nombre",tvNombre.getText().toString());
//        bundle.putString("bio", tvdescripcion.getText().toString());

        //Vistas de los Fragments
        FrameLayout fragments = findViewById(id.fragments);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inicio) {
                rvVideos.setVisibility(View.VISIBLE);
                fragments.setVisibility(View.GONE);
                return true;
            } else {
                rvVideos.setVisibility(View.GONE);
                fragments.setVisibility(View.VISIBLE);
                Fragment selectedFragment = null;

                if (id == R.id.nav_inicio) {
                    selectedFragment = new HomeFragment();
                } else if (id == R.id.nav_amigos) {
                    selectedFragment = new FriendsFragment();
                } else if (id == R.id.nav_subir) {
                    selectedFragment = new CreateFragment();
                } else if (id == R.id.nav_bandeja) {
                    selectedFragment = new NotificationsFragment();
                } else if (id == R.id.nav_perfil) {
                    //ProfileFragment profileFragment = new ProfileFragment();
                    selectedFragment = new ProfileFragment();
                    //profileFragment.setArguments(bundle);
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragments, selectedFragment)
                            .commit();
                }
                return true;
            }
        });
    }
    public void SubirArchivo()
    {
        DatabaseReference videosRef = FirebaseDatabase.getInstance().getReference("videos");

        videosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Video> listavideosdeFirebase = new ArrayList<>();

                for (DataSnapshot videoSnapshot : snapshot.getChildren()) {
                    String url = videoSnapshot.child("videoURL").getValue(String.class);
                    String titulo = "Video Público";
                    if (url != null) {
                        listavideosdeFirebase.add(new Video(titulo, url));
                    }
                }

                //Publicar los videos al principio
                List<Video> videoscombinados = new ArrayList<>(videoAdapter.getCurrentData());
                videoscombinados.addAll(listavideosdeFirebase);
                videoAdapter.updateData(videoscombinados);
//                for (DataSnapshot videoSnapshot : snapshot.getChildren()) {
//                    Object value = videoSnapshot.getValue();
//
//                    if (value instanceof String) {
//                        String url = (String) value;
//                        listavideosdeFirebase.add(new Video("Video Firebase", url));
//                    } else if (value instanceof Map) {
//                        Map<String, Object> map = (Map<String, Object>) value;
//                        for (Object innerValue : map.values()) {
//                            if (innerValue instanceof String) {
//                                String url = (String) innerValue;
//                                listavideosdeFirebase.add(new Video("Video Firebase", url));
//                            }
//                        }
//                    }
//                }
//                List<Video> videoscombinados = new ArrayList<>(videoAdapter.getCurrentData());
//                videoscombinados.addAll(listavideosdeFirebase);
//                videoAdapter.updateData(videoscombinados);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VideoActivity.this, "Error al cargar videos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}