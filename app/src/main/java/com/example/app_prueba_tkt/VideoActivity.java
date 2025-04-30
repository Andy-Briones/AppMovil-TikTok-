package com.example.app_prueba_tkt;

import static com.example.app_prueba_tkt.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RouteListingPreference;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba_tkt.adapters.VideoAdapter;
import com.example.app_prueba_tkt.entities.Video;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    ImageButton imgbtnLike;
    int contadorLike = 0;
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

        List<Video> data = new ArrayList<>();

        data.add(new Video("Video 1", "/storage/emulated/0/Download/13509997_2160_3840_30fps.mp4"));
        data.add(new Video("Video 2", "/storage/emulated/0/Download/13500906_1080_1920_30fps.mp4"));
        data.add(new Video("Video 3", "/storage/emulated/0/Download/71350-538489808_small.mp4"));
        data.add(new Video("Video 4", "/storage/emulated/0/Download/71563-538962838_small.mp4"));
        data.add(new Video("Video 5", "/storage/emulated/0/Download/242729_small.mp4"));


        VideoAdapter videoAdapter = new VideoAdapter(data);
        RecyclerView rvVideos = findViewById(R.id.rvVideo);
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        rvVideos.setAdapter(videoAdapter);

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
                    selectedFragment = new ProfileFragment();
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
}