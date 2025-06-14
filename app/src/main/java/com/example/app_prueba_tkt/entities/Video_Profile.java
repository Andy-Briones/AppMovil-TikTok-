package com.example.app_prueba_tkt.entities;

import com.example.app_prueba_tkt.VideoActivity;

public class Video_Profile {
    public String videoURL;
    public long timestamp;
    public String userId;
    public Video_Profile(){}
    public Video_Profile(String videoURL, long timestamp)
    {
        this.videoURL=videoURL;
        this.timestamp = timestamp;
    }
}
