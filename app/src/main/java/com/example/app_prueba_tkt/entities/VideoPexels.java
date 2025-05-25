package com.example.app_prueba_tkt.entities;

import java.util.List;

public class VideoPexels {
    public int id;
    public List<VideoFile> video_files;

    public static class VideoFile {
        public String quality;
        public String file_type;
        public String link;
    }
}
